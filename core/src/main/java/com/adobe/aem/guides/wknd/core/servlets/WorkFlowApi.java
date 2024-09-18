package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/bin/executeworkflow"}
)
public class WorkFlowApi extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        final ResourceResolver resolver = request.getResourceResolver();
        String Status ="Workflow Execution";
        String payload = request.getRequestParameter("page").getString();
        WorkflowSession workflowSession = resolver.adaptTo(WorkflowSession.class);
        try {
            WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/wknd-page-version");
            WorkflowData workflowData = workflowSession.newWorkflowData("JCR_Path",payload);
            Status = workflowSession.startWorkflow(workflowModel,workflowData).getState();
        } catch (WorkflowException e) {
            throw new RuntimeException(e);
        }
    }
}
