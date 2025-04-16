package com.example.demo.GuiController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.demo.model.ChiTietHoaDon;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class ChiTietHoaDonController {

    @FXML
    private TableView<ChiTietHoaDon> table;

    @FXML
    private TableColumn<ChiTietHoaDon, String> maSachColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Double> donGiaColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Integer> soLuongColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Double> khuyenMaiColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Double> thanhTienColumn;

    @FXML
    private TextField maSach;

    @FXML
    private TextField soLuong;

    @FXML
    private TextField maSach1;

    @FXML
    private TextField maSach2;

    @FXML
    private TextField txtMaHD;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai;

    private final ObservableList<ChiTietHoaDon> danhSachHoaDon = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Liên kết cột với thuộc tính đối tượng ChiTietHoaDon
        maSachColumn.setCellValueFactory(new PropertyValueFactory<>("masp"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        khuyenMaiColumn.setCellValueFactory(new PropertyValueFactory<>("khuyenmai"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
        btnAdd.setOnAction(event -> onAddClicked(event ));
        table.setItems(danhSachHoaDon);
    }

    @FXML
    void onAddClicked(ActionEvent event) {
        try {
            String masp = maSach.getText();
            int soluong = Integer.parseInt(soLuong.getText());
            double dongia = 100.0; // Bạn nên lấy từ DB theo mã sản phẩm
            double khuyenmai = 0.1; // ví dụ giảm 10%
            double thanhtien = soluong * dongia * (1 - khuyenmai);

            ChiTietHoaDon chiTiet = new ChiTietHoaDon();
            chiTiet.setMasp(masp);
            chiTiet.setSl(soluong);
            chiTiet.setDongia((int) dongia);
            chiTiet.setThanhtien((int)thanhtien);

            danhSachHoaDon.add(chiTiet);

            maSach.clear();
            soLuong.clear();
        } catch (NumberFormatException e) {
            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dữ liệu không hợp lệ");
            alert.setContentText("Vui lòng nhập đúng định dạng số cho số lượng.");
            alert.showAndWait();
        }
    }

    @FXML
    void onXuatHoaDonClicked(ActionEvent event) {
        // Thực hiện lưu thông tin hóa đơn vào CSDL
        // Có thể gọi services đã viết: ChiTietHoaDonServices
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Xuất hóa đơn");
        alert.setContentText("Hóa đơn đã được xuất thành công!");
        alert.showAndWait();
    }

    // Thêm sự kiện nếu cần cho các nút menu bên trái (btnThongKe, btnKhachHang,...)
    @FXML
    void onThongKeClicked(ActionEvent event) {
        System.out.println("Chuyển đến màn hình thống kê...");
    }

    // Các sự kiện khác tương tự...
}
