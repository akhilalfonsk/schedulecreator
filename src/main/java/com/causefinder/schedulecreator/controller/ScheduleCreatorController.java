package com.causefinder.schedulecreator.controller;

import com.causefinder.schedulecreator.client.RealTimeDataSoapClient;
import com.causefinder.schedulecreator.client.StopDataByRouteAndDirectionSoapClient;
import com.causefinder.schedulecreator.scheduling.FlushRouteStatusUpdates;
import com.causefinder.schedulecreator.service.PathFinderService;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.StopEvent;
import com.causefinder.schedulecreator.soap.model.Stops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleCreatorController {

    @Autowired
    RealTimeDataSoapClient realTimeDataSoapClient;

    @Autowired
    StopDataByRouteAndDirectionSoapClient stopDataByRouteAndDirectionSoapClient;

    @Autowired
    PathFinderService pathFinderService;

    @Autowired
    FlushRouteStatusUpdates flushRouteStatusUpdates;


    @RequestMapping(value = "/realtime", method = RequestMethod.GET)
    public List<StopData> viewRealTimeData(@RequestParam Integer busStopId) {
        return realTimeDataSoapClient.getRealTimeData(busStopId);
    }

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public List<Stops> viewStopDataByRouteAndDirection(@RequestParam String route, @RequestParam String direction) {
        return stopDataByRouteAndDirectionSoapClient.getStopDataByRouteAndDirection(route, direction);
    }

    @RequestMapping(value = "/journey", method = RequestMethod.GET)
    public List<StopData> viewJourneyProgressReport(@RequestParam String route, @RequestParam String direction, @RequestParam Integer journeyRef) {
        return pathFinderService.getJourneyProgressReport(route, direction, journeyRef);
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<StopEvent> viewStopEvents() {
        return flushRouteStatusUpdates.viewStopEvents();
    }
}
