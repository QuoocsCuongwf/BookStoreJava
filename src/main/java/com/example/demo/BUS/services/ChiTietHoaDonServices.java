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
    public int addChiTietHoaDon(ChiTietHoaDon c){
        SanPhamServices sanPhamServices=new SanPhamServices();
        if(sanPhamServices.findBySanPham(c.getMasp())){
            if(sanPhamServices.searchSanPham(c.getMasp()).get(0).getSl()-c.getSl()>=0){
                list.add(c);
                sanPhamServices.searchSanPham(c.getMasp()).get(0).setSl(sanPhamServices.searchSanPham(c.getMasp()).get(0).getSl()-c.getSl());
                return 200;
            }
            return 400;
        }
        return 404;
    }
    public int updateList(ChiTietHoaDon chiTietHoaDonNew, ChiTietHoaDon chiTietHoaDonOld){
        int status=addChiTietHoaDon(chiTietHoaDonNew);
        if(status==200){
            list.remove(chiTietHoaDonOld);
            deleteList(chiTietHoaDonOld.getMahd(), chiTietHoaDonNew.getMahd());
            SanPhamServices sanPhamServices=new SanPhamServices();
            sanPhamServices.searchSanPham(chiTietHoaDonNew.getMasp()).get(0).setSl(sanPhamServices.searchSanPham(chiTietHoaDonNew.getMasp()).get(0).getSl()+chiTietHoaDonNew.getSl());
            return 200;
        }
        return status;
    }
    public void deleteList(String maHoaDon, String maSanPham){
        chiTietHoaDonDAO.deleteChiTietHoaDon(maHoaDon,maSanPham);
        for(ChiTietHoaDon c:list){
            if (c.getMahd().equals(maHoaDon) && c.getMasp().equals(maSanPham)){
                list.remove(c);
                chiTietHoaDonDAO.deleteChiTietHoaDon(maHoaDon,maSanPham);
            }
        }
    }
}
