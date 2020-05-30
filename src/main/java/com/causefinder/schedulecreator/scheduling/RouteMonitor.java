package com.causefinder.schedulecreator.scheduling;

import com.causefinder.schedulecreator.service.PathFinderService;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class RouteMonitor {
    public static final int MONITOR_FREQUENCY_IN_MIN = 1;
    private static String TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    @Autowired
    PathFinderService pathFinderService;

    @Value("${routemonitor.routes}")
    private List<String> monitoredRoutesIdList;

    private List<Pair<String, String>> monitoredRoutes = new ArrayList<>();
    private Map<Pair, Map<Stops, List<StopData>>> previousRouteStatus = new HashMap<>();

    @Scheduled(initialDelay = MONITOR_FREQUENCY_IN_MIN * 30000, fixedRate = MONITOR_FREQUENCY_IN_MIN * 60000)
    public void syncMonitorRouteInbound() {

        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Status monitoring of routes {} started", monitoredRoutesIdList);
        if (monitoredRoutes.isEmpty()) {
            monitoredRoutesIdList.stream().forEach(route -> {
                monitoredRoutes.add(Pair.with(route, "I"));
                monitoredRoutes.add(Pair.with(route, "O"));
            });
        }
        monitoredRoutes.parallelStream().forEach(this::updateRouteStatusSaveDelta);
        watch.stop();
        log.info("Status monitoring of {} completed successfully.Time Taken:{}", monitoredRoutesIdList, watch.formatTime());
    }

    private void updateRouteStatusSaveDelta(Pair<String, String> monitoredRoute) {
        String route = monitoredRoute.getValue0();
        String direction = monitoredRoute.getValue1();
        String dirStr = "I".equalsIgnoreCase(direction) ? "Inbound" : "Outbound";
        StopWatch watch = new StopWatch();
        watch.start();
        //log.info("Monitoring thread for Route:({},{}) started", route,dirStr);
        Map<Stops, List<StopData>> currentRouteStatus = pathFinderService.getCurrentRouteStatusReport(route, direction);
        if (Objects.nonNull(previousRouteStatus.get(monitoredRoute))) {
            Map<Stops, List<StopData>> deltaStatus = pathFinderService.findDeltaStatus(previousRouteStatus.get(monitoredRoute), currentRouteStatus);
            pathFinderService.poolDeltaStatus(deltaStatus);
        }
        previousRouteStatus.put(monitoredRoute, currentRouteStatus);
        watch.stop();
        log.info("Monitoring thread for Route:({},{}) completed. Time taken:{}", route, dirStr, watch.formatTime());
    }

}