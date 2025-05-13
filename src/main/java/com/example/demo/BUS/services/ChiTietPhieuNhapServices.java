package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.ChiTietPhieuNhapDAO;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.ChiTietPhieuNhap;
import com.example.demo.model.PhieuNhap;
import com.example.demo.model.SanPham;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChiTietPhieuNhapServices {
    private static final ChiTietPhieuNhapDAO chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();
    private static final List<ChiTietPhieuNhap> list = new ArrayList<>();

    public List<ChiTietPhieuNhap> getList(){
        list.clear();
        list.addAll(chiTietPhieuNhapDAO.getList());
        return list;
    }
    public List<ChiTietPhieuNhap> getList(String maPhieuNhap){
        getList();
        List<ChiTietPhieuNhap> resultList = new ArrayList<>();
        for (ChiTietPhieuNhap c : list) {
            if (c.getMapn().equals(maPhieuNhap)) {
                resultList.add(c);
            }
        }
        return resultList;
    }
    // hàm kiểm tra mã sản phẩm đã có trong danh sách sản phẩm hay không
    public boolean isValidChiTietPhieuNhap(ChiTietPhieuNhap c) {
        SanPhamServices sanPhamServices = new SanPhamServices();
        sanPhamServices.getListSanPham();
        if (!sanPhamServices.searchSanPham(c.getMasp()).isEmpty()) { // kiểm tra xem có sản phẩm nào có masp giống với c không
            return true;
        }
        return false; // Không tìm thấy sản phẩm
    }
    public String checkChiTietPhieuNhap(ChiTietPhieuNhap c) {
        if (isValidChiTietPhieuNhap(c)) {
            return "success"; // OK
        }
        return "fail"; // not found
    }
    public void addChiTietPhieuNhap( List<ChiTietPhieuNhap> listChiTietPhieuNhap) {
        SanPhamServices sanPhamServices = new SanPhamServices();
        for (ChiTietPhieuNhap c : listChiTietPhieuNhap) {
            if (isValidChiTietPhieuNhap(c)) {
                list.add(c);
                SanPham sanPham = sanPhamServices.searchSanPham(c.getMasp()).getFirst();
                sanPham.setSl(sanPham.getSl()+c.getSl());
                sanPhamServices.updateSanPham(sanPham);
                chiTietPhieuNhapDAO.addChiTietPhieuNhap(c);

            }
        }
    }
    public String updateList(ChiTietPhieuNhap newItem, ChiTietPhieuNhap oldItem) {
        String status = checkChiTietPhieuNhap(newItem);
        if (status.contains("success")) {
            deleteList(oldItem.getMapn(), oldItem.getMasp());
            list.add(newItem) ;// Gồm cập nhật cả list và DB
        }
        return status;
    }

    public void deleteList(String maPhieuNhap, String maSanPham) {
        Iterator<ChiTietPhieuNhap> iterator = list.iterator();
        while (iterator.hasNext()) {
            ChiTietPhieuNhap c = iterator.next();
            if (c.getMapn().equals(maPhieuNhap) && c.getMasp().equals(maSanPham)) {
                iterator.remove();
                break;
            }
        }
        chiTietPhieuNhapDAO.deleteChiTietPhieuNhap(maPhieuNhap, maSanPham);
    }

    public void deleteList(String maPhieuNhap) {
        Iterator<ChiTietPhieuNhap> iterator = list.iterator();
        while (iterator.hasNext()) {
            ChiTietPhieuNhap c = iterator.next();
            if (c.getMapn().equals(maPhieuNhap)) {
                iterator.remove();
            }
        }
        chiTietPhieuNhapDAO.deleteChiTietPhieuNhap(maPhieuNhap);
    }





}
