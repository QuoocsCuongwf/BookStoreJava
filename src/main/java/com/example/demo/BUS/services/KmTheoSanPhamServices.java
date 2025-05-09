package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KmTheoSanPhamDAO;
import com.example.demo.model.KmTheoSanPham;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KmTheoSanPhamServices {
    private KmTheoSanPhamDAO kmTheoSanPhamDAO  = new KmTheoSanPhamDAO();
    private List<KmTheoSanPham> listKmTheoSanPham = new ArrayList<KmTheoSanPham>();
    public List<KmTheoSanPham>getKmTheoSanPhamList() {
        listKmTheoSanPham=kmTheoSanPhamDAO.getListKmTheoSanPham();
        System.out.println(listKmTheoSanPham);
        return listKmTheoSanPham;
    }
    public List<KmTheoSanPham> findByIDKmTheoSanPham (String idkMai){
        List<KmTheoSanPham> listResult = new ArrayList<KmTheoSanPham>();
        for (KmTheoSanPham kmTheoSanPham : listKmTheoSanPham) {
            if(kmTheoSanPham.getMactkm().contains(idkMai)){
                listResult.add(kmTheoSanPham);
            }
        }
        return listResult;
    }


    public String addKmTheoSanPham(KmTheoSanPham kMai) {
        if(!checkMaKhuyenMai(kMai.getMactkm())){
            kmTheoSanPhamDAO.addKmTheoSanPham(kMai);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaKhuyenMai(String makm) {
        for(KmTheoSanPham kMai : listKmTheoSanPham){
            if(kMai.getMactkm().equals(makm)){
                return true;
            }
        }
        return false;
    }

    public List<KmTheoSanPham> searchChuongTrinh(String in4KMai) {
        List<KmTheoSanPham> result= listKmTheoSanPham.stream()
                .filter(kmTheoSanPham ->
                        String.valueOf(kmTheoSanPham.getMactkm()).contains(in4KMai) ||
                                String.valueOf(kmTheoSanPham.getMasp()).contains(in4KMai) ||
                                String.valueOf(kmTheoSanPham.getPhantramkhuyenmai()).contains(in4KMai) ||
                                String.valueOf(kmTheoSanPham.getNgayBatDau()).contains(in4KMai) ||
                                String.valueOf(kmTheoSanPham.getNgayKetThuc()).contains(in4KMai))

                .collect(Collectors.toList());
        return result;
    }
    public void deleteKmTheoSanPham(String maKmai) {
        listKmTheoSanPham.removeIf(kmTheoSanPham -> kmTheoSanPham.getMactkm().equals(maKmai));
        kmTheoSanPhamDAO.deleteKmTheoSanPham(maKmai);
    }
    public String updateKmTheoSanPham(KmTheoSanPham kMai) {
        kmTheoSanPhamDAO.UpdateKmTheoSanPham(kMai);
        listKmTheoSanPham = kmTheoSanPhamDAO.getListKmTheoSanPham(); // Cập nhật danh sách từ DB
        return "update success";
    }
}
