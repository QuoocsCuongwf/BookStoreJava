package com.example.demo.databaseAccesssObject;

import com.example.demo.BUS.services.SanPhamServices;
import com.example.demo.model.NhanVien;
import com.example.demo.model.SanPham;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SanPhamDAO {
    private List<SanPham> listSanPham=new ArrayList<>();
    ConnectDatabase cnn=new ConnectDatabase();
    public List<SanPham> getListSanPham() {
        String sql = "select * from san_pham";
        String json = cnn.query(sql);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listSanPham=mapper.readValue(json,  new TypeReference<List<SanPham>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listSanPham;
    }

    public void deleteSanPham(String maSanPham) {
        String sql = "delete from san_pham where masp='"+maSanPham+"'";
        System.out.println(sql);
        cnn.insert(sql);
    }
//    public void insertSanPham(SanPham sp) {
//        String sql = "INSERT INTO SAN_PHAM (MASP, TENSP, SL, MATL, MATG, MANXB, NAMXB, DONGIA, SOTRANG, ANHBIA) VALUES (" +
//                "'" + sp.getMasp() + "', " +
//                "'" + sp.getTensp().replace("'", "''") + "', " + // tránh lỗi khi có dấu nháy
//                sp.getSl() + ", " +
//                "'" + sp.getMatl() + "', " +
//                "'" + sp.getMatg() + "', " +
//                "'" + sp.getManxb() + "', " +
//                sp.getNamxb() + ", " +
//                sp.getDongia() + ", " +
//                sp.getSotrang() + ", " +
//                "'" + sp.getAnhbia() + "'" +
//                ");";
//        cnn.update(sql);
//    }
    // duyen
    public void insertSanPham(SanPham sp) {
        String sql = "INSERT INTO SAN_PHAM (MASP, TENSP, SL, MATL, MATG, MANXB, NAMXB, DONGIA, SOTRANG, ANHBIA) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Tạo mảng chứa các tham số để gán vào PreparedStatement
        Object[] params = new Object[] {
                sp.getMasp(),
                sp.getTensp(),
                sp.getSl(),
                sp.getMatl(),
                sp.getMatg(),
                sp.getManxb(),
                sp.getNamxb(),
                sp.getDongia(),
                sp.getSotrang(),
                sp.getAnhbia()
        };
        // Log câu lệnh SQL và các tham số
        System.out.println("SQL: " + sql);
        System.out.println("Parameters: " + Arrays.toString(params));

        // Gọi phương thức insert của ConnectDatabase để thực thi câu lệnh
        cnn.insertSP(sql, params);
    }

    public void updateSanPham(SanPham sp) {
        String sql = "UPDATE SAN_PHAM SET " +
                "TENSP = '" + sp.getTensp().replace("'", "''") + "', " +
                "SL = " + sp.getSl() + ", " +
                "MATL = '" + sp.getMatl() + "', " +
                "MATG = '" + sp.getMatg() + "', " +
                "MANXB = '" + sp.getManxb() + "', " +
                "NAMXB = " + sp.getNamxb() + ", " +
                "DONGIA = " + sp.getDongia() + ", " +
                "SOTRANG = " + sp.getSotrang() + ", " +
                "ANHBIA = '" + sp.getAnhbia() + "' " +
                "WHERE MASP = '" + sp.getMasp() + "'";
        System.out.println(sql);
        cnn.update(sql);
    }

    public static void main(String[] args) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        System.out.println( sanPhamDAO.getListSanPham());


    }

}
