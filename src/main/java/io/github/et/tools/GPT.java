package io.github.et.tools;

import com.alibaba.fastjson2.JSONObject;
import io.github.et.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GPT {
    public static String getReply(String question) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(Main.URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + Main.APIKEY);
            connection.setRequestProperty("x-foo", "true");
            connection.setDoOutput(true);
            String jsonInputString = "{"
                    + "\"model\": \"gpt-4o-mini\","
                    + "\"messages\": ["
                    + "{"
                    + "\"role\": \"user\","
                    + "\"content\": \""+question+"\""
                    + "}"
                    + "]"
                    + "}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JSONObject jsonObject = JSONObject.parseObject(response.toString());
            String content = jsonObject.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            return(content);
        } catch (Exception e) {
            return("出错: " + e.getMessage());
        }
    }
}
