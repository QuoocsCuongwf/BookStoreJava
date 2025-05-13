package com.example.demo.databaseAccesssObject;

import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.NhanVien;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.aop.scope.ScopedObject;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    ConnectDatabase conn=new ConnectDatabase();
    List<ChiTietHoaDon> listChiTietHoaDon=new ArrayList<ChiTietHoaDon>();
    public List<ChiTietHoaDon> getList() {
        String sql = "select * from chi_tiet_hoa_don";
        String json= conn.query(sql);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listChiTietHoaDon=mapper.readValue(json,  new TypeReference<List<ChiTietHoaDon>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listChiTietHoaDon;
    }
    public void addChiTietHoaDon(ChiTietHoaDon don) {
        String query = "INSERT INTO CHI_TIET_HOA_DON (MAHD, MASP, SL, DONGIA, THANHTIEN) VALUES (" +
                "'" + don.getMahd() + "', " +
                "'" + don.getMasp() + "', " +
                don.getSl() + ", " +
                don.getDongia() + ", " +
                don.getThanhtien() + ")";

        conn.insert(query);
    }
    public void deleteChiTietHoaDon(String mahd,String masp) {
        String sql="delete Chi_tiet_hoa_don where mahd='"+mahd+"' and masp='"+masp+"'";
        conn.update(sql);
    }
    public void deleteChiTietHoaDon(String mahd) {
        String sql="delete Chi_tiet_hoa_don where mahd='"+mahd+"'";
        conn.update(sql);
    }
    public void updateChiTietHoaDon(ChiTietHoaDon don) {
        String query = "UPDATE CHI_TIET_HOA_DON SET " +
                "SL = " + don.getSl() + ", " +
                "DONGIA = " + don.getDongia() + ", " +
                "THANHTIEN = " + don.getThanhtien() + " " +
                "WHERE MAHD = '" + don.getMahd() + "' " +
                "AND MASP = '" + don.getMasp() + "'";

        conn.update(query);
    }


}
