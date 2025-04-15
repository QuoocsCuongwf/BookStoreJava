package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KhachHangDAO;
import com.example.demo.model.KhachHang;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KhachHangServices {
    List<KhachHang> listKhachHang = new ArrayList<>();
    KhachHangDAO khachHangDAO=new KhachHangDAO();

    public List<KhachHang> getListKhachHang(){
        listKhachHang = khachHangDAO.getListKhachHang();
        System.out.println(listKhachHang);
        return listKhachHang;
    }

    public List<KhachHang> findKhachHangById(String id){
        List<KhachHang> reSultKhachHangFindList = new ArrayList<>();
        for (KhachHang khachHang : listKhachHang) {
            if (khachHang.getMakh().equals(id)) {
                reSultKhachHangFindList.add(khachHang);
            }
        }
        return reSultKhachHangFindList;
    }
    public Boolean checkMaKhachHang(String maKhachHang){
        for (KhachHang khachHang : listKhachHang) {
            if (khachHang.getMakh().equals(maKhachHang)) {
                return true;
            }
        }
        return false;
    }
    public List<KhachHang> searchKhachHang(String inforKhachHang){
        List<KhachHang> reSultKhachHangFindList = listKhachHang.stream()
                .filter(khachHang -> khachHang.getMakh().contains(inforKhachHang)||khachHang.getTenkh().contains(inforKhachHang) ||khachHang.getHokh().contains(inforKhachHang))
                .collect(Collectors.toList());
        return reSultKhachHangFindList;
    }
    public String addKhachHang(KhachHang khachHang){
        if (!checkMaKhachHang(khachHang.getMakh())){
            khachHangDAO.addKhachHang(khachHang);
            System.out.println("add sucess");
            return "Add success";
        }
        return "fail";
    }
    public void deleteKhachHang(String maKhachHang){
        listKhachHang.removeIf(khachHang -> khachHang.getMakh().equals(maKhachHang));
        khachHangDAO.deleteKhachHang(maKhachHang);
    }

    public static void main(String[] args) {
        KhachHangServices khachHangServices = new KhachHangServices();
        KhachHang khachHang = new KhachHang();
        khachHang.setMakh("one");
        khachHang.setTenkh("two");
        khachHang.setHokh("one");
        khachHang.setDiachi("TPHCM");
        khachHang.setEmail("khachhang@gmail.com");
        khachHang.setSdt("0987651212");
    }

}
