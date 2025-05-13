package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.NhaCungCapDAO;
import com.example.demo.model.NhaCungCap;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhaCungCapServices {
    private NhaCungCapDAO nhaCungCapDAO=new NhaCungCapDAO();
    private List<NhaCungCap> listNhaCungCap = new ArrayList<>();
    public List<NhaCungCap> getListNhaCungCap() {
        listNhaCungCap=nhaCungCapDAO.getListNhaCungCap();
        System.out.println(listNhaCungCap);
        return listNhaCungCap;
    }

    public List<NhaCungCap> findByIdNhaCungCap(String idNhaCungCap) {
        List<NhaCungCap> listResult = new ArrayList<NhaCungCap>();
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getMaNhaCungCap().contains(idNhaCungCap)) {
                listResult.add(nhaCungCap);
            }
        }
        return listResult;
    }


    public String addNhaCungCap(NhaCungCap nhaCungCap) {
        // Load lại danh sách mới nhất từ DB
        listNhaCungCap = nhaCungCapDAO.getListNhaCungCap();

        if (!checkMaNhaCungCap(nhaCungCap.getMaNhaCungCap())) {
            try {
                nhaCungCapDAO.addNhaCungCap(nhaCungCap);
                return "Add Success";
            } catch (Exception e) {
                e.printStackTrace();
                return "Add Fail - Exception: " + e.getMessage();
            }
        }
        return "Add Fail - Duplicate mã NCC";
    }

    public boolean checkMaNhaCungCap(String idNhaCungCap) {
        for (NhaCungCap nhaCungCap : listNhaCungCap) {
            if (nhaCungCap.getMaNhaCungCap().equals(idNhaCungCap)) {
                return true;
            }
        }
        return false;
    }

    public List<NhaCungCap> searchNhaCungCap(String inforNhaCungCap) {
        List<NhaCungCap> result=listNhaCungCap.stream()
                .filter(nhaCungCap -> nhaCungCap.getMaNhaCungCap().contains(inforNhaCungCap) || nhaCungCap.getTenNhaCungCap().contains(inforNhaCungCap))
                .collect(Collectors.toList());
        return result;
    }

    public void deleteNhaCungCap(String maNhaCungCap) {
        listNhaCungCap.removeIf(ncc ->ncc.getMaNhaCungCap().equals(maNhaCungCap));
        nhaCungCapDAO.deleteNhaCungCap(maNhaCungCap);
    }

    public String updateNhaCungCap(NhaCungCap nhaCungCap) {
        for (int i = 0; i < listNhaCungCap.size(); i++) {
            if (listNhaCungCap.get(i).getMaNhaCungCap().equals(nhaCungCap.getMaNhaCungCap())){
                listNhaCungCap.set(i, nhaCungCap);
            }
        }
        nhaCungCapDAO.updateNhaCungCap(nhaCungCap);
        return "update Success";
    }

    public static void main(String[] args) {
        NhaCungCapServices nhaCungCapServices= new NhaCungCapServices();
        NhaCungCap nhaCungCap =new NhaCungCap();
        nhaCungCap.setMaNhaCungCap("NCC001");
        nhaCungCap.setTenNhaCungCap("Cao Minh Triet");
        nhaCungCap.setSdt("0123456789");
        nhaCungCap.setDiaChi("C11/35G");
        nhaCungCap.setEmail("nhacungcap@gmail.com");

        nhaCungCapServices.addNhaCungCap(nhaCungCap);
    }




}
