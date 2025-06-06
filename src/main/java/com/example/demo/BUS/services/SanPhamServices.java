package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.SanPhamDAO;
import com.example.demo.model.ChuongTrinhKhuyenMai;
import com.example.demo.model.KmTheoSanPham;
import com.example.demo.model.SanPham;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SanPhamServices {
    private static List<SanPham> listSanPham=new ArrayList<SanPham>();
    private SanPhamDAO sanPhamDAO=new SanPhamDAO();
    private ChuongTrinhKhuyenMaiServices khuyenMaiServices=new ChuongTrinhKhuyenMaiServices();
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
    public void insertListSanPham(List<SanPham> listSanPhamExcel) {
        Iterator<SanPham> iterator = listSanPhamExcel.iterator();

        while (iterator.hasNext()) {
            SanPham spExcel = iterator.next();
            boolean exists = false;

            for (SanPham sp : listSanPham) {
                if (sp.getMasp().equals(spExcel.getMasp())) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                iterator.remove(); // Xoá an toàn khi đang lặp
            } else {
                listSanPham.add(spExcel);
            }
        }
        sanPhamDAO.insertListSanPham(listSanPhamExcel); // Chỉ insert sp mới
    }

    public boolean checkMaSanPham(String maSanPham) {
        for (SanPham nhanVien : listSanPham) {
            if (nhanVien.getMasp().equals(maSanPham)) {
                return true;
            }
        }
        return false;
    }
    public SanPham timTheoMa(String maSanPham) {
        for (SanPham sanPham : listSanPham) {
            if (sanPham.getMasp().equals(maSanPham)) {
                return sanPham;
            }
        }
        return null;
    }
    public int getGia(String maSanPham, LocalDate ngayMua) {
        SanPham sp = timTheoMa(maSanPham);
        if (sp == null) return 0;
        System.out.println(ngayMua);
        int giaGoc = sp.getDongia();

        for (ChuongTrinhKhuyenMai ctkm : khuyenMaiServices.getListChuongTrinhKhuyenMai()) {
            if (true) {
                System.out.println("ngayMua: " + ngayMua);
                System.out.println("ngaybd: " + ctkm.getNgaybd());
                System.out.println("ngaykt: " + ctkm.getNgaykt());

                if (!ngayMua.isBefore(ctkm.getNgaybd()) && !ngayMua.isAfter(ctkm.getNgaykt())) {
                    System.out.println("Vào if rồi");
                } else {
                    System.out.println("Không vào vì không nằm trong khoảng ngày");
                }
            }

            if (!ngayMua.isBefore(ctkm.getNgaybd()) && !ngayMua.isAfter(ctkm.getNgaykt())) {
                System.out.println("kiem tra if");
                if (ctkm instanceof KmTheoSanPham) {
                    KmTheoSanPham km = (KmTheoSanPham) ctkm;
                    if (maSanPham.equals(km.getMasp())) {
                        double giamGia = giaGoc * (km.getPhantramkhuyenmai() / 100.0);
                        return (int) (giaGoc - giamGia);
                    }
                }
            }
        }

        return giaGoc;
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
