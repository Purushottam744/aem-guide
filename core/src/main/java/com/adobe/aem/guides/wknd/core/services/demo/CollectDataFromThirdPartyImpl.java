package com.adobe.aem.guides.wknd.core.services.demo;

import com.adobe.aem.guides.wknd.core.models.PoJo.RequestPojo;
import com.adobe.aem.guides.wknd.core.services.CollectDataFromThirdParty;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@Component(service = CollectDataFromThirdParty.class,immediate = true)
public class CollectDataFromThirdPartyImpl implements CollectDataFromThirdParty{

    private static final String apiEndPoint = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_AbhICO585r6FeqYrI3IitUG2iPHuBBXlNyTGgmCZ";
    @Override
    public ArrayList<String> collectData() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // Prepare the HttpGet request
            HttpGet httpGet = new HttpGet(apiEndPoint);
            httpGet.setHeader("Accept", "application/json");

            // Execute the request
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // Check if the status code is 200 OK
                if (statusCode == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    if (entity != null) {
                        // Convert the response entity to a String
                        String result = EntityUtils.toString(entity);

                        // Deserialize JSON response using Gson
                        Gson gson = new Gson();
                        RequestPojo requestPojo = gson.fromJson(result, RequestPojo.class);
                        // Extract specific data (e.g., USD and EUR)
                        Map<String, Double > obj = requestPojo.getData();
                        String AUD = String.valueOf(obj.get("AUD"));
                        String BGN = String.valueOf(obj.get("BGN"));
                        ArrayList<String> data = new ArrayList<>();
                        data.add(AUD);
                        data.add(BGN);


                        // Create a response string with only the specific data
                        return data;

                    }
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }
    //UpdateDataInContentFragment(String AUD, String BGN);

}
