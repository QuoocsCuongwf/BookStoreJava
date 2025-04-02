//package com.example.demo.controller;
//
//import com.example.demo.NhanVien;
//import com.example.demo.Reponsitory;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.Pane;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//@Component
//@Controller
//public class NhanVienController implements Initializable {
//    @Autowired
//    private Reponsitory repo ;
//    @FXML
//    private Pane nhanVienPane;
//    @FXML
//    private TableView<NhanVien> tableView;
//    @FXML
//    private TableColumn<NhanVien, Integer> maNhanVienColumn;
//    @FXML
//    private TableColumn<NhanVien, String> tenNhanVienColumn;
//    @FXML
//    private TableColumn<NhanVien, String> hoNhanVienColumn;
//    @FXML
//    private TableColumn<NhanVien, String> chucVuNhanVienColumn;
//    @FXML
//    private TableColumn<NhanVien, Integer> luongNhanVienColumn;
//    @FXML
//    private TableColumn<NhanVien, String> sdtNhanVienColumn;
//    private ObservableList<NhanVien> data;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println("Repo is null: " + (repo == null));
//
//        data = FXCollections.observableArrayList(repo.findAll());
//        maNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        tenNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
//        hoNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("ho"));
//        chucVuNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
//        luongNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("luong"));
//        sdtNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
//        tableView.setItems(data);
//    }
//
//
//}
