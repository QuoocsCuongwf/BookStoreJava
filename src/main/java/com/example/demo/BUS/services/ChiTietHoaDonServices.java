package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.ChiTietHoaDonDAO;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.example.demo.model.SanPham;

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
    public int checkChiTietHoaDon(ChiTietHoaDon c){
        SanPhamServices sanPhamServices=new SanPhamServices();
        List<SanPham> listSanPham=sanPhamServices.getListSanPham();
        System.out.println("find: "+c.getMasp());
        for(SanPham s:listSanPham){
            if (s.getMasp().equals(c.getMasp())){
                if(sanPhamServices.searchSanPham(c.getMasp()).get(0).getSl()-c.getSl()>=0){
                    list.add(c);
                    sanPhamServices.searchSanPham(c.getMasp()).get(0).setSl(sanPhamServices.searchSanPham(c.getMasp()).get(0).getSl()-c.getSl());
                    return 200;
                }
                return 400;
            }
            System.out.println("find: "+s.getMasp());
        }
        System.out.println("Khong tim thay san pham");
        return 404;
    }
    public void addChiTietHoaDon(ChiTietHoaDon c){
        chiTietHoaDonDAO.addChiTietHoaDon(c);
    }
    public int updateList(ChiTietHoaDon chiTietHoaDonNew, ChiTietHoaDon chiTietHoaDonOld){
        int status=checkChiTietHoaDon(chiTietHoaDonNew);
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
