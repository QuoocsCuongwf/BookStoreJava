package com.example.demo.databaseAccesssObject;

import com.example.demo.model.NhanVien;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    List<NhanVien> listNhanVien=new ArrayList<NhanVien>();;
    public NhanVienDAO() {    }
    public List<NhanVien> getListNhanVien() {
        ConnectDatabase condb=new ConnectDatabase();
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



}
//
//spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=bookstore;encrypt=true;trustServerCertificate=true;
//spring.datasource.username=SA
//spring.datasource.password=Admin123@
//        spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
//
//spring.jpa.hibernate.ddl-auto=update
//spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect