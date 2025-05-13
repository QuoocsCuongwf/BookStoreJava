package com.example.demo.BUS.services;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

import com.example.demo.model.HoaDon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MomoPayment {

    public static String CreateQRCode(String mahd) {
        try {
            HoaDonServices hoaDonServices = new HoaDonServices();
            HoaDon hoaDon=new HoaDon();
                    hoaDon=hoaDonServices.findByIdHoaDon(mahd);
            String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
            String partnerCode = "MOMO";
            String accessKey = "F8BBA842ECF85";
            String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";

            String orderId = mahd;
            String requestId = UUID.randomUUID().toString();
            String orderInfo = "SACH GU SHOP";
            String redirectUrl = "https://webhook.site/redirect";
            String ipnUrl = "https://webhook.site/ipn";
            String amount = String.valueOf((int) hoaDon.getTongtien()); // MoMo chỉ chấp nhận số nguyên
            String extraData = "";
            String requestType = "captureWallet";

            // Chuỗi để ký
            String rawSignature = String.format(
                    "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                    accessKey, amount, extraData, ipnUrl, orderId, orderInfo, partnerCode, redirectUrl, requestId, requestType
            );

            // Tạo chữ ký
            String signature = hmacSHA256(rawSignature, secretKey);

            // Tạo request JSON
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode json = objectMapper.createObjectNode();
            json.put("partnerCode", partnerCode);
            json.put("partnerName", "MoMo Test");
            json.put("storeId", "Store123");
            json.put("requestId", requestId);
            json.put("amount", amount);
            json.put("orderId", orderId);
            json.put("orderInfo", orderInfo);
            json.put("redirectUrl", redirectUrl);
            json.put("ipnUrl", ipnUrl);
            json.put("lang", "vi");
            json.put("extraData", extraData);
            json.put("requestType", requestType);
            json.put("signature", signature);

            // Gửi request
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();

            // Đọc phản hồi
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder responseBuilder = new StringBuilder();
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line);
            }
            in.close();

            // In kết quả
            JsonNode josonResponse = objectMapper.readTree(responseBuilder.toString());
            ObjectNode response = (ObjectNode) josonResponse;
            System.out.println("Phản hồi từ MoMo:");
            System.out.println(josonResponse.toString());
            System.out.println("👉 Truy cập payUrl: " + response.get("payUrl").asText());
            return response.get("payUrl").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String hmacSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
