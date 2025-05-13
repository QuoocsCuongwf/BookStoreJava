package com.example.demo.BUS.spring_controller;

import com.example.demo.GuiController.CallApi;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        CallApi callApi=new CallApi();
        callApi.callGetApi("http://localhost:8080/nhanVien/timKiem");
    }
}