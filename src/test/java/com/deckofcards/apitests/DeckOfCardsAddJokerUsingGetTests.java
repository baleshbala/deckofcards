package com.deckofcards.apitests;

import com.deckofcards.apihandlers.DeckOfCardsApi;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public class DeckOfCardsAddJokerUsingGetTests {
    CloseableHttpClient client;
    CloseableHttpResponse response;
    JSONObject jsonObject;
    String jkey_deckID = "deck_id";
    String jkey_success = "success";
    String jkey_remaining = "remaining";
    String jkey_shuffled = "shuffled";

    @BeforeClass
    public void AddJokerSetUp() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.AddJokersToDeckUsingGet("https://deckofcardsapi.com/api/deck/new/");
        jsonObject = DeckOfCardsApi.getJsonData(response);
    }

    @AfterClass
    public void tearDown() throws IOException {
        response.close();
    }

    @Test(groups="StatusCheck", description="Check status code test")
    public void getSuccessStatusTest() {
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test(description="Check content type test", dependsOnGroups = "StatusCheck")
    public void getContentTypeTest(){
        System.out.println("First assert");
        Assert.assertEquals(response.getEntity().getContentType().getValue(), "application/json");
    }

    @Test(description="Check for ID field", dependsOnGroups = "StatusCheck")
    public void getDeckIdTest() throws IOException {
        Assert.assertNotNull(DeckOfCardsApi.getJsonValue(jsonObject, jkey_deckID));
    }

    @Test(description="Check for Success field", dependsOnGroups = "StatusCheck")
    public void getSuccessTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_success), "true");
    }

    @Test(description="Check for remaining cards field", dependsOnGroups = "StatusCheck")
    public void getRemainingCardsTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_remaining), "54");
    }

    @Test(description="Check for Shuffled cards field", dependsOnGroups = "StatusCheck")
    public void getShuffledTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_shuffled), "false");
    }
}

