package com.example.demo.databaseAccesssObject;

import com.example.demo.model.KhachHang;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.ArrayList;
import java.util.List;
// xong
public class KhachHangDAO {
    List<KhachHang> listKhachHang = new ArrayList<>() ;
    ConnectDatabase connectDatabase = new ConnectDatabase();
    public KhachHangDAO() {}

    public List<KhachHang> getListKhachHang(){
        String values = connectDatabase.query("select * from Khach_Hang");
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            objectMapper.registerModule(new JavaTimeModule());
            listKhachHang = objectMapper.readValue(values, new TypeReference<List<KhachHang>>() {});
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return listKhachHang;
    }
    public void deleteKhachHang (String maKhachHang){
        System.out.println("deleted KhachHang:"+maKhachHang);
        connectDatabase.insert("DELETE FROM KHACH_HANG WHERE MAKH='"+maKhachHang+"'");
    }
    public void   addKhachHang (KhachHang khachHang){
        String query = "INSERT INTO KHACH_HANG (makh, hokh, tenkh, email, sdt,diachi) " +
                "VALUES ('" + khachHang.getMakh() + "', '" +
                khachHang.getHokh() + "', '" +
                khachHang.getTenkh() + "', '" +
                khachHang.getEmail() + "', '" +
                khachHang.getSdt() + "', '" +
                khachHang.getDiachi()  + "')";

       connectDatabase.insert(query);
    }
    public void updateKhachHang(KhachHang khachHang) {
        String query = "UPDATE KHACH_HANG " +
                "SET hokh = '" + khachHang.getHokh() + "', " +
                "tenkh = '" + khachHang.getTenkh() + "', " +
                "email = '" + khachHang.getEmail() + "', " +
                "sdt = '" + khachHang.getSdt() + "', " +
                "diachi = '" + khachHang.getDiachi() + "' " +
                "WHERE makh = '" + khachHang.getMakh() + "'";
        connectDatabase.update(query);
    }



}