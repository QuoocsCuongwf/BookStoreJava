package com.example.demo.databaseAccesssObject;

import com.example.demo.model.KmTheoHoaDon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class KmTheoHoaDonDAO {

    List<KmTheoHoaDon> listKmTheoHoaDon = new ArrayList<KmTheoHoaDon>();
    ConnectDatabase condb = new ConnectDatabase();

    public KmTheoHoaDonDAO() {
    }

    public List<KmTheoHoaDon> getListKmTheoHoaDon() {
        String values = condb.query("SELECT \n" +
                "    ctkm.mactkm,\n" +
                "\tctkm.tenchuongtrinh,\n" +
                "    ctkm.ngaybd,\n" +
                "    ctkm.ngaykt,\n" +
                "    kmhd.phantramkhuyenmai,\n" +
                "    kmhd.SOTIENHOADON\n" +
                "FROM \n" +
                "    CHUONG_TRINH_KHUYEN_MAI ctkm\n" +
                "JOIN \n" +
                "    KM_THEO_HOA_DON kmhd ON ctkm.mactkm = kmhd.mactkm");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(values);
        try {
            mapper.registerModule(new JavaTimeModule());
            listKmTheoHoaDon = mapper.readValue(values, new TypeReference<List<KmTheoHoaDon>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listKmTheoHoaDon;
    }

    public void deleteKhuyenMai(String maChuongTrinhKhuyenMai) {
        System.out.println("Delete ma chuong trinh khuyen mai " + maChuongTrinhKhuyenMai);
        condb.insert("delete from KM_THEO_HOA_DON where MACTKM='" + maChuongTrinhKhuyenMai + "'");
        condb.insert("delete from CHUONG_TRINH_KHUYEN_MAI where MACTKM='" + maChuongTrinhKhuyenMai + "'");
    }

    public void addKhuyenMai(KmTheoHoaDon kmTheoHoaDon) {
        try {
            // Start a transaction (if condb supports it)

            // Format LocalDate fields as YYYY-MM-DD strings
            String ngaybd = kmTheoHoaDon.getNgaybd() != null ? kmTheoHoaDon.getNgaybd().toString() : "NULL";
            String ngaykt = kmTheoHoaDon.getNgaykt() != null ? kmTheoHoaDon.getNgaykt().toString() : "NULL";

            // Escape single quotes in tenchuongtrinh to prevent SQL injection
            String tenchuongtrinh = kmTheoHoaDon.getTenchuongtrinh() != null
                    ? kmTheoHoaDon.getTenchuongtrinh().replace("'", "''")
                    : "";

            // Insert into CHUONG_TRINH_KHUYEN_MAI
            String query1 = "INSERT INTO CHUONG_TRINH_KHUYEN_MAI (mactkm, tenchuongtrinh, ngaybd, ngaykt) " +
                    "VALUES ('" + kmTheoHoaDon.getMactkm() + "', '" + tenchuongtrinh + "', " +
                    (ngaybd.equals("NULL") ? "NULL" : "'" + ngaybd + "'") + ", " +
                    (ngaykt.equals("NULL") ? "NULL" : "'" + ngaykt + "'") + ")";
            condb.insert(query1);

            // Insert into KM_THEO_HOA_DON
            String query2 = "INSERT INTO KM_THEO_HOA_DON (mactkm, phantramkhuyenmai, sotienhoadon) " +
                    "VALUES ('" + kmTheoHoaDon.getMactkm() + "', " +
                    (kmTheoHoaDon.getPhantramkhuyenmai() != null ? kmTheoHoaDon.getPhantramkhuyenmai() : "NULL") + ", " +
                    (kmTheoHoaDon.getTongtien() != null ? kmTheoHoaDon.getTongtien() : "NULL") + ")";
            condb.insert(query2);

        } catch (Exception e) {
            // Roll back the transaction on error
            throw new RuntimeException("Failed to add KhuyenMai: " + e.getMessage(), e);
        }
    }
    public void updateChuongTrinhKhuyenMai(KmTheoHoaDon kmTheoHoaDon) {
        try {


            // Định dạng các trường LocalDate thành chuỗi YYYY-MM-DD
            String ngaybd = kmTheoHoaDon.getNgaybd() != null ? kmTheoHoaDon.getNgaybd().toString() : "NULL";
            String ngaykt = kmTheoHoaDon.getNgaykt() != null ? kmTheoHoaDon.getNgaykt().toString() : "NULL";

            // Thoát ký tự nháy đơn cho các chuỗi
            String mactkm = kmTheoHoaDon.getMactkm() != null ? kmTheoHoaDon.getMactkm().replace("'", "''") : "";
            String tenchuongtrinh = kmTheoHoaDon.getTenchuongtrinh() != null
                    ? kmTheoHoaDon.getTenchuongtrinh().replace("'", "''")
                    : "";

            // Cập nhật bảng CHUONG_TRINH_KHUYEN_MAI
            String query1 = "UPDATE CHUONG_TRINH_KHUYEN_MAI " +
                    "SET tenchuongtrinh = '" + tenchuongtrinh + "', " +
                    "ngaybd = " + (ngaybd.equals("NULL") ? "NULL" : "'" + ngaybd + "'") + ", " +
                    "ngaykt = " + (ngaykt.equals("NULL") ? "NULL" : "'" + ngaykt + "'") + " " +
                    "WHERE mactkm = '" + mactkm + "'";
            condb.update(query1);

            // Cập nhật bảng KM_THEO_HOA_DON
            String query2 = "UPDATE KM_THEO_HOA_DON " +
                    "SET phantramkhuyenmai = " + (kmTheoHoaDon.getPhantramkhuyenmai() != null ? kmTheoHoaDon.getPhantramkhuyenmai() : "NULL") + ", " +
                    "sotienhoadon = " + (kmTheoHoaDon.getTongtien() != null ? kmTheoHoaDon.getTongtien() : "NULL") + " " +
                    "WHERE mactkm = '" + mactkm + "'";
            condb.update(query2);

        } catch (Exception e) {
            // Hoàn tác giao dịch nếu có lỗi
            throw new RuntimeException("Không thể cập nhật khuyến mãi: " + e.getMessage(), e);
        }
    }

}