package com.causefinder.schedulecreator.scheduling;

import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.StopEvent;
import com.causefinder.schedulecreator.soap.model.Stops;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FlushRouteStatusUpdates {
    public static final int DATA_FLUSH_FREQUENCY_IN_MIN = 5;

    private LinkedList<Map<Stops, List<StopData>>> bufferForRouteStatusUpdates = new LinkedList<>();
    private ModelMapper modelMapper = new ModelMapper();

    @Scheduled(initialDelay = DATA_FLUSH_FREQUENCY_IN_MIN * 30000, fixedRate = DATA_FLUSH_FREQUENCY_IN_MIN * 60000)
    public void syncMonitorRouteInbound() {
        log.info("Flushing route updates started");
        List<Map<Stops, List<StopData>>> recentRouteStatusUpdates = new ArrayList<>();
        synchronized (bufferForRouteStatusUpdates) {
            //Collections.copy(recentRouteStatusUpdates, bufferForRouteStatusUpdates);
            // bufferForRouteStatusUpdates.clear();
        }

    }

    public void addDeltaStatusToBuffer(Map<Stops, List<StopData>> item) {
        synchronized (bufferForRouteStatusUpdates) {
            bufferForRouteStatusUpdates.addLast(item);
        }
    }

    public List<StopEvent> viewStopEvents() {
        List<Map<Stops, List<StopData>>> recentRouteStatusUpdates;
        synchronized (bufferForRouteStatusUpdates) {
            recentRouteStatusUpdates = (List<Map<Stops, List<StopData>>>) bufferForRouteStatusUpdates.clone();
        }
        return convertToStopEvents(recentRouteStatusUpdates);
    }

    private List<StopEvent> convertToStopEvents(List<Map<Stops, List<StopData>>> recentRouteStatusUpdates) {
        return recentRouteStatusUpdates.parallelStream()
                .flatMap(chunk -> chunk.entrySet().stream().flatMap(entry ->
                        entry.getValue().stream().map(stopData -> {
                            StopEvent stopEvent = modelMapper.map(stopData, StopEvent.class);
                            modelMapper.map(entry.getKey(), stopEvent);
                            return stopEvent;
                        })
                )).collect(Collectors.toList());
    }
}
