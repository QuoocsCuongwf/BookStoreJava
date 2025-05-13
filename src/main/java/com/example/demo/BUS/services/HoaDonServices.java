package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.HoaDonDAO;
import com.example.demo.model.HoaDon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HoaDonServices {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private static List<HoaDon> listHoaDon = new ArrayList<>();

    public List<HoaDon> getHoaDonList() {
        listHoaDon = hoaDonDAO.getListHoaDon();
        return listHoaDon;
    }

    public HoaDon findByIdHoaDon(String maHoaDon) {
        for (HoaDon hoaDon : listHoaDon) {
            if(hoaDon.getMahd().equals(maHoaDon)){
                System.out.println("findByIdHoaDon "+hoaDon.getMahd());
                return hoaDon;
            }
        }
        System.out.println("not found");
        return null;
    }

    public String addHoaDon(HoaDon hoaDon) {
        if (!checkMaHoaDon(hoaDon.getMahd())) {
            listHoaDon.add(hoaDon);
            System.out.println("HoaDon: " + hoaDon.getMahd());
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
