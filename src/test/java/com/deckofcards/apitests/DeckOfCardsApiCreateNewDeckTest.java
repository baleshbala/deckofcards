package com.deckofcards.apitests;

import com.deckofcards.apihandlers.DeckOfCardsApi;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeckOfCardsApiCreateNewDeckTest {

    String jkey_declID = "deck_id";
    String jkey_success = "success";
    String jkey_remaining = "remaining";
    String jkey_shuffled = "shuffled";

    CloseableHttpResponse response;
    JSONObject jsonObject;

    @BeforeClass
    public void GlobalSetup() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.CreateNewDeck("https://deckofcardsapi.com/api/deck/new/");
        jsonObject = DeckOfCardsApi.getJsonData(response);
    }

    @AfterClass
    public void tearDown() throws IOException {
        response.close();
    }

    @Test(groups="StatusCheck", description="Check status code test")
    public void getSuccessStatusTest() throws IOException, URISyntaxException {
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test(description="Check content type test", dependsOnGroups = "StatusCheck")
    public void getContentTypeTest(){
        System.out.println("First assert");
        Assert.assertEquals(response.getEntity().getContentType().getValue(), "application/json");
    }

    @Test(description="Check for ID field", dependsOnGroups = "StatusCheck")
    public void getDeckIdTest() throws IOException {
        Assert.assertNotNull(DeckOfCardsApi.getJsonValue(jsonObject, jkey_declID));
    }

    @Test(description="Check for Success field", dependsOnGroups = "StatusCheck")
    public void getSuccessTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_success), "true");
    }

    @Test(description="Check for remaining cards field", dependsOnGroups = "StatusCheck")
    public void getRemainingCardsTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_remaining), "52");
    }

    @Test(description="Check for remaining cards field", dependsOnGroups = "StatusCheck")
    public void getShuffledTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_shuffled), "false");
    }
}
