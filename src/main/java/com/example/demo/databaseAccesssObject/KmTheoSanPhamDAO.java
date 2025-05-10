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

    public KmTheoSanPhamDAO() {
    }

    public List<KmTheoSanPham> getListKmTheoSanPham() {
        String values = condb.query("select * from CHUONG_TRINH_KHUYEN_MAI");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listKmTheoSanPham = mapper.readValue(values, new TypeReference<List<KmTheoSanPham>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listKmTheoSanPham;
    }

    public void deleteKmTheoSanPham(String maKmai) {
        System.out.println("Delete chuong trinh khuyen mai " + maKmai);
        condb.insert("delete from KM_THEO_SAN_PHAM where MACTKM='" + maKmai + "'");
        condb.insert("delete from CHUONG_TRINH_KHUYEN_MAI where MACTKM='" + maKmai + "'");
    }

    public void addKmTheoSanPham(KmTheoSanPham kmTheoSanPham) {
        String query =
                "INSERT INTO KM_THEO_SAN_PHAM (mactkm, masp, phantramkhuyenmai, ngaybatdau, ngayketthuc) " +
                        "VALUES ('" + kmTheoSanPham.getMactkm() + "', '" +
                        kmTheoSanPham.getMasp() + "', " +
                        kmTheoSanPham.getPhantramkhuyenmai() + ", '" +
                        kmTheoSanPham.getNgayBatDau() + "', '" +
                        kmTheoSanPham.getNgayKetThuc() + "');";

        String query2 =
                "INSERT INTO CHUONG_TRINH_KHUYEN_MAI (mactkm, phantramkhuyenmai, ngaybatdau, ngayketthuc) " +
                        "VALUES ('" + kmTheoSanPham.getMactkm() + "', " +
                        kmTheoSanPham.getPhantramkhuyenmai() + ", '" +
                        kmTheoSanPham.getNgayBatDau() + "', '" +
                        kmTheoSanPham.getNgayKetThuc() + "');";

        condb.insert(query);
        condb.insert(query2);

    }

    public void UpdateKmTheoSanPham(KmTheoSanPham kmTheoSanPham) {
        String query = "UPDATE KM_THEO_SAN_PHAM SET " +
                "MASANPHAM = '" + kmTheoSanPham.getMasp() + "', " +
                "PHANTRAMKHUYENMAI = '" + kmTheoSanPham.getPhantramkhuyenmai() + "', " +
                "NGAYBATDAU = '" + kmTheoSanPham.getNgayBatDau() + "', " +
                "NGAYKETTHUC = '" + kmTheoSanPham.getNgayKetThuc() + "' " + // Không có dấu phẩy cuối
                "WHERE MACTKM = '" + kmTheoSanPham.getMactkm() + "';" +

                "UPDATE CHUONG_TRINH_KHUYEN_MAI " +
                "PHANTRAMKHUYENMAI = " + kmTheoSanPham.getPhantramkhuyenmai() + "'," +
                "NGAYBATDAU = " + kmTheoSanPham.getNgayBatDau() + "'," +
                "NGAYKETTHUC = " + kmTheoSanPham.getNgayKetThuc() +
                "WHERE MACTKM = '" + kmTheoSanPham.getMactkm() + "'," +

                "');";

        try {
            condb.update(query); // Giả sử condb có phương thức update(String)
            System.out.println("Cập nhật khuyến mãi thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
        }
    }
}
