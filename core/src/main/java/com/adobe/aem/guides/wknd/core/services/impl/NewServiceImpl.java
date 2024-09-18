package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.NewService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

@Component(service = NewService.class)
public class NewServiceImpl implements NewService{

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public ResourceResolver getResourceResolver() {
        ResourceResolver resolver = null;
        Map<String,Object> param = getServiceParams();
        try{
            resolver = resourceResolverFactory.getResourceResolver(param);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        return resolver;
    }

    private static Map<String,Object> getServiceParams(){
        Map<String,Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE,"practiceService");
        return param;

    }

}
