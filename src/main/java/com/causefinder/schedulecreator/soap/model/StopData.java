package com.causefinder.schedulecreator.soap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StopData {
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_OperatorRef")
    private String MonitoredVehicleJourney_OperatorRef;
    @JacksonXmlProperty(localName = "MonitoredStopVisit_MonitoringRef")
    private String MonitoredStopVisit_MonitoringRef;
    @JacksonXmlProperty(localName = "FramedVehicleJourneyRef_DataFrameRef")
    private String FramedVehicleJourneyRef_DataFrameRef;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_PublishedLineName")
    private String MonitoredVehicleJourney_PublishedLineName;
    @JacksonXmlProperty(localName = "ServiceDelivery_MoreData")
    private String ServiceDelivery_MoreData;
    @JacksonXmlProperty(localName = "MonitoredCall_AimedDepartureTime")
    private Date MonitoredCall_AimedDepartureTime;
    @JacksonXmlProperty(localName = "MonitoredCall_VehicleAtStop")
    private String MonitoredCall_VehicleAtStop;
    @JacksonXmlProperty(localName = "MonitoredStopVisit_RecordedAtTime")
    private Date MonitoredStopVisit_RecordedAtTime;

    @JacksonXmlProperty(localName = "ServiceDelivery_ProducerRef")
    private String ServiceDelivery_ProducerRef;
    @JacksonXmlProperty(localName = "StopMonitoringDelivery_Version")
    private String StopMonitoringDelivery_Version;
    @JacksonXmlProperty(localName = "ServiceDelivery_Status")
    private String ServiceDelivery_Status;
    @JacksonXmlProperty(localName = "MonitoredCall_ExpectedDepartureTime")
    private Date MonitoredCall_ExpectedDepartureTime;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_DestinationRef")
    private String MonitoredVehicleJourney_DestinationRef;
    @JacksonXmlProperty(localName = "LineNote")
    private String LineNote;
    @JacksonXmlProperty(localName = "MonitoredCall_ExpectedArrivalTime")
    private Date MonitoredCall_ExpectedArrivalTime;
    @JacksonXmlProperty(localName = "StopMonitoringDelivery_ResponseTimestamp")
    private Date StopMonitoringDelivery_ResponseTimestamp;
    @JacksonXmlProperty(localName = "FramedVehicleJourneyRef_DatedVehicleJourneyRef")
    private String FramedVehicleJourneyRef_DatedVehicleJourneyRef;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_LineRef")
    private String MonitoredVehicleJourney_LineRef;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_DestinationName")
    private String MonitoredVehicleJourney_DestinationName;
    @JacksonXmlProperty(localName = "Timestamp")
    private Date Timestamp;
    @JacksonXmlProperty(localName = "ServiceDelivery_ResponseTimestamp")
    private Date ServiceDelivery_ResponseTimestamp;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_DirectionRef")
    private String MonitoredVehicleJourney_DirectionRef;
    @JacksonXmlProperty(localName = "MonitoredCall_AimedArrivalTime")
    private Date MonitoredCall_AimedArrivalTime;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_Monitored")
    private String MonitoredVehicleJourney_Monitored;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_InCongestion")
    private String MonitoredVehicleJourney_InCongestion;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_VehicleRef")
    private String MonitoredVehicleJourney_VehicleRef;
    @JacksonXmlProperty(localName = "StopMonitoringDelivery_RequestMessageRef")
    private String StopMonitoringDelivery_RequestMessageRef;
    @JacksonXmlProperty(localName = "MonitoredVehicleJourney_BlockRef")
    private String MonitoredVehicleJourney_BlockRef;
    @JacksonXmlProperty(localName = "MonitoredCall_VisitNumber")
    private String MonitoredCall_VisitNumber;

}
