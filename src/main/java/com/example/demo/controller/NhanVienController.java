package com.example.demo.controller;

import com.example.demo.model.NhanVien;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
@Component
@Controller
public class NhanVienController implements Initializable {
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
        maNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tenNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        hoNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("ho"));
        chucVuNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
        luongNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("luong"));
        sdtNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        tableView.setItems(data);
    }


}
