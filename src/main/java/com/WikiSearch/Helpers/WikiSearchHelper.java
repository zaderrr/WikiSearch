package com.WikiSearch.Helpers;






import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WikiSearchHelper {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static HashMap<String, String> SearchApi(String searchQ) throws ExecutionException, InterruptedException, TimeoutException {


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://oldschool.runescape.wiki/api.php?action=opensearch&format=xml&search="+ searchQ +"&namespace=0&limit=10"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        CompletableFuture<HttpResponse<String>> response =
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
        return ParseSearchResult(result);


    }

    public static HashMap<String, String> ParseSearchResult(String results) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        HashMap<String, String> SearchResults = new HashMap<String,String>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(results)));
            Element root = document.getDocumentElement();

            NodeList items = root.getElementsByTagName("Item");


            for (int i = 0; i < items.getLength(); i++) {
                SearchResults.put(items.item(i).getFirstChild().getTextContent(), items.item(i).getLastChild().getTextContent());
            }
            return SearchResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SearchResults;
    }




}
