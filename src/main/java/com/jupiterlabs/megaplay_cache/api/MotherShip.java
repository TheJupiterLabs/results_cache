package com.jupiterlabs.megaplay_cache.api;

import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
public class MotherShip {

    private String bearer;
    private final String tokeRequestUrl = "https://gle.mothership-tnaig.com/engine/requestToken";
    private final String contentRequestUrl = "https://gle.mothership-tnaig.com/engine/miraPowerBallAPI";

    private long requestCounter = 1000000L;
    private String cachedContent = "";

    private long cacheTime = 0;

    /*
     {
     "ip":"102.132.246.194",
     "gl_key":"glEng",
     "url":"megaplay.co.za",
     "sid":"8883332221"
 }

     */
    private void getToken() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("ip", "159.203.104.207");
        requestBody.put("gl_key", "glEng");
        requestBody.put("url", "megaplay.co.za");
        requestBody.put("sid", String.valueOf(requestCounter++));
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        Map<String, Object> response = restTemplate.postForObject(tokeRequestUrl, requestBody, Map.class);
        this.bearer = (String) response.get("gl_token");
    }

    public String getContent() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - cacheTime > 1000 * 60 * 5) {
            cachedContent = fetchContent();
            cacheTime = currentTime;
        }
        return cachedContent;
    }


    public String fetchContent() {

        getToken();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + bearer);
        headers.add("Content-Type", "application/json");
        RequestEntity requestEntity = RequestEntity.get(contentRequestUrl).headers(headers).build();
        String response = restTemplate.exchange(requestEntity, String.class).getBody();
        return response;
    }

}
