package com.causefinder.schedulecreator.scheduling;

import com.causefinder.schedulecreator.service.PathFinderService;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class RouteMonitor {
    public static final int MONITOR_FREQUENCY_IN_MIN = 1;
    public List<Pair<String, String>> MONITORED_ROUTE_LIST = Arrays.asList(
            Pair.with("44", "I"),
            Pair.with("44", "O"),
            Pair.with("41", "I"),
            Pair.with("41", "O"),
            Pair.with("42", "I"),
            Pair.with("42", "O"),
            Pair.with("43", "I"),
            Pair.with("43", "O"),
            Pair.with("16", "I"),
            Pair.with("16", "O"),
            Pair.with("25", "I"),
            Pair.with("25", "O")
    );
    @Autowired
    PathFinderService pathFinderService;

    private Map<Pair, Map<Stops, List<StopData>>> previousRouteStatus = new HashMap<>();

    @Scheduled(initialDelay = MONITOR_FREQUENCY_IN_MIN * 30000, fixedRate = MONITOR_FREQUENCY_IN_MIN * 60000)
    public void syncMonitorRouteInbound() {
        log.info("Updating route list started");
        MONITORED_ROUTE_LIST.parallelStream().forEach(this::updateRouteStatusSaveDelta);
        log.info("Updating route list ended");
    }

    private void updateRouteStatusSaveDelta(Pair<String, String> monitoredRoute) {
        String route = monitoredRoute.getValue0();
        String direction = monitoredRoute.getValue1();
        String dirStr = "I".equalsIgnoreCase(direction) ? "Inbound" : "Outbound";
        log.info("Updating {}-Route:{} status started", dirStr, route);
        Map<Stops, List<StopData>> currentRouteStatus = pathFinderService.getCurrentRouteStatusReport(route, direction);
        if (Objects.nonNull(previousRouteStatus.get(monitoredRoute))) {
            Map<Stops, List<StopData>> deltaStatus = pathFinderService.findDeltaStatus(previousRouteStatus.get(monitoredRoute), currentRouteStatus);
            pathFinderService.poolDeltaStatus(deltaStatus);
        }
        previousRouteStatus.put(monitoredRoute, currentRouteStatus);
        log.info("Updating {}-Route:{} status ended", dirStr, route);
    }
}