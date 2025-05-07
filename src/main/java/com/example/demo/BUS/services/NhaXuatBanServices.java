package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.NhaXuatBanDAO;
import com.example.demo.model.NhaXuatBan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhaXuatBanServices {
    private final NhaXuatBanDAO nhaXuatBanDAO = new NhaXuatBanDAO();
    private List<NhaXuatBan> listNhaXuatBan = new ArrayList<>();

    public List<NhaXuatBan> getListNhaXuatBan() {
        listNhaXuatBan = nhaXuatBanDAO.getListNhaXuatBan();
        System.out.println(listNhaXuatBan);
        return listNhaXuatBan;
    }

    public List<NhaXuatBan> findByIdNhaXuatBan(String idNhaXuatBan) {
        List<NhaXuatBan> listResult = new ArrayList<>();
        for (NhaXuatBan nhaXuatBan : listNhaXuatBan) {
            if (nhaXuatBan.getMaNhaXuatBan().contains(idNhaXuatBan)) {
                listResult.add(nhaXuatBan);
            }
        }
        return listResult;
    }

    public String addNhaXuatBan(NhaXuatBan nhaXuatBan) {
        // Load the latest list from DB
        listNhaXuatBan = nhaXuatBanDAO.getListNhaXuatBan();

        if (!checkMaNhaXuatBan(nhaXuatBan.getMaNhaXuatBan())) {
            try {
                nhaXuatBanDAO.addNhaXuatBan(nhaXuatBan);
                return "Add Success";
            } catch (Exception e) {
                e.printStackTrace();
                return "Add Fail - Exception: " + e.getMessage();
            }
        }
        return "Add Fail - Duplicate mã NXB";
    }

    public boolean checkMaNhaXuatBan(String idNhaXuatBan) {
        for (NhaXuatBan nhaXuatBan : listNhaXuatBan) {
            if (nhaXuatBan.getMaNhaXuatBan().equals(idNhaXuatBan)) {
                return true;
            }
        }
        return false;
    }

    public List<NhaXuatBan> searchNhaXuatBan(String inforNhaXuatBan) {
        List<NhaXuatBan> result = listNhaXuatBan.stream()
                .filter(nhaXuatBan -> nhaXuatBan.getMaNhaXuatBan().contains(inforNhaXuatBan) || nhaXuatBan.getTenNhaXuatBan().contains(inforNhaXuatBan))
                .collect(Collectors.toList());
        return result;
    }

    public void deleteNhaXuatBan(String maNhaXuatBan) {
        listNhaXuatBan.removeIf(nxb -> nxb.getMaNhaXuatBan().equals(maNhaXuatBan));
        nhaXuatBanDAO.deleteNhaXuatBan(maNhaXuatBan);
    }

    public String updateNhaXuatBan(NhaXuatBan nhaXuatBan) {
        nhaXuatBanDAO.updateNhaXuatBan(nhaXuatBan);
        return "Update Success";
    }
}