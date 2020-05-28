package com.causefinder.schedulecreator.scheduling;

import com.causefinder.schedulecreator.service.PathFinderService;
import com.causefinder.schedulecreator.soap.model.StopData;
import com.causefinder.schedulecreator.soap.model.Stops;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@Profile("44")
public class RouteMonitor_44 {
    public static final int FREQUENCY_IN_SEC = 240;

    @Autowired
    PathFinderService pathFinderService;

    private Map<Stops, List<StopData>> previousRouteStatusInbound = null;
    private Map<Stops, List<StopData>> previousRouteStatusOutbound = null;

    @Scheduled(initialDelay = FREQUENCY_IN_SEC * 500, fixedRate = FREQUENCY_IN_SEC * 1000)
    public void syncMonitorRouteInbound() {
        log.info("Updating Route 44 stop started-Inbound");
        Map<Stops, List<StopData>> currentRouteStatus = pathFinderService.getCurrentRouteStatusReport("44", "I");
        if (Objects.nonNull(previousRouteStatusInbound)) {
            Map<Stops, List<StopData>> deltaStatus = pathFinderService.findDeltaStatus(previousRouteStatusInbound, currentRouteStatus);
            pathFinderService.saveDeltaStatus(deltaStatus);
        }
        previousRouteStatusInbound = currentRouteStatus;
        log.info("Updating Route 44 ended-Inbound");
    }

    @Scheduled(initialDelay = FREQUENCY_IN_SEC * 1000, fixedRate = FREQUENCY_IN_SEC * 1000)
    public void syncMonitorRouteOutBound() {
        log.info("Updating Route 44 stop started-Outbound");
        Map<Stops, List<StopData>> currentRouteStatus = pathFinderService.getCurrentRouteStatusReport("44", "O");
        log.info("Updating Route 44 ended-Outbound");
    }

    private void updateRouteStatusSaveDelta(String route, String direction) {
        log.info("Updating Route " + route + " stop started-Inbound");
        Map<Stops, List<StopData>> currentRouteStatus = pathFinderService.getCurrentRouteStatusReport("44", "I");
        if (Objects.nonNull(previousRouteStatusInbound)) {
            Map<Stops, List<StopData>> deltaStatus = pathFinderService.findDeltaStatus(previousRouteStatusInbound, currentRouteStatus);
            pathFinderService.saveDeltaStatus(deltaStatus);
        }
        previousRouteStatusInbound = currentRouteStatus;
        log.info("Updating Route 44 ended-Inbound");
    }
}