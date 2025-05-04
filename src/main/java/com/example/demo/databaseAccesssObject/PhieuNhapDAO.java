package com.example.demo.databaseAccesssObject;

import com.example.demo.model.PhieuNhap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {

    List<PhieuNhap> listPhieuNhap = new ArrayList<PhieuNhap>();
    ConnectDatabase condbtg =new ConnectDatabase();
    public PhieuNhapDAO() {}

    public List<PhieuNhap> getListPhieuNhap() {
        String values = condbtg.query("select * from Phieu_Nhap");
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.registerModule(new JavaTimeModule());
            listPhieuNhap=mapper.readValue(values, new TypeReference<List<PhieuNhap>>() {});
        }  catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return listPhieuNhap;
    }
    public void deletePhieuNhap(String maPhieuNhap) {
        System.out.println("deletePhieuNhap"+maPhieuNhap);
        condbtg.insert("delete from PHIEU_NHAP where MAPN='"+maPhieuNhap+"'");
    }
    public void addPhieuNhap(PhieuNhap phieuNhap) {
        String query = "INSERT INTO PHIEU_NHAP (mapn,ngaynhap,manv,mancc,tongtien)"+
                "VALUES('"+phieuNhap.getMapn() + "','" +
                phieuNhap.getNgaynhap() +"', " +
                phieuNhap.getManv()+"', '" +
                phieuNhap.getMancc() + "', '" +
                phieuNhap.getTongtien() +")";
        condbtg.insert(query);
    }
    public void updatePhieuNhap(PhieuNhap phieuNhap){
        String query = "UPDATE PHIEU_NHAP" +
                "SET NGAYNHAP= "+phieuNhap.getNgaynhap()
                +",MANV= "+phieuNhap.getManv()
                +",MANCC= "+phieuNhap.getMancc()
                +",TONGTIEN= "+phieuNhap.getTongtien()
                +"WHERE MAPN='"+phieuNhap.getMapn()+"'";
        condbtg.update(query);

    }
}