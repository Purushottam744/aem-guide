package com.adobe.aem.guides.wknd.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import java.util.ArrayList;
import java.util.Map;

public interface UpdateDataInCf {
    public void updateDataInCf(ResourceResolver resolver, Map<String,String> data);
}
