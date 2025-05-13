package com.example.demo.GuiController;


import com.example.demo.BUS.services.ChuongTrinhKhuyenMaiServices;
import com.example.demo.model.*;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Component
@Controller
public class KhuyenMaiController implements Initializable {
    ChuongTrinhKhuyenMaiServices chuongTrinhKhuyenMaiServices=new ChuongTrinhKhuyenMaiServices();
    private ObservableList<ChuongTrinhKhuyenMai> listTongQuat ;
    List <ChuongTrinhKM> chuongTrinhKMList = new ArrayList<>();
//KmTheoSoTienHoaDon
//    private  ObservableList<KmTheoSoTienHoaDon> data;
//    List<KmTheoSoTienHoaDon> KmTheoSoTienHoaDonList=new ArrayList<>();

//    private ObservableList<KmTheoSanPham> data1;
//    List<KmTheoSanPham> kmTheoSanPhamList=new ArrayList<>();

    @FXML private TableView<ChuongTrinhKhuyenMai> tableView;
    @FXML private TableColumn<ChuongTrinhKhuyenMai, Integer> maChuongTrinhKMColumn;
    @FXML private TableColumn<ChuongTrinhKhuyenMai, String> phanTramKMColumn;
    @FXML private TableColumn<ChuongTrinhKhuyenMai, LocalDate> ngayBatDauColumn;
    @FXML private TableColumn<ChuongTrinhKhuyenMai, LocalDate> ngayKetThucColumn;


    // KMSP
    @FXML private TextField txt_CTKMSanPham,txt_maSanPham,txt_phanTramKhuyenMaiSP;
    @FXML private DatePicker datePickerNgayBatDauSP, datePickerNgayKetThucSP;
    @FXML private Button btnAddKMSP;
    @FXML private HBox inforFormButtonContainerSP;
    @FXML private Pane inforContainerKMSP;
    private List<KmTheoSanPham> listKmTheoSanPham = new ArrayList<>();

    //HD
    @FXML private VBox inforContainerKMTT;
    @FXML private HBox inforFormButtonContainerTT;
    @FXML private DatePicker datePickerNgayBatDauTT;
    @FXML private DatePicker datePickerNgayKetThucTT;
    @FXML private TextField textFieldTimKiem;
    @FXML private TextField txt_CTKMTongTien, txt_tongTien, txt_phanTramKhuyenMai, txt_TenCTKM,txt_TenCTKMSP;
    @FXML private Button btnAddKMTT;
    private List<KmTheoSoTienHoaDon> listKmTheoSoTienHoaDonList=new ArrayList<>();
    List<ChuongTrinhKhuyenMai> listChuongTrinhKhuyenMai = new ArrayList<>();


    private Button btnDeleteKhuyenMaiSP = new Button("    Xóa    ");
    private Button btnUpdateKhuyenMaiSP = new Button("Cập nhật");
    private Button btnDeleteKhuyenMaiHD = new Button("    Xóa    ");
    private Button btnUpdateKhuyenMaiHD = new Button("Cập nhật");
    @FXML

