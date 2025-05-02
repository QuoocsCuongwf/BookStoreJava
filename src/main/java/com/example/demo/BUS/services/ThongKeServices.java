package com.example.demo.BUS.services;

import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.example.demo.model.SanPham;
import com.example.demo.model.ThongKeSanPham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class ThongKeServices {
    public List<ThongKeSanPham> thongKeTheoKhoangThoiGian(Date tuNgay,Date denNgay){
        List<ThongKeSanPham> listResult=new ArrayList<>();
        SanPhamServices sanPhamServices = new SanPhamServices();
        List<SanPham> listSanPham = sanPhamServices.getListSanPham();
        ChiTietHoaDonServices chiTietHoaDonServices = new ChiTietHoaDonServices();
        HoaDonServices hoaDonServices = new HoaDonServices();
        List<HoaDon> listHoaDon=hoaDonServices.getHoaDonList();
        for (int i = 0; i < listSanPham.size(); i++) {
            ThongKeSanPham thongKeSanPham = new ThongKeSanPham();
            thongKeSanPham.setTenSach(listSanPham.get(i).getTensp());
            thongKeSanPham.setSoLuongBanRa(0);
            for (int j = 0; j < listHoaDon.size(); j++) {
                LocalDate localDate = listHoaDon.get(j).getNgaylap(); // Giả sử trả về LocalDate
                Date ngayLap = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (ngayLap != null && !ngayLap.before(tuNgay) && !ngayLap.after(denNgay)) {
                    List<ChiTietHoaDon> listChiTietHoaDon=chiTietHoaDonServices.getList(listHoaDon.get(j).getMahd());
                    for (ChiTietHoaDon chiTietHoaDon : listChiTietHoaDon) {
                        if (chiTietHoaDon.getMasp().equals(listSanPham.get(i).getMasp())) {
                            thongKeSanPham.setSoLuongBanRa(thongKeSanPham.getSoLuongBanRa()+chiTietHoaDon.getSl());
                        }
                    }
                }

            }
            listResult.add(thongKeSanPham);
        }
        return listResult;
    }

    public List<ThongKeSanPham> thongKeTheoNam(int nam) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date tuNgay = sdf.parse("01-01-" + nam);
            Date denNgay = sdf.parse("31-12-" + nam);
            return thongKeTheoKhoangThoiGian(tuNgay, denNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<ThongKeSanPham> thongKeSanPhamTheoQuy(int nam,int quy) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date tuNgay=new Date();
            Date denNgay=new Date();
            if (quy == 2 || quy == 3) {
                tuNgay = sdf.parse("01-0"+(quy-1)*3 +"-"+ nam);
                denNgay = sdf.parse("30-0"+quy*3 +"-" + nam);
            }
            if (quy == 4) {
                tuNgay = sdf.parse("01-10-"+ nam);
                denNgay = sdf.parse("31-12-" + nam);
            }
            if (quy == 1) {
                tuNgay = sdf.parse("01-01-"+ nam);
                denNgay = sdf.parse("31-03-" + nam);
            }
            return thongKeTheoKhoangThoiGian(tuNgay, denNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date tuNgay = sdf.parse("29-04-2025");
        Date denNgay = sdf.parse("02-05-2025");

        ThongKeServices thongKeServices = new ThongKeServices();
        List<ThongKeSanPham> listThongKeSanPham = thongKeServices.thongKeTheoKhoangThoiGian(tuNgay, denNgay);
        for (ThongKeSanPham sp : listThongKeSanPham) {
            System.out.println(sp.getTenSach() + "  " + sp.getSoLuongBanRa());
        }
    }
}
