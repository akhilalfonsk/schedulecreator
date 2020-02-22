package com.causefinder.schedulecreator.controller;

import com.causefinder.schedulecreator.client.BusDataScrapperClient;
import com.causefinder.schedulecreator.client.BusDataScrapperSoapClient;
import com.causefinder.schedulecreator.external.model.RouteResponse;
import com.causefinder.schedulecreator.external.model.TimeTableResponse;
import com.causefinder.schedulecreator.model.Trip;
import com.causefinder.schedulecreator.service.TripEventService;
import com.google.cloud.bigquery.BigQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.PriorityQueue;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleCreatorController {

    @Autowired
    BigQuery config;

    @Autowired
    BusDataScrapperSoapClient soapClient;

    @Autowired
    BusDataScrapperClient restClient;

    @Autowired
    TripEventService tripEventService;

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
}
