package com.example.demo.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NhaCungCapController {
    @FXML
    private TextField txtSupplierID, txtSupplierName, txtPhone, txtAddress, txtEmail;

    @FXML
    private Button btnAdd, btnEdit, btnDelete, btnClear;

    @FXML
    private TableView<?> tableSupplier; // Cần định nghĩa model dữ liệu cho TableView

    @FXML
    private void addSupplier() {
        String id = txtSupplierID.getText();
        String name = txtSupplierName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        // Xử lý thêm nhà cung cấp (lưu vào database hoặc danh sách)
        System.out.println("Thêm nhà cung cấp: " + name);
    }

    @FXML
    private void editSupplier() {
        // Xử lý sửa thông tin nhà cung cấp
        System.out.println("Sửa nhà cung cấp");
    }

    @FXML
    private void deleteSupplier() {
        // Xử lý xóa nhà cung cấp
        System.out.println("Xóa nhà cung cấp");
    }

    @FXML
    private void clearFields() {
        txtSupplierID.clear();
        txtSupplierName.clear();
        txtPhone.clear();
        txtAddress.clear();
        txtEmail.clear();
    }

}
