package com.causefinder.schedulecreator.controller;

import com.causefinder.schedulecreator.client.BusDataScrapperClient;
import com.causefinder.schedulecreator.client.RealTimeDataSoapClient;
import com.causefinder.schedulecreator.client.StopDataByRouteAndDirectionSoapClient;
import com.causefinder.schedulecreator.external.model.RouteResponse;
import com.causefinder.schedulecreator.external.model.TimeTableResponse;
import com.causefinder.schedulecreator.model.Trip;
import com.causefinder.schedulecreator.model.TripDebug;
import com.causefinder.schedulecreator.service.TripEventService;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import com.google.cloud.bigquery.BigQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleCreatorController {

    @Autowired
    BigQuery config;

    @Autowired
    RealTimeDataSoapClient realTimeDataSoapClient;

    @Autowired
    StopDataByRouteAndDirectionSoapClient stopDataByRouteAndDirectionSoapClient;

    @Autowired
    BusDataScrapperClient restClient;

    @Autowired
    TripEventService tripEventService;

    @RequestMapping(value = "/soap/realtime", method = RequestMethod.GET)
    public List<StopData> viewRealTimeData(@RequestParam String busStopId) {
        return realTimeDataSoapClient.getRealTimeData(busStopId);
    }

    @RequestMapping(value = "/soap/route", method = RequestMethod.GET)
    public List<Stops> viewStopDataByRouteAndDirection(@RequestParam String route, @RequestParam String direction) {
        return stopDataByRouteAndDirectionSoapClient.getStopDataByRouteAndDirection(route,direction);
    }

    @RequestMapping(value = "/raw/route", method = RequestMethod.GET)
    public RouteResponse viewRawRouteData(@RequestParam String operator, @RequestParam String route) {
        return tripEventService.getRoutesWithSchedules(operator, route);
    }

    @RequestMapping(value = "/raw/weeklytimetable", method = RequestMethod.GET)
    public TimeTableResponse viewRawWeeklyBusStopTimeTable(@RequestParam String route, @RequestParam String stopId) {
        return restClient.getWeeklyTimeTableInformation(route, stopId);
    }

    @RequestMapping(value = "/weeklytrips", method = RequestMethod.GET)
    public Map<DayOfWeek, Map<String, PriorityQueue<Trip>>> weeklyTripEvents(@RequestParam String route) {
        return tripEventService.getWeeklyTripEvents(route);
    }

    @RequestMapping(value = "/dailytrips", method = RequestMethod.GET)
    public Map<DayOfWeek, Map<String, PriorityQueue<Trip>>> dailyTripEvents(@RequestParam String route, @RequestParam int dayOfWeek) {
        return tripEventService.getDailyTripEvents(route, DayOfWeek.of(dayOfWeek));
    }

    @RequestMapping(value = "/lite/dailytrips", method = RequestMethod.GET)
    public Map<DayOfWeek, Map<String, List<TripDebug>>> dailyTripEventsLite(@RequestParam String route, @RequestParam int dayOfWeek) {
        return tripEventService.getDailyTripEventsDebug(route, DayOfWeek.of(dayOfWeek));
    }
}
