package com.example.demo.GuiController;

import com.example.demo.BUS.services.ThongKeServices;
import com.example.demo.model.ThongKe4Quy;
import com.example.demo.model.ThongKeSanPham;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {
    @FXML private Label lb_LoaiHienThi4Quy;
    @FXML private TableView<ThongKeSanPham> tb_Ban, tb_Nhap;
    @FXML private TableColumn<ThongKeSanPham,String> tb_Nhap_TenSP,tb_Ban_TenSP;
    @FXML private TableColumn<ThongKeSanPham,Integer> tb_Nhap_SL,tb_Ban_SL;

    @FXML private TableView<ThongKe4Quy>tb_TheoQuy;
    @FXML private TableColumn<ThongKe4Quy,String> tb_TheoQuy_TenSP;
    @FXML private TableColumn<ThongKe4Quy,Integer> tb_TheoQuy_SLQuy1,tb_TheoQuy_SLQuy2,tb_TheoQuy_SLQuy3,tb_TheoQuy_SLQuy4;

    @FXML private DatePicker ngayKetThuc,ngayBatDau;
    @FXML private ComboBox<Integer> cb_Nam;
    @FXML private ComboBox<String> cb_QuyTheoNhapHayBan = new ComboBox<>();
    @FXML private ComboBox<Integer> cb_Quy1234 = new ComboBox<>();
    @FXML private Button btn_TheoKhoangThoiGian,btn_TheoNam;
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTHD,btnTacGia,btnKhuyenMai,btnHoaDon,btnPhieuNhap;
    LeftMenuController leftMenuController = new LeftMenuController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap);
        cb_Nam.getItems().addAll(2019,2020,2021,2023,2024,2025);
        cb_QuyTheoNhapHayBan.getItems().addAll("Nhap","Ban");
        cb_Quy1234.getItems().addAll(1,2,3,4);

        tb_Nhap_TenSP.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tb_Nhap_SL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tb_Ban_SL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tb_Ban_TenSP.setCellValueFactory(new PropertyValueFactory<>("tenSach"));

        tb_TheoQuy_TenSP.setCellValueFactory(new PropertyValueFactory<>("tenSach"));
        tb_TheoQuy_SLQuy1.setCellValueFactory(new PropertyValueFactory<>("soLuongQ1"));
        tb_TheoQuy_SLQuy2.setCellValueFactory(new PropertyValueFactory<>("soLuongQ2"));
        tb_TheoQuy_SLQuy3.setCellValueFactory(new PropertyValueFactory<>("soLuongQ3"));
        tb_TheoQuy_SLQuy4.setCellValueFactory(new PropertyValueFactory<>("soLuongQ4"));

    }
    public void ThongKeTheoThoiGian(){
        LocalDate startDate = ngayBatDau.getValue();
        LocalDate endDate = ngayKetThuc.getValue();
        List<ThongKeSanPham> listNhap = new ArrayList<>();
        List<ThongKeSanPham> listBan = new ArrayList<>();
        ThongKeServices thongKeServices = new ThongKeServices();
        ObservableList<ThongKeSanPham> ob_listNhap = FXCollections.observableArrayList();
        ObservableList<ThongKeSanPham> ob_listBan = FXCollections.observableArrayList();
        if (startDate != null && endDate!= null) {
            Date tuNgay = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date denNgay = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            listBan = thongKeServices.thongKeBanHangTheoKhoangThoiGian(tuNgay,denNgay);
            listNhap = thongKeServices.thongKeNhapHangTheoKhoangThoiGian(tuNgay,denNgay);
            ob_listNhap = FXCollections.observableArrayList(listNhap);
            ob_listBan = FXCollections.observableArrayList(listBan);
            tb_Nhap.setItems(ob_listNhap);
            tb_Ban.setItems(ob_listBan);
        }else {
            showMessage("THỐNG KÊ THEO KHOẢNG THỜI GIAN","FAIL","Vui lòng chọn thời gian thời điêmmr bắt đâuuf và kết thúc");
            return;
        }

    }
    public void ThongKe4QuyTheoNam( ){
        tb_TheoQuy.refresh();
        String luaChonNhapBan = cb_QuyTheoNhapHayBan.getValue();
        Integer year = cb_Nam.getValue();
        List<ThongKe4Quy> list = new ArrayList<>();
        if (year == null) {
            showMessage("THỐNG KÊ 4 QUÝ THEO NĂM","FAIL","Vui lòng chọn năm !");
            return;
        }
        ObservableList<ThongKe4Quy> ob_list = FXCollections.observableArrayList();
        ThongKeServices thongKeServices = new ThongKeServices();
        if (luaChonNhapBan.contains("Ban")) {
            list = thongKeServices.thongKeSanPhamBanRaTrong4Quy(year);
        } else if (luaChonNhapBan.contains("Nhap")) {
            list = thongKeServices.thongKeSanPhamNhapVaoTrong4Quy(year);
        }else {
            showMessage("THỐNG KÊ 4 QUÝ THEO NĂM","FAIL","Vui lòng chọn loại thông kê : nhập hoặc bán !");
            return;
        }
        ob_list = FXCollections.observableArrayList(list);
        tb_TheoQuy.setItems(ob_list);

    }
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu không cần chờ
    }
    public void ThongKe1QuyTheoNam(){
        tb_Nhap.refresh();
        tb_Ban.refresh();
        Integer year = cb_Nam.getValue();
        Integer quy = cb_Quy1234.getValue();
        List<ThongKeSanPham> listNhap = new ArrayList<>();
        List<ThongKeSanPham> listBan = new ArrayList<>();
        ObservableList<ThongKeSanPham> ob_listNhap = FXCollections.observableArrayList();
        ObservableList<ThongKeSanPham> ob_listBan = FXCollections.observableArrayList();
        if (year != null && quy != null) {
            ThongKeServices thongKeServices = new ThongKeServices();
            listNhap = thongKeServices.thongKeSanPhamNhapVaoTheoQuy(year,quy);
            listBan = thongKeServices.thongKeSanPhamBanRaTheoQuy(year,quy);
        }else {
            showMessage("THỐNG KÊ 1 QUÝ ","FAIL","Vui lòng chọn đủ thông tin quý và năm");
            return;
        }
        ob_listNhap = FXCollections.observableArrayList(listNhap);
        ob_listBan = FXCollections.observableArrayList(listBan);
        tb_Nhap.setItems(ob_listNhap);
        tb_Ban.setItems(ob_listBan);
    }

}
