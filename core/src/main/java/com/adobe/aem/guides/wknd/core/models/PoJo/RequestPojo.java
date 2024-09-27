package com.adobe.aem.guides.wknd.core.models.PoJo;

import java.util.Map;

public class RequestPojo {
    private Map<String, Double> data;

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }
}
