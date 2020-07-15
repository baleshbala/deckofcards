package com.deckofcards.apitests;

import com.deckofcards.apihandlers.DeckOfCardsApi;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeckOfCradsDrawCardTests {

    String jkey_declID = "deck_id";
    String jkey_success = "success";
    String jkey_remaining = "remaining";

    CloseableHttpResponse response;
    JSONObject jsonObject;
    String numbToDraw = "1";
    String deckID = null;

    @BeforeClass
    public void Setup() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.CreateNewDeck("https://deckofcardsapi.com/api/deck/new/");
        jsonObject = DeckOfCardsApi.getJsonData(response);
        deckID = DeckOfCardsApi.getJsonValue(jsonObject, jkey_declID);

        response = DeckOfCardsApi.drawCardsFromDeckUsingGet("https://deckofcardsapi.com/api/deck/", deckID, numbToDraw);
        jsonObject = DeckOfCardsApi.getJsonData(response);
    }

    @AfterClass
    public void tearDown() throws IOException {
        response.close();
    }

    @Test(groups="StatusCheck", description="Check status code test")
    public void getSuccessStatusTest(){
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test(priority = 1, description="Check content type test", dependsOnGroups = "StatusCheck")
    public void getContentTypeTest(){
        System.out.println("First assert");
        Assert.assertEquals(response.getEntity().getContentType().getValue(), "application/json");
    }

    @Test(priority = 2, description="Check for ID field", dependsOnGroups = "StatusCheck")
    public void getDeckIdTest() throws IOException {
        Assert.assertNotNull(DeckOfCardsApi.getJsonValue(jsonObject, jkey_declID));
    }

    @Test(priority = 3, description="Check for Success field", dependsOnGroups = "StatusCheck")
    public void getSuccessTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_success), "true");
    }

    @Test(priority = 4, description="Check for remaining cards field", dependsOnGroups = "StatusCheck")
    public void getRemainingCardsTest() throws IOException {
        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_remaining), "51");
    }

    @Test(priority = 5, description="Check for remaining cards field after drawing 2 cards", dependsOnGroups = "StatusCheck")
    public void drawMoreThanOneCard() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.drawCardsFromDeckUsingGet("https://deckofcardsapi.com/api/deck/", deckID, "2");
        jsonObject = DeckOfCardsApi.getJsonData(response);

        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_remaining), "49");
    }

    @Test(priority = 6, description="Check for remaining cards field, with bad value to Draw", dependsOnGroups = "StatusCheck")
    public void countNumberOfCradsDrawn() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.drawCardsFromDeckUsingGet("https://deckofcardsapi.com/api/deck/", deckID, "3");
        jsonObject = DeckOfCardsApi.getJsonData(response);
        JSONArray cardsArray = jsonObject.getJSONArray("cards");

        Assert.assertEquals(cardsArray.length(), 3);
    }

    // THIS TEST WILL FAIL, DECKOFCARDS API HAS AN ERROR.
    // When given -n, All Cards are deleted but only n card remains.
    @Test(priority = 7, description="Check for remaining cards field, with bad value to Draw", dependsOnGroups = "StatusCheck")
    public void drawUsingBadCountValue() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.drawCardsFromDeckUsingGet("https://deckofcardsapi.com/api/deck/", deckID, "-1");
        jsonObject = DeckOfCardsApi.getJsonData(response);

        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_remaining), "50", "Expected to Fail, Seems like an error in the API; When given -n, n is a number, All Cards are deleted but only n card remains.");
    }

    @Test(priority = 8, description="Check for remaining cards field, with bad value to Draw", dependsOnGroups = "StatusCheck")
    public void drawUsingLargerCountValue() throws IOException, URISyntaxException {
        response = DeckOfCardsApi.drawCardsFromDeckUsingGet("https://deckofcardsapi.com/api/deck/", deckID, "64");
        jsonObject = DeckOfCardsApi.getJsonData(response);

        Assert.assertEquals(DeckOfCardsApi.getJsonValue(jsonObject, jkey_remaining), "49", "Not working as expected, insteadof failing its removing all the cards");
    }
}
