package com.ua.travel_mate.monitor;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ServiceMonitor {
    String flowId;
    public String initializeFlow() {
        flowId = UUID.randomUUID().toString();
        return flowId;
    }
}
