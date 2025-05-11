package com.example.demo.GuiController;


import com.example.demo.model.ChuongTrinhKM;
import com.example.demo.model.KmTheoSanPham;
import com.example.demo.model.KmTheoSoTienHoaDon;
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
    private ObservableList<ChuongTrinhKM> listTongQuat ;
    List <ChuongTrinhKM> chuongTrinhKMList = new ArrayList<>();
//KmTheoSoTienHoaDon
//    private  ObservableList<KmTheoSoTienHoaDon> data;
//    List<KmTheoSoTienHoaDon> KmTheoSoTienHoaDonList=new ArrayList<>();

//    private ObservableList<KmTheoSanPham> data1;
//    List<KmTheoSanPham> kmTheoSanPhamList=new ArrayList<>();

    @FXML private TableView<ChuongTrinhKM> tableView;
    @FXML private TableColumn<ChuongTrinhKM, Integer> maChuongTrinhKMColumn;
    @FXML private TableColumn<ChuongTrinhKM, Double> phanTramKMColumn;
    @FXML private TableColumn<ChuongTrinhKM, LocalDate> ngayBatDauColumn;
    @FXML private TableColumn<ChuongTrinhKM, LocalDate> ngayKetThucColumn;


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
    @FXML private TextField txt_CTKMTongTien, txt_tongTien, txt_phanTramKhuyenMai;
    @FXML private Button btnAddKMTT;
    private List<KmTheoSoTienHoaDon> listKmTheoSoTienHoaDonList=new ArrayList<>();



    private Button btnDeleteKhuyenMaiSP = new Button("    Xóa    ");
    private Button btnUpdateKhuyenMaiSP = new Button("Cập nhật");
    private Button btnDeleteKhuyenMaiHD = new Button("    Xóa    ");
    private Button btnUpdateKhuyenMaiHD = new Button("Cập nhật");
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap;
    LeftMenuController leftMenuController=new LeftMenuController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap);
        inforContainerKMTT.setVisible(false);
        inforContainerKMSP.setVisible(false);
        maChuongTrinhKMColumn.setCellValueFactory(new PropertyValueFactory<>("mactkm"));
        phanTramKMColumn.setCellValueFactory(new PropertyValueFactory<>("phantramkhuyenmai"));
        ngayBatDauColumn.setCellValueFactory(new PropertyValueFactory<>("ngaybd"));
        ngayKetThucColumn.setCellValueFactory(new PropertyValueFactory<>("ngaykt"));
        CallApi callApi = new CallApi();
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    String tmp = null;
                    tmp = callApi.callPostRequestBody("http://localhost:8080/ChuongTrinhKM/checkKieuKhuyenMai",newValue.getMactkm());
                    if ( tmp.contains("hd")){
                        List<KmTheoSoTienHoaDon> kmhd = convertJsonToListKmTheoSoTienHoaDon(callApi.callPostRequestParam("http://localhost:8080/KhuyenMaiTongTien/search","find=", newValue.getMactkm()));
                        if (!kmhd.isEmpty()) {
                            showSelectedItemHD(kmhd.get(0));
                        };
                        listenerChangeValuesOfKmTheoSoTienHoaDon();
                    }else if (tmp.contains("sp")){
                        List<KmTheoSanPham> kmsp = convertJsonToListKmTheoSanPham(callApi.callPostRequestParam("http://localhost:8080/KmTheoSanPham/searchKmTheoSanPham","find=", newValue.getMactkm()));
                        if (!kmsp.isEmpty()) {
                            showSelectedItemKmtheoSanPham(kmsp.get(0));
                        };
                        listenerChangeValuesOfKmtheoSanPham();
                    }else {
                        showMessage("INITTIALIZE else","null","null");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        /// ////
                btnDeleteKhuyenMaiSP.setOnAction(event -> DeleteKmTheoSanPham(btnDeleteKhuyenMaiSP));
                btnUpdateKhuyenMaiSP.setOnAction(event -> UpdateKmTheoSanPham(newValue));
                btnDeleteKhuyenMaiHD.setOnAction(event -> DeleteKmTheoSoTienHoaDon(btnDeleteKhuyenMaiHD));
                btnUpdateKhuyenMaiHD.setOnAction(event -> UpdateKmTheoSoTienHoaDon(newValue));




            } else {
                System.out.println("No item selected");
            }
        });

        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/ChuongTrinhKM/getAllChuongTrinhKM");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        chuongTrinhKMList = convertJsonToListChuongTrinhKM(json);
        listTongQuat = FXCollections.observableArrayList(chuongTrinhKMList);
        tableView.setItems(listTongQuat);

        try {
            listKmTheoSanPham = convertJsonToListKmTheoSanPham(callApi.callGetApi("http://localhost:8080/KmTheoSanPham/getAll"));
            listKmTheoSoTienHoaDonList =convertJsonToListKmTheoSoTienHoaDon(callApi.callGetApi("http://localhost:8080/KhuyenMaiTongTien/getAll"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
    public void DeleteKmTheoSoTienHoaDon(Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < listKmTheoSoTienHoaDonList.size()) {
            KmTheoSoTienHoaDon khuyenMai = listKmTheoSoTienHoaDonList.get(selectedIndex);
            System.out.println("Khuyen Mai Theo Tong Tien selected " + khuyenMai.getMactkm());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/KhuyenMai/Delete", "maChuongTrinh=",khuyenMai.getMactkm());
            listKmTheoSoTienHoaDonList.remove(selectedIndex);
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void UpdateKmTheoSoTienHoaDon( ChuongTrinhKM chuongTrinhKM) {
        KmTheoSoTienHoaDon KmTheoSoTienHoaDon = new KmTheoSoTienHoaDon();
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
        KmTheoSoTienHoaDon.setSotienhoadon(tongTien);
        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        KmTheoSoTienHoaDon.setPhantramkhuyenmai(phanTram);
        KmTheoSoTienHoaDon.setNgaybd(datePickerNgayBatDauTT.getValue());
        KmTheoSoTienHoaDon.setNgaykt(datePickerNgayKetThucTT.getValue());

        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/KhuyenMaiTongTien/Update",convertKmTheoSoTienHoaDonToJson(KmTheoSoTienHoaDon));
        if (resultApi.contains("success") || resultApi.contains("Success")) {
            for (int i = 0; i < chuongTrinhKMList.size(); i++) {
                if (chuongTrinhKMList.get(i).getMactkm().equals(KmTheoSoTienHoaDon.getMactkm())) {
                    chuongTrinhKMList.set(i,(ChuongTrinhKM) KmTheoSoTienHoaDon); // thay thế đúng phần tử
                    break;
                }
            }
            showMessage("Success","Sua thanh cong",resultApi);
            listTongQuat = FXCollections.observableArrayList(chuongTrinhKMList);
            tableView.setItems(listTongQuat);
        }
    }
    public void openInforContainerHD() {
        String maCTKM = "KMHD" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        txt_CTKMTongTien.setText(maCTKM);
        txt_tongTien.setText("");
        txt_phanTramKhuyenMai.setText("");
        datePickerNgayBatDauTT.setValue(LocalDate.now());
        datePickerNgayKetThucTT.setValue(LocalDate.now());
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
        KmTheoSoTienHoaDon KmTheoSoTienHoaDon = new KmTheoSoTienHoaDon();
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
        KmTheoSoTienHoaDon.setSotienhoadon(tongTien);
        KmTheoSoTienHoaDon.setNgaybd(datePickerNgayBatDauTT.getValue());
        KmTheoSoTienHoaDon.setNgaykt(datePickerNgayKetThucTT.getValue());
        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KhuyenMaiTongTien/Add", convertKmTheoSoTienHoaDonToJson(KmTheoSoTienHoaDon));
        System.out.println(result);
        if (result.contains("Success") || result.contains("success")) {
            listKmTheoSoTienHoaDonList.add(KmTheoSoTienHoaDon);
            showMessage("ADD SUCCESS","KMTT ADD SUCCESS","Them ");
        }
    }
    public void showSelectedItemHD(KmTheoSoTienHoaDon KmTheoSoTienHoaDon) {
        openInforContainerHD();
        txt_CTKMTongTien.setText(KmTheoSoTienHoaDon.getMactkm());
        txt_tongTien.setText(String.valueOf(KmTheoSoTienHoaDon.getSotienhoadon()));
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
        listTongQuat= FXCollections.observableArrayList(chuongTrinhKMList);
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
    public void DeleteKmTheoSanPham(Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        ChuongTrinhKM selected = tableView.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0 && selectedIndex < chuongTrinhKMList.size()) {
            System.out.println("Khuyen Mai Theo San Pham selected " + selected.getMactkm());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/KmTheoSanPham/Delete", "find=", selected.getMactkm());
            chuongTrinhKMList.remove(selectedIndex);
            listTongQuat = FXCollections.observableArrayList(chuongTrinhKMList);
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }

    }
    public void UpdateKmTheoSanPham( ChuongTrinhKM chuongTrinhKM) {
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
        if (datePickerNgayBatDauSP.getValue()==null ||datePickerNgayBatDauTT.getValue()==null){
            showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
        }

        khuyenMai.setMactkm(txt_CTKMSanPham.getText());
        khuyenMai.setMasp(txt_maSanPham.getText());
        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMaiSP.getText());
        khuyenMai.setPhantramkhuyenmai(phanTram);
        khuyenMai.setNgaybd(datePickerNgayBatDauSP.getValue());
        khuyenMai.setNgaykt(datePickerNgayKetThucSP.getValue());

        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/KmTheoSanPham/Update",convertKmTheoSanPhamToJson(khuyenMai));
        if (resultApi.contains("success") || resultApi.contains("Success")) {
            for (int i = 0 ; i < chuongTrinhKMList.size() ; i++) {
                if (chuongTrinhKMList.get(i).getMactkm().equals(khuyenMai.getMactkm())){
                    chuongTrinhKMList.set(i,(ChuongTrinhKM) khuyenMai);
                    break;
                }
            }
            for ( int i = 0 ; i < listKmTheoSanPham.size() ; i++) {
                if (listKmTheoSanPham.get(i).getMactkm().equals(khuyenMai.getMactkm())){
                    listKmTheoSanPham.set(i,khuyenMai);
                    break;
                }
            }
            showMessage("Success","Sua thanh cong",resultApi);
            listTongQuat = FXCollections.observableArrayList(chuongTrinhKMList);
            tableView.setItems(listTongQuat);
        }
    }
    public void openInforContainerKmTheoSanPham() {
        String maCTKM = "KMSP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        txt_CTKMSanPham.setText(maCTKM);
        txt_maSanPham.setText("");
        txt_phanTramKhuyenMaiSP.setText("");
        datePickerNgayBatDauSP.setValue(LocalDate.now());
        datePickerNgayKetThucSP.setValue(LocalDate.now());
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
        kmTheoSanPham.setPhantramkhuyenmai(phanTram);
        kmTheoSanPham.setNgaybd(datePickerNgayBatDauSP.getValue());
        kmTheoSanPham.setNgaykt(datePickerNgayKetThucSP.getValue());
        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KmTheoSanPham/Add", convertKmTheoSanPhamToJson(kmTheoSanPham));
        System.out.println(result);
        if (result.contains("Success")||result.contains("success")) {
            listKmTheoSanPham.add(kmTheoSanPham);
            showMessage("them" , "Success","okkkkkk");
        }
    }

    public void showSelectedItemKmtheoSanPham(KmTheoSanPham khuyenMai) {
        txt_CTKMSanPham.setText(khuyenMai.getMactkm());
        txt_maSanPham.setText(String.valueOf(khuyenMai.getMasp()));
        txt_phanTramKhuyenMaiSP.setText(String.valueOf(khuyenMai.getPhantramkhuyenmai()));
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
    }
//    public void timKiemKmTheoSanPham() {
//        String find = textFieldTimKiem.getText();
//        CallApi callApi = new CallApi();
//        String json = callApi.callPostRequestParam("http://localhost:8080/KmTheoSanPham/timKiem", "find=", find);
//        listKmTheoSanPham = FXCollections.observableArrayList(convertJsonToListKmTheoSanPham(json));
////        tableView.setItems(data);
//    }

}