package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.ChiTietHoaDonDAO;
import com.example.demo.model.ChiTietHoaDon;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonServices {
    ChiTietHoaDonDAO chiTietHoaDonDAO=new ChiTietHoaDonDAO();
    List<ChiTietHoaDon> list=new ArrayList<ChiTietHoaDon>();
    public List<ChiTietHoaDon> getList(){
        list=chiTietHoaDonDAO.getList();
        return list;
    }
    public List<ChiTietHoaDon> getList(String maHoaDon){
        List<ChiTietHoaDon> listResults=new ArrayList<ChiTietHoaDon>();
        for(ChiTietHoaDon c:list){
            if (c.getMahd().equals(maHoaDon)){
                listResults.add(c);
            }
        }
        return listResults;
    }
    public void addChiTietHoaDon(ChiTietHoaDon c){
        chiTietHoaDonDAO.addChiTietHoaDon(c);
        list.add(c);
    }
    public void updateList(ChiTietHoaDon chiTietHoaDon){
        chiTietHoaDonDAO.updateChiTietHoaDon(chiTietHoaDon);
    }
    public void deleteList(String maHoaDon, String maSanPham){
        chiTietHoaDonDAO.deleteChiTietHoaDon(maHoaDon,maSanPham);
        for(ChiTietHoaDon c:list){
            if (c.getMahd().equals(maHoaDon) && c.getMasp().equals(maSanPham)){
                list.remove(c);
            }
        }
    }
}
