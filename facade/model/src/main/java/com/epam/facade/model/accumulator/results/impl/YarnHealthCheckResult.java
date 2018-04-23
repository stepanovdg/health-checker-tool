package com.epam.facade.model.accumulator.results.impl;

import com.epam.facade.model.HealthCheckActionType;
import com.epam.facade.model.accumulator.results.BaseActionResult;
import com.epam.health.tool.model.ServiceStatusEnum;

import java.util.List;

public class YarnHealthCheckResult implements BaseActionResult {
    private ServiceStatusEnum status;
    private List<YarnJob> jobResults;

    public ServiceStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ServiceStatusEnum status) {
        this.status = status;
    }

    public List<YarnJob> getJobResults() {
        return jobResults;
    }

    public void setJobResults(List<YarnJob> jobResults) {
        this.jobResults = jobResults;
    }

    @Override
    public HealthCheckActionType getHealthActionType() {
        return HealthCheckActionType.YARN_SERVICE;
    }

    public static class YarnJob {
        private String name;
        private boolean success;
        private List<String> alerts;

        public YarnJob(String name, boolean success, List<String> alerts) {
            this.name = name;
            this.success = success;
            this.alerts = alerts;
        }

        public String getName() {
            return name;
        }

        public boolean isSuccess() {
            return success;
        }

        public List<String> getAlerts() {
            return alerts;
        }
    }
}