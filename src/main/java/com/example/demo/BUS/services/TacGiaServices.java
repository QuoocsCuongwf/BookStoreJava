package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.TacGiaDAO;
import com.example.demo.model.TacGia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TacGiaServices {
    private TacGiaDAO tacGiaDAO = new TacGiaDAO();
    private List<TacGia> listTacGia = new ArrayList<TacGia>();
    public List<TacGia>getTacGiaList() {
        listTacGia=tacGiaDAO.getListTacGia();
        System.out.println(listTacGia);
        return listTacGia;
    }
    public List<TacGia> findByIDTacGia (String idtacGia){
        List<TacGia> listResult = new ArrayList<TacGia>();
        for (TacGia tacGia : listTacGia) {
            if(tacGia.getMatg().contains(idtacGia)){
                listResult.add(tacGia);
            }
        }
        return listResult;
    }


    public String addTacGia(TacGia tacGia) {
        if(!checkMaTacGia(tacGia.getMatg())){
            tacGiaDAO.addTacGia(tacGia);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaTacGia(String matg) {
        for(TacGia tacGia : listTacGia){
            if(tacGia.getMatg().equals(matg)){
                return true;
            }
        }
        return false;
    }

    public List<TacGia> searchTacGia(String in4TacGia) {
        List<TacGia> result = listTacGia.stream().filter( tacGia -> tacGia.getMatg().contains(in4TacGia) || tacGia.getHotg().contains(in4TacGia) || tacGia.getTentg().contains(in4TacGia) || tacGia.getQuequan().contains(in4TacGia) ||  String.valueOf(tacGia.getNamsinh()).contains(in4TacGia))
                .collect(Collectors.toList());
        return result;
    }
    public void deleteTacGia(String maTacGia) {
        listTacGia.removeIf(tacGia -> tacGia.getMatg().equals(maTacGia));
        tacGiaDAO.deleteTacGia(maTacGia);
    }
    public String updateTacGia (TacGia tacGia) {
        tacGiaDAO.updateTacGia(tacGia);
        return "update success";
    }

    public static void main(String[] args){
        TacGiaServices tacGiaServices=new TacGiaServices();
        TacGia tacGia = new TacGia();
        tacGia.setMatg("TG001");
        tacGia.setHotg("Nguyễn");
        tacGia.setTentg("Nhật Ánh");
        tacGia.setQuequan("Quảng Nam");
        tacGia.setNamsinh(1955);

        tacGiaServices.addTacGia(tacGia);
        tacGiaServices.getTacGiaList();


    }
}
