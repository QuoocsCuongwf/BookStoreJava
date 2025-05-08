package com.example.demo.GuiController;

import com.example.demo.model.HoaDon;
import com.example.demo.model.SanPham;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.UUID;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ChiTietHoaDonController implements Initializable {

    @FXML
    private TableView<ChiTietHoaDon> table;
    @FXML
    Label tongTienLabel;
    @FXML
    private TableColumn<ChiTietHoaDon, String> maSachColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Double> donGiaColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Integer> soLuongColumn;

    @FXML
    private TableColumn<ChiTietHoaDon, Double> thanhTienColumn;

    @FXML
    private TextField textFieldMaNV, textFieldMaKH, textFieldMaSach, textFieldMaHoaDon, textFieldSL;

    private static List<HoaDon> listHoaDon = new ArrayList<>();
    @FXML
    private Button btnAdd;

    boolean isSuaHoaDon = false;
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnPhieuNhap;
    @FXML DatePicker ngayTaoHoaDon;
    @FXML ChoiceBox<String> thanhToan;
    @FXML Tab tabTaoHD,tabPayment;
    @FXML TabPane tabPane;
    @FXML WebView webMoMo;
    private ObservableList<ChiTietHoaDon> danhSachChiTietHoaDon = FXCollections.observableArrayList();
    LeftMenuController leftMenuController = new LeftMenuController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap);
        // Liên kết cột với thuộc tính đối tượng ChiTietHoaDon
        maSachColumn.setCellValueFactory(new PropertyValueFactory<>("masp"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
        btnAdd.setOnAction(event -> onAddClicked(event));
        table.setItems(danhSachChiTietHoaDon);
            String maHoaDon = UUID.randomUUID().toString();
            textFieldMaHoaDon.setText("HD" +maHoaDon.substring(maHoaDon.length() - 8));
        textFieldMaHoaDon.setDisable(true);
        table.setOnMouseClicked((MouseEvent event) -> {
            ChiTietHoaDon selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                textFieldMaSach.setText(selected.getMasp());
                textFieldSL.setText(String.valueOf(selected.getSl()));
                // Nếu bạn có thêm thông tin khác, có thể set thêm vào các TextField tương ứng
            }
        });
        thanhToan.getItems().addAll("Tiền mặt","Chuyển khoản");

    }

    public void xuatHoaDon() {

    }

    @FXML
    void onAddClicked(ActionEvent event) {
        ChiTietHoaDon chiTiet = new ChiTietHoaDon();
        try {
            boolean isTonTai = false;
            List<SanPham> listSanPham = new ArrayList<>();
            if (listSanPham.size() == 0) {
                SanPhamController sanPhamController = new SanPhamController();
                listSanPham = sanPhamController.getListSanPham();
            }
            String masp = textFieldMaSach.getText();
            int soluong = Integer.parseInt(textFieldSL.getText());
            System.out.println(listSanPham.size());
            chiTiet.setMasp(masp);
            chiTiet.setMahd(textFieldMaHoaDon.getText());
            chiTiet.setSl(Integer.parseInt(textFieldSL.getText()));
            for (SanPham sp : listSanPham) {
                if (sp.getMasp().equals(textFieldMaSach.getText())) {
                    chiTiet.setDongia(sp.getDongia());
                    break;
                }
            }
            for (int i = 0; i < danhSachChiTietHoaDon.size(); i++) {
                if (danhSachChiTietHoaDon.get(i).getMasp().equals(textFieldMaSach.getText())) {
                    danhSachChiTietHoaDon.get(i).setSl(danhSachChiTietHoaDon.get(i).getSl() + Integer.parseInt(textFieldSL.getText()));
                    table.setItems(danhSachChiTietHoaDon);
                    textFieldMaSach.clear();
                    textFieldSL.clear();
                    isTonTai = true;
                    break;
                }
            }
            if (!isTonTai) {

                CallApi callApi = new CallApi();
                int result = Integer.parseInt(callApi.callPostRequestBody("http://localhost:8080/chiTietHoaDon/check", convertCTHDtoJson(chiTiet)));
                if (result == 200) {
                    danhSachChiTietHoaDon.add(chiTiet);
                    textFieldMaSach.clear();
                    textFieldSL.clear();
                }
                if (result == 400) {
                    showMessage("error", "Lỗi số lượng", "Số lượng sản phẩm trong kho không đủ");
                }
                if (result == 404) {
                    showMessage("Not find", "Lỗi không tìm thấy", "Sản phẩm không tồn tại");
                }
            }
        } catch (NumberFormatException e) {
            // Hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dữ liệu không hợp lệ");
            alert.setContentText("Vui lòng nhập đúng định dạng số cho số lượng.");
            alert.showAndWait();
        }
        chiTiet.setThanhtien(chiTiet.getDongia() * chiTiet.getSl());
        int tongTien = 0;
        for (int i = 0; i < danhSachChiTietHoaDon.size(); i++) {
            tongTien += danhSachChiTietHoaDon.get(i).getDongia() * danhSachChiTietHoaDon.get(i).getSl();
        }
        tongTienLabel.setText("Tổng tiền thanh toán: " + String.valueOf(tongTien));
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
    void onXuatHoaDonClicked() throws JsonProcessingException {
        if (isSuaHoaDon) {
            isSuaHoaDon = false;
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMahd(textFieldMaHoaDon.getText());
            hoaDon.setNgaylap(ngayTaoHoaDon.getValue());
            hoaDon.setManv(textFieldMaNV.getText());
            hoaDon.setMakh(textFieldMaKH.getText());
            for (ChiTietHoaDon cthd : danhSachChiTietHoaDon) {
                hoaDon.setTongtien(hoaDon.getTongtien() + cthd.getThanhtien());
            }
            HoaDonController hoaDonController = new HoaDonController();
            hoaDonController.getHoaDonList().add(hoaDon);
            CallApi callApi = new CallApi();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String json = null;
            try {
                json = objectMapper.writeValueAsString(hoaDon);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            callApi.callPostRequestBody("http://localhost:8080/hoaDon/Update", json);
            callApi.callPostRequestParam("http://localhost:8080/chiTietHoaDon/deleteAllCTHD", "maHoaDon=", hoaDon.getMahd());
            json = objectMapper.writeValueAsString(danhSachChiTietHoaDon);
            System.out.println("chuoi cthd"+json);
            callApi.callPostRequestBody("http://localhost:8080/chiTietHoaDon/add", json);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Xuất hóa đơn");
            alert.setContentText("Hóa đơn đã được xuất thành công!");
            alert.showAndWait();
            return;
    }

        // Thực hiện lưu thông tin hóa đơn vào CSDL
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMahd(textFieldMaHoaDon.getText());
        hoaDon.setNgaylap(java.time.LocalDate.now());
        hoaDon.setManv(textFieldMaNV.getText());
        hoaDon.setMakh(textFieldMaKH.getText());
        for (ChiTietHoaDon cthd : danhSachChiTietHoaDon) {
            hoaDon.setTongtien(hoaDon.getTongtien() + cthd.getThanhtien());
        }
        HoaDonController hoaDonController = new HoaDonController();
        hoaDonController.getHoaDonList().add(hoaDon);
        CallApi callApi = new CallApi();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(hoaDon);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        callApi.callPostRequestBody("http://localhost:8080/hoaDon/Add", json);
        json = objectMapper.writeValueAsString(danhSachChiTietHoaDon);
        System.out.println("chuoi cthd"+json);
        callApi.callPostRequestBody("http://localhost:8080/chiTietHoaDon/add", json);
        if (thanhToan.getValue().equals("Chuyển khoản")) {
            tabPane.getSelectionModel().selectNext();
            String payUrl=callApi.callPostRequestParam("http://localhost:8080/chiTietHoaDon/momoPayment","maHoaDon=", hoaDon.getMahd());
            System.out.println(payUrl);
            webMoMo.getEngine().load(payUrl);
        }
        // Có thể gọi services đã viết: ChiTietHoaDonServices
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Xuất hóa đơn");
        alert.setContentText("Hóa đơn đã được xuất thành công!");
        alert.showAndWait();

    }
    public void deleteChiTietHoaDon() {
        String maCanXoa = textFieldMaSach.getText();
        System.out.println("delete: "+maCanXoa);
        danhSachChiTietHoaDon.removeIf(cthd -> cthd.getMasp().equals(maCanXoa));
        table.setItems(danhSachChiTietHoaDon);
    }
    public void updateChiTietHoaDon(){
        List<SanPham> listSanPham=new ArrayList<>();
        if(listSanPham.isEmpty()){
            SanPhamController sanPhamController=new SanPhamController();
            listSanPham=sanPhamController.getListSanPham();
        }
        String masp = textFieldMaSach.getText();
        int soluong = Integer.parseInt(textFieldSL.getText());

        ChiTietHoaDon chiTiet = new ChiTietHoaDon();
        chiTiet.setMasp(masp);
        chiTiet.setMahd(textFieldMaHoaDon.getText());
        chiTiet.setSl(Integer.parseInt(textFieldSL.getText()));
        for(SanPham sanPham: listSanPham){
            if(sanPham.getMasp().equals(masp)){
                chiTiet.setDongia(sanPham.getDongia());
            }
        }
        chiTiet.setThanhtien(Integer.parseInt(textFieldSL.getText())*chiTiet.getDongia());
        for (int i = 0; i < danhSachChiTietHoaDon.size(); i++) {
            if (danhSachChiTietHoaDon.get(i).getMasp().equals(masp)) {
                danhSachChiTietHoaDon.set(i, chiTiet);
                break;
            }
        }
    }

    // Thêm sự kiện nếu cần cho các nút menu bên trái (btnThongKe, btnKhachHang,...)
    @FXML
    void onThongKeClicked(ActionEvent event) {
        System.out.println("Chuyển đến màn hình thống kê...");
    }
    @FXML
    void themHoaDonMoi(){
        danhSachChiTietHoaDon.clear();
        textFieldMaNV.clear();
        textFieldSL.clear();
        textFieldMaKH.clear();
        textFieldMaHoaDon.clear();
        String maHoaDon = UUID.randomUUID().toString();
        textFieldMaHoaDon.setText("HD" +maHoaDon.substring(maHoaDon.length() - 8));
        tongTienLabel.setText("Tổng tiền thanh toán: ");
    }
    public void suaHoaDon(HoaDon hoaDon){
        isSuaHoaDon=true;
        textFieldMaHoaDon.setText(hoaDon.getMahd());
        textFieldMaKH.setText(hoaDon.getMakh());
        textFieldMaNV.setText(hoaDon.getManv());
        CallApi callApi = new CallApi();
        List<ChiTietHoaDon> listChiTietHoaDon=new ArrayList<>();
        String json=callApi.callPostRequestParam("http://localhost:8080/chiTietHoaDon/getByMaHoaDon","maHoaDon=",hoaDon.getMahd());
        ObjectMapper mapper = new ObjectMapper();
        try {
            listChiTietHoaDon=mapper.readValue(json, new TypeReference<List<ChiTietHoaDon>>() {});
        } catch (JsonProcessingException e) {}
        danhSachChiTietHoaDon.clear();
        danhSachChiTietHoaDon=FXCollections.observableArrayList(listChiTietHoaDon);
        table.setItems(danhSachChiTietHoaDon);

    }
    // Các sự kiện khác tương tự...
}
