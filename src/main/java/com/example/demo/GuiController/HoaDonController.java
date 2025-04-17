package com.example.demo.GuiController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class HoaDonController implements Initializable {

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> maHDColumn;

    @FXML
    private TableColumn<?, ?> ngayTaoColumn;

    @FXML
    private TableColumn<?, ?> maNVColumn;

    @FXML
    private TableColumn<?, ?> maKHColumn;

    @FXML
    private TableColumn<?, ?> tongTienHoaDonColumn;

    @FXML
    private TextField textFieldTimKiem;

    @FXML
    private TextField textFieldMaHD;

    @FXML
    private TextField textFieldMaKhachHang;

    @FXML
    private TextField textFieldMaNhanVien;

    @FXML
    private DatePicker datePickerNgayTaoHD;

    @FXML
    private ListView<String> listCTHD;

    @FXML
    private Button btnAddCTHD;

    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai;

    private final ObservableList<String> danhSachChiTiet = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo danh sách cho ListView
        listCTHD.setItems(danhSachChiTiet);
    }

    @FXML
    private void timKiem() {
        String tuKhoa = textFieldTimKiem.getText();
        System.out.println("Đang tìm kiếm với từ khóa: " + tuKhoa);

        // TODO: thêm logic tìm kiếm thực tế
    }

    @FXML
    private void addCTHD() {
        String maHD = textFieldMaHD.getText();
        String maSP = "SP giả định"; // Thay bằng mã sản phẩm thực tế nếu có giao diện nhập
        int soLuong = 1; // Hoặc lấy từ một TextField số lượng

        String chiTiet = String.format("HD: %s - SP: %s - SL: %d", maHD, maSP, soLuong);
        danhSachChiTiet.add(chiTiet);
    }

    @FXML
    private void clossInforContainer() {
        // Đóng form hoặc ẩn nó
        System.out.println("Đóng thông tin hóa đơn");
        // Hoặc bạn có thể thay đổi visibility của VBox inforForm
    }

    // Thêm các hàm xử lý cho các nút khác nếu cần
}
