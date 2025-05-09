package com.example.demo.GuiController;

import com.example.demo.model.ChuongTrinhKhuyenMai;
import com.example.demo.model.KmTheoSanPham;
import com.example.demo.model.KmTheoTongTien;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URL;

import java.time.LocalDate;
import java.util.*;

@Component
@Controller
public class KmTheoTongTienController implements Initializable {

    @FXML
    private Pane inforContainer;
    @FXML
    private Pane khuyenMaiTheoHoaDonPane;
    @FXML
    private TableView<KmTheoTongTien> tableView;
    @FXML
    private TableColumn<KmTheoTongTien, Integer> maChuongTrinhKMColumn;
//    @FXML
//    private TableColumn<KhuyenMai, String> maSanPhamColumn;
//    @FXML
//    private TableColumn<KhuyenMai, String> tongTienColumn;
    @FXML
    private TableColumn<KmTheoTongTien, String> phanTramKMColumn;

    @FXML
    private TableColumn<KmTheoTongTien, LocalDate> ngayBatDauColumn;
    @FXML
    private TableColumn<KmTheoTongTien, LocalDate> ngayKetThucColumn;
    @FXML
    private DatePicker datePickerNgayBatDau;
    @FXML
    private DatePicker datePickerNgayKetThuc;


    private ObservableList<ChuongTrinhKhuyenMai> listTongQuat ;
    List <ChuongTrinhKhuyenMai> chuongTrinhKhuyenMaiList = new ArrayList<>();

    private  ObservableList<KmTheoTongTien> data;
    List<KmTheoTongTien> kmTheoTongTienList=new ArrayList<>();

    private  ObservableList<KmTheoSanPham> data1;
    List<KmTheoSanPham> kmTheoSanPhamList=new ArrayList<>();

    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private TextField txt_maChuongTrinhKhuyenMai, txt_tongTien, txt_phanTramKhuyenMai;
    @FXML
    private TextField txt_maSanPham;
    @FXML
    private Pane inforForm;
    @FXML
    private Pane inforForms;
    @FXML
    private Button btnAddKM;
    @FXML
    private Button btnAddKMs;
    @FXML
    private Button timKiem;
    @FXML
    private Button openInforContainer;
    @FXML
    private Button clossInforContainer;
    @FXML
    private HBox inforFormButtonContainer;

    private LocalDate ngayBatDau;

