package com.example.demo.databaseAccesssObject;

import com.example.demo.model.NhaXuatBan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class NhaXuatBanDAO {
    List<NhaXuatBan> listNhaXuatBan = new ArrayList<NhaXuatBan>();
    ConnectDatabase condb = new ConnectDatabase();

    public NhaXuatBanDAO() {
    }

    public List<NhaXuatBan> getListNhaXuatBan() {
        String values = condb.query("select * from NHA_XUAT_BAN");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listNhaXuatBan = mapper.readValue(values, new TypeReference<List<NhaXuatBan>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listNhaXuatBan;
    }

    public void deleteNhaXuatBan(String maNXB) {
        System.out.println("Delete nha xuat ban " + maNXB);
        condb.insert("delete from NHA_XUAT_BAN where MANXB='" + maNXB + "'");
    }

    public void addNhaXuatBan(NhaXuatBan nhaXuatBan) {
        String query = "INSERT INTO NHA_XUAT_BAN (MANXB, TENNXB, SDT, DIACHI, EMAIL) " +
                "VALUES ('" + nhaXuatBan.getMaNhaXuatBan() + "', '" +
                nhaXuatBan.getTenNhaXuatBan() + "', '" +
                nhaXuatBan.getSdt() + "', '" +
                nhaXuatBan.getDiaChi() + "', '" +
                nhaXuatBan.getEmail() + "')";

        condb.insert(query);
    }

    public void updateNhaXuatBan(NhaXuatBan nhaXuatBan) {
        String query = "UPDATE NHA_XUAT_BAN SET " +
                "TENNXB='" + nhaXuatBan.getTenNhaXuatBan() + "', " +
                "SDT='" + nhaXuatBan.getSdt() + "', " +
                "DIACHI='" + nhaXuatBan.getDiaChi() + "', " +
                "EMAIL='" + nhaXuatBan.getEmail() + "' " +
                "WHERE MANXB='" + nhaXuatBan.getMaNhaXuatBan() + "'";

        condb.update(query);
    }
}