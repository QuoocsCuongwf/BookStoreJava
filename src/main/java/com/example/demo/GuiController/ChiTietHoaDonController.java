package com.example.demo.GuiController;

import com.example.demo.model.HoaDon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.demo.model.ChiTietHoaDon;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ChiTietHoaDonController implements Initializable {

    @FXML
    private TableView<ChiTietHoaDon> table;

    @FXML
    private TableColumn<ChiTietHoaDon, String> maSachColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Double> donGiaColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Integer> soLuongColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Double> thanhTienColumn;

    @FXML
    private TextField textFieldMaNV,textFieldMaKH,textFieldMaSach,textFieldMaHoaDon,textFieldSL;

    private static List<HoaDon> listHoaDon=new ArrayList<>();
    @FXML
    private Button btnAdd;


    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai;

    private final ObservableList<ChiTietHoaDon> danhSachChiTietHoaDon = FXCollections.observableArrayList();
    LeftMenuController leftMenuController=new LeftMenuController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai);
        // Liên kết cột với thuộc tính đối tượng ChiTietHoaDon
        maSachColumn.setCellValueFactory(new PropertyValueFactory<>("masp"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
        btnAdd.setOnAction(event -> onAddClicked(event ));
        table.setItems(danhSachChiTietHoaDon);
        if(listHoaDon.isEmpty()){
            System.out.println("listHoaDon is empty");
            HoaDonController hoaDonController=new HoaDonController();
            listHoaDon=hoaDonController.getHoaDonList();
        }
        textFieldMaHoaDon.setText("HD"+listHoaDon.size()+1);
        textFieldMaHoaDon.setDisable(true);
    }
    public void xuatHoaDon(){

    }
    @FXML
    void onAddClicked(ActionEvent event) {
        try {

            String masp = textFieldMaSach.getText();
            int soluong = Integer.parseInt(textFieldSL.getText());
            double dongia = 100.0; // Bạn nên lấy từ DB theo mã sản phẩm
            double khuyenmai = 0.1; // ví dụ giảm 10%
            double thanhtien = soluong * dongia * (1 - khuyenmai);

            ChiTietHoaDon chiTiet = new ChiTietHoaDon();
            chiTiet.setMasp(masp);
            chiTiet.setMahd(textFieldMaHoaDon.getText());
            chiTiet.setSl(soluong);
            chiTiet.setDongia((int) dongia);
            chiTiet.setThanhtien((int)thanhtien);
            CallApi callApi=new CallApi();
            int result=Integer.parseInt(callApi.callPostRequestBody("http://localhost:8080/chiTietHoaDon/check",convertCTHDtoJson(chiTiet)));
            if (result==200) {
                danhSachChiTietHoaDon.add(chiTiet);
                textFieldMaSach.clear();
                textFieldSL.clear();
            }
            if (result==400) {
                showMessage("error","Lỗi số lượng","Số lượng sản phẩm trong kho không đủ");
            }
            if (result==404) {
                showMessage("Not find","Lỗi không tìm thấy","Sản phẩm không tồn tại");
            }
        } catch (NumberFormatException e) {
            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dữ liệu không hợp lệ");
            alert.setContentText("Vui lòng nhập đúng định dạng số cho số lượng.");
            alert.showAndWait();
        }
    }
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu không cần chờ
    }
    public String convertCTHDtoJson(ChiTietHoaDon chiTietHoaDon) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(chiTietHoaDon);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // hoặc return "{}"; tùy bạn muốn xử lý lỗi như nào
        }
    }

    @FXML
    void onXuatHoaDonClicked() {
        // Thực hiện lưu thông tin hóa đơn vào CSDL
        HoaDon hoaDon=new HoaDon();
        hoaDon.setMahd(textFieldMaHoaDon.getText());
        hoaDon.setNgaylap(java.time.LocalDate.now());
        hoaDon.setManv(textFieldMaNV.getText());
        hoaDon.setMakh(textFieldMaKH.getText());
        HoaDonController hoaDonController=new HoaDonController();
        hoaDonController.getHoaDonList().add(hoaDon);
        CallApi callApi=new CallApi();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(hoaDon);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        callApi.callPostRequestBody("http://localhost:8080/hoaDon/Add",json);
        for(ChiTietHoaDon cthd:danhSachChiTietHoaDon){
            cthd.setThanhtien(cthd.getDongia()*cthd.getSl());
            try {
                json = objectMapper.writeValueAsString(cthd);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            System.out.println(json);
            callApi.callPostRequestBody("http://localhost:8080/chiTietHoaDon/add",json);
        }
        // Có thể gọi services đã viết: ChiTietHoaDonServices
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Xuất hóa đơn");
        alert.setContentText("Hóa đơn đã được xuất thành công!");
        alert.showAndWait();
    }
    public void deleteChiTietHoaDon(){

    }
    public void updateChiTietHoaDon(){

    }

    // Thêm sự kiện nếu cần cho các nút menu bên trái (btnThongKe, btnKhachHang,...)
    @FXML
    void onThongKeClicked(ActionEvent event) {
        System.out.println("Chuyển đến màn hình thống kê...");
    }

    // Các sự kiện khác tương tự...
}
