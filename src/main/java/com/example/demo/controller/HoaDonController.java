package com.example.demo.controller;

import com.example.demo.model.TableChiTietHoaDon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HoaDonController implements Initializable {
    @FXML
    private TextField maSach;
    @FXML
    private TextField soLuong;
    @FXML private Button btnAdd;
    @FXML
    private TableView<TableChiTietHoaDon> table;
    @FXML
    private TableColumn<TableChiTietHoaDon, Integer> maSachColumn;
    @FXML
    private TableColumn<TableChiTietHoaDon,  String> tenSachColumn;
    @FXML
    private TableColumn<TableChiTietHoaDon, Integer> donGiaColumn;
    @FXML
    private TableColumn<TableChiTietHoaDon,Integer> soLuongColumn;
    @FXML
    private TableColumn<TableChiTietHoaDon, Float> khuyenMaiColumn;
    @FXML
    private TableColumn<TableChiTietHoaDon, Integer> thanhTienColumn;;
    private ObservableList<TableChiTietHoaDon> data;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setOnAction(e -> add());
        data = FXCollections.observableArrayList(
                new TableChiTietHoaDon(123,"abchh",12,1,0.2,2)
        );
        maSachColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tenSachColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        khuyenMaiColumn.setCellValueFactory(new PropertyValueFactory<>("khuyenMai"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        table.setItems(data);

    }
    public void add() {
        TableChiTietHoaDon tableChiTietHoaDon =new TableChiTietHoaDon(Integer.parseInt(maSach.getText()),"qqqqqq",12,1,0.2,2);
        data.add(tableChiTietHoaDon);
    }
}
