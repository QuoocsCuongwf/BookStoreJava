package com.example.demo.controller;

import com.example.demo.model.KhachHang;
import com.example.demo.model.NhanVien;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class KhachHangController implements Initializable {

    @FXML
    private AnchorPane khachHangAnchorPane;

    @FXML
    private Pane inforContainerKhachHang;
    @FXML
    private ObservableList<NhanVien> data;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<KhachHang, String> hoKhachHangColumn,emailKhachHangColumn,
            tenKhachHangColumn,diaChiKhachHangColumn;
    @FXML
    private TableColumn<KhachHang,Integer> maKhachHangColumn;

    @FXML
    private TextField txt_maKhachHang,txt_hoKhachHang,txt_tenKhachHang,txt_diaChi,
            txt_emailKhachHang,txt_sdtKhachHang,txt_timKiemKhachHang;

    @FXML
    private Button btn_themKhachHang,btn_capNhatKhachHang,btn_xoaKhachHang,
            btn_timKiemKhachHang,btn_danhSachKhachHang;


    //
    public void themKhachHang(ActionEvent actionEvent) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




}
