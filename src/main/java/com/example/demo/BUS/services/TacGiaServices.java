package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.TacGiaDAO;
import com.example.demo.model.TacGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TacGiaServices {
    private final TacGiaDAO tacGiaDAO;
    private List<TacGia> listTacGia = new ArrayList<>();

    @Autowired
    public TacGiaServices(TacGiaDAO tacGiaDAO) {
        this.tacGiaDAO = tacGiaDAO;
        this.listTacGia = tacGiaDAO.getListTacGia();
    }

    public List<TacGia> getTacGiaList() {
        listTacGia = tacGiaDAO.getListTacGia();
        System.out.println(listTacGia);
        return listTacGia;
    }

    public String addTacGia(TacGia tacGia) {
        if (tacGia == null || tacGia.getMatg() == null || tacGia.getMatg().isEmpty()) {
            return "Add Fail: Invalid TacGia or MaTacGia";
        }
        if (!checkMaTacGia(tacGia.getMatg())) {
            tacGiaDAO.addTacGia(tacGia);
            listTacGia.add(tacGia);
            return "Add Success";
        }
        return "Add Fail: MaTacGia already exists";
    }

    public boolean checkMaTacGia(String matg) {
        if (matg == null) return false;
        return listTacGia.stream().anyMatch(tacGia -> tacGia.getMatg().equals(matg));
    }

    public List<TacGia> searchTacGia(String in4TacGia) {
        if (in4TacGia == null) return new ArrayList<>();
        return listTacGia.stream()
                .filter(tacGia -> tacGia.getMatg().contains(in4TacGia) ||
                        tacGia.getHotg().contains(in4TacGia) ||
                        tacGia.getTentg().contains(in4TacGia) ||
                        tacGia.getQuequan().contains(in4TacGia) ||
                        String.valueOf(tacGia.getNamsinh()).contains(in4TacGia))
                .collect(Collectors.toList());
    }

    public void deleteTacGia(String maTacGia) {
        if (maTacGia != null) {
            listTacGia.removeIf(tacGia -> tacGia.getMatg().equals(maTacGia));
            tacGiaDAO.deleteTacGia(maTacGia);
        }
    }

    public String updateTacGia(TacGia tacGia) {
        if (tacGia == null || tacGia.getMatg() == null || tacGia.getMatg().isEmpty()) {
            return "Update Fail: Invalid TacGia or MaTacGia";
        }
        tacGiaDAO.updateTacGia(tacGia);
        for (int i = 0; i < listTacGia.size(); i++) {
            if (listTacGia.get(i).getMatg().equals(tacGia.getMatg())) {
                listTacGia.set(i, tacGia);
                break;
            }
        }
        return "update success";
    }
}