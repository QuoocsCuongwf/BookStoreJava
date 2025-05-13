package com.example.demo.databaseAccesssObject;

import com.example.demo.model.KmTheoSanPham;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class KmTheoSanPhamDAO {
    List<KmTheoSanPham> listKmTheoSanPham = new ArrayList<KmTheoSanPham>();
    ConnectDatabase condb = new ConnectDatabase();


    public List<KmTheoSanPham> getListKmTheoSanPham() {
        String values = condb.query( "SELECT ctkm.mactkm, ctkm.tenchuongtrinh, ctkm.ngaybd, ctkm.ngaykt, " +
                "kmtsp.masp, kmtsp.phantramkhuyenmai " +
                "FROM CHUONG_TRINH_KHUYEN_MAI ctkm " +
                "JOIN KM_THEO_SAN_PHAM kmtsp ON ctkm.mactkm = kmtsp.mactkm");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listKmTheoSanPham = mapper.readValue(values, new TypeReference<List<KmTheoSanPham>>() {
            });
        } catch (JsonProcessingException e) {
            System.err.println("DAO :LỖI JSON -> List<KmTheoSanPham>: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return listKmTheoSanPham;
    }

    public void deleteKmTheoSanPham(String maKmai) {
        System.out.println("Delete khuyen mai theo san pham " + maKmai);
        condb.insert("delete from KM_THEO_SAN_PHAM where MACTKM='" + maKmai + "'");
        condb.insert("delete from CHUONG_TRINH_KHUYEN_MAI where MACTKM='" + maKmai + "'");
    }

    public void addKmTheoSanPham(KmTheoSanPham kmTheoSanPham) {
        String ngaybd = kmTheoSanPham.getNgaybd() != null ? kmTheoSanPham.getNgaybd().toString() : "NULL";
        String ngaykt = kmTheoSanPham.getNgaykt() != null ? kmTheoSanPham.getNgaykt().toString() : "NULL";

        // Escape single quotes in tenchuongtrinh to prevent SQL injection
        String tenchuongtrinh = kmTheoSanPham.getTenchuongtrinh() != null
                ? kmTheoSanPham.getTenchuongtrinh().replace("'", "''")
                : "";

        String query1 =
                "INSERT INTO CHUONG_TRINH_KHUYEN_MAI (mactkm, tenchuongtrinh, ngaybd, ngaykt) " +
                        "VALUES ('" + kmTheoSanPham.getMactkm() + "', '" + tenchuongtrinh + "', " +
                        (ngaybd.equals("NULL") ? "NULL" : "'" + ngaybd + "'") + ", " +
                        (ngaykt.equals("NULL") ? "NULL" : "'" + ngaykt + "'") + ")";
        String query2 =
                "INSERT INTO KM_THEO_SAN_PHAM (mactkm, masp, phantramkhuyenmai) " +
                        "VALUES ('" + kmTheoSanPham.getMactkm() + "', '" +
                        kmTheoSanPham.getMasp() + "', '" +
                        kmTheoSanPham.getPhantramkhuyenmai() + "');";
        condb.insert(query1);
        condb.insert(query2);

    }

    public void UpdateKmTheoSanPham(KmTheoSanPham kmTheoSanPham) {
        String query1= "UPDATE CHUONG_TRINH_KHUYEN_MAI SET " +
                "tenchuongtrinh = '" + kmTheoSanPham.getTenchuongtrinh() + "', " +
                "NGAYBD = '" + kmTheoSanPham.getNgaybd() + "', " +
                "NGAYKT = '" + kmTheoSanPham.getNgaykt() +"' "+
                "WHERE MACTKM = '" + kmTheoSanPham.getMactkm() + "'" ;
        String query2 = "UPDATE KM_THEO_SAN_PHAM SET " +
                "MASP = '" + kmTheoSanPham.getMasp() + "', " +
                "PHANTRAMKHUYENMAI = " + kmTheoSanPham.getPhantramkhuyenmai() + " "+// Không có dấu phẩy cuối
                "WHERE MACTKM = '" + kmTheoSanPham.getMactkm() + "';" ;
        try {
            condb.update(query1); // Giả sử condb có phương thức update(String)
            condb.update(query2);
            System.out.println("Cập nhật khuyến mãi thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
        }
    }
}