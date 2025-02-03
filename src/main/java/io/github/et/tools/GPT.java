package io.github.et.tools;

import com.alibaba.fastjson2.JSONObject;
import io.github.et.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GPT {
    private static HashMap<Long,List<JSONObject>> messageHistory = new HashMap<>();

    public static String getReply(long groupNum,String question) {
        if(!messageHistory.containsKey(groupNum)) {
            messageHistory.put(groupNum,new ArrayList<>());
        }
        try {
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messageHistory.get(groupNum).add(userMessage);
            if(messageHistory.get(groupNum).size() > Integer.parseInt((String) Main.botInfo.getOrDefault("Max_Message_Count","16"))) {
                messageHistory.get(groupNum).remove(0);
            }
            JSONObject info = new JSONObject();
            info.put("model", "gpt-4o-mini");
            info.put("messages", messageHistory.get(groupNum));
            HttpURLConnection connection = (HttpURLConnection) new URL(Main.URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + Main.APIKEY);
            connection.setRequestProperty("x-foo", "true");
            connection.setDoOutput(true);
            String jsonInputString = info.toJSONString();
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
            if(content==null) {
                throw new NullPointerException("content is null");
            } else if(content.equals("null")){
                throw new NullPointerException("content is null");
            } else {
                JSONObject assistantMessage = new JSONObject();
                assistantMessage.put("role", "assistant");
                assistantMessage.put("content", content);
                messageHistory.get(groupNum).add(assistantMessage);
            }
            return content;

        } catch (Exception e) {
            messageHistory.get(groupNum).remove(messageHistory.get(groupNum).size() - 1);
            return "出错: " + e.getMessage();
        }
    }
}
