package com.example.demo.databaseAccesssObject;

import com.example.demo.model.ChuongTrinhKhuyenMai;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class ChuongTrinhKMDAO {
    List<ChuongTrinhKhuyenMai> listChuongTrinhKhuyenMai = new ArrayList<ChuongTrinhKhuyenMai>();
    ConnectDatabase condb = new ConnectDatabase();

    public ChuongTrinhKMDAO() {
    }

    public List<ChuongTrinhKhuyenMai> getListChuongTrinhKhuyenMai() {
        String values = condb.query("select * from CHUONG_TRINH_KHUYEN_MAI");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listChuongTrinhKhuyenMai = mapper.readValue(values, new TypeReference<List<ChuongTrinhKhuyenMai>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listChuongTrinhKhuyenMai;
    }

    public void deleteChuongTrinhKhuyenMai(String maChuongTrinh) {
        System.out.println("Delete chuong trinh khuyen mai " + maChuongTrinh);
        condb.insert("delete from CHUONG_TRINH_KHUYEN_MAI where MACTKM='" + maChuongTrinh + "'");
    }

    public void addChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai kmTheoHoaDon) {
        String query = "INSERT INTO KM_THEO_HOA_DON (mactkm, phantramkhuyenmai, ngaybatdau, ngayketthuc) " +
                "VALUES ('" + kmTheoHoaDon.getMactkm() + "'," +
                kmTheoHoaDon.getPhantramkhuyenmai() + "," +
                "'" + kmTheoHoaDon.getNgayBatDau() + "'" + "," +
                "'" + kmTheoHoaDon.getNgayKetThuc() + "'" + ")";

        condb.insert(query);
    }
    public void UpdateChuongTrinhChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai kmTheoHoaDon) {
        String query = "UPDATE CHUONG_TRINH_KHUYEN_MAI SET " +
                "PHANTRAMKHUYENMAI = '" + kmTheoHoaDon.getPhantramkhuyenmai() + "', " +
              ///  "TONGTIEN = '" + kmTheoHoaDon.getTongtien() + "', " +
                "NGAYBATDAU = '" + kmTheoHoaDon.getNgayBatDau() + "', " +
                "NGAYKETTHUC = '" + kmTheoHoaDon.getNgayKetThuc() + "' " + // Không có dấu phẩy cuối
                "WHERE MACTKM = '" + kmTheoHoaDon.getMactkm() + "'";

        try {
            condb.update(query); // Giả sử condb có phương thức update(String)
            System.out.println("Cập nhật khuyến mãi thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
        }
    }
}
