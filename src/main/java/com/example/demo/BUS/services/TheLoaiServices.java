package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.TheLoaiDAO;
import com.example.demo.model.TheLoai;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheLoaiServices {
    private final TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
    private static List<TheLoai> listTheLoai = new ArrayList<>();

    public List<TheLoai> getListTheLoai() {
        listTheLoai = theLoaiDAO.getListTheLoai();
        System.out.println(listTheLoai);
        return listTheLoai;
    }

    public List<TheLoai> findByIdTheLoai(String idTheLoai) {
        List<TheLoai> listResult = new ArrayList<>();
        for (TheLoai theLoai : listTheLoai) {
            if (theLoai.getMaTheLoai().contains(idTheLoai)) {
                listResult.add(theLoai);
            }
        }
        return listResult;
    }

    public String addTheLoai(TheLoai theLoai) {
        listTheLoai = theLoaiDAO.getListTheLoai();
        if (!checkMaTheLoai(theLoai.getMaTheLoai())) {
            try {
                theLoaiDAO.addTheLoai(theLoai);
                return "Add Success";
            } catch (Exception e) {
                e.printStackTrace();
                return "Add Fail - Exception: " + e.getMessage();
            }
        }
        return "Add Fail - Duplicate mã thể loại";
    }

    public boolean checkMaTheLoai(String idTheLoai) {
        for (TheLoai theLoai : listTheLoai) {
            if (theLoai.getMaTheLoai().equals(idTheLoai)) {
                return true;
            }
        }
        return false;
    }

    public List<TheLoai> searchTheLoai(String inforTheLoai) {
        return listTheLoai.stream()
                .filter(theLoai -> theLoai.getMaTheLoai().contains(inforTheLoai) || theLoai.getTenTheLoai().contains(inforTheLoai))
                .collect(Collectors.toList());
    }

    public void deleteTheLoai(String maTheLoai) {
        listTheLoai.removeIf(tl -> tl.getMaTheLoai().equals(maTheLoai));
        theLoaiDAO.deleteTheLoai(maTheLoai);
    }

    public String updateTheLoai(TheLoai theLoai) {
        for (int i = 0; i < listTheLoai.size(); i++) {
            if (listTheLoai.get(i).getMaTheLoai().equals(theLoai.getMaTheLoai())) {
                listTheLoai.set(i, theLoai);
            }
        }
        theLoaiDAO.updateTheLoai(theLoai);
        return "Update Success";
    }
}