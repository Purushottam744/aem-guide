package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.models.RequestPojo;
import com.adobe.aem.guides.wknd.core.services.CollectDataFromThirdParty;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.print.DocFlavor;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;


@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/bin/practice"}
)
public class PracticeServlet extends SlingSafeMethodsServlet {
    private static final String apiEndPoint = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_AbhICO585r6FeqYrI3IitUG2iPHuBBXlNyTGgmCZ";
    //RequestPojo requestPojo;
    @Reference
    CollectDataFromThirdParty collectDataFromThirdParty;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//
//            // Prepare the HttpGet request
//            HttpGet httpGet = new HttpGet(apiEndPoint);
//            httpGet.setHeader("Accept", "application/json");
//
//            // Execute the request
//            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
//                int statusCode = httpResponse.getStatusLine().getStatusCode();
//
//                // Check if the status code is 200 OK
//                if (statusCode == 200) {
//                    HttpEntity entity = httpResponse.getEntity();
//                    if (entity != null) {
//                        // Convert the response entity to a String
//                        String result = EntityUtils.toString(entity);
//
//                        // Deserialize JSON response using Gson
//                        Gson gson = new Gson();
//                        RequestPojo requestPojo = gson.fromJson(result, RequestPojo.class);
//                        // Extract specific data (e.g., USD and EUR)
//                        Map<String, Double > obj = requestPojo.getData();
//                        String AUD = String.valueOf(obj.get("AUD"));
//                        String BGN = String.valueOf(obj.get("BGN"));
//
//                        // Create a response string with only the specific data
//                       String responseData = "AUD: " + obj.get("AUD") + ", BGN: " + obj.get("BGN");
//                       response.setContentType("text/plain");
//                       response.getWriter().write(responseData);
//                    }
//                }
//            }
//        } catch (IOException e) {
//
//            throw e;
//        }
        //Above is the working code
        ArrayList<String> data = collectDataFromThirdParty.collectData();
        response.getWriter().write(data.get(0));
        //collectDataFromThirdParty.UpdateDataInContentFragment(String AUD, String BGN);
    }
}
