package com.mediatoolkit.moneydistributor.api.service.util;

import org.springframework.stereotype.Component;

@Component
public class TimeProvider {

    public Long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}
