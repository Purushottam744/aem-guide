package com.adobe.aem.guides.wknd.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;


@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = {"wknd/components/a-training/accordion"})
@Exporter(name = "jackson", extensions = "json",selector = "geeks",
options = {
        @ExporterOption(name = "SerializationFeature.WRAP_ROOT_VALUE", value = "true"),
        @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true")
})
@JsonRootName("F&Q")
public class ContactCardsModel {

    @JsonIgnore
    @SlingObject
    SlingHttpServletRequest slingRequest;

    @JsonProperty(value = "json-id")
    @ValueMapValue
    public String id;

    @JsonProperty(value = "contact")
    @ChildResource(name = "contact")
    public List<Contact> contact;



    public SlingHttpServletRequest getSlingRequest() {
        return slingRequest;
    }

    public List<Contact> getContact() {
        return contact;
    }

    public String getId() {
        return id;
    }
}