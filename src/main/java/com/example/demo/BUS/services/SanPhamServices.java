package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.SanPhamDAO;
import com.example.demo.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamServices {
    private static List<SanPham> listSanPham=new ArrayList<SanPham>();
    private SanPhamDAO sanPhamDAO=new SanPhamDAO();

    public List<SanPham> getListSanPham() {
        listSanPham=sanPhamDAO.getListSanPham();
        return listSanPham;
    }
    public String insertSanPham(SanPham sanPham) {
        if(!checkMaSanPham(sanPham.getMasp())){
            listSanPham.add(sanPham);
            sanPhamDAO.insertSanPham(sanPham);
            return "success";
        };
        return "fail";
    }
    public boolean checkMaSanPham(String maSanPham) {
        for (SanPham nhanVien : listSanPham) {
            if (nhanVien.getMasp().equals(maSanPham)) {
                return true;
            }
        }
        return false;
    }

    public void deleteSanPham(String maSanPham) {
        if(checkMaSanPham(maSanPham)){
            for (SanPham sanPham : listSanPham) {
                if (sanPham.getMasp().equals(maSanPham)) {
                    listSanPham.remove(sanPham);
                    break;
                }
            }
            sanPhamDAO.deleteSanPham(maSanPham);
            System.out.println("delete: "+maSanPham);
        }
    }

    public void updateSanPham(SanPham sanPham) {
        for (int i = 0; i < listSanPham.size(); i++) {
            if (listSanPham.get(i).getMasp().equals(sanPham.getMasp())) {
                listSanPham.set(i, sanPham); // cập nhật tại chỗ, không cần xoá rồi thêm
                sanPhamDAO.updateSanPham(sanPham);
                break;
            }
        }
    }
    public List<SanPham> timKiemNangCao(String find, int giaMin, int giaMax) {
        List<SanPham> reslist = new ArrayList<>();
        String findLower = find.toLowerCase();

        for (SanPham sanPham : listSanPham) {
            String maspLower = sanPham.getMasp().toLowerCase();
            String tenspLower = sanPham.getTensp().toLowerCase();
            int dongia = sanPham.getDongia();

            boolean matchesKeyword = maspLower.contains(findLower) || tenspLower.contains(findLower);
            boolean inPriceRange = dongia >= giaMin && dongia <= giaMax;

            if (matchesKeyword && inPriceRange) {
                reslist.add(sanPham);
            }
        }

        return reslist;
    }


    public List<SanPham> searchSanPham(String find) {
        List<SanPham> resultSearch=new ArrayList<>();
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getMasp().equals(find)) {
                resultSearch.add(sanPham);
            }
            if (sanPham.getTensp().contains(find)) {
                resultSearch.add(sanPham);
            }
        }
        return resultSearch;
    }

    public static void main(String[] args) {
        SanPhamServices sanPhamServices=new SanPhamServices();
        sanPhamServices.getListSanPham();
    }
}
