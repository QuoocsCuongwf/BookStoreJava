package com.example.demo.databaseAccesssObject;

import com.example.demo.model.ChuongTrinhKM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class ChuongTrinhKMDAO {
    List<ChuongTrinhKM> listChuongTrinhKM = new ArrayList<ChuongTrinhKM>();
    ConnectDatabase condb = new ConnectDatabase();


    public List<ChuongTrinhKM> getAllChuongTrinhKM() {
        String values = condb.query("select * from CHUONG_TRINH_KHUYEN_MAI");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listChuongTrinhKM = mapper.readValue(values, new TypeReference<List<ChuongTrinhKM>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listChuongTrinhKM;
    }
}
