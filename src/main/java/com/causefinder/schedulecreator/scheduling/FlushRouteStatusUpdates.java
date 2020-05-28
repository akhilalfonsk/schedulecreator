package com.causefinder.schedulecreator.scheduling;

import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class FlushRouteStatusUpdates {
    public static final int DATA_FLUSH_FREQUENCY_IN_MIN = 5;

    private LinkedList<Map<Stops, List<StopData>>> bufferForRouteStatusUpdates = new LinkedList<>();

    @Scheduled(initialDelay = DATA_FLUSH_FREQUENCY_IN_MIN * 30000, fixedRate = DATA_FLUSH_FREQUENCY_IN_MIN * 60000)
    public void syncMonitorRouteInbound() {
        log.info("Flushing route updates started");
        List<Map<Stops, List<StopData>>> recentRouteStatusUpdates = new ArrayList<>();
        synchronized (bufferForRouteStatusUpdates) {
            Collections.copy(recentRouteStatusUpdates, bufferForRouteStatusUpdates);
            bufferForRouteStatusUpdates.clear();
        }

    }

    public void addDeltaStatusToBuffer(Map<Stops, List<StopData>> item) {
        synchronized (bufferForRouteStatusUpdates) {
            bufferForRouteStatusUpdates.addLast(item);
        }
    }
}
