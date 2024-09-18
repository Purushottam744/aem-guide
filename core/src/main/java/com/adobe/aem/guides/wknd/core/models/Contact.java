package com.adobe.aem.guides.wknd.core.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;



@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Contact {

    @JsonProperty("contactName")
    @ValueMapValue
    private String contactName;

    @JsonProperty("contactEmail")
    @ValueMapValue
    private String contactEmail;

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }
}