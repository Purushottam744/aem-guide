package com.adobe.aem.guides.wknd.core.services.demo;

import com.adobe.aem.guides.wknd.core.services.UpdateDataInCf;
import com.adobe.cq.dam.cfm.*;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Map;


@Component(service = UpdateDataInCf.class, immediate = true)
@ServiceRanking(1000)
public class UpdateDataInCfImpl implements UpdateDataInCf {
    Logger logger = LoggerFactory.getLogger(UpdateDataInCfImpl.class);

    private static final String PATH = "/content/dam/wknd/currency";
    @Override
    public void updateDataInCf(ResourceResolver resolver, Map<String,String> data) {
        Resource resource = resolver.getResource(PATH);
        Session session = resolver.adaptTo(Session.class);
        ContentFragment contentFragment = resource.adaptTo(ContentFragment.class);
        try {
            VariationTemplate variation = contentFragment.createVariation("Variation1","Newly Variation","This is our try variation");
            variation.getName();


        } catch (ContentFragmentException e) {
            throw new RuntimeException(e);
        }
        try{
            ContentElement contentElement =  contentFragment.getElement("name");
//            This is with api
//            contentElement.setContent("Abhi","text/html");
            contentElement.setContent(data.get("name"), "text/html");

            contentElement.getName();
            contentElement.getValue();


            ContentElement contentElement1 =  contentFragment.getElement("id");

//            This is with api
//            contentElement1.setContent(data.get(0).toString(), "text/html");
            contentElement1.setContent(data.get("id"), "text/html");
            contentElement1.getName();
            contentElement1.getValue();

            ContentElement contentElement2 =  contentFragment.getElement("gender");
            contentElement2.setContent(data.get("gender"), "text/html");

            contentElement2.getName();
            contentElement2.getValue();

            session.save();
            logger.error("Content Element: {} ",contentElement.getContentType());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
