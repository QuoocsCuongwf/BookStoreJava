package com.example.demo.databaseAccesssObject;

import com.example.demo.model.TacGia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;


public class TacGiaDAO {
    List<TacGia> listTacGia = new ArrayList<TacGia>();
    ConnectDatabase condbtg =new ConnectDatabase();
    public TacGiaDAO() {}

    public List<TacGia> getListTacGia() {
        String values = condbtg.query("select * from Tac_Gia");
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.registerModule(new JavaTimeModule());
            listTacGia=mapper.readValue(values, new TypeReference<List<TacGia>>() {});
            }  catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return listTacGia;
    }
    public void deleteTacGia(String maTacGia) {
        System.out.println("deleteTacGia"+maTacGia);
        condbtg.insert("delete from TAc_GIA where MATG='"+maTacGia+"'");
    }
    public void addTacGia(TacGia tacGia) {
        String query = "INSERT INTO TAC_GIA (matg,hotg,tentg,quequan,namsinh)"+
                "VALUES('"+tacGia.getMatg() + "','" +
                tacGia.getHotg()+"', '" +
                tacGia.getTentg() + "', '" +
                tacGia.getQuequan() +"', " +
                tacGia.getNamsinh() +")";
        condbtg.insert(query);
    }
    public void updateTacGia(TacGia tacGia){
        String query = "UPDATE TAC_GIA SET "
                + "HOTG = '" + tacGia.getHotg() + "', "
                + "TENTG = '" + tacGia.getTentg() + "', "
                + "QUEQUAN = '" + tacGia.getQuequan() + "', "
                + "NAMSINH = " + tacGia.getNamsinh() + " "
                + "WHERE MATG = '" + tacGia.getMatg() + "'";
        condbtg.update(query);
    }


}
