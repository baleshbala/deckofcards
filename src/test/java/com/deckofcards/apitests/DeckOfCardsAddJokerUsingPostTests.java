package com.deckofcards.apitests;

import com.deckofcards.apihandlers.DeckOfCardsApi;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public class DeckOfCardsAddJokerUsingPostTests {
    CloseableHttpClient client;
    CloseableHttpResponse response;
    JSONObject jsonObject;
    String jkey_declID = "deck_id";
    String jkey_success = "success";
    String jkey_remaining = "remaining";
    String jkey_shuffled = "shuffled";

    @BeforeClass
    public void AddJokerSetUp() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.AddJokersToDeckUsingPost("https://deckofcardsapi.com/api/deck/new/");
        jsonObject = DeckOfCardsApi.getJsonData(response);
    }

    @AfterClass
    public void tearDown() throws IOException {
        response.close();
    }

    @Test(groups="StatusCheckPost", description="Check status code test")
    public void getSuccessStatusTest() throws IOException, URISyntaxException {
        Assert.assertEquals(response.getStatusLine().getStatusCode(),200, "Expected to Fail. Seems like Adding Joker using POST is broken in DeckOfCards API.");
    }

    @Test(description="Check content type test", dependsOnGroups = "StatusCheckPost")
    public void getContentTypeTest(){
        System.out.println("First assert");
        Assert.assertEquals(response.getEntity().getContentType().getValue(), "application/json");
    }

    @Test(description="Check for ID field", dependsOnGroups = "StatusCheckPost")
    public void getDeckIdTest() throws IOException {
        Assert.assertNotNull(DeckOfCardsApi.getJsonValue(jsonObject, jkey_declID));
    }

    @Test(description="Check for Success field", dependsOnGroups = "StatusCheckPost")
    public void getSuccessTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_success), "true");
    }

    @Test(description="Check for remaining cards field", dependsOnGroups = "StatusCheckPost")
    public void getRemainingCardsTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_remaining), "54");
    }

    @Test(description="Check for remaining cards field", dependsOnGroups = "StatusCheckPost")
    public void getShuffledTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_shuffled), "false");
    }
}

