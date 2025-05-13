package com.example.demo.GuiController;

import com.example.demo.BUS.services.PhieuNhapServices;
import com.example.demo.BUS.services.SanPhamServices;
import com.example.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.springframework.boot.actuate.info.JavaInfoContributor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@Component
public class ChiTietPhieuNhapController implements Initializable {

    @FXML private TableView<ChiTietPhieuNhap> table;
    @FXML private Label tongTienLabel;
    @FXML private TableColumn<ChiTietPhieuNhap, String> maSachColumn;
    @FXML private TableColumn<ChiTietPhieuNhap, Integer> donGiaColumn,soLuongColumn,thanhTienColumn;
    @FXML private TextField txt_MaPhieuNhap,txt_MaNhanVien,txt_MaNhaCungCap,txt_MaSach,txt_SL,txt_GiaNhap;
    @FXML private DatePicker ngayTaoPhieu;
    @FXML private static List<ChiTietPhieuNhap> list=new ArrayList<>();
    @FXML private Button btnAdd;
    @FXML private ComboBox<String> cb_NV,cb_NCC,cb_SP;
    //
    @FXML Tab tabTaoHD;
    @FXML TabPane tabPane;
    Boolean isSuaPhieuNhap = false;
    private ObservableList<ChiTietPhieuNhap> danhSachChiTietPhieuNhap = FXCollections.observableArrayList();
    LeftMenuController leftMenuController = new LeftMenuController();
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnPhieuNhap,btnTaoPhieuNhap,btnNhaXuatBan,btnTheLoai;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD,  btnKhuyenMai,
                btnTheLoai, btnNhaXuatBan, btnPhieuNhap,
                btnTaoPhieuNhap);
        // Liên kết cột với thuộc tính đối tượng ChiTietHoaDon
        maSachColumn.setCellValueFactory(new PropertyValueFactory<>("masp"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
        btnAdd.setOnAction(event -> onAddClicked(event));

        cb_NV.getItems().addAll(comboBoxNhanVien());cb_SP.getItems().addAll(comboBoxSanPham());
        cb_NCC.getItems().addAll(comboBoxNhaCungCap());
        table.setItems(danhSachChiTietPhieuNhap);

        String maPhieuNhap = UUID.randomUUID().toString();
        txt_MaPhieuNhap.setText("PN" +maPhieuNhap.substring(maPhieuNhap.length() - 8));
        txt_MaPhieuNhap.setDisable(true);
        table.setOnMouseClicked((MouseEvent event) -> {
            ChiTietPhieuNhap selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                txt_MaSach.setText(selected.getMasp());
                txt_SL.setText(String.valueOf(selected.getSl()));
                // Nếu bạn có thêm thông tin khác, có thể set thêm vào các TextField tương ứng
            }
        });

    }
    public List<String> comboBoxNhanVien() {
        NhanVienController nhanVienController = new NhanVienController();
        List<String> tmp = new ArrayList<>();
        nhanVienController.getNhanVienList().forEach(nv->{
            tmp.add(nv.getManv());
        });
        return tmp;
    }
    public void setNhanVien(){
        String selected = cb_NV.getValue();
        if (selected != null) {
            txt_MaNhanVien.setText(selected);
        }
    }
    public List<String> comboBoxSanPham() {
        SanPhamController sanPhamController = new SanPhamController();
        List<String> tmp = new ArrayList<>();
        sanPhamController.getListSanPham().forEach(sanPham->{
            tmp.add(sanPham.getMasp());
        });
        return tmp;
    }
    public void setSanPham(){
        String selected = cb_SP.getValue();
        if (selected != null) {
            txt_MaSach.setText(selected);
        }
    }
    public List<String> comboBoxNhaCungCap() {
        NhaCungCapController nhaCapController = new NhaCungCapController();
        List<String> tmp = new ArrayList<>();
        nhaCapController.getListNhaCungCap().forEach( nhaCungCap->{
            tmp.add(nhaCungCap.getMaNhaCungCap());
        });
        return tmp;
    }
    public void setNhaCungCap(){
        String selected = cb_NCC.getValue();
        if (selected != null) {
            txt_MaNhaCungCap.setText(selected);
        }
    }

    @FXML
    void onAddClicked(ActionEvent event) {
        ChiTietPhieuNhap chiTiet = new ChiTietPhieuNhap();
        try {
            boolean isTonTai = false;
            List<SanPham> listSanPham = new ArrayList<>();
            if (listSanPham.size() == 0) {
                SanPhamController sanPhamController = new SanPhamController();
                listSanPham = sanPhamController.getListSanPham();
            }
            System.out.println(listSanPham.size());
            chiTiet.setMasp(txt_MaSach.getText());
            chiTiet.setMapn(txt_MaPhieuNhap.getText());
            chiTiet.setDongia(Integer.parseInt(txt_GiaNhap.getText()));
            System.out.println("DUYENNNNNNNNNNNNNNNNNNNNNNNNNNNN");
            System.err.println("SOLUONG :" +txt_SL.getText());
            chiTiet.setSl(Integer.valueOf(txt_SL.getText()));
            if (chiTiet.getSl() < 0 || chiTiet.getDongia() < 0){
                showMessage("THEM CHI TIET PHIEU NHAP ","FAIL","Vui lòng nhập số dương");
                return;
            }
            // đoạn for này dùng để kiểm tra masp có đã có trên dữ liệu sản phẩm của kho chưa , nên gọi SANPHAMSERVICE cho gọn
            // à không gọi service mắc công éo hiểu nữa
            String find = "";
            for (SanPham sp : listSanPham) {
                if (sp.getMasp().equals(txt_MaSach.getText())) {
                    find = sp.getMasp();
                }
            }
            if (find.equals("") || txt_SL.getText().isEmpty()||txt_GiaNhap.getText().isEmpty() ||
                    txt_MaNhaCungCap.getText().isEmpty() || txt_MaSach.getText().isEmpty() ||
                    txt_SL.getText().isEmpty() || txt_MaNhanVien. getText().isEmpty() ||ngayTaoPhieu.getValue() == null) {
                showMessage("Thêm chi tiết phiếu nhập","FAIL","Vui lòng điền đầy đủ thông tin");
                return;
            }
            // đoạn for này là để khi thê chi tiết hóa đơn trùng sản phẩm thì nó sẽ cộng dồn
            for (int i = 0; i < danhSachChiTietPhieuNhap.size(); i++) {
                if (danhSachChiTietPhieuNhap.get(i).getMasp().equals(txt_MaSach.getText())) {
                    danhSachChiTietPhieuNhap.get(i).setSl(danhSachChiTietPhieuNhap.get(i).getSl() + Integer.parseInt(txt_SL.getText()));
                    table.setItems(danhSachChiTietPhieuNhap);
                    txt_MaSach.clear();
                    txt_SL.clear();
                    isTonTai = true;
                    break;
                }
            }
            if (!isTonTai) {

                CallApi callApi = new CallApi();
                String json = null;
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    json = objectMapper.writeValueAsString(chiTiet);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                String result = null;
                result = callApi.callPostRequestBody("http://localhost:8080/ChiTietPhieuNhap/check",json);
                if (result.contains("success")) {
                    danhSachChiTietPhieuNhap.add(chiTiet);
                    table.setItems(danhSachChiTietPhieuNhap);
                    txt_MaSach.clear();
                    txt_SL.clear();
                } else {
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
        for (int i = 0; i < danhSachChiTietPhieuNhap.size(); i++) {
            tongTien += danhSachChiTietPhieuNhap.get(i).getDongia() * danhSachChiTietPhieuNhap.get(i).getSl();
        }
        tongTienLabel.setText("Tổng tiền thanh toán: " + String.valueOf(tongTien));
    }
    //xong
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu k hông cần chờ
    }
    //xong
    public String convertListCTPNtoJson(List<ChiTietPhieuNhap> listCTPNCanChuyenThanhJSON) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(listCTPNCanChuyenThanhJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // hoặc return "{}"; tùy bạn muốn xử lý lỗi như nào
        }
    }
    public List<ChiTietPhieuNhap> convertJsonToListPhieuNhap(String json) {

        List<ChiTietPhieuNhap> listTMP = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            listTMP = objectMapper.readValue(json, new TypeReference<List<ChiTietPhieuNhap>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listTMP;
    }
    // xong
    public void updatePhieuNhapInfor( PhieuNhap phieuNhap) {
        isSuaPhieuNhap=true;
        txt_MaNhaCungCap.setText(phieuNhap.getMancc());
        txt_MaPhieuNhap.setText(phieuNhap.getMapn());
        txt_MaNhanVien.setText(phieuNhap.getManv());
        List<ChiTietPhieuNhap> listTamThoi = new ArrayList<>();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/ChiTietPhieuNhap/getByMaPhieuNhap","find=",phieuNhap.getMapn());
        listTamThoi = convertJsonToListPhieuNhap(json);
        danhSachChiTietPhieuNhap.clear();
        danhSachChiTietPhieuNhap =FXCollections.observableArrayList(listTamThoi);
        table.setItems(danhSachChiTietPhieuNhap);
    }
    public void updateChiTietPhieuNhap(){

        try{
            List<SanPham> listSanPham = new ArrayList<>();
            if (listSanPham.isEmpty()) {
                SanPhamController sanPhamController = new SanPhamController();
                listSanPham = sanPhamController.getListSanPham();
            }
            ChiTietPhieuNhap tmp = new ChiTietPhieuNhap();
            tmp.setMapn(txt_MaPhieuNhap.getText());
            tmp.setDongia(Integer.parseInt((txt_GiaNhap.getText())));
            tmp.setMasp(txt_MaSach.getText());
            tmp.setSl(Integer.parseInt(txt_SL.getText()));
            if (tmp.getDongia() < 0 || tmp.getSl() < 0) {
                showMessage("CAP NHAT CHI TIET PHIEU NHAP ","FAIL","Vui lòng nhập số dương");
                return;
            }
            String find = "";
            for (SanPham sanPham : listSanPham) {
                if (sanPham.getMasp().equals(tmp.getMasp())) {
                    find=sanPham.getMasp();
                    tmp.setThanhtien(tmp.getDongia()* tmp.getSl());
                    break;
                }
            }
            if (find.equals("") || txt_SL.getText().isEmpty()||txt_GiaNhap.getText().isEmpty() ||
                    txt_MaNhaCungCap.getText().isEmpty() || txt_MaSach.getText().isEmpty() ||
                    txt_SL.getText().isEmpty() || txt_MaNhanVien. getText().isEmpty() ||ngayTaoPhieu.getValue() == null) {
                showMessage("Cập nhật chi tiết phiếu nhập","FAIL","Vui lòng điền đầy đủ thông tin");
                return;
            }
            System.out.println("ĐỘ DÀI DANH SÁCH CHI TIẾT PHIẾU NHẬP :"+danhSachChiTietPhieuNhap.size());
            for ( int i = 0 ; i < danhSachChiTietPhieuNhap.size(); i++ ) {
                System.out.println(danhSachChiTietPhieuNhap.get(i).getMasp() +"=====va===="+tmp.getMasp());
                if (danhSachChiTietPhieuNhap.get(i).getMasp().equals(tmp.getMasp())) {
                    showMessage("UPDATECHITIETPHIEUNHAP","??","cập nhật "+danhSachChiTietPhieuNhap.get(i).getMasp());
                    danhSachChiTietPhieuNhap.set(i,tmp);
                    table.setItems(danhSachChiTietPhieuNhap);
                    break;
                }
            }

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.err.println("BUG UPDATE CHITIET PHIEU NHAP");
        }

        catch (Exception e) {
            e.printStackTrace();
        }


    }
    @FXML
    public void onXuatPhieuNhapClicked() throws JsonProcessingException {
        if (isSuaPhieuNhap) {
            isSuaPhieuNhap = false;
            PhieuNhap phieuNhap = new PhieuNhap();
            phieuNhap.setMapn(txt_MaPhieuNhap.getText());
            phieuNhap.setMancc(txt_MaNhaCungCap.getText());
            phieuNhap.setManv(txt_MaNhanVien.getText());
            phieuNhap.setTongtien(0);
            phieuNhap.setNgaynhap(java.time.LocalDate.now());
            for (ChiTietPhieuNhap ctpn : danhSachChiTietPhieuNhap) {
                phieuNhap.setTongtien(phieuNhap.getTongtien() + ctpn.getThanhtien());

            }

            PhieuNhapController phieuNhapController = new PhieuNhapController();
//            phieuNhapController.getPhieuNhapList().add(phieuNhap);

            CallApi callApi = new CallApi();
            String json = phieuNhapController.convertPhieuNhapToJson(phieuNhap);
            String result = callApi.callPostRequestBody("http://localhost:8080/phieuNhap/Update", json);
            String result2 =callApi.callPostRequestParam("http://localhost:8080/ChiTietPhieuNhap/deleteAllCTPN", "maPhieuNhap=",phieuNhap.getMapn());

            System.out.println("chuoi ctpn"+json);
            String ctpnJson = convertListCTPNtoJson(danhSachChiTietPhieuNhap);
            String result3 =callApi.callPostRequestBody("http://localhost:8080/ChiTietPhieuNhap/Add", ctpnJson);
            System.err.println("++++++++++++++++++++++++ "+" \n KET QUA RESULT :"+result+" "+result2+" "+result3+"\n +++++++++++++++++++");

            if (result.contains("success") && result2.contains("success") && result3.contains("success")) {
                showMessage("XUAT PHIEU NHAP","SUCCESS","Cập nhật phiếu nhập thành công");
            }else {
                showMessage("XUAT PHIEU NHAP","FAIL","Đã có sự cố trong quá trình cập nhật, vui lòng kiểm tra lại API");
            }
            return;
        }
        try {
            // Thực hiện lưu thông tin phiếu nhập vào CSDL
            PhieuNhap phieuNhap = new PhieuNhap();
            phieuNhap.setMapn(txt_MaPhieuNhap.getText());
            phieuNhap.setMancc(txt_MaNhaCungCap.getText());
            phieuNhap.setManv(txt_MaNhanVien.getText());
            phieuNhap.setTongtien(0);
            phieuNhap.setNgaynhap(java.time.LocalDate.now());
            for ( ChiTietPhieuNhap ctpn : danhSachChiTietPhieuNhap) {
                phieuNhap.setTongtien(ctpn.getThanhtien()+phieuNhap.getTongtien());
            }
            PhieuNhapController phieuNhapController = new PhieuNhapController();
            phieuNhapController.getPhieuNhapList().add(phieuNhap);

            CallApi callApi = new CallApi();
            String json = phieuNhapController.convertPhieuNhapToJson(phieuNhap);
            System.out.println("ADD PHIEUNHAP :"+callApi.callPostRequestBody("http://localhost:8080/phieuNhap/Add", json));
            String json2 = convertListCTPNtoJson(danhSachChiTietPhieuNhap);
            System.out.println("chuoi ctpn"+json);
            System.out.println("ADD CTPN :"+callApi.callPostRequestBody("http://localhost:8080/ChiTietPhieuNhap/Add", json2));
            showMessage("XUAT PHIEU NHAP","SUCCESS","Xuất phiếu nhập thành công");

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        if (thanhToan.getValue().equals("Chuyển khoản")) {
//            tabPane.getSelectionModel().selectNext();
//            String payUrl=callApi.callPostRequestParam("http://localhost:8080/chiTietHoaDon/momoPayment","maHoaDon=", hoaDon.getMahd());
//            System.out.println(payUrl);
//            webMoMo.getEngine().load(payUrl);
//        }
        // Có thể gọi services đã viết: ChiTietHoaDonServices


    }
    public void deleteChiTietPhieuNhap() {
        String maSanPhamCanXoa = txt_MaSach.getText();
        System.out.println("delete: "+maSanPhamCanXoa);
        danhSachChiTietPhieuNhap.removeIf(chiTietPhieuNhap -> chiTietPhieuNhap.getMasp().equals(maSanPhamCanXoa));
        table.setItems(danhSachChiTietPhieuNhap);
    }
    public void themPhieuNhapMoi(){
        danhSachChiTietPhieuNhap.clear();
        txt_SL.clear();
        txt_MaSach.clear();
        txt_MaNhaCungCap.clear();
        txt_MaNhanVien.clear();
        String maPhieuNhap = UUID.randomUUID().toString();
        txt_MaPhieuNhap.setText("PN"+maPhieuNhap.substring(maPhieuNhap.length()-8));
        tongTienLabel.setText("Tổng tiền thanh toán: ");
    }



}
