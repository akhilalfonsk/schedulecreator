package com.causefinder.schedulecreator.soap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Stops {
    @JacksonXmlProperty(localName = "Address")
    private String Address;
    @JacksonXmlProperty(localName = "StopNumber")
    private String StopNumber;
    @JacksonXmlProperty(localName = "Latitude")
    private String Latitude;
    @JacksonXmlProperty(localName = "SeqNumber")
    private String SeqNumber;
    @JacksonXmlProperty(localName = "Direction")
    private String Direction;
    @JacksonXmlProperty(localName = "Longitude")
    private String Longitude;
    @JacksonXmlProperty(localName = "Route")
    private String Route;
    @JacksonXmlProperty(localName = "SeqNumberExt")
    private String SeqNumberExt;
    @JacksonXmlProperty(localName = "Location")
    private String Location;
}
