package com.example.demo.databaseAccesssObject;

import com.example.demo.model.KmTheoSanPham;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class KmTheoSanPhamDAO {

    private List<KmTheoSanPham> listKmTheoSanPham = new ArrayList<>();
    private ConnectDatabase condb = new ConnectDatabase();

    public KmTheoSanPhamDAO() {
    }

    // Lấy danh sách khuyến mãi theo sản phẩm
    public List<KmTheoSanPham> getListKmTheoSanPham() {
        String values = condb.query(
                "SELECT ctkm.mactkm, ctkm.tenchuongtrinh, ctkm.ngaybd, ctkm.ngaykt, " +
                        "kmtsp.masp, kmtsp.phantramkhuyenmai " +
                        "FROM CHUONG_TRINH_KHUYEN_MAI ctkm " +
                        "JOIN KM_THEO_SAN_PHAM kmtsp ON ctkm.mactkm = kmtsp.mactkm");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        System.out.println(values);
        try {
            listKmTheoSanPham = mapper.readValue(values, new TypeReference<List<KmTheoSanPham>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listKmTheoSanPham;
    }

    // Thêm khuyến mãi theo sản phẩm
    public void addKhuyenMai(KmTheoSanPham km) {
        String query = "INSERT INTO KM_THEO_SAN_PHAM (mactkm, masp, phantramkhuyenmai) " +
                "VALUES ('" + km.getMactkm() + "', '" + km.getMasp() + "', '" + km.getPhantramkhuyenmai() + "')";
        condb.insert(query);
    }

    // Cập nhật khuyến mãi theo sản phẩm
    public void updateKhuyenMai(KmTheoSanPham km) {
        String query = "UPDATE KM_THEO_SAN_PHAM SET " +
                "phantramkhuyenmai = '" + km.getPhantramkhuyenmai() + "' " +
                "WHERE mactkm = '" + km.getMactkm() + "' AND masp = '" + km.getMasp() + "'";
        condb.update(query);
    }

    // Xoá khuyến mãi theo sản phẩm
    public void deleteKhuyenMai(String mactkm, String masp) {
        String query = "DELETE FROM KM_THEO_SAN_PHAM " +
                "WHERE mactkm = '" + mactkm + "' AND masp = '" + masp + "'";
        condb.update(query);
    }
}

