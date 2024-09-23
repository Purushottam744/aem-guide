package com.adobe.aem.guides.wknd.core.workflow.impl;

import com.adobe.aem.guides.wknd.core.services.impl.ResouceResolver;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Component(service = WorkflowProcess.class, immediate = true,
property = {
        "process.label" + "=Aem Guide Workflow",
        Constants.SERVICE_VENDOR + "=AEM Geeks"
})
public class CustomWorkflowProcess implements WorkflowProcess {
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        //ResouceResolver resolver = workflowSession.adaptTo(ResouceResolver.class);
        WorkflowData workflowData = workItem.getWorkflowData();
        //if(workflowData.getPayloadType().equals("JCR_PATH")){
            Session session = workflowSession.adaptTo(Session.class);

            String payloadPath = workflowData.getPayload().toString() + "/jcr:content";
            try {
                Node node = (Node) session.getItem(payloadPath);
                String[] processargs = metaDataMap.get("PROCESS_ARGS","string").toString().split(",");
                for(String wfArgs: processargs){
                    String[] args = wfArgs.split(":");
                    String prop = args[0];
                    String value = args[1];
                    if(node!=null){
                        node.setProperty(prop,value);
                    }
                }
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        //}



    }
}
