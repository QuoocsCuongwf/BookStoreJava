package com.example.demo.databaseAccesssObject;

import com.example.demo.model.NhanVien;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    List<NhanVien> listNhanVien=new ArrayList<NhanVien>();
    ConnectDatabase condb=new ConnectDatabase();
    public NhanVienDAO() {    }
    public List<NhanVien> getListNhanVien() {
        String values = condb.query("select * from Nhan_Vien");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listNhanVien=mapper.readValue(values,  new TypeReference<List<NhanVien>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listNhanVien;
    }

    public void deleteNhanVien(String maNhanVien) {
        System.out.println("Delete nhan vien "+maNhanVien);
        condb.insert("delete from NHAN_VIEN where MANV='"+maNhanVien+"'");
    }

    public void addNhanVien(NhanVien nhanVien) {
        String query = "INSERT INTO NHAN_VIEN (manv, honv, tennv, cccd, chucvu, mail, ngayvaolam, luong) " +
                "VALUES ('" + nhanVien.getManv() + "', '" +
                nhanVien.getHonv() + "', '" +
                nhanVien.getTennv() + "', '" +
                nhanVien.getCccd() + "', '" +
                nhanVien.getChucvu() + "', '" +
                nhanVien.getMail() + "', '" +
                nhanVien.getNgayvaolam() + "', " +
                nhanVien.getLuong() + ")";

        condb.insert(query);
    }



}
//
//spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=bookstore;encrypt=true;trustServerCertificate=true;
//spring.datasource.username=SA
//spring.datasource.password=Admin123@
//        spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
//
//spring.jpa.hibernate.ddl-auto=update
//spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect