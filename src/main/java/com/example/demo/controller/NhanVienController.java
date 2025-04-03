package com.example.demo.controller;

import com.example.demo.model.NhanVien;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Component
@Controller
public class NhanVienController implements Initializable {

    @FXML
    private Pane inforContainer;

    @FXML
    private Pane nhanVienPane;
    @FXML
    private TableView<NhanVien> tableView;
    @FXML
    private TableColumn<NhanVien, Integer> maNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> tenNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> hoNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> chucVuNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, Integer> luongNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> sdtNhanVienColumn;
    private ObservableList<NhanVien> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inforContainer.setVisible(false);

        maNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("manv"));
        tenNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("tennv"));
        hoNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("honv"));
        chucVuNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("chucvu"));
        luongNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("luong"));
        sdtNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("cccd"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Item: " + newValue.getMail());
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });
        String json = callApi("http://localhost:8080/nhanVien/getAllNhanVien");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<NhanVien> nhanVienList = new ArrayList<NhanVien>();
        System.out.println("json: " + json);
        try {
            nhanVienList = objectMapper.readValue(json, new TypeReference<List<NhanVien>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nhanVienList);
        data = FXCollections.observableArrayList(nhanVienList);
        tableView.setItems(data);
    }

     public String callApi(String urlApi) {
        String values="";
        try {
            URL url = new URL(urlApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            values=response.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         return values;
     }

    public void deleteNhanVien() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            NhanVien nhanVien = data.get(selectedIndex);
            data.remove(selectedIndex); // Optional: remove from ObservableList to update the table
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }

    public void openInforContainer(){
        inforContainer.setVisible(true);
    }
    public void clossInforContainer(){
        inforContainer.setVisible(false);
    }

}