    LeftMenuController leftMenuController=new LeftMenuController();
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnPhieuNhap,btnTaoPhieuNhap,btnNhaXuatBan,btnTheLoai;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD,  btnKhuyenMai,
                btnTheLoai, btnNhaXuatBan, btnPhieuNhap,
                btnTaoPhieuNhap);
        inforContainerKMTT.setVisible(false);
        inforContainerKMSP.setVisible(false);
        listenerChangeValuesOfKmtheoSanPham();
        listenerChangeValuesOfKmTheoSoTienHoaDon();
        maChuongTrinhKMColumn.setCellValueFactory(new PropertyValueFactory<>("mactkm"));
        phanTramKMColumn.setCellValueFactory(new PropertyValueFactory<>("tenchuongtrinh"));
        ngayBatDauColumn.setCellValueFactory(new PropertyValueFactory<>("ngaybd"));
        ngayKetThucColumn.setCellValueFactory(new PropertyValueFactory<>("ngaykt"));
        CallApi callApi = new CallApi();
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    if (newValue instanceof KmTheoSanPham){
                        showSelectedItemKmtheoSanPham((KmTheoSanPham) newValue);
                    } else {
                        showSelectedItemHD((KmTheoHoaDon) newValue);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                btnDeleteKhuyenMaiSP.setOnAction(event ->
                {
                    String maChuongTrinhKhuyenMai=txt_CTKMSanPham.getText();
                    delete(maChuongTrinhKhuyenMai);
                });
                btnUpdateKhuyenMaiSP.setOnAction(event -> UpdateKmTheoSanPham());
                btnDeleteKhuyenMaiHD.setOnAction(event ->
                {
                    String maChuongTrinh=txt_CTKMTongTien.getText();
                    delete(maChuongTrinh);
                });
                btnUpdateKhuyenMaiHD.setOnAction(event -> UpdateKmTheoSoTienHoaDon());
            } else {
                System.out.println("No item selected");
            }
        });
        if (listChuongTrinhKhuyenMai.isEmpty()){
            listChuongTrinhKhuyenMai=chuongTrinhKhuyenMaiServices.getListChuongTrinhKhuyenMai();
            listTongQuat = FXCollections.observableArrayList(listChuongTrinhKhuyenMai);
        }

        tableView.setItems(listTongQuat);
    }
    public List<KmTheoSoTienHoaDon> convertJsonToListKmTheoSoTienHoaDon(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<KmTheoSoTienHoaDon>KmTheoSoTienHoaDonList = new ArrayList<>();
        System.out.println("json: " + json);
        try {
            KmTheoSoTienHoaDonList = objectMapper.readValue(json, new TypeReference<List<KmTheoSoTienHoaDon>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return KmTheoSoTienHoaDonList;
    }
    public String convertKmTheoSoTienHoaDonToJson(KmTheoSoTienHoaDon KmTheoSoTienHoaDon) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = mapper.writeValueAsString(KmTheoSoTienHoaDon);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    public void listenerChangeValuesOfKmTheoSoTienHoaDon() {
        List<TextField> fields = Arrays.asList(
                txt_CTKMTongTien, txt_tongTien, txt_phanTramKhuyenMai
        );
        fields.forEach(f -> {
            if (f != null) {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainerTT.getChildren().indexOf(btnDeleteKhuyenMaiHD);
                    if (index >= 0) {
                        inforFormButtonContainerTT.getChildren().set(index, btnUpdateKhuyenMaiHD);
                    } else {
                        System.err.println("btnDeleteKhuyenMaiSP không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }
    public void UpdateKmTheoSoTienHoaDon() {
        KmTheoHoaDon KmTheoSoTienHoaDon = new KmTheoHoaDon();
        List<TextField> textFields=Arrays.asList(txt_CTKMTongTien, txt_phanTramKhuyenMai, txt_tongTien
        );
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        if (datePickerNgayBatDauTT.getValue()== null || datePickerNgayKetThucTT.getValue() == null) {
            showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        KmTheoSoTienHoaDon.setMactkm(txt_CTKMTongTien.getText());
        int tongTien = Integer.parseInt(txt_tongTien.getText());
        KmTheoSoTienHoaDon.setTongtien(tongTien/1.0);
        KmTheoSoTienHoaDon.setTenchuongtrinh(txt_TenCTKM.getText());
        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        KmTheoSoTienHoaDon.setPhantramkhuyenmai(phanTram);
        KmTheoSoTienHoaDon.setNgaybd(datePickerNgayBatDauTT.getValue());
        KmTheoSoTienHoaDon.setNgaykt(datePickerNgayKetThucTT.getValue());

            for (int i = 0; i < listChuongTrinhKhuyenMai.size(); i++) {
                if (listChuongTrinhKhuyenMai.get(i).getMactkm().equals(KmTheoSoTienHoaDon.getMactkm())) {
                    listChuongTrinhKhuyenMai.set(i, KmTheoSoTienHoaDon); // thay thế đúng phần tử
                    break;
                }
            }
        chuongTrinhKhuyenMaiServices.update(KmTheoSoTienHoaDon);
        showMessage("Success","Sua thanh cong","ok");
        listTongQuat = FXCollections.observableArrayList(listChuongTrinhKhuyenMai);
        tableView.setItems(listTongQuat);
    }
    public void openInforContainerHD() {
        String maCTKM = "KMHD" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        txt_CTKMTongTien.setText(maCTKM);
        txt_tongTien.setText("");
        txt_phanTramKhuyenMai.setText("");
        datePickerNgayBatDauTT.setValue(null);
        datePickerNgayKetThucTT.setValue(null);
        inforContainerKMTT.setVisible(true);
    }
    public void clossInforContainerKmTheoHoaDon() {
        int index = inforFormButtonContainerTT.getChildren().indexOf(btnDeleteKhuyenMaiHD);
        if (index >= 0) {
            inforFormButtonContainerTT.getChildren().set(index, btnAddKMTT);
        }
        inforContainerKMTT.setVisible(false);
    }
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void addKmTheoTongTien() {
        KmTheoHoaDon KmTheoSoTienHoaDon = new KmTheoHoaDon();
        List<TextField> textFields = Arrays.asList(
                txt_CTKMTongTien,txt_phanTramKhuyenMai,txt_tongTien);
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        KmTheoSoTienHoaDon.setMactkm(txt_CTKMTongTien.getText());
        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        int tongTien = Integer.parseInt(txt_tongTien.getText());
        KmTheoSoTienHoaDon.setPhantramkhuyenmai(phanTram);
        KmTheoSoTienHoaDon.setTenchuongtrinh(txt_TenCTKM.getText());
        KmTheoSoTienHoaDon.setTongtien(tongTien/1.0);
        KmTheoSoTienHoaDon.setNgaybd(datePickerNgayBatDauTT.getValue());
        KmTheoSoTienHoaDon.setNgaykt(datePickerNgayKetThucTT.getValue());
        chuongTrinhKhuyenMaiServices.insert(KmTheoSoTienHoaDon);
        showMessage("ADD SUCCESS","KMTT ADD SUCCESS","Them ");
        listTongQuat= FXCollections.observableArrayList(listChuongTrinhKhuyenMai);
        tableView.setItems(listTongQuat);
    }
    public void showSelectedItemHD(KmTheoHoaDon KmTheoSoTienHoaDon) {
        openInforContainerHD();
        txt_CTKMTongTien.setText(KmTheoSoTienHoaDon.getMactkm());
        txt_tongTien.setText(String.valueOf(KmTheoSoTienHoaDon.getTongtien()));
        txt_TenCTKM.setText(KmTheoSoTienHoaDon.getTenchuongtrinh());
        txt_phanTramKhuyenMai.setText(String.valueOf(KmTheoSoTienHoaDon.getPhantramkhuyenmai()));
        datePickerNgayBatDauTT.setValue(KmTheoSoTienHoaDon.getNgaybd());
        datePickerNgayKetThucTT.setValue(KmTheoSoTienHoaDon.getNgaykt());
        int index = inforFormButtonContainerTT.getChildren().indexOf(btnAddKMTT);
        if (index >= 0) {
            inforFormButtonContainerTT.getChildren().set(index, btnDeleteKhuyenMaiHD);
        } else {
            System.err.println("btnAddKhuyenMai không tồn tại trong inforFormButtonContainer!");
        }
        index = inforFormButtonContainerTT.getChildren().indexOf(btnUpdateKhuyenMaiHD);
        if (index >= 0) {
            inforFormButtonContainerTT.getChildren().set(index, btnDeleteKhuyenMaiHD);
        } else {
            System.err.println("btnDeleteKhuyenMaiSP không tồn tại trong inforFormButtonContainer !");

        }
    }

    public void timKiem() {
        String find = textFieldTimKiem.getText();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/ChuongTrinhKM/timKiem", "find=", find);
        chuongTrinhKMList =convertJsonToListChuongTrinhKM(json);
        listTongQuat= FXCollections.observableArrayList(listChuongTrinhKhuyenMai);
        tableView.setItems(listTongQuat);
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
    public List<ChuongTrinhKM> convertJsonToListChuongTrinhKM(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<ChuongTrinhKM> chuongTrinhKMListTmp = new ArrayList<>();
        try{
            chuongTrinhKMListTmp = objectMapper.readValue(json, new TypeReference<List<ChuongTrinhKM>>() {});
        } catch (JsonProcessingException e) {
            System.err.println("LỖI CHUYỂN ĐỔI JSON->LIST CTKM HÀM CONVERT: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return chuongTrinhKMListTmp;
    }
    public void listenerChangeValuesOfKmtheoSanPham() { // san pham
        List<TextField> fields = Arrays.asList(
                txt_CTKMSanPham, txt_maSanPham, txt_phanTramKhuyenMai
        );
        fields.forEach(f -> {
            if (f != null) {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainerSP.getChildren().indexOf(btnDeleteKhuyenMaiSP);
                    if (index >= 0) {
                        inforFormButtonContainerSP.getChildren().set(index, btnUpdateKhuyenMaiSP);
                    } else {
                        System.err.println("btnDeleteKhuyenMaiSP không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }
    public void delete(String maChuongTrinh) {
        for (int i=0;i<listChuongTrinhKhuyenMai.size();i++) {
            if (listChuongTrinhKhuyenMai.get(i).equals(maChuongTrinh)) {
                listChuongTrinhKhuyenMai.remove(i);
                break;
            }
        }
        chuongTrinhKhuyenMaiServices.delete(maChuongTrinh);
        listTongQuat= FXCollections.observableArrayList(listChuongTrinhKhuyenMai);
        tableView.setItems(listTongQuat);
    }
    public void UpdateKmTheoSanPham() {
        KmTheoSanPham khuyenMai = new KmTheoSanPham();
        List<TextField> textFields=Arrays.asList(txt_CTKMSanPham, txt_phanTramKhuyenMaiSP, txt_maSanPham
        );
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };

        khuyenMai.setMactkm(txt_CTKMSanPham.getText());
        khuyenMai.setMasp(txt_maSanPham.getText());
        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMaiSP.getText());
        khuyenMai.setPhantramkhuyenmai(phanTram);
        khuyenMai.setTenchuongtrinh(txt_TenCTKMSP.getText());
        khuyenMai.setNgaybd(datePickerNgayBatDauSP.getValue());
        khuyenMai.setNgaykt(datePickerNgayKetThucSP.getValue());
        chuongTrinhKhuyenMaiServices.update(khuyenMai);
            for (int i = 0 ; i < listChuongTrinhKhuyenMai.size() ; i++) {
                if (listChuongTrinhKhuyenMai.get(i).getMactkm().equals(khuyenMai.getMactkm())){
                    listChuongTrinhKhuyenMai.set(i,khuyenMai);
                    break;
                }
            }
            showMessage("Success","Sua thanh cong","yesssss");
            listTongQuat = FXCollections.observableArrayList(listChuongTrinhKhuyenMai);
            tableView.setItems(listTongQuat);
    }
    public void openInforContainerKmTheoSanPham() {
        String maCTKM = "KMSP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        txt_CTKMSanPham.setText(maCTKM);
        txt_maSanPham.setText("");
        txt_phanTramKhuyenMaiSP.setText("");
        datePickerNgayBatDauSP.setValue(null);
        datePickerNgayKetThucSP.setValue(null);
        inforContainerKMSP.setVisible(true);
    }

    public void clossInforContainerKmTheoSanPham() {
        int index = inforFormButtonContainerSP.getChildren().indexOf(btnDeleteKhuyenMaiSP);
        if (index >= 0) {
            inforFormButtonContainerSP.getChildren().set(index, btnAddKMSP);
        }
        inforContainerKMSP.setVisible(false);
    }
    public void addKmTheoSanPham() {
        KmTheoSanPham kmTheoSanPham = new KmTheoSanPham();
        List<TextField> textFields = Arrays.asList(
                txt_CTKMSanPham,txt_phanTramKhuyenMaiSP,txt_maSanPham);
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }
        kmTheoSanPham.setMactkm(txt_CTKMSanPham.getText());
        kmTheoSanPham.setMasp(txt_maSanPham.getText());
        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMaiSP.getText());
        kmTheoSanPham.setTenchuongtrinh(txt_TenCTKMSP.getText());
        kmTheoSanPham.setPhantramkhuyenmai(phanTram);
        kmTheoSanPham.setNgaybd(datePickerNgayBatDauSP.getValue());
        kmTheoSanPham.setNgaykt(datePickerNgayKetThucSP.getValue());
        chuongTrinhKhuyenMaiServices.insert(kmTheoSanPham);
        showMessage("them" , "Success","okkkkkk");
        listTongQuat=FXCollections.observableArrayList(listChuongTrinhKhuyenMai);
        tableView.setItems(listTongQuat);
    }

    public void showSelectedItemKmtheoSanPham(KmTheoSanPham khuyenMai) {
        inforContainerKMSP.setVisible(true);
        txt_CTKMSanPham.setText(khuyenMai.getMactkm());
        txt_TenCTKMSP.setText(khuyenMai.getTenchuongtrinh());
        txt_maSanPham.setText(khuyenMai.getMasp());
        txt_phanTramKhuyenMaiSP.setText(khuyenMai.getPhantramkhuyenmai().toString());
        datePickerNgayBatDauSP.setValue(khuyenMai.getNgaybd());
        datePickerNgayKetThucSP.setValue(khuyenMai.getNgaykt());

        int index = inforFormButtonContainerSP.getChildren().indexOf(btnAddKMSP);
        if (index >= 0) {
            inforFormButtonContainerSP.getChildren().set(index, btnDeleteKhuyenMaiSP);
        } else {
            System.err.println("btnAddKhuyenMaiSP không tồn tại trong inforFormButtonContainers!");
        }
        index = inforFormButtonContainerSP.getChildren().indexOf(btnUpdateKhuyenMaiSP);
        if (index >= 0) {
            inforFormButtonContainerSP.getChildren().set(index, btnDeleteKhuyenMaiSP);
        } else {
            System.err.println("btnDeleteKhuyenMaiSP không tồn tại trong inforFormButtonContainers !");

        }
        listenerChangeValuesOfKmTheoSoTienHoaDon();
    }
//    public void timKiemKmTheoSanPham() {
//        String find = textFieldTimKiem.getText();
//        CallApi callApi = new CallApi();
//        String json = callApi.callPostRequestParam("http://localhost:8080/KmTheoSanPham/timKiem", "find=", find);
//        listKmTheoSanPham = FXCollections.observableArrayList(convertJsonToListKmTheoSanPham(json));
////        tableView.setItems(data);
//    }

}