package com.causefinder.schedulecreator.service;

import com.causefinder.schedulecreator.client.RealTimeDataSoapClient;
import com.causefinder.schedulecreator.client.StopDataByRouteAndDirectionSoapClient;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PathFinderService {
    @Autowired
    RealTimeDataSoapClient realTimeDataSoapClient;

    @Autowired
    StopDataByRouteAndDirectionSoapClient stopDataByRouteAndDirection;

    public List<StopData> getJourneyProgressReport(String route, String direction, Integer journeyRef) {
        List<Stops> routeBusStops = stopDataByRouteAndDirection.getStopDataByRouteAndDirection(route, direction);
        return routeBusStops.parallelStream()
                .flatMap(routeBusStop -> realTimeDataSoapClient.getRealTimeData(routeBusStop.getStopId())
                        .stream().filter(stopData -> stopData.getDatedVehicleJourneyRef().equals(journeyRef)))
                .collect(Collectors.toList());
    }

    public Map<Stops, List<StopData>> getCurrentRouteStatusReport(String route, String direction) {
        List<Stops> routeBusStops = stopDataByRouteAndDirection.getStopDataByRouteAndDirection(route, direction);
        return routeBusStops.parallelStream()
                .map(routeBusStop -> {
                    List<StopData> filteredStopData = realTimeDataSoapClient.getRealTimeData(routeBusStop.getStopId())
                            .stream().filter(stopData -> stopData.getRouteId().equalsIgnoreCase(route)
                                    && stopData.getDirection().equalsIgnoreCase(direction)).collect(Collectors.toList());
                    return new AbstractMap.SimpleEntry<Stops, List<StopData>>(routeBusStop, filteredStopData);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public Map<Stops, List<StopData>> findDeltaStatus(Map<Stops, List<StopData>> previous, Map<Stops, List<StopData>> current) {
        return previous.keySet().parallelStream().map(busStop -> {
            List<StopData> prevStatus = previous.get(busStop);
            List<StopData> currStatus = current.get(busStop);
            return new AbstractMap.SimpleEntry<Stops, List<StopData>>(busStop, prevStatus.stream()
                    .filter(prevStatusItem -> !currStatus.contains(prevStatusItem)).collect(Collectors.toList()));
        })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public boolean isLastStop(String route, String direction, Integer stopId) {
        Stops finalStop = stopDataByRouteAndDirection.getStopDataByRouteAndDirection(route, direction).stream().max(Stops::compareTo).get();
        return finalStop.getStopId().equals(stopId);
    }

    public void saveDeltaStatus(Map<Stops, List<StopData>> deltaStatus) {

    }

}
