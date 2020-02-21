package com.causefinder.schedulecreator.client;

import com.causefinder.schedulecreator.exception.BigQuerySearchException;
import com.google.cloud.bigquery.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BigQueryClient {

    @Autowired
    private BigQuery bigQueryClient;

    public List<?> executeTripUpdate(String query) throws BigQuerySearchException {
        final JobId jobId = JobId.of(UUID.randomUUID().toString());
        final QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).setUseLegacySql(false).build();
        Job queryJob = bigQueryClient.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
        try {
            queryJob = queryJob.waitFor();
            if (queryJob == null) {
                throw new BigQuerySearchException("Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                throw new BigQuerySearchException(queryJob.getStatus().getError().toString());
            }
            final TableResult result = queryJob.getQueryResults();
            return null;
        } catch (InterruptedException ex) {
            throw new BigQuerySearchException("InterruptedException - query job failed", ex);
        }
    }

    public void insertTripEvents(String query) throws BigQuerySearchException {
        final JobId jobId = JobId.of(UUID.randomUUID().toString());
       /* final QueryJobConfiguration queryConfig = InsertAllRequest.newBuilder().
        Job queryJob = bigQueryClient.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
        try {
            queryJob = queryJob.waitFor();
            if (queryJob == null) {
                throw new BigQuerySearchException("Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                throw new BigQuerySearchException(queryJob.getStatus().getError().toString());
            }
            final TableResult result = queryJob.get;
        } catch (InterruptedException ex) {
            throw new BigQuerySearchException("InterruptedException - query job failed", ex);
        }*/
    }

}
