package com.example.demo.databaseAccesssObject;

import com.example.demo.model.KmTheoSoTienHoaDon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.ArrayList;
import java.util.List;

public class KmTheoSoTienHoaDonDAO {

    List<KmTheoSoTienHoaDon> listKmTheoSoTienHoaDon = new ArrayList<KmTheoSoTienHoaDon>();
    ConnectDatabase condb = new ConnectDatabase();

    public KmTheoSoTienHoaDonDAO() {
    }

    public List<KmTheoSoTienHoaDon> getListKmTheoSoTienHoaDon() {
        String values = condb.query("select * from KM_THEO_HOA_DON");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listKmTheoSoTienHoaDon = mapper.readValue(values, new TypeReference<List<KmTheoSoTienHoaDon>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listKmTheoSoTienHoaDon;
    }

    public void deleteKmTheoSoTienHoaDon(String maChuongTrinh) {
        System.out.println("Delete khuyen mai theo tong tien " + maChuongTrinh);
        condb.insert("delete from KM_THEO_HOA_DON where MACTKM='" + maChuongTrinh + "'");
        condb.insert("delete from CHUONG_TRINH_KHUYEN_MAI where MACTKM = '" + maChuongTrinh + "'");
    }
    public void addKmTheoSoTienHoaDon(KmTheoSoTienHoaDon kmTheoHoaDon) {
        String query =
                "INSERT INTO CHUONG_TRINH_KHUYEN_MAI (mactkm, phantramkhuyenmai, ngaybd, ngaykt) " +
                        "VALUES ('" + kmTheoHoaDon.getMactkm() + "', " +
                        kmTheoHoaDon.getPhantramkhuyenmai() + ", '" +
                        kmTheoHoaDon.getNgaybd() + "', '" +
                        kmTheoHoaDon.getNgaykt() + "'); " +

                        "INSERT INTO KM_THEO_HOA_DON (mactkm, sotienhoadon, phantramkhuyenmai, ngaybd, ngaykt) " +
                        "VALUES ('" + kmTheoHoaDon.getMactkm() + "', " +
                        kmTheoHoaDon.getSotienhoadon() + ", " +
                        kmTheoHoaDon.getPhantramkhuyenmai() + ", '" +
                        kmTheoHoaDon.getNgaybd() + "', '" +
                        kmTheoHoaDon.getNgaykt() + "');";
        condb.insert(query);
    }

    public void UpdateKmTheoSoTienHoaDon(KmTheoSoTienHoaDon kmTheoHoaDon) {
        String query = "UPDATE KM_THEO_HOA_DON SET " +
                "PHANTRAMKHUYENMAI = " + kmTheoHoaDon.getPhantramkhuyenmai() + ", " +
                "sotienhoadon = " + kmTheoHoaDon.getSotienhoadon() + ", " +
                "NGAYBD = '" + kmTheoHoaDon.getNgaybd() + "', " +
                "NGAYKT = '" + kmTheoHoaDon.getNgaykt() + "' " + // Không có dấu phẩy cuối
                "WHERE MACTKM = '" + kmTheoHoaDon.getMactkm() +"'";

        String query2= "UPDATE CHUONG_TRINH_KHUYEN_MAI SET" +
                "PHANTRAMKHUYENMAI = " + kmTheoHoaDon.getPhantramkhuyenmai() + ", " +
                "NGAYBD = '" + kmTheoHoaDon.getNgaybd() + "', " +
                "NGAYKT = '" + kmTheoHoaDon.getNgaykt() +"' "+
                "WHERE MACTKM = '" + kmTheoHoaDon.getMactkm() + "'";
        try {
            condb.update(query); // Giả sử condb có phương thức update(String)
            condb.update(query2);
            System.out.println("Cập nhật khuyến mãi thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
        }
    }

}