package com.example.demo.databaseAccesssObject;

import com.example.demo.model.NhaCungCap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {
    List<NhaCungCap> listNhaCungCap=new ArrayList<NhaCungCap>();
    ConnectDatabase condb=new ConnectDatabase();
    public NhaCungCapDAO() {    }
    public List<NhaCungCap> getListNhaCungCap() {
        String values = condb.query("select * from NHA_CUNG_CAP");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listNhaCungCap=mapper.readValue(values,  new TypeReference<List<NhaCungCap>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listNhaCungCap;
    }

    public void deleteNhaCungCap(String maNCC) {
        System.out.println("Delete nha cung cap "+maNCC);
        condb.insert("delete from NHA_CUNG_CAP where MANCC='"+maNCC+"'");
    }

    public void addNhaCungCap(NhaCungCap nhaCungCap) {
        String query = "INSERT INTO NHA_CUNG_CAP (MANCC, TENNCC, SDT, DIACHI, EMAIL) " +
                "VALUES ('" + nhaCungCap.getMaNhaCungCap() + "', '" +
                nhaCungCap.getTenNhaCungCap() + "', '" +
                nhaCungCap.getSdt() + "', '" +
                nhaCungCap.getDiaChi() + "', '" +
                nhaCungCap.getEmail() + "')";

        condb.insert(query);
    }

    public void updateNhaCungCap(NhaCungCap nhaCungCap) {
        String query = "UPDATE NHA_CUNG_CAP SET " +
                "TENNCC='" + nhaCungCap.getTenNhaCungCap() + "', " +
                "SDT='" + nhaCungCap.getSdt() + "', " +
                "DIACHI='" + nhaCungCap.getDiaChi() + "', " +
                "EMAIL='" + nhaCungCap.getEmail() + "' " +
                "WHERE MANCC='" + nhaCungCap.getMaNhaCungCap() + "'";

        condb.update(query);

    }



}
//
//spring.datasource.url=dbc:sqlserver://localhost:1433;databaseName=bookstore;encrypt=true;trustServerCertificate=true;
//spring.datasource.username=SA
//spring.datasource.password=Admin123@
//        spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
//
//spring.jpa.hibernate.ddl-auto=update
//spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect