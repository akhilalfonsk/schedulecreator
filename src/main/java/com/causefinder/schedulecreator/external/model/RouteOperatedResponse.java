package com.causefinder.schedulecreator.external.model;

import java.sql.Timestamp;
import java.util.List;

public class RouteOperatedResponse {
    private String errormessage;
    private String numberofresults;
    private List<OperatorInfo> results;
    private String errorcode;
    private Timestamp timestamp;
}