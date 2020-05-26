package com.causefinder.schedulecreator.service;

import com.causefinder.schedulecreator.client.RealTimeDataSoapClient;
import com.causefinder.schedulecreator.client.StopDataByRouteAndDirectionSoapClient;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
