package com.example.demo.GuiController;

import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    private TableView<ChiTietHoaDon> cthdTable;
    @FXML private TableColumn<HoaDon, String> maSpColumn;
    @FXML private TableColumn<HoaDon, Integer> slColumn;
    @FXML private TableColumn<HoaDon, Integer> thanhTienColumn;
    @FXML
    private TableColumn<HoaDon, String> maHDColumn;

    @FXML
    private TableColumn<HoaDon, LocalDate> ngayTaoColumn;

    @FXML
    private TableColumn<HoaDon, String> maNVColumn;

    @FXML
    private TableColumn<HoaDon, String> maKHColumn;
    @FXML
    private HoaDon hoaDonDuocChon;
    @FXML
    private TableColumn<HoaDon, Double> tongTienHoaDonColumn;
    @FXML Label labelTongTien;
    @FXML
    private TextField textFieldTimKiem;

    private static List<HoaDon> listHoaDon=new ArrayList<>();
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai;
    private static List<ChiTietHoaDon> listChiTietHoaDon=new ArrayList<>();
    private ObservableList<ChiTietHoaDon> danhSachChiTiet = FXCollections.observableArrayList();
    private ObservableList<HoaDon> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeftMenuController leftMenuController=new LeftMenuController();
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai);
        // Liên kết cột với thuộc tính đối tượng HoaDon
        maHDColumn.setCellValueFactory(new PropertyValueFactory<>("mahd"));
        maKHColumn.setCellValueFactory(new PropertyValueFactory<>("makh"));
        maNVColumn.setCellValueFactory(new PropertyValueFactory<>("manv"));
        ngayTaoColumn.setCellValueFactory(new PropertyValueFactory<>("ngaylap"));
        tongTienHoaDonColumn.setCellValueFactory(new PropertyValueFactory<>("tongtien"));
        //Lien ket voi bang cthd
        maSpColumn.setCellValueFactory(new PropertyValueFactory<>("masp"));
        slColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));

        if (true) {
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
                    hoaDonDuocChon=newValue;
                    showSelectedItem(newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });
        cthdTable.setItems(danhSachChiTiet);
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
        CallApi callApi = new CallApi();
        List<ChiTietHoaDon> listChiTietHoaDon=new ArrayList<>();
        String json=callApi.callPostRequestParam("http://localhost:8080/chiTietHoaDon/getByMaHoaDon","maHoaDon=",newValue.getMahd());
        ObjectMapper mapper = new ObjectMapper();
        try {
            listChiTietHoaDon=mapper.readValue(json, new TypeReference<List<ChiTietHoaDon>>() {});
        } catch (JsonProcessingException e) {}
        System.out.println(json);
        danhSachChiTiet = FXCollections.observableArrayList(listChiTietHoaDon);
        cthdTable.setItems(danhSachChiTiet);
    }


    @FXML
    private void timKiem() {
        String tuKhoa = textFieldTimKiem.getText();
        System.out.println("Đang tìm kiếm với từ khóa: " + tuKhoa);

        // TODO: thêm logic tìm kiếm thực tế
    }

    @FXML
    private void xoaHoaDon() {
                CallApi callApi=new CallApi();
                callApi.callPostRequestParam("http://localhost:8080/hoaDon/Delete","maHoaDon=",hoaDonDuocChon.getMahd());
                listHoaDon.remove(hoaDonDuocChon);
                danhSachChiTiet.remove(hoaDonDuocChon);
                showMessage(" "," "," da xoa");
                // Thực hiện các hành động khác với dữ kiện được chọn

    }
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu không cần chờ
    }
    @FXML
    private void suaHoaDon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TaoHoaDon.fxml"));
        Parent root = loader.load();
        ChiTietHoaDonController controller=loader.getController();
        controller.suaHoaDon(hoaDonDuocChon);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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