    private LocalDate ngayKetThuc;

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }
    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    private Button btnDeleteKhuyenMai = new Button("    Xóa    ");
    private Button btnUpdateKhuyenMai = new Button("Cập nhật");
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai;
    LeftMenuController leftMenuController=new LeftMenuController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai);
        inforContainer.setVisible(false);
        maChuongTrinhKMColumn.setCellValueFactory(new PropertyValueFactory<>("mactkm"));
        //maSanPhamColumn.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        //tongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        phanTramKMColumn.setCellValueFactory(new PropertyValueFactory<>("phantramkhuyenmai"));
        ngayBatDauColumn.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
        ngayKetThucColumn.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfKmTheoTongTien();
            } else {
                System.out.println("No item selected");
            }
        });
        CallApi callApi = new CallApi();
        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/KhuyenMai/getAllKhuyenMai");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        kmTheoTongTienList = convertJsonToListKmTheoTongTien(json);
        System.out.println(kmTheoTongTienList);
        data = FXCollections.observableArrayList(kmTheoTongTienList);
        tableView.setItems(data);
        btnDeleteKhuyenMai.setOnAction(event -> DeleteKmTheoTongTien(btnDeleteKhuyenMai));
        btnUpdateKhuyenMai.setOnAction(event -> UpdateKmTheoTongTien());

    }
    public List<KmTheoTongTien> convertJsonToListKmTheoTongTien(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<KmTheoTongTien>kmTheoTongTienList = new ArrayList<>();
        System.out.println("json: " + json);
        try {
            kmTheoTongTienList = objectMapper.readValue(json, new TypeReference<List<KmTheoTongTien>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return kmTheoTongTienList;

    }
    public String convertKmTheoTongTienToJson(KmTheoTongTien kmTheoTongTien) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = mapper.writeValueAsString(kmTheoTongTien);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    public void listenerChangeValuesOfKmTheoTongTien() {
        List<TextField> fields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai, txt_tongTien, txt_phanTramKhuyenMai,txt_maSanPham
                );
        fields.forEach(f -> {
            if (f != null) {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhuyenMai);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateKhuyenMai);
                    } else {
                        System.err.println("btnDeleteKhuyenMai không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }
    public void DeleteKmTheoTongTien(Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            KmTheoTongTien khuyenMai = data.get(selectedIndex);
            System.out.println("Khuyen Mai Theo Tong Tien selected " + khuyenMai.getMactkm());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/KhuyenMai/Delete", "maChuongTrinh=",khuyenMai.getMactkm());
            data.remove(selectedIndex);
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void UpdateKmTheoTongTien() {
        KmTheoTongTien kmTheoTongTien = new KmTheoTongTien();
        List<TextField> textFields=Arrays.asList(txt_maChuongTrinhKhuyenMai, txt_phanTramKhuyenMai, txt_tongTien
                );
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        kmTheoTongTien.setMactkm(txt_maChuongTrinhKhuyenMai.getText());
        int tongTien = Integer.parseInt(txt_tongTien.getText());
        kmTheoTongTien.setTongtien(tongTien);

        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        kmTheoTongTien.setPhantramkhuyenmai(phanTram);

        kmTheoTongTien.setNgayBatDau(datePickerNgayBatDau.getValue());
        kmTheoTongTien.setNgayKetThuc(datePickerNgayKetThuc.getValue());

        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/KhuyenMai/Update",convertKmTheoTongTienToJson(kmTheoTongTien));
        if (resultApi.contains("success")) {
            for (int i = 0; i < kmTheoTongTienList.size(); i++) {
                if (kmTheoTongTienList.get(i).getMactkm().equals(kmTheoTongTien.getMactkm())) {
                    kmTheoTongTienList.set(i, kmTheoTongTien); // thay thế đúng phần tử
                    break;
                }
            }
            showMessage("Success","Sua thanh cong",resultApi);
            data = FXCollections.observableArrayList(kmTheoTongTienList);
            tableView.setItems(data);
        }
    }
    public void openInforContainer() {
        txt_maChuongTrinhKhuyenMai.setText("");
        txt_tongTien.setText("");
        txt_phanTramKhuyenMai.setText("");
        datePickerNgayBatDau.setValue(LocalDate.now());
        datePickerNgayKetThuc.setValue(LocalDate.now());
        inforContainer.setVisible(true);
    }
    public void clossInforContainer() {
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhuyenMai);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddKM);
        }
        inforContainer.setVisible(false);
    }
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void addKmTheoTongTien() {
        KmTheoTongTien kmTheoTongTien = new KmTheoTongTien();
        List<TextField> textFields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai,txt_phanTramKhuyenMai,txt_tongTien);
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }
        kmTheoTongTien.setMactkm(txt_maChuongTrinhKhuyenMai.getText());

        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        int tongTien = Integer.parseInt(txt_tongTien.getText());

        kmTheoTongTien.setPhantramkhuyenmai(phanTram);
        kmTheoTongTien.setTongtien(tongTien);

        kmTheoTongTien.setNgayBatDau(datePickerNgayBatDau.getValue());
        kmTheoTongTien.setNgayKetThuc(datePickerNgayKetThuc.getValue());

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KhuyenMai/Add", convertKmTheoTongTienToJson(kmTheoTongTien));
        System.out.println(result);
        if (result.contains("Success")){
            kmTheoTongTienList.add(kmTheoTongTien);
            data.add(kmTheoTongTien);
        }
    }
    public void showSelectedItem(KmTheoTongTien kmTheoTongTien) {
        openInforContainer();
        txt_maChuongTrinhKhuyenMai.setText(kmTheoTongTien.getMactkm());
        txt_tongTien.setText(String.valueOf(kmTheoTongTien.getTongtien()));
        txt_phanTramKhuyenMai.setText(String.valueOf(kmTheoTongTien.getPhantramkhuyenmai()));
        datePickerNgayBatDau.setValue(kmTheoTongTien.getNgayBatDau());
        datePickerNgayKetThuc.setValue(kmTheoTongTien.getNgayKetThuc());
        int index = inforFormButtonContainer.getChildren().indexOf(btnAddKM);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteKhuyenMai);
        } else {
            System.err.println("btnAddKhuyenMai không tồn tại trong inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateKhuyenMai);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteKhuyenMai);
        } else {
            System.err.println("btnDeleteKhuyenMai không tồn tại trong inforFormButtonContainer !");

        }
    }
    public void timKiem() {
        String find = textFieldTimKiem.getText();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/KhuyenMai/timKiem", "find=", find);
        data = FXCollections.observableArrayList(convertJsonToListKmTheoTongTien(json));
        tableView.setItems(data);
    }
    public List<KmTheoSanPham> convertJsonToListKmTheoSanPham(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<KmTheoSanPham> kmTheoSanPhamList = new ArrayList<>();
        System.out.println("json: " + json);
        try {
            kmTheoSanPhamList = objectMapper.readValue(json, new TypeReference<List<KmTheoSanPham>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return kmTheoSanPhamList;

    }
    public String convertKmTheoSanPhamToJson(KmTheoSanPham khuyenMai) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = mapper.writeValueAsString(khuyenMai);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    public void listenerChangeValuesOfKmtheoSanPham() { // san pham
        List<TextField> fields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai, txt_maSanPham, txt_phanTramKhuyenMai
        );
        fields.forEach(f -> {
            if (f != null) {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhuyenMai);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateKhuyenMai);
                    } else {
                        System.err.println("btnDeleteKhuyenMai không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }
    public void DeleteKmTheoSanPham(Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data1.size()) {
            KmTheoSanPham khuyenMai = data1.get(selectedIndex);
            System.out.println("Khuyen Mai Theo San Pham selected " + khuyenMai.getMactkm());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/KmTheoSanPham/Delete", "maKmai=",khuyenMai.getMactkm());
            data1.remove(selectedIndex);
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void UpdateKmTheoSanPham() {
        KmTheoSanPham khuyenMai = new KmTheoSanPham();
        List<TextField> textFields=Arrays.asList(txt_maChuongTrinhKhuyenMai, txt_phanTramKhuyenMai, txt_maSanPham
        );
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };

        khuyenMai.setMactkm(txt_maChuongTrinhKhuyenMai.getText());
        khuyenMai.setMasp(txt_maSanPham.getText());

        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        khuyenMai.setPhantramkhuyenmai(phanTram);

        khuyenMai.setNgayBatDau(datePickerNgayBatDau.getValue());
        khuyenMai.setNgayKetThuc(datePickerNgayKetThuc.getValue());


        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/KmTheoSanPham/Update",convertKmTheoSanPhamToJson(khuyenMai));
        if (resultApi.contains("success")) {
            for (int i = 0; i < kmTheoSanPhamList.size(); i++) {
                if (kmTheoSanPhamList.get(i).getMactkm().equals(khuyenMai.getMactkm())) {
                    kmTheoSanPhamList.set(i, khuyenMai); // thay thế đúng phần tử
                    break;
                }
            }
            showMessage("Success","Sua thanh cong",resultApi);
            data1 = FXCollections.observableArrayList(kmTheoSanPhamList);
            tableView.setItems(data);
        }
    }
    public void openInforContainerKmTheoSanPham() {
        txt_maChuongTrinhKhuyenMai.setText("");
        txt_maSanPham.setText("");
        txt_phanTramKhuyenMai.setText("");
        datePickerNgayBatDau.setValue(LocalDate.now());
        datePickerNgayKetThuc.setValue(LocalDate.now());
        inforContainer.setVisible(true);
    }
    public void clossInforContainerKmTheoSanPham() {
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhuyenMai);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddKM);
        }
        inforContainer.setVisible(false);
    }
    public void addKmTheoSanPham() {
        KmTheoSanPham kmTheoSanPham = new KmTheoSanPham();
        List<TextField> textFields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai,txt_phanTramKhuyenMai,txt_maSanPham);
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }
        kmTheoSanPham.setMactkm(txt_maChuongTrinhKhuyenMai.getText());
        kmTheoSanPham.setMasp(txt_maSanPham.getText());

        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());


        kmTheoSanPham.setPhantramkhuyenmai(phanTram);


        kmTheoSanPham.setNgayBatDau(datePickerNgayBatDau.getValue());
        kmTheoSanPham.setNgayKetThuc(datePickerNgayKetThuc.getValue());

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KmTheoSanPham/Add", convertKmTheoSanPhamToJson(kmTheoSanPham));
        System.out.println(result);
        if (result.contains("success")){
            kmTheoSanPhamList.add(kmTheoSanPham);
            data1.add(kmTheoSanPham);
        }
    }
    public void showSelectedItemKmtheoSanPham(KmTheoSanPham khuyenMai) {
        openInforContainer();
        txt_maChuongTrinhKhuyenMai.setText(khuyenMai.getMactkm());
        txt_maSanPham.setText(String.valueOf(khuyenMai.getMasp()));
        txt_phanTramKhuyenMai.setText(String.valueOf(khuyenMai.getPhantramkhuyenmai()));
        datePickerNgayBatDau.setValue(khuyenMai.getNgayBatDau());
        datePickerNgayKetThuc.setValue(khuyenMai.getNgayKetThuc());


        int index = inforFormButtonContainer.getChildren().indexOf(btnAddKM);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteKhuyenMai);
        } else {
            System.err.println("btnAddKhuyenMai không tồn tại trong inforFormButtonContainers!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateKhuyenMai);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteKhuyenMai);
        } else {
            System.err.println("btnDeleteKhuyenMai không tồn tại trong inforFormButtonContainers !");

        }
    }
    public void timKiemKmTheoSanPham() {
        String find = textFieldTimKiem.getText();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/KmTheoSanPham/timKiem", "find=", find);
        data1 = FXCollections.observableArrayList(convertJsonToListKmTheoSanPham(json));
        tableView.setItems(data);
    }

}
