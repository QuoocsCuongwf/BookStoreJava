package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.HoaDonDAO;
import com.example.demo.model.HoaDon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HoaDonServices {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private List<HoaDon> listHoaDon = new ArrayList<>();

    public List<HoaDon> getHoaDonList() {
        listHoaDon = hoaDonDAO.getListHoaDon();
        System.out.println(listHoaDon);
        return listHoaDon;
    }

    public List<HoaDon> findByIdHoaDon(String maHoaDon) {
        return listHoaDon.stream()
                .filter(hd -> hd.getMahd().contains(maHoaDon))
                .collect(Collectors.toList());
    }

    public String addHoaDon(HoaDon hoaDon) {
        if (!checkMaHoaDon(hoaDon.getMahd())) {
            hoaDonDAO.addHoaDon(hoaDon);
            return "Add Success";
        }
        return "Add Fail - Duplicate MAHD";
    }

    public boolean checkMaHoaDon(String maHoaDon) {
        return listHoaDon.stream().anyMatch(hd -> hd.getMahd().equals(maHoaDon));
    }

    public List<HoaDon> searchHoaDon(String keyword) {
        return listHoaDon.stream()
                .filter(hd -> hd.getMahd().contains(keyword)
                        || (hd.getManv() != null && hd.getManv().contains(keyword))
                        || (hd.getMakh() != null && hd.getMakh().contains(keyword)))
                .collect(Collectors.toList());
    }

    public void deleteHoaDon(String maHoaDon) {
        listHoaDon.removeIf(hd -> hd.getMahd().equals(maHoaDon));
        hoaDonDAO.deleteHoaDon(maHoaDon);
    }

    public String updateHoaDon(HoaDon hoaDon) {
        hoaDonDAO.updateHoaDon(hoaDon);
        return "Update Success";
    }

    public static void main(String[] args) {
        HoaDonServices hoaDonServices = new HoaDonServices();
        hoaDonServices.getHoaDonList();
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMahd("HD010");
        hoaDon.setNgaylap(java.time.LocalDate.now());
        hoaDon.setManv("NV01");
        hoaDon.setMakh("KH01");
        hoaDon.setTongtien(250000);

        System.out.println(hoaDonServices.addHoaDon(hoaDon));
    }
}
