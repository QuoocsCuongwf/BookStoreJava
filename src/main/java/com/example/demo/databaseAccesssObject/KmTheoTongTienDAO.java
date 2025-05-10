package com.example.demo.databaseAccesssObject;

import com.example.demo.model.KmTheoTongTien;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.ArrayList;
import java.util.List;

public class KmTheoTongTienDAO {

    List<KmTheoTongTien> listKmTheoTongTien = new ArrayList<KmTheoTongTien>();
    ConnectDatabase condb = new ConnectDatabase();

    public KmTheoTongTienDAO() {
    }

    public List<KmTheoTongTien> getListKmTheoTongTien() {
        String values = condb.query("select * from KM_THEO_HOA_DON");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listKmTheoTongTien = mapper.readValue(values, new TypeReference<List<KmTheoTongTien>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listKmTheoTongTien;
    }

    public void deleteKmTheoTongTien(String maChuongTrinh) {
        System.out.println("Delete chuong trinh khuyen mai " + maChuongTrinh);
        condb.insert("delete from KM_THEO_HOA_DON where MACTKM='" + maChuongTrinh + "'");
        condb.insert("delete from CHUONG_TRINH_KHUYEN_MAI where MACTKM = '" + maChuongTrinh + "'");
    }
    public void addKmTheoTongTien(KmTheoTongTien kmTheoHoaDon) {
        String query =
                "INSERT INTO CHUONG_TRINH_KHUYEN_MAI (mactkm, ngaybd, ngaykt) " +
                        "VALUES ('" + kmTheoHoaDon.getMactkm() + "', '" +
                        kmTheoHoaDon.getNgayBatDau() + "', '" +
                        kmTheoHoaDon.getNgayKetThuc() + "'); " +

                "INSERT INTO KM_THEO_HOA_DON (mactkm, tongtien, phantramkhuyenmai, ngaybatdau, ngayketthuc) " +
                    "VALUES ('" + kmTheoHoaDon.getMactkm() + "', " +
                    kmTheoHoaDon.getTongtien() + ", " +
                    kmTheoHoaDon.getPhantramkhuyenmai() + ", '" +
                    kmTheoHoaDon.getNgayBatDau() + "', '" +
                    kmTheoHoaDon.getNgayKetThuc() + "');";

        condb.insert(query);
    }

    public void UpdateKmTheoTongTien(KmTheoTongTien kmTheoHoaDon) {
        String query = "UPDATE KM_THEO_HOA_DON SET " +
                "PHANTRAMKHUYENMAI = '" + kmTheoHoaDon.getPhantramkhuyenmai() + "', " +
                "TONGTIEN = '" + kmTheoHoaDon.getTongtien() + "', " +
                "NGAYBATDAU = '" + kmTheoHoaDon.getNgayBatDau() + "', " +
                "NGAYKETTHUC = '" + kmTheoHoaDon.getNgayKetThuc() + "' " + // Không có dấu phẩy cuối
                "WHERE MACTKM = '" + kmTheoHoaDon.getMactkm() +

                "UPDATE CHUONG_TRINH_KHUYEN_MAI " +
                "PHANTRAMKHUYENMAI = " + kmTheoHoaDon.getPhantramkhuyenmai() + "'," +
                "NGAYBATDAU = " + kmTheoHoaDon.getNgayBatDau() + "'," +
                "NGAYKETTHUC = " + kmTheoHoaDon.getNgayKetThuc() +
                "WHERE MACTKM = '" + kmTheoHoaDon.getMactkm() + "'," +

                "');";


        try {
            condb.update(query); // Giả sử condb có phương thức update(String)
            System.out.println("Cập nhật khuyến mãi thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
        }
    }

}