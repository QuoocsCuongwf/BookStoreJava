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
            kmTheoHoaDonDAO.updateChuongTrinhKhuyenMai((KmTheoHoaDon) chuongTrinhKhuyenMai);
        } else {
            kmTheoSanPhamDAO.UpdateKmTheoSanPham((KmTheoSanPham) chuongTrinhKhuyenMai);
        }
    }
    public void delete(String mactkm) {
        for (int i = 0; i < listChuongTrinhKhuyenMai.size(); i++) {
            if (listChuongTrinhKhuyenMai.get(i).getMactkm().equals(mactkm)) {
                if (listChuongTrinhKhuyenMai.get(i) instanceof KmTheoHoaDon) {
                    kmTheoHoaDonDAO.deleteKhuyenMai(mactkm);
                } else {
                    kmTheoSanPhamDAO.deleteKmTheoSanPham(mactkm);
                }
                listChuongTrinhKhuyenMai.remove(i);
                break;
            }
        }
    }
    public void insert(ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        for (int i = 0; i < listChuongTrinhKhuyenMai.size(); i++) {
            if (listChuongTrinhKhuyenMai.get(i).getMactkm().equals(chuongTrinhKhuyenMai.getMactkm())) {
                return;
            }
        }
        listChuongTrinhKhuyenMai.add(chuongTrinhKhuyenMai);
        if (chuongTrinhKhuyenMai instanceof KmTheoHoaDon) {
            kmTheoHoaDonDAO.addKhuyenMai((KmTheoHoaDon) chuongTrinhKhuyenMai);
        }
        if (chuongTrinhKhuyenMai instanceof KmTheoSanPham) {
            kmTheoSanPhamDAO.addKmTheoSanPham((KmTheoSanPham) chuongTrinhKhuyenMai);
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
        chuongTrinhKhuyenMaiServices.delete("km001");
//        KmTheoHoaDon kmTheoHoaDon=new KmTheoHoaDon();
//        kmTheoHoaDon.setMactkm("km001");
//        kmTheoHoaDon.setTenchuongtrinh("001");
//        kmTheoHoaDon.setTongtien(10000.0);
//        kmTheoHoaDon.setPhantramkhuyenmai(15.0);
//        chuongTrinhKhuyenMaiServices.insert(kmTheoHoaDon);
    }

}
