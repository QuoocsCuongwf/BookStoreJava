package com.example.demo.databaseAccesssObject;

import com.example.demo.model.TheLoai;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {
    List<TheLoai> listTheLoai = new ArrayList<>();
    ConnectDatabase condb = new ConnectDatabase();

    public TheLoaiDAO() {
    }

    public List<TheLoai> getListTheLoai() {
        String values = condb.query("select * from THE_LOAI");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listTheLoai = mapper.readValue(values, new TypeReference<List<TheLoai>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listTheLoai;
    }

    public void deleteTheLoai(String maTheLoai) {
        System.out.println("Delete the loai " + maTheLoai);
        condb.insert("delete from THE_LOAI where MATL='" + maTheLoai + "'");
    }

    public void addTheLoai(TheLoai theLoai) {
        String query = "INSERT INTO THE_LOAI (MATL, TENTL) " +
                "VALUES ('" + theLoai.getMaTheLoai() + "', '" +
                theLoai.getTenTheLoai() + "')";

        condb.insert(query);
    }

    public void updateTheLoai(TheLoai theLoai) {
        String query = "UPDATE THE_LOAI SET " +
                "TENTL='" + theLoai.getTenTheLoai() + "' " +
                "WHERE MATL='" + theLoai.getMaTheLoai() + "'";

        condb.update(query);
    }
}