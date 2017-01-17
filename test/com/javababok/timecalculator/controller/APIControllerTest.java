package com.javababok.timecalculator.controller;

import com.javababok.timecalculator.service.APIService;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;


public class APIControllerTest {


    @Test
    public void getTimeInMsWithCorrectLocation() throws NullPointerException, JSONException, IOException, URISyntaxException {
        APIController apiController = new APIController(APIService.getInstance());
        assertEquals("{\"time\":48072000,\"status\":\"OK\"}", apiController.getTimeInMs("Amsterdam", "Budapest").toString());
    }

    @Test
    public void wrongLocation() throws NullPointerException, JSONException, IOException, URISyntaxException {
        APIController apiController = new APIController(APIService.getInstance());
        assertEquals("{\"time\":0,\"status\":\"NOT_FOUND ERROR: Place doesn't exist!\"}", apiController.getTimeInMs("Amsterdam", "sdfghjkertzudfghertz").toString());

    }

    @Test
    public void overseaLocation() throws NullPointerException, JSONException, IOException, URISyntaxException {
        APIController apiController = new APIController(APIService.getInstance());
        assertEquals("{\"time\":0,\"status\":\"ZERO_RESULTS ERROR: Oversea location!\"}", apiController.getTimeInMs("Amsterdam", "New York").toString());

    }

    @Test
    public void getRouteDetails() throws Exception {
        APIController apiController = new APIController(APIService.getInstance());
        assertEquals("{\"duration\":{\"text\":\"13 hours 21 mins\",\"value\":48072},\"distance\":{\"text\":\"1,395 km\",\"value\":1395271},\"status\":\"OK\"}", apiController.getRouteDetails("Amsterdam", "Budapest").toString());

    }

    @Test
    public void checkStatus() throws NullPointerException, JSONException, IOException, URISyntaxException {
        APIController apiController = new APIController(APIService.getInstance());
        assertEquals("OK", apiController.checkStatus("Amsterdam", "Budapest").toString());
    }


}