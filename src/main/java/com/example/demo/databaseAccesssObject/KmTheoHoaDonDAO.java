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
    }

    public void addKhuyenMai(KmTheoHoaDon kmTheoHoaDon) {
        String query = "INSERT INTO NHAN_VIEN (mactkm,tongtien,phantramkhuyenmai) " +
                "VALUES ('" + kmTheoHoaDon.getMactkm() + "', '" +
                kmTheoHoaDon.getTongtien() + "', '" +
                kmTheoHoaDon.getPhantramkhuyenmai() + "', '" +
                ")";

        condb.insert(query);
    }
    public void updateChuongTrinhKhuyenMai(KmTheoHoaDon kmTheoHoaDon) {
        String query = "UPDATE NHAN_VIEN" +
                "SET TENNV='" + kmTheoHoaDon.getMactkm()
                + "', CCCD='" + kmTheoHoaDon.getPhantramkhuyenmai()
                + "', CHUCVU='" + kmTheoHoaDon.getTongtien()
                +
                "'";
        condb.update(query);
    }

}