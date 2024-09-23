 Work Flow: If you have series of task then you can achieve that task using workflow.

 Once you create this model it is stored at two places:

 1- Model Design /conf/global/settings/workflow/models/aem-guides-wknd
 2- Runtime Model /var/workflow/models/aem-guides/wknd

 Note: when your work flow execute then runtime model is used

                Model

                Flow Start
                step1
                step2
                step3
                stepn
                Flow End

 These steps are mainly four type
 1. Container Step: When you want to trigger another model/workflow from this workflow.
 2. Participant Step: when you want a human Interaction
 3. Decision Step: When you to perform a task on condition.
 4. Process Step: Custom Process will be achieved via this.

 To call this model there are three ways:
 1. Launcher
 2. API/Code
 3. Manually

To Trigger the workflow via api:------------------
There are two important module of your workflow:
!. Workflow Model - which is the core of your workflow and which has all the execution logic.
2. Payload

#######Now to trigger this workflow you need a workflow session
WorkflowSession workflowSession =  resolver.adapt(WorkflowSession.class);

#######Then you will need workflow model (this is basically run time model)
WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/wknd-version");

#######Once you get model, you can get a Workflow Data
WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);
workflowSession.startWorkflow(workflowModel,workflowData);

#######Additional When you want to use metaData
Map<String, Object> workflowMetaData = new HashMap<>();
workflowSession.startWorkflow(workflowModel,workflowData,workflowMetaData);
 