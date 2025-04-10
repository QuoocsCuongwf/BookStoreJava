package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class CallApi {
    private URL url=null;
    HttpURLConnection httpURLConnection=null;
    CallApi(){}
    public void createConnectApi(String api) {
        try {
            this.url =new URL(api);
            this.httpURLConnection=(HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String callGetApi(String api) throws IOException {
        StringBuilder response=new StringBuilder();
        if(httpURLConnection==null){
            createConnectApi(api);
        }
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        String responseCode=httpURLConnection.getResponseMessage();
        BufferedReader br=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        while ((line=br.readLine())!=null){
            response.append(line);
        }
        br.close();
        System.out.println("response:"+responseCode);

        return response.toString();
    }

    public String callPostRequestParam(String api, String key, String value){
        String resultsApi = "";
        try {
            if(httpURLConnection==null){
                createConnectApi(api);
            }
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setDoOutput(true);

            // ❌ Đừng dùng ? ở đây
            String params =key + URLEncoder.encode(value, StandardCharsets.UTF_8); // encode là tốt nhất
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                os.write(params.getBytes(StandardCharsets.UTF_8));
            }

            int status = httpURLConnection.getResponseCode();
            if (status >= 400) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.err.println("ERROR: " + line);
                    }
                }
                throw new RuntimeException("HTTP error: " + status);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            resultsApi = response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultsApi;
    }

    public String callPostRequestBody(String api, String json) {
        String resultsApi="";
        try {
            if(httpURLConnection==null){
                createConnectApi(api);
            }
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            try(OutputStream os=httpURLConnection.getOutputStream()){
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }
            // Đọc phản hồi
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (var reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }
                resultsApi=response.toString();
                System.out.println("Response: " + response.toString());
            }

            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultsApi;
    }
}
