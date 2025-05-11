package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KmTheoHoaDonDAO;
import com.example.demo.databaseAccesssObject.KmTheoSanPhamDAO;
import com.example.demo.model.ChuongTrinhKhuyenMai;
import com.example.demo.model.KmTheoHoaDon;
import com.example.demo.model.KmTheoSanPham;

import java.util.ArrayList;
import java.util.List;

public class ChuongTrinhKhuyenMaiServices {
    List<ChuongTrinhKhuyenMai> listChuongTrinhKhuyenMai=new ArrayList<>();
    KmTheoHoaDonDAO kmTheoHoaDonDAO=new KmTheoHoaDonDAO();
    KmTheoSanPhamDAO kmTheoSanPhamDAO=new KmTheoSanPhamDAO();
    public List<ChuongTrinhKhuyenMai> getListChuongTrinhKhuyenMai() {
        List<KmTheoHoaDon> listKhuyenMaiTheoHoaDon=kmTheoHoaDonDAO.getListKmTheoHoaDon();
        for (KmTheoHoaDon kmTheoHoaDon:listKhuyenMaiTheoHoaDon) {
            listChuongTrinhKhuyenMai.add(kmTheoHoaDon);
        }
        List<KmTheoSanPham> listKhuyenMaiTheoSanPham=kmTheoSanPhamDAO.getListKmTheoSanPham();
        for (KmTheoSanPham kmTheoSanPham:listKhuyenMaiTheoSanPham) {
            listChuongTrinhKhuyenMai.add(kmTheoSanPham);
        }
        return listChuongTrinhKhuyenMai;
    }

    public void update(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        for (int i = 0; i < listChuongTrinhKhuyenMai.size(); i++) {
            if(listChuongTrinhKhuyenMai.get(i).getMactkm().equals(chuongTrinhKhuyenMai.getMactkm())){
                listChuongTrinhKhuyenMai.set(i,chuongTrinhKhuyenMai);
            }
        }
        if (chuongTrinhKhuyenMai instanceof KmTheoHoaDon) {

        }
    }

    public static void main(String[] args) {
        ChuongTrinhKhuyenMaiServices chuongTrinhKhuyenMaiServices=new ChuongTrinhKhuyenMaiServices();
        List<ChuongTrinhKhuyenMai> listChuongTrinhKhuyenMai=new ArrayList<>();
        listChuongTrinhKhuyenMai=chuongTrinhKhuyenMaiServices.getListChuongTrinhKhuyenMai();
        for (ChuongTrinhKhuyenMai chuongTrinhKhuyenMai:listChuongTrinhKhuyenMai) {
            if (chuongTrinhKhuyenMai instanceof  KmTheoSanPham) {
                KmTheoSanPham kmTheoSanPham=(KmTheoSanPham) chuongTrinhKhuyenMai;
                System.out.println(kmTheoSanPham.getMactkm()+" "+kmTheoSanPham.getMasp());
            }
        }
    }

}
