package com.javababok.timecalculator.controller;

import com.javababok.timecalculator.service.APIService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Response;
import spark.Request;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;


public class APIController {

    private static APIService apiService;
    // ORIGIN string store the webshop location
    // TODO: Change origin location, what you want!
    private static final String ORIGIN = "Amsterdam";

    public APIController(APIService apiService){
        APIController.apiService = apiService;
    }

    public JSONObject location (Request request, Response response) throws IOException, URISyntaxException, JSONException {
        return this.getTimeInMs(request.params(":destination"));
    }

    // This method create the final json with the output data.
    public JSONObject getTimeInMs(String destination) throws IOException, URISyntaxException, JSONException {
        JSONObject json = new JSONObject();
        String status = checkStatus(destination);
        // If something went wrong, the time will be 0.
        if(!status.equals("OK")) {
            json.put("time", 0);
            json.put("status", status);
            return json;
        }
        // Here start the work with the jon from Google Maps.
        JSONObject routeDetails = getRouteDetails(destination);
        Integer timeInSec = (Integer) ((JSONObject) routeDetails.get("duration")).get("value");
        // Convert second to millisecond.
        json.put("time", TimeUnit.SECONDS.toMillis(timeInSec));
        json.put("status", status);
        return json;
    }

    public String checkStatus(String destination) throws IOException, URISyntaxException, JSONException {
        // Check the status and expand it, if no OK (or something else).
        JSONObject routeDetails = getRouteDetails(destination);
        String status = routeDetails.get("status").toString();
        switch (status) {
            case "ZERO_RESULTS":
                return "ZERO_RESULTS ERROR: Oversea location!";

            case "NOT_FOUND":
                return "NOT_FOUND ERROR: Place doesn't exist!";
            default:
                return status;
        }
    }

    public JSONObject getRouteDetails(String destination) throws IOException, URISyntaxException, JSONException {
        // Here get the json from Google and return it.
        JSONObject jsonObject = new JSONObject(apiService.calcTime(ORIGIN, destination));
        JSONObject routeDetailsList = (JSONObject) ((JSONArray) jsonObject.get("rows")).get(0);
        return (JSONObject) ((JSONArray) routeDetailsList.get("elements")).get(0);
    }

}
