package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.models.PoJo.Data;
import com.adobe.aem.guides.wknd.core.models.PoJo.RequestDataPojo;
import com.adobe.aem.guides.wknd.core.services.CollectDataFromThirdParty;
import com.adobe.aem.guides.wknd.core.services.UpdateDataInCf;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/bin/practice"}
)
public class PracticeServlet extends SlingAllMethodsServlet {
    private static final String apiEndPoint = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_AbhICO585r6FeqYrI3IitUG2iPHuBBXlNyTGgmCZ";
    //RequestPojo requestPojo;
    @Reference
    CollectDataFromThirdParty collectDataFromThirdParty;

    @Reference
    UpdateDataInCf updateDataInCf;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
       ResourceResolver resolver = request.getResourceResolver();
       StringBuilder jsonData = new StringBuilder();
       String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine())!=null){
            jsonData.append(line);
        }
        Gson gson = new Gson();
        RequestDataPojo pojo = gson.fromJson(jsonData.toString(),RequestDataPojo.class);
        Data data1 = pojo.getData();
        String name = data1.getName();
        String id = data1.getId();
        String gender = data1.getGender();
        Map<String,String> normaldata = new HashMap<>();
        normaldata.put("name",data1.getName());
        normaldata.put("id",data1.getId());
        normaldata.put("gender",data1.getGender());


        ArrayList<String> data = collectDataFromThirdParty.collectData();
//        updateDataInCf.updateDataInCf(resolver,data);
        updateDataInCf.updateDataInCf(resolver,normaldata);
        response.getWriter().write(data.get(1));

        //collectDataFromThirdParty.UpdateDataInContentFragment(String AUD, String BGN);
    }
}
