package com.causefinder.schedulecreator.client;

import com.causefinder.schedulecreator.soap.model.StopEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BigQueryClient {

    @Value("${gcp.tripdb.dataset}")
    private String datasetName;
    @Value("${gcp.tripdb.table}")
    private String tableName;

    @Autowired
    private BigQuery bigQueryClient;

    private ObjectMapper oMapper = new ObjectMapper();

    public void pushDataToGCP(List<StopEvent> stopEvents) {
        TableId tableId = TableId.of(datasetName, tableName);
        InsertAllRequest.Builder builder = InsertAllRequest.newBuilder(tableId);
        stopEvents.parallelStream().forEach(stopEvent -> builder.addRow(oMapper.convertValue(stopEvent, Map.class)));
        InsertAllResponse response = bigQueryClient.insertAll(builder.build());
        if (response.hasErrors()) {
            for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                StringBuilder errorString = new StringBuilder("Error in Pushing data to GCP:");
                errorString.append("[").append(entry.getKey()).append("=>[");
                entry.getValue().stream().forEach(err -> errorString.append(err.getMessage()).append(","));
                errorString.append("]]");
                log.warn("Error in Pushing data to GCP: {} ", errorString.toString());
            }
        }
    }

}
