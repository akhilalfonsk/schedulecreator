package com.causefinder.schedulecreator.external.model;

import java.sql.Timestamp;
import java.util.List;

public class RealtimeBusInformationResponse {
    private String errormessage;
    private String numberofresults;
    private String stopid;
    private List<RealTimeInfo> results;
    private String errorcode;
    private Timestamp timestamp;
}
