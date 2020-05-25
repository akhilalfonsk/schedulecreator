package com.causefinder.schedulecreator.soap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Stops implements Comparable<Stops> {
    @JacksonXmlProperty(localName = "Address")
    private String Address;
    @JacksonXmlProperty(localName = "StopNumber")
    private Integer StopNumber;
    @JacksonXmlProperty(localName = "Latitude")
    private String Latitude;
    @JacksonXmlProperty(localName = "SeqNumber")
    private Integer SeqNumber;
    @JacksonXmlProperty(localName = "Direction")
    private String Direction;
    @JacksonXmlProperty(localName = "Longitude")
    private String Longitude;
    @JacksonXmlProperty(localName = "Route")
    private String Route;
    @JacksonXmlProperty(localName = "SeqNumberExt")
    private Integer SeqNumberExt;
    @JacksonXmlProperty(localName = "Location")
    private String Location;

    @Override
    public int compareTo(Stops o) {
       if(!getRoute().equalsIgnoreCase(o.getRoute())) return getRoute().compareTo(o.getRoute());
       else if(!getSeqNumber().equals(o.getSeqNumber())) return  getSeqNumber().compareTo(o.getSeqNumber());
       else return getSeqNumberExt().compareTo(getSeqNumberExt());
    }
}
