package com.example.demo.databaseAccesssObject;

import com.example.demo.model.HoaDon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    List<HoaDon> listHoaDon = new ArrayList<>();
    ConnectDatabase condb = new ConnectDatabase();

    public HoaDonDAO() {}

    public List<HoaDon> getListHoaDon() {
        String values = condb.query("SELECT * FROM HOA_DON");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listHoaDon = mapper.readValue(values, new TypeReference<List<HoaDon>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listHoaDon;
    }

    public void deleteHoaDon(String maHoaDon) {
        System.out.println("Delete hoa don " + maHoaDon);
        condb.insert("DELETE FROM HOA_DON WHERE MAHD='" + maHoaDon + "'");
    }

    public void addHoaDon(HoaDon hoaDon) {
        String query = "INSERT INTO HOA_DON (mahd, ngaylap, manv, makh, tongtien) VALUES ('" +
                hoaDon.getMahd() + "', '" +
                hoaDon.getNgaylap() + "', '" +
                hoaDon.getManv() + "', '" +
                hoaDon.getMakh() + "', " +
                hoaDon.getTongtien() + ")";

        condb.insert(query);
    }

    public void updateHoaDon(HoaDon hoaDon) {
        String query = "UPDATE HOA_DON SET " +
                "NGAYLAP = '" + hoaDon.getNgaylap() + "', " +
                "MANV = '" + hoaDon.getManv() + "', " +
                "MAKH = '" + hoaDon.getMakh() + "', " +
                "TONGTIEN = " + hoaDon.getTongtien() + " " +
                "WHERE MAHD = '" + hoaDon.getMahd() + "'";

        condb.update(query);
    }

    public static void main(String[] args) {
        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        List<HoaDon> list = hoaDonDAO.getListHoaDon();
        list.forEach(hd -> System.out.println(hd.getMahd() + " - " + hd.getTongtien()));
    }
}

