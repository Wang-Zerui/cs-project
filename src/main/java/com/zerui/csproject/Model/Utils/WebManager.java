package com.zerui.csproject.Model.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
public class WebManager {
    public static HashMap<String, Object> sendPOST(String email, String password) throws IOException {
        URL url = new URL(DEF.apiURL+DEF.webKey);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");
        String data = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}", email, password);
        byte[] out = data.getBytes(StandardCharsets.UTF_8);
        OutputStream stream = http.getOutputStream();
        stream.write(out);
        if (http.getResponseCode()==200) {
            String resp = getResponseBody(http).trim();
            System.out.println(resp);
            HashMap<String, Object> hashMap = parseJSON(resp);
            http.disconnect();
            return hashMap;
        } else {
            http.disconnect();
            return null;
        }
    }

    public static String getResponseBody(HttpURLConnection conn) {
        BufferedReader br;
        StringBuilder body;
        String line = "";
        try {
            br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            body = new StringBuilder();
            while ((line = br.readLine()) != null)
                body.append(line);
            return body.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap parseJSON(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, HashMap.class);
    }
}
