package com.adobe.aem.guides.wknd.core.servlets;


import com.day.cq.replication.ReplicationStatus;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component(
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Replication Status Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/checkReplicationStatus"
        }
)
public class ReplicationStatusServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String contentPath = request.getParameter("path");
        JsonObject jsonResponse = new JsonObject();

        if (contentPath == null || contentPath.isEmpty()) {
            jsonResponse.addProperty("error", "Content path is required");
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
        } else {
            Resource resource = request.getResourceResolver().getResource(contentPath);
            if (resource == null) {
                jsonResponse.addProperty("error", "Resource not found");
                response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            } else {
                ReplicationStatus replicationStatus = resource.adaptTo(ReplicationStatus.class);
                if (replicationStatus != null) {
                    jsonResponse.addProperty("path", contentPath);
                    jsonResponse.addProperty("isPublished", replicationStatus.isActivated());
                    jsonResponse.addProperty("lastReplicationAction", replicationStatus.getLastReplicationAction() != null ? replicationStatus.getLastReplicationAction().toString() : "N/A");
                    jsonResponse.addProperty("lastReplicationDate", replicationStatus.getLastPublished() != null ? formatDate(replicationStatus.getLastPublished().getTime()) : "N/A");
                    jsonResponse.addProperty("isInReplicationQueue", replicationStatus.isPending());
                } else {
                    jsonResponse.addProperty("error", "Replication status not available for this resource");
                    response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(date);
    }
}