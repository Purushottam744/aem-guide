package com.adobe.aem.guides.wknd.core.services.demo;

import com.adobe.aem.guides.wknd.core.services.UpdateDataInCf;
import com.adobe.cq.dam.cfm.*;
import com.adobe.cq.xf.ExperienceFragmentVariation;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Component(service = UpdateDataInCf.class, immediate = true)
@ServiceRanking(1001)
public class CFDataBaseImpl implements UpdateDataInCf {
    Logger logger = LoggerFactory.getLogger(CFDataBaseImpl.class);

    private static final String PATH = "/content/dam/wknd/currency";

    @Override
    public void updateDataInCf(ResourceResolver resolver, Map<String, String> data) {
        Resource resource = resolver.getResource(PATH);
        Session session = resolver.adaptTo(Session.class);
        ContentFragment contentFragment = resource.adaptTo(ContentFragment.class);

        try {
            String variationName = "Variation_" + UUID.randomUUID().toString();
            VariationTemplate variationTemplate = contentFragment.createVariation(
                    variationName,
                    "Newly Created Variation",
                    "Variation for new entry"
            );
            Iterator<ContentElement> contentElementIterator = contentFragment.getElements();
            while (contentElementIterator.hasNext()){
                ContentElement element = contentElementIterator.next();
                ContentVariation variation = element.getVariation(variationName);

                //this will update in created variation
                if (element.getName().equals("name")){
                    variation.setContent(data.get("name"), "text/plain"); 
                } else if (element.getName().equals("id")) {
                    variation.setContent(data.get("id"), "text/plain");
                }
                else {
                    variation.setContent(data.get("gender"), "text/plain");
                }

                //it will update in master
                //element.setContent(data.get("name"), "text/plain");
                //element.getContent();
            }


            logger.info("Created new variation: {}", variationName);

            // Get the elements and set data for the newly created variation
//            ContentElement nameElement = contentFragment.getElement("name");
//            nameElement.setContent(data.get("name"), "text/plain");
//            logger.info("Set name: {}", data.get("name"));
//
//            ContentElement idElement = contentFragment.getElement("id");
//            idElement.setContent(data.get("id"), "text/plain");
//            logger.info("Set id: {}", data.get("id"));
//
//            ContentElement genderElement = contentFragment.getElement("gender");
//            genderElement.setContent(data.get("gender"), "text/plain");
//            logger.info("Set gender: {}", data.get("gender"));

            // Save the session to persist changes
            session.save();
            logger.info("Content Fragment updated and saved with new variation: {}", variationName);

        } catch (ContentFragmentException | RuntimeException | RepositoryException e) {
            logger.error("Error while updating Content Fragment: ", e);
            throw new RuntimeException(e);
        }
    }
}
