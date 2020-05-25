package com.causefinder.schedulecreator.soap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StopData {
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_OperatorRef")
    private String operatorId;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_LineRef")
    private String routeId;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_DirectionRef")
    private String direction;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_DestinationRef")
    private Integer destinationId;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_DestinationName")
    private String destinationName;
    @JacksonXmlProperty(localName = "MonitoredStopVisit_MonitoringRef")
    private Integer stopId;
    @JacksonXmlProperty(localName = "FramedVehicleJourneyRef_DataFrameRef")
    private String dataFrameRef;
    @JacksonXmlProperty(localName = "FramedVehicleJourneyRef_DatedVehicleJourneyRef")
    private Integer datedVehicleJourneyRef;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_VehicleRef")
    private Integer vehicleRef;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_BlockRef")
    private Integer blockRef;
    @JacksonXmlProperty(localName = "MonitoredCall_VisitNumber")
    private Integer visitNumber;

    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_InCongestion")
    private boolean inCongestion;
    @JacksonXmlProperty(localName = "MonitoredCall_VehicleAtStop")
    private boolean vehicleAtStop;

    @JacksonXmlProperty(localName = "MonitoredCall_AimedArrivalTime")
    private Date aimedArrivalTime;
    @JacksonXmlProperty(localName = "MonitoredCall_ExpectedArrivalTime")
    private Date expectedArrivalTime;

    @JacksonXmlProperty(localName = "MonitoredCall_AimedDepartureTime")
    private Date aimedDepartureTime;
    @JacksonXmlProperty(localName = "MonitoredCall_ExpectedDepartureTime")
    private Date expectedDepartureTime;

    @JacksonXmlProperty(localName = "LineNote")
    private String lineNote;

    @JacksonXmlProperty(localName = "ServiceDelivery_ResponseTimestamp")
    private Date recordedAtTime;
}
