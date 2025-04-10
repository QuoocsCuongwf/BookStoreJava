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
        String values = condbtg.query("select * from TacGia");
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
        String query = "INSERT INTO TAC_GIA (matg,hotg,tentg,quequantg,namsinhtg)"+
                "VALUES('"+tacGia.getMatg() + "','" +
                tacGia.getMatg() + "', '"+
                tacGia.getHotg()+"', '" +
                tacGia.getTentg() + "', '" +
                tacGia.getQuequan() +"', '" +
                tacGia.getNamsinh() + "', '"
                ;
        condbtg.insert(query);
    }

}
