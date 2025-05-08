package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.ChiTietHoaDonDAO;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.example.demo.model.SanPham;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChiTietHoaDonServices {
    private static final ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    private static final List<ChiTietHoaDon> list = new ArrayList<>();

    public List<ChiTietHoaDon> getList() {
        list.clear();
        list.addAll(chiTietHoaDonDAO.getList());
        return list;
    }

    public List<ChiTietHoaDon> getList(String maHoaDon) {
            getList();

        List<ChiTietHoaDon> resultList = new ArrayList<>();
        for (ChiTietHoaDon c : list) {
            if (c.getMahd().equals(maHoaDon)) {
                resultList.add(c);
            }
        }
        return resultList;
    }

    public boolean isValidChiTietHoaDon(ChiTietHoaDon c) {
        SanPhamServices sanPhamServices = new SanPhamServices();
        List<SanPham> listSanPham = sanPhamServices.getListSanPham();
        for (SanPham s : listSanPham) {
            if (s.getMasp().equals(c.getMasp())) {
                return s.getSl() >= c.getSl();
            }
        }
        return false; // Không tìm thấy sản phẩm
    }

    public int checkChiTietHoaDon(ChiTietHoaDon c) {
        if (isValidChiTietHoaDon(c)) {
            return 200; // OK
        }

        SanPhamServices sanPhamServices = new SanPhamServices();
        if (sanPhamServices.searchSanPham(c.getMasp()).isEmpty()) {
            return 404; // Not Found
        }
        return 400; // Không đủ số lượng
    }

    public void addChiTietHoaDon(List<ChiTietHoaDon> chiTietHoaDonList) throws Exception {
        for(ChiTietHoaDon c:chiTietHoaDonList) {
            list.add(c);
            SanPhamServices sanPhamServices = new SanPhamServices();
            if (sanPhamServices.searchSanPham(c.getMasp()).isEmpty()) {
                sanPhamServices.getListSanPham();
            }
            SanPham sanPham = sanPhamServices.searchSanPham(c.getMasp()).get(0);
            sanPham.setSl(sanPham.getSl() - c.getSl());
            sanPhamServices.updateSanPham(sanPham);
            chiTietHoaDonDAO.addChiTietHoaDon(c);
        }
        HoaDonServices hoaDonServices = new HoaDonServices();
        if(hoaDonServices.getHoaDonList().isEmpty()){
            hoaDonServices.getHoaDonList();
        }
        HoaDon hoaDon=hoaDonServices.findByIdHoaDon(chiTietHoaDonList.get(0).getMahd());
        String outputPath = "D:\\code\\BookStoreJava\\src\\main\\resources\\Output\\HoaDon\\" + hoaDon.getMahd() + ".pdf";
        PdfExporter.xuatHoaDonPDF(hoaDon,chiTietHoaDonList,outputPath);

    }

    public int updateList(ChiTietHoaDon newItem, ChiTietHoaDon oldItem) {
        int status = checkChiTietHoaDon(newItem);
        if (status == 200) {
            deleteList(oldItem.getMahd(), oldItem.getMasp());
            list.add(newItem) ;// Gồm cập nhật cả list và DB
        }
        return status;
    }

    public void deleteList(String maHoaDon, String maSanPham) {
        Iterator<ChiTietHoaDon> iterator = list.iterator();
        while (iterator.hasNext()) {
            ChiTietHoaDon c = iterator.next();
            if (c.getMahd().equals(maHoaDon) && c.getMasp().equals(maSanPham)) {
                iterator.remove();
                break;
            }
        }
        chiTietHoaDonDAO.deleteChiTietHoaDon(maHoaDon, maSanPham);
    }

    public void deleteList(String maHoaDon) {
        Iterator<ChiTietHoaDon> iterator = list.iterator();
        while (iterator.hasNext()) {
            ChiTietHoaDon c = iterator.next();
            if (c.getMahd().equals(maHoaDon)) {
                iterator.remove();
            }
        }
        chiTietHoaDonDAO.deleteChiTietHoaDon(maHoaDon);
    }

    public static void main(String[] args) {
        ChiTietHoaDonServices chiTietHoaDonServices = new ChiTietHoaDonServices();
        chiTietHoaDonServices.deleteList("HD8");
    }
}
