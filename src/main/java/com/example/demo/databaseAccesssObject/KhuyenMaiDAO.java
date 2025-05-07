package com.example.demo.databaseAccesssObject;

import com.example.demo.model.KmTheoHoaDon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {

    List<KmTheoHoaDon> listKhuyenMai = new ArrayList<KmTheoHoaDon>();
    ConnectDatabase condb = new ConnectDatabase();

    public KhuyenMaiDAO() {
    }

    public List<KmTheoHoaDon> getListKhuyenMai() {
        String values = condb.query("select * from KM_THEO_HOA_DON");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listKhuyenMai = mapper.readValue(values, new TypeReference<List<KmTheoHoaDon>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listKhuyenMai;
    }

    public void deleteKhuyenMai(String maChuongTrinhKhuyenMai) {
        System.out.println("Delete ma chuong trinh khuyen mai " + maChuongTrinhKhuyenMai);
        condb.insert("delete from KM_THEO_HOA_DON where MACTKM='" + maChuongTrinhKhuyenMai + "'");
    }

    public void addKhuyenMai(KmTheoHoaDon kmTheoHoaDon) {
        String query = "INSERT INTO KM_THEO_HOA_DON (mactkm,tongtien,phantramkhuyenmai) " +
                "VALUES ('" + kmTheoHoaDon.getMactkm() + "', '" +
                kmTheoHoaDon.getTongtien() + "', '" +
                kmTheoHoaDon.getPhantramkhuyenmai() + "', '" +
                ")";

        condb.insert(query);
    }
    public void updateChuongTrinhKhuyenMai(KmTheoHoaDon kmTheoHoaDon) {
        String query = "UPDATE KM_THEO_HOA_DON" +
           //     "SET MASANPHAM='" + kmTheoHoaDon.getMasanpham()
                 "SET PHAN TRAM KHUYEN MAI='" + kmTheoHoaDon.getPhantramkhuyenmai()
                + "', TONG TIEN='" + kmTheoHoaDon.getTongtien()
                +
                "'";
        condb.update(query);
    }
}