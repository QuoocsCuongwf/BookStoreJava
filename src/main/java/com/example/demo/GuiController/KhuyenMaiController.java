package com.example.demo.GuiController;

import com.example.demo.model.KmTheoHoaDon;
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
public class KhuyenMaiController implements Initializable {

    @FXML
    private Pane inforContainer;
    @FXML
    private Pane khuyenMaiTheoHoaDonPane;
    @FXML
    private TableView<KmTheoHoaDon> tableView;
    @FXML
    private TableColumn<KmTheoHoaDon, Integer> maChuongTrinhKMColumn;
    @FXML
    private TableColumn<KmTheoHoaDon, String> maSanPhamColumn;
    @FXML
    private TableColumn<KmTheoHoaDon, String> tongTienColumn;
    @FXML
    private TableColumn<KmTheoHoaDon, String> phanTramKMColumn;

    @FXML
    private TableColumn<KmTheoHoaDon, LocalDate> ngayBatDauColumn;
    @FXML
    private TableColumn<KmTheoHoaDon, LocalDate> ngayKetThucColumn;
    @FXML
    private DatePicker datePickerNgayBatDau;
    @FXML
    private DatePicker datePickerNgayKetThuc;

    private  ObservableList<KmTheoHoaDon> data;
    List<KmTheoHoaDon> khuyenMaiList=new ArrayList<>();

    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private TextField txt_maChuongTrinhKhuyenMai, txt_tongTien, txt_phanTramKhuyenMai;
//    @FXML
//    private TextField txt_maSanPham;
    @FXML
    private Pane inforForm;
    @FXML
    private Button btnAddKM;
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

//    public LocalDate getNgayBatDau() {
//        return ngayBatDau;
//    }
//
//    public void setNgayBatDau(LocalDate ngayBatDau) {
//        this.ngayBatDau = ngayBatDau;
//    }
//
//    public LocalDate getNgayKetThuc() {
//        return ngayKetThuc;
//    }
//    public void setNgayKetThuc(LocalDate ngayKetThuc) {
//        this.ngayKetThuc = ngayKetThuc;
//    }

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
        maSanPhamColumn.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        tongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        phanTramKMColumn.setCellValueFactory(new PropertyValueFactory<>("phantramkhuyenmai"));
//        ngayBatDauColumn.setCellValueFactory(new PropertyValueFactory<>("ngayBatDau"));
//        ngayKetThucColumn.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfKhuyenMai();
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
        khuyenMaiList = convertJsonToListKhuyenMai(json);
        System.out.println(khuyenMaiList);
        data = FXCollections.observableArrayList(khuyenMaiList);
        tableView.setItems(data);
        btnDeleteKhuyenMai.setOnAction(event -> DeleteKhuyenMai(btnDeleteKhuyenMai));
        btnUpdateKhuyenMai.setOnAction(event -> UpdateKhuyenMai());
        ngayBatDauColumn.setCellFactory(column -> new TableCell<KmTheoHoaDon, LocalDate>()
        {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setOnAction(event -> {
                    KmTheoHoaDon khuyenMai = getTableView().getItems().get(getIndex());
                    khuyenMai.setNgayBatDau(datePicker.getValue());
                });
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    datePicker.setValue(item);
                    setGraphic(datePicker);
                }
            }
        });
        ngayKetThucColumn.setCellFactory(column -> new TableCell<KmTheoHoaDon, LocalDate>()
        {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setOnAction(event -> {
                    KmTheoHoaDon khuyenMai = getTableView().getItems().get(getIndex());
                    khuyenMai.setNgayBatDau(datePicker.getValue());
                });
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    datePicker.setValue(item);
                    setGraphic(datePicker);
                }
            }
        });
    }

    public List<KmTheoHoaDon> convertJsonToListKhuyenMai(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<KmTheoHoaDon> khuyenMaiList = new ArrayList<>();
        System.out.println("json: " + json);
        try {
            khuyenMaiList = objectMapper.readValue(json, new TypeReference<List<KmTheoHoaDon>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return khuyenMaiList;

    }

    public String convertKhuyenMaiToJson(KmTheoHoaDon khuyenMai) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(khuyenMai);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenerChangeValuesOfKhuyenMai() {
        List<TextField> fields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai, txt_tongTien, txt_phanTramKhuyenMai
                );
        fields.forEach(f -> {
            if (f != null) {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhuyenMai);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateKhuyenMai);
                    } else {
                        System.err.println("btnDeleteKmTheoHoaDon không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }
    public void DeleteKhuyenMai(Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            KmTheoHoaDon khuyenMai = data.get(selectedIndex);
            System.out.println("Khuyen Mai Theo Hoa Don selected " + khuyenMai.getMactkm());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/KhuyenMai/Delete", "maKm=",khuyenMai.getMactkm());
            data.remove(selectedIndex);
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void UpdateKhuyenMai() {
        KmTheoHoaDon khuyenMai = new KmTheoHoaDon();
        List<TextField> textFields=Arrays.asList(txt_maChuongTrinhKhuyenMai, txt_phanTramKhuyenMai, txt_tongTien
                );
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        khuyenMai.setMactkm(txt_maChuongTrinhKhuyenMai.getText());

        Double tongTien = Double.parseDouble(txt_tongTien.getText());
        khuyenMai.setTongtien(tongTien);

        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        khuyenMai.setPhantramkhuyenmai(phanTram);

//        khuyenMai.setNgayBatDau(datePickerNgayBatDau.getValue());
//        khuyenMai.setNgayKetThuc(datePickerNgayKetThuc.getValue());


        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/khuyenMai/Update",convertKhuyenMaiToJson(khuyenMai));
        if (resultApi.contains("Success")) {
            for (int i = 0; i < khuyenMaiList.size(); i++) {
                if (khuyenMaiList.get(i).getMactkm().equals(khuyenMai.getMactkm())) {
                    khuyenMaiList.set(i, khuyenMai); // thay thế đúng phần tử
                    break;
                }
            }
            showMessage("Success","Sua sach thanh cong",resultApi);
            data = FXCollections.observableArrayList(khuyenMaiList);
            tableView.setItems(data);
        }
    }
    public void openInforContainer() {
        txt_maChuongTrinhKhuyenMai.setText("");
//        txt_maSanPham.setText("");
        txt_tongTien.setText("");
        txt_phanTramKhuyenMai.setText("");
//        datePickerNgayBatDau.setValue(LocalDate.now());
//        datePickerNgayKetThuc.setValue(LocalDate.now());
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
    public void addKhuyenMai() {
        KmTheoHoaDon khuyenMai = new KmTheoHoaDon();
        List<TextField> textFields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai,txt_phanTramKhuyenMai,txt_tongTien);
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }
        khuyenMai.setMactkm(txt_maChuongTrinhKhuyenMai.getText());
//        kmTheoHoaDon.setMasanpham(txt_maSanPham.getText());

        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        Double tongTien = Double.parseDouble(txt_tongTien.getText());

        khuyenMai.setPhantramkhuyenmai(phanTram);
        khuyenMai.setTongtien(tongTien);

       //khuyenMai.setNgayBatDau(datePickerNgayBatDau.getValue());
       //khuyenMai.setNgayKetThuc(datePickerNgayKetThuc.getValue());



        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KhuyenMai/Add", convertKhuyenMaiToJson(khuyenMai));
        System.out.println(result);
        if (result.contains("Success")){
            khuyenMaiList.add(khuyenMai);
            data.add(khuyenMai);
        }
    }
    public void showSelectedItem(KmTheoHoaDon khuyenMai) {
        openInforContainer();
        txt_maChuongTrinhKhuyenMai.setText(khuyenMai.getMactkm());
//        txt_maSanPham.setText(String.valueOf(khuyenMai.getMasanpham()));
        txt_tongTien.setText(String.valueOf(khuyenMai.getTongtien()));
        txt_phanTramKhuyenMai.setText(String.valueOf(khuyenMai.getPhantramkhuyenmai()));
//        datePickerNgayBatDau.setValue(khuyenMai.getNgayBatDau());
//        datePickerNgayKetThuc.setValue(khuyenMai.getNgayKetThuc());


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
        data = FXCollections.observableArrayList(convertJsonToListKhuyenMai(json));
        tableView.setItems(data);
    }

}
