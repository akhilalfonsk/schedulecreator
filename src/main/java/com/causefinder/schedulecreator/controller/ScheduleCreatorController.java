package com.causefinder.schedulecreator.controller;

import com.causefinder.schedulecreator.client.BusDataScrapperClient;
import com.causefinder.schedulecreator.client.RealTimeDataSoapClient;
import com.causefinder.schedulecreator.client.StopDataByRouteAndDirectionSoapClient;
import com.causefinder.schedulecreator.rest.model.RouteResponse;
import com.causefinder.schedulecreator.rest.model.TimeTableResponse;
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
    RealTimeDataSoapClient realTimeDataSoapClient;

    @Autowired
    StopDataByRouteAndDirectionSoapClient stopDataByRouteAndDirectionSoapClient;


    @RequestMapping(value = "/raw/soap/realtime", method = RequestMethod.GET)
    public List<StopData> viewRealTimeData(@RequestParam String busStopId) {
        return realTimeDataSoapClient.getRealTimeData(busStopId);
    }

    @RequestMapping(value = "/raw/soap/route", method = RequestMethod.GET)
    public List<Stops> viewStopDataByRouteAndDirection(@RequestParam String route, @RequestParam String direction) {
        return stopDataByRouteAndDirectionSoapClient.getStopDataByRouteAndDirection(route, direction);
    }

}
