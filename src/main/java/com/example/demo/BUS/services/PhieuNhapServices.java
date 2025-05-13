package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.PhieuNhapDAO;
import com.example.demo.model.PhieuNhap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhieuNhapServices {
    private PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
    private List<PhieuNhap> phieuNhapList = new ArrayList<PhieuNhap>();

    public List<PhieuNhap> getPhieuNhapList() {
        phieuNhapList = phieuNhapDAO.getListPhieuNhap();
        return phieuNhapList;
    }
    public boolean checkPhieuNhap( String maPhieuNhap) {
        phieuNhapList = phieuNhapDAO.getListPhieuNhap();
        for (PhieuNhap p : phieuNhapList) {
            if (p.getMapn().equals(maPhieuNhap)) {
                return true;
            }
        }
        return false;
    }
    public String addPhieuNhap(PhieuNhap p) {
        if (!checkPhieuNhap(p.getMapn())) {
            phieuNhapList.add(p);
            phieuNhapDAO.addPhieuNhap(p);
            return "success";
        }
        return "fail";
    }
    public String deletePhieuNhap( String maPhieuNhap) {
        for (PhieuNhap p1 : phieuNhapList) {
            if (p1.getMapn().equals(maPhieuNhap)){
                phieuNhapList.remove(p1);
                phieuNhapDAO.deletePhieuNhap(maPhieuNhap);
                return "success";
            }
        }
        return "fail";
    }
    public String updatePhieuNhap(PhieuNhap p) {
        for ( int i = 0; i < phieuNhapList.size(); i++ ) {
            if ( p.getMapn().equals(phieuNhapList.get(i).getMapn())) {
                phieuNhapList.set(i, p);
                phieuNhapDAO.updatePhieuNhap(p);
                return "success";
            }
        }
        return "fail";
    }
    public List<PhieuNhap> searchPhieuNhap(String searchPhieuNhap) {
        phieuNhapList=getPhieuNhapList();
        List<PhieuNhap> searchPhieuNhapList = phieuNhapList.stream()
                .filter(phieuNhap ->phieuNhap.getMapn().contains(searchPhieuNhap) ||
                        phieuNhap.getManv().contains(searchPhieuNhap) ||  phieuNhap.getMancc().contains(searchPhieuNhap))
                .collect(Collectors.toList());
        return searchPhieuNhapList;
    }
}
