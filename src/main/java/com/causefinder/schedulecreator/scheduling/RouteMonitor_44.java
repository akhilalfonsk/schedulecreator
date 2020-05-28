package com.causefinder.schedulecreator.scheduling;

import com.causefinder.schedulecreator.service.PathFinderService;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@Profile("44")
public class RouteMonitor_44 {
    public static final int MONITOR_FREQUENCY_IN_MIN = 3;
    public List<Pair<String, String>> MONITORED_ROUTE_LIST = Arrays.asList(
            Pair.with("44", "I"),
            Pair.with("44", "O")
    );
    @Autowired
    PathFinderService pathFinderService;

    private Map<Stops, List<StopData>> previousRouteStatusInbound = null;
    private Map<Stops, List<StopData>> previousRouteStatusOutbound = null;

    @Scheduled(initialDelay = MONITOR_FREQUENCY_IN_MIN * 30000, fixedRate = MONITOR_FREQUENCY_IN_MIN * 60000)
    public void syncMonitorRouteInbound() {
        log.info("Updating route list started");
        MONITORED_ROUTE_LIST.parallelStream().forEach(monitoredRoute ->
                updateRouteStatusSaveDelta(monitoredRoute.getValue0(), monitoredRoute.getValue1()));
        log.info("Updating route list ended");
    }

    private void updateRouteStatusSaveDelta(String route, String direction) {
        String dirStr = "I".equalsIgnoreCase(direction) ? "Inbound" : "Outbound";
        log.info("Updating {}-Route:{} status started", dirStr, route);
        Map<Stops, List<StopData>> currentRouteStatus = pathFinderService.getCurrentRouteStatusReport(route, direction);
        if (Objects.nonNull(previousRouteStatusInbound)) {
            Map<Stops, List<StopData>> deltaStatus = pathFinderService.findDeltaStatus(previousRouteStatusInbound, currentRouteStatus);
            pathFinderService.poolDeltaStatus(deltaStatus);
        }
        previousRouteStatusInbound = currentRouteStatus;
        log.info("Updating {}-Route:{} status ended", dirStr, route);
    }
}