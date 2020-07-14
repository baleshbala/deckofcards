package com.deckofcards.apihandlers;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DeckOfCardsApi {

    /**
     * This method calls the DeckOfCards API to create a new Deck.
     * @param url
     * @return Response Object
     * @throws IOException
     * @throws URISyntaxException
     */
    public static CloseableHttpResponse CreateNewDeck(String url) throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet(new URI(url)));
        return response;
    }

    /**
     * This method adds two Joker cards to the existing Deck.
     * @param url
     * @return Response Object
     * @throws IOException
     * @throws URISyntaxException
     */
    public static CloseableHttpResponse AddJokersToDeckUsingGet(String url) throws IOException, URISyntaxException {

        String enableJokers = "jokers_enabled=true";
        url = new String(url+"/?"+enableJokers);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet(new URI(url)));
        return response;
    }

    /**
     * This method adds two Joker cards to the existing Deck.
     * @param url
     * @return Response Object
     * @throws IOException
     * @throws URISyntaxException
     */
    public static CloseableHttpResponse AddJokersToDeckUsingPost(String url) throws IOException, URISyntaxException {

        String enableJokers = "jokers_enabled=true";
        url = new String(url+"/?"+enableJokers);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);

        CloseableHttpResponse response = client.execute(httppost);
        return response;
    }

    /**
     * This method is to draw desired number of cards from deck.
     * @param url
     * @return Response Object
     * @throws IOException
     * @throws URISyntaxException
     */
    public static CloseableHttpResponse drawCardsFromDeckUsingGet(String url, String deckID, String numOfCardsToDraw) throws IOException, URISyntaxException {

        url = new String(url+deckID+"/draw/?"+numOfCardsToDraw);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet(new URI(url)));
        return response;
    }

    /**
     * This method parses the response and returns the Json Data
     * @param response
     * @return
     * @throws IOException
     */
    public static JSONObject getJsonData(CloseableHttpResponse response) throws IOException {
        JSONObject jsonObject = null;
        if(response.getStatusLine().getStatusCode() == 200){
            String jsonString = EntityUtils.toString(response.getEntity());
            jsonObject = new JSONObject(jsonString);
            return jsonObject;
        }
        return null;
    }

    /**
     * This method parses the Json Data to get Specific value
     * @param jsonObject,jsonKey
     * @return
     * @throws IOException
     */
    public static String getJsonValue(JSONObject jsonObject, String jsonKey) throws IOException {

        String jsonValue = null;
        if(jsonObject != null){
            jsonValue = jsonObject.get(jsonKey).toString();
        }
        return jsonValue;
    }
}
