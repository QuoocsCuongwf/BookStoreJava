package com.example.demo.GuiController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class CallApi {
    private URL url=null;
    HttpURLConnection httpURLConnection=null;
    public CallApi(){}
    public void createConnectApi(String api) {
        try {
            this.url =new URL(api);
            this.httpURLConnection=(HttpURLConnection) url.openConnection(); // tạo kết nối chuẩn bị sẵn chứ chưa thật sự kết nối
            // ?trả về một URLConnection object(lớp cha của lớp HttpURLConnection) – một cái “cổng” để cấu hình:
            //        Thiết lập phương thức (GET, POST)
            //        Thêm header
            //        Gửi dữ liệu (với POST)
            //        Và nhiều thứ khác
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String callGetApi(String api) throws IOException { // throws IOException: có thể xảy ra lỗi mạng, nên khai báo để bắt buộc xử lý.
        StringBuilder response=new StringBuilder();// nối chuỗi hiệu quả, chứa dữ liệu từ server trả về
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
                createConnectApi(api);
            
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setDoOutput(true);

            // ❌ Đừng dùng ? ở đây
            String params =key + URLEncoder.encode(value, StandardCharsets.UTF_8); // encode là tốt nhất
            //String params = URLEncoder.encode(key, StandardCharsets.UTF_8) + URLEncoder.encode(value, StandardCharsets.UTF_8);
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
        String resultsApi = "";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Gửi JSON vào body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 200 && responseCode < 300 ?
                                    connection.getInputStream() :
                                    connection.getErrorStream(),
                            StandardCharsets.UTF_8))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                resultsApi = response.toString();
                System.out.println("Response: " + resultsApi);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return resultsApi;
    }
    public int callPostRequestBody(String api) {
        String resultsApi = "";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 200 && responseCode < 300 ?
                                    connection.getInputStream() :
                                    connection.getErrorStream(),
                            StandardCharsets.UTF_8))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                resultsApi = response.toString();
                System.out.println("Response: " + resultsApi);
            }
            return responseCode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
