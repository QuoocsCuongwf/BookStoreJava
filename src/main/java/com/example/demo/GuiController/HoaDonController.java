package com.example.demo.GuiController;

import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class HoaDonController implements Initializable {

    @FXML
    private TableView<HoaDon> tableView;

    @FXML
    private TableColumn<HoaDon, String> maHDColumn;

    @FXML
    private TableColumn<HoaDon, LocalDate> ngayTaoColumn;

    @FXML
    private TableColumn<HoaDon, String> maNVColumn;

    @FXML
    private TableColumn<HoaDon, String> maKHColumn;

    @FXML
    private TableColumn<HoaDon, Double> tongTienHoaDonColumn;
    @FXML Label labelTongTien;
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
    private ListView<ChiTietHoaDon> listCTHD;
    private static List<HoaDon> listHoaDon=new ArrayList<>();
    @FXML
    private Button btnAddCTHD;
    @FXML
    private Pane inforContainer;
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnTheLoai, btnNhaXuatBan;
    private static List<ChiTietHoaDon> listChiTietHoaDon=new ArrayList<>();
    private ObservableList<ChiTietHoaDon> danhSachChiTiet = FXCollections.observableArrayList();
    private ObservableList<HoaDon> data = FXCollections.observableArrayList();

    LeftMenuController leftMenuController=new LeftMenuController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai, btnTheLoai, btnNhaXuatBan);
        inforContainer.setVisible(false);
        maHDColumn.setCellValueFactory(new PropertyValueFactory<>("mahd"));
        maKHColumn.setCellValueFactory(new PropertyValueFactory<>("makh"));
        maNVColumn.setCellValueFactory(new PropertyValueFactory<>("manv"));
        ngayTaoColumn.setCellValueFactory(new PropertyValueFactory<>("ngaylap"));
        tongTienHoaDonColumn.setCellValueFactory(new PropertyValueFactory<>("tongtien"));
        if (listHoaDon.isEmpty() || listHoaDon == null) {
            CallApi callApi = new CallApi();
            try {
                String json=callApi.callGetApi("http://localhost:8080/hoaDon/getAllHoaDon");
                listHoaDon=convertJsonToHoaDon(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        data = FXCollections.observableArrayList(listHoaDon);
        tableView.setItems(data);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    showSelectedItem(newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });
        listCTHD.setItems(danhSachChiTiet);
    }
    public List<HoaDon> convertJsonToHoaDon(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            listHoaDon = mapper.readValue(json, new TypeReference<List<HoaDon>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listHoaDon;
    }
    private void showSelectedItem(HoaDon newValue) throws IOException {
        inforContainer.setVisible(true);
        textFieldMaHD.setText(newValue.getMahd());
        textFieldMaKhachHang.setText(newValue.getMakh());
        textFieldMaNhanVien.setText(newValue.getManv());
        datePickerNgayTaoHD.setValue(newValue.getNgaylap());
        if (listChiTietHoaDon.isEmpty()){
            String json;
            CallApi callApi=new CallApi();
            json=callApi.callGetApi("http://localhost:8080/chiTietHoaDon/getAll");
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.registerModule(new JavaTimeModule());
                listChiTietHoaDon = mapper.readValue(json, new TypeReference<List<ChiTietHoaDon>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        danhSachChiTiet=FXCollections.observableArrayList(
                listChiTietHoaDon.stream()
                        .filter(chiTietHoaDon -> chiTietHoaDon.getMahd().equals(newValue.getMahd()))
                        .collect(Collectors.toList())
        );
        listCTHD.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ChiTietHoaDon item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Mã SP: %s | SL: %d | Giá: %d | Thành tiền: %d",
                            item.getMasp(), item.getSl(), item.getDongia(), item.getThanhtien()));
                    System.out.println("Render cell: " + item.getMasp());

                }
            }
        });

        listCTHD.setItems(danhSachChiTiet);

        // Tùy chỉnh hiển thị

        labelTongTien.setText(String.valueOf(newValue.getTongtien()));
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

//        String chiTiet = String.format("HD: %s - SP: %s - SL: %d", maHD, maSP, soLuong);
//        danhSachChiTiet.add(chiTiet);
    }

    @FXML
    private void clossInforContainer() {
        // Đóng form hoặc ẩn nó
        System.out.println("Đóng thông tin hóa đơn");
        inforContainer.setVisible(false);
    }

    public List<HoaDon> getHoaDonList() {
        if (listHoaDon.isEmpty() || listHoaDon == null) {
            CallApi callApi = new CallApi();
            try {
                String json=callApi.callGetApi("http://localhost:8080/hoaDon/getAllHoaDon");
                listHoaDon=convertJsonToHoaDon(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listHoaDon;
    }

    public void setHoaDonList(List<HoaDon> hoaDonList) {
        this.listHoaDon = hoaDonList;
    }

    // Thêm các hàm xử lý cho các nút khác nếu cần
}
