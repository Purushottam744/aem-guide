package com.adobe.aem.guides.wknd.core.servlets;


import com.adobe.aem.guides.wknd.core.services.NewService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.apache.sling.scripting.jsp.taglib.SlingFunctions.listChildren;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes="wknd/components/a-training/accordion",
        methods=HttpConstants.METHOD_GET,
        selectors = "html",
        extensions="json")
public class ExportModalDataUsingSlingApi extends SlingSafeMethodsServlet {
//    @Reference
//    NewService newService;



    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> data = new HashMap<>();
        ResourceResolver resolver = request.getResourceResolver();
//        ResourceResolver resolver = newService.getResourceResolver();

        Resource resource = resolver.getResource("/content/wknd/us/homepage/jcr:content/root/container/container/accordion");

        Iterator<Resource> children = resource.listChildren();
        while (children.hasNext()){
            Resource child = children.next();
            if("contact".equals(child.getName())){
                Iterator<Resource> contactItems = child.listChildren();
                while (contactItems.hasNext()){
                    Resource item = contactItems.next();
                    Map<String,Object> innerData = new HashMap<>();
                    String contactEmail = item.getValueMap().get("contactEmail",String.class);
                    String contactName = item.getValueMap().get("contactName",String.class);
                    innerData.put("ContactEmail",contactEmail);
                    innerData.put("ContactName",contactName);
                    data.put(item.getName(),innerData);
                }
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        response.getWriter().write(gson.toJson(data));





        response.setContentType("application/json");
        response.getWriter().write("Hi there");
    }
}
