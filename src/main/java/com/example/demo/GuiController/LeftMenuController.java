package com.example.demo.GuiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LeftMenuController {

    public void bindHandlers(
            Button btnThongKe, Button btnKhachHang, Button btnSanPham,
            Button btnNhanVien, Button btnNCC, Button btnTacGia,
            Button btnHoaDon, Button btnTHD, Button btnKhuyenMai,
            Button btnTheLoai, Button btnNhaXuatBan, Button btnPhieuNhap,
            Button btnTaoPhieuNhap
    ) {
        // Bind mandatory buttons
        btnThongKe.setOnAction(this::handleThongKe);
        btnKhachHang.setOnAction(this::handleKhachHang);
        btnSanPham.setOnAction(this::handleSanPham);
        btnNhanVien.setOnAction(this::handleNhanVien);
        btnNCC.setOnAction(this::handleNCC);
        btnTacGia.setOnAction(this::handleTacGia);
        btnHoaDon.setOnAction(this::handleHoaDon);
        btnTHD.setOnAction(this::handleTHD);
        btnKhuyenMai.setOnAction(this::handleKhuyenMai);

        // Bind optional buttons with null checks
        if (btnTheLoai != null) {
            btnTheLoai.setOnAction(this::handleTheLoai);
        }
        if (btnNhaXuatBan != null) {
            btnNhaXuatBan.setOnAction(this::handleNhaXuatBan);
        }
        if (btnPhieuNhap != null) {
            btnPhieuNhap.setOnAction(this::handlePhieuNhap);
        }
        if (btnTaoPhieuNhap != null) {
            btnTaoPhieuNhap.setOnAction(this::handleTaoPhieuNhap);
        }
    }

    private void handleThongKe(ActionEvent event) {
        System.out.println("Thống kê clicked");
        loadScene(event, "/ThongKe.fxml");
    }

    private void handleKhachHang(ActionEvent event) {
        System.out.println("Khách hàng clicked");
        loadScene(event, "/KhachHang.fxml");
    }

    private void handleSanPham(ActionEvent event) {
        System.out.println("Sản phẩm clicked");
        loadScene(event, "/SanPham.fxml");
    }

    private void handleNhanVien(ActionEvent event) {
        System.out.println("Nhân viên clicked");
        loadScene(event, "/NhanVien.fxml");
    }

    private void handleNCC(ActionEvent event) {
        System.out.println("Nhà cung cấp clicked");
        loadScene(event, "/NhaCungCap.fxml");
    }

    private void handleTacGia(ActionEvent event) {
        System.out.println("Tác giả clicked");
        loadScene(event, "/TacGia.fxml");
    }

    private void handleHoaDon(ActionEvent event) {
        System.out.println("Hóa đơn clicked");
        loadScene(event, "/HoaDon.fxml");
    }

    private void handleTHD(ActionEvent event) {
        System.out.println("Tạo hóa đơn clicked");
        loadScene(event, "/TaoHoaDon.fxml");
    }

    private void handleKhuyenMai(ActionEvent event) {
        System.out.println("Khuyến mãi clicked");
        loadScene(event, "/KhuyenMai.fxml");
    }

    private void handlePhieuNhap(ActionEvent event) {
        System.out.println("Phiếu nhập clicked");
        System.out.println("Load path: " + getClass().getResource("/PhieuNhap.fxml"));
        loadScene(event, "/PhieuNhap.fxml");
    }

    private void handleTheLoai(ActionEvent event) {
        System.out.println("Thể loại clicked");
        loadScene(event, "/TheLoai.fxml");
    }

    private void handleNhaXuatBan(ActionEvent event) {
        System.out.println("Nhà xuất bản clicked");
        loadScene(event, "/NhaXuatBan.fxml");
    }

    private void handleTaoPhieuNhap(ActionEvent event) {
        System.out.println("Tạo phiếu nhập clicked");
        loadScene(event, "/TaoPhieuNhap.fxml");
    }

    private void loadScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/asset/css/main.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Lỗi load FXML: " + fxmlPath);
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Lỗi khác khi load scene:");
            ex.printStackTrace();
        }
    }
}