package com.example.demo.BUS.services;

import com.example.demo.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class ThongKeServices {
    public List<ThongKeSanPham> thongKeNhapHangTheoKhoangThoiGian(Date tuNgay,Date denNgay){
        List<ThongKeSanPham> listResult=new ArrayList<>();

        SanPhamServices sanPhamServices=new SanPhamServices();
        List<SanPham> listSanPham=sanPhamServices.getListSanPham();
        PhieuNhapServices phieuNhapServices=new PhieuNhapServices();
        ChiTietPhieuNhapServices chiTietPhieuNhapServices=new ChiTietPhieuNhapServices();
        List<PhieuNhap> listPhieuNhap=phieuNhapServices.getPhieuNhapList();

        for (int i = 0; i < listSanPham.size(); i++) {
            ThongKeSanPham thongKeSanPham = new ThongKeSanPham();
            thongKeSanPham.setTenSach(listSanPham.get(i).getTensp());
            thongKeSanPham.setSoLuong(0);
            for (int j=0;j<listPhieuNhap.size();j++) {
                LocalDate localDate = listPhieuNhap.get(j).getNgaynhap(); // Giả sử trả về LocalDate
                Date ngayLap = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (ngayLap != null && !ngayLap.before(tuNgay) && !ngayLap.after(denNgay)){
                    List<ChiTietPhieuNhap> chiTietPhieuNhapList=chiTietPhieuNhapServices.getList(listPhieuNhap.get(j).getMapn());
                    for (ChiTietPhieuNhap chiTietPhieuNhap:chiTietPhieuNhapList) {
                        if (chiTietPhieuNhap.getMasp().equals(listSanPham.get(i).getMasp())) {
                            thongKeSanPham.setSoLuong(thongKeSanPham.getSoLuong() + chiTietPhieuNhap.getSl());
                        }
                    }
                }
            }
            listResult.add(thongKeSanPham);
        }
        return  listResult;
    }
    public List<ThongKeSanPham> thongKeBanHangTheoKhoangThoiGian(Date tuNgay,Date denNgay){
        List<ThongKeSanPham> listResult=new ArrayList<>();

        SanPhamServices sanPhamServices = new SanPhamServices();
        List<SanPham> listSanPham = sanPhamServices.getListSanPham();
        ChiTietHoaDonServices chiTietHoaDonServices = new ChiTietHoaDonServices();
        HoaDonServices hoaDonServices = new HoaDonServices();
        List<HoaDon> listHoaDon=hoaDonServices.getHoaDonList();
        for (int i = 0; i < listSanPham.size(); i++) {
            ThongKeSanPham thongKeSanPham = new ThongKeSanPham();
            thongKeSanPham.setTenSach(listSanPham.get(i).getTensp());
            thongKeSanPham.setSoLuong(0);
            for (int j = 0; j < listHoaDon.size(); j++) {
                LocalDate localDate = listHoaDon.get(j).getNgaylap(); // Giả sử trả về LocalDate
                Date ngayLap = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (ngayLap != null && !ngayLap.before(tuNgay) && !ngayLap.after(denNgay)) {
                    List<ChiTietHoaDon> listChiTietHoaDon=chiTietHoaDonServices.getList(listHoaDon.get(j).getMahd());
                    for (ChiTietHoaDon chiTietHoaDon : listChiTietHoaDon) {
                        if (chiTietHoaDon.getMasp().equals(listSanPham.get(i).getMasp())) {
                            thongKeSanPham.setSoLuong(thongKeSanPham.getSoLuong()+chiTietHoaDon.getSl());
                        }
                    }
                }

            }
            listResult.add(thongKeSanPham);
        }
        return listResult;
    }

    public List<ThongKe4Quy> thongKeSanPhamBanRaTrong4Quy(int nam){
        List<ThongKe4Quy> listResult=new ArrayList<>();
        List<ThongKeSanPham> listThongKeSanPhamQ1=thongKeSanPhamBanRaTheoQuy(nam,1);
        List<ThongKeSanPham> listThongKeSanPhamQ2=thongKeSanPhamBanRaTheoQuy(nam,2);
        List<ThongKeSanPham> listThongKeSanPhamQ3=thongKeSanPhamBanRaTheoQuy(nam,3);
        List<ThongKeSanPham> listThongKeSanPhamQ4=thongKeSanPhamBanRaTheoQuy(nam,4);
        for(int i=0;i<listThongKeSanPhamQ1.size();i++){
            ThongKe4Quy thongKe4Quy=new ThongKe4Quy();
            thongKe4Quy.setTenSach(listThongKeSanPhamQ1.get(i).getTenSach());
            thongKe4Quy.setSoLuongQ1(listThongKeSanPhamQ1.get(i).getSoLuong());
            listResult.add(thongKe4Quy);
        }
        for(int i=0;i<listThongKeSanPhamQ2.size();i++){
            for (int j=0;j<listResult.size();j++){
                if (listThongKeSanPhamQ2.get(i).getTenSach().equals(listResult.get(j).getTenSach())){
                    listResult.get(j).setSoLuongQ2(listThongKeSanPhamQ2.get(i).getSoLuong());
                    break;
                }
            }
        }
        for(int i=0;i<listThongKeSanPhamQ3.size();i++){
            for (int j=0;j<listResult.size();j++){
                if(listThongKeSanPhamQ3.get(i).getTenSach().equals(listResult.get(j).getTenSach())){
                    listResult.get(j).setSoLuongQ3(listThongKeSanPhamQ3.get(i).getSoLuong());
                    break;
                }
            }
        }
        for(int i=0;i<listThongKeSanPhamQ4.size();i++){
            for (int j=0;j<listResult.size();j++){
                if (listThongKeSanPhamQ4.get(i).getTenSach().equals(listResult.get(j).getTenSach())){
                    listResult.get(j).setSoLuongQ3(listThongKeSanPhamQ4.get(i).getSoLuong());
                }
            }
        }
        return listResult;
    }

    public List<ThongKeSanPham> thongKeBanHangTheoNam(int nam) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date tuNgay = sdf.parse("01-01-" + nam);
            Date denNgay = sdf.parse("31-12-" + nam);
            return thongKeBanHangTheoKhoangThoiGian(tuNgay, denNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<ThongKeSanPham> thongKeSanPhamBanRaTheoQuy(int nam,int quy) {
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
            return thongKeBanHangTheoKhoangThoiGian(tuNgay, denNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<ThongKeSanPham> thongKeNhapHangTheoNam(int nam) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date tuNgay = sdf.parse("01-01-" + nam);
            Date denNgay = sdf.parse("31-12-" + nam);
            return thongKeNhapHangTheoKhoangThoiGian(tuNgay, denNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<ThongKeSanPham> thongKeSanPhamNhapVaoTheoQuy(int nam,int quy) {
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
            return thongKeNhapHangTheoKhoangThoiGian(tuNgay, denNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<ThongKe4Quy> thongKeSanPhamNhapVaoTrong4Quy(int nam){
        List<ThongKe4Quy> listResult=new ArrayList<>();
        List<ThongKeSanPham> listThongKeSanPhamQ1=thongKeSanPhamNhapVaoTheoQuy(nam,1);
        List<ThongKeSanPham> listThongKeSanPhamQ2=thongKeSanPhamNhapVaoTheoQuy(nam,2);
        List<ThongKeSanPham> listThongKeSanPhamQ3=thongKeSanPhamNhapVaoTheoQuy(nam,3);
        List<ThongKeSanPham> listThongKeSanPhamQ4=thongKeSanPhamNhapVaoTheoQuy(nam,4);
        for(int i=0;i<listThongKeSanPhamQ1.size();i++){
            ThongKe4Quy thongKe4Quy=new ThongKe4Quy();
            thongKe4Quy.setTenSach(listThongKeSanPhamQ1.get(i).getTenSach());
            thongKe4Quy.setSoLuongQ1(listThongKeSanPhamQ1.get(i).getSoLuong());
            listResult.add(thongKe4Quy);
        }
        for(int i=0;i<listThongKeSanPhamQ2.size();i++){
            for (int j=0;j<listResult.size();j++){
                if (listThongKeSanPhamQ2.get(i).getTenSach().equals(listResult.get(j).getTenSach())){
                    listResult.get(j).setSoLuongQ2(listThongKeSanPhamQ2.get(i).getSoLuong());
                    break;
                }
            }
        }
        for(int i=0;i<listThongKeSanPhamQ3.size();i++){
            for (int j=0;j<listResult.size();j++){
                if(listThongKeSanPhamQ3.get(i).getTenSach().equals(listResult.get(j).getTenSach())){
                    listResult.get(j).setSoLuongQ3(listThongKeSanPhamQ3.get(i).getSoLuong());
                    break;
                }
            }
        }
        for(int i=0;i<listThongKeSanPhamQ4.size();i++){
            for (int j=0;j<listResult.size();j++){
                if (listThongKeSanPhamQ4.get(i).getTenSach().equals(listResult.get(j).getTenSach())){
                    listResult.get(j).setSoLuongQ3(listThongKeSanPhamQ4.get(i).getSoLuong());
                }
            }
        }
        return listResult;
    }


    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date tuNgay = sdf.parse("29-04-2025");
        Date denNgay = sdf.parse("18-05-2025");

        ThongKeServices thongKeServices = new ThongKeServices();
        List<ThongKe4Quy> thongKe4QuyList=thongKeServices.thongKeSanPhamBanRaTrong4Quy(2025);
        for (ThongKe4Quy thongKe4Quy:thongKe4QuyList) {
            System.out.println(thongKe4Quy.getTenSach()+"  "+thongKe4Quy.getSoLuongQ1()+"  "+thongKe4Quy.getSoLuongQ2()+" "+thongKe4Quy.getSoLuongQ3());
        }
    }
}
