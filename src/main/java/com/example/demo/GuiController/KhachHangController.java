package com.example.demo.GuiController;

import com.example.demo.GuiController.CallApi;
import com.example.demo.model.KhachHang;
import com.example.demo.model.NhanVien;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException ;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class KhachHangController implements Initializable {

    @FXML
    private Pane khachHangPane;

    @FXML
    private Pane inforContainerKhachHang;

    @FXML
    private HBox inforFormButtonContainer; //cái bọc 2 nút thêm đóng



    private ObservableList<KhachHang> data;
    List<KhachHang> khachHangList = new ArrayList<>();

    @FXML
    private TableView<KhachHang> tableView;

    @FXML
    private TableColumn<KhachHang, String> hoKhachHangColumn,emailKhachHangColumn,
            tenKhachHangColumn,diaChiKhachHangColumn,sdtKhachHangColumn;
    @FXML
    private TableColumn<KhachHang,Integer> maKhachHangColumn;

    @FXML
    private TextField txt_maKhachHang,txt_hoKhachHang,txt_tenKhachHang,txt_diaChiKhachHang,
            txt_emailKhachHang,txt_sdtKhachHang,txt_timKiemKhachHang;

    @FXML
    private Button  btn_timKiemKhachHang;

    @FXML
    private Button btnAddKhachHang;

    private Button btnDeleteKhachHang = new Button("   xoa  ");
    private Button btnUpdateKhachHang = new Button("cap nhat");

//    public void themKhachHang(ActionEvent actionEvent) {}
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai;
    LeftMenuController leftMenuController=new LeftMenuController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai);
        inforContainerKhachHang.setVisible(false);
        maKhachHangColumn.setCellValueFactory( new PropertyValueFactory<>("makh"));
        hoKhachHangColumn.setCellValueFactory( new PropertyValueFactory<>("hokh"));
        tenKhachHangColumn.setCellValueFactory( new PropertyValueFactory<>("tenkh"));
        emailKhachHangColumn.setCellValueFactory( new PropertyValueFactory<>("email"));
        diaChiKhachHangColumn.setCellValueFactory( new PropertyValueFactory<>("diachi"));
        sdtKhachHangColumn.setCellValueFactory( new PropertyValueFactory<>("sdt"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItems(newValue);
                listenerChangeValuesOfKhachHang();
            }else {
                System.err.println("No selected item");
            }

        });
        CallApi callApi=new CallApi();
        String json = null;

        try {
            json = callApi.callGetApi("http://localhost:8080/KhachHang/getAllKhachHang"); // thay đổi thành khách hàng
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        khachHangList = convertJSONToListKhachHang(json);
        data = FXCollections.observableArrayList(khachHangList);
        tableView.setItems(data);
        btnDeleteKhachHang.setOnAction(event ->deleteKhachHang() );
        //xong

    }
    public void deleteKhachHang(){
        int indexSelected = tableView.getSelectionModel().getSelectedIndex();
        if(indexSelected >=0 && indexSelected < data.size()){
            KhachHang khachHang = data.get(indexSelected);
            System.out.println("Khach hang selected "+khachHang.getMakh());
            data.remove(indexSelected);
            CallApi callApi=new CallApi();
            callApi.callPostRequestParam("http://localhost:8080/KhachHang/deleteKhachHang","maKhachHang",khachHang.getMakh());
            khachHangList.remove(indexSelected);
            tableView.getSelectionModel().clearSelection();
        }
        //xong
    }
    public void addKhachHang(){
        KhachHang khachHang = new KhachHang();
        List<TextField> textFields = Arrays.asList(txt_maKhachHang,txt_hoKhachHang,
                txt_tenKhachHang,txt_diaChiKhachHang,txt_emailKhachHang,txt_sdtKhachHang);
        for (TextField tf : textFields) {
            if(tf.getText().equals("")){
                showMessage("ERROR","TEXT FIELDS NULL","VUI LÒNG NHẬP ĐẦY ĐỦ THÔNG TIN");
                System.err.println("TEXT FIELDS NULL");
                return;
            }
        }
        khachHang.setMakh(txt_maKhachHang.getText());
        khachHang.setHokh(txt_hoKhachHang.getText());
        khachHang.setTenkh(txt_tenKhachHang.getText());
        khachHang.setSdt(txt_sdtKhachHang.getText());
        khachHang.setEmail(txt_emailKhachHang.getText());
        khachHang.setDiachi(txt_diaChiKhachHang.getText());
        khachHangList.add(khachHang);
        data.add(khachHang);
        CallApi callApi=new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KhachHang/addKhachHang",convertKhachHangToJSON(khachHang));
        System.out.println(result+" khahc hang thanh cong ");
        //xong
    }
    public List<KhachHang> convertJSONToListKhachHang(String json) {
        // đối tượng cốt lỗi của thư viện Jackson, hỗ trợ chuyển đổi dữ liệu JSON
        ObjectMapper objectMapper = new ObjectMapper();
        // registerModule :  đăng kí module
        // JavaTimeModule: module giúp chuyển đổi dữ liệu localdate ( dạng dữ liệu đặc biệt-thời gian )
        objectMapper.registerModule(new JavaTimeModule());
        List<KhachHang> khachHangListTemp = new ArrayList<>();
        try {
            khachHangListTemp = objectMapper.readValue(json, new TypeReference<List<KhachHang>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return khachHangListTemp;
        //xong
    }
    public String convertKhachHangToJSON(KhachHang khachHang) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = objectMapper.writeValueAsString(khachHang);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
        //xong

    }
    public void closeInforContainer(){
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhachHang);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddKhachHang);
        }
        inforContainerKhachHang.setVisible(false);
    }
    public void openInforContainer(){
        txt_maKhachHang.setText("");
        txt_hoKhachHang.setText("");
        txt_tenKhachHang.setText("");
        txt_diaChiKhachHang.setText("");
        txt_emailKhachHang.setText("");
        txt_sdtKhachHang.setText("");

        inforContainerKhachHang.setVisible(true);
        //xong
    }
    public void showSelectedItems(KhachHang newValue){
        openInforContainer();
        txt_maKhachHang.setText(newValue.getMakh());
        txt_hoKhachHang.setText(newValue.getHokh());
        txt_tenKhachHang.setText(newValue.getTenkh());
        txt_emailKhachHang.setText(newValue.getEmail());
        txt_sdtKhachHang.setText(newValue.getSdt());
        txt_diaChiKhachHang.setText(newValue.getDiachi());

        int index = inforFormButtonContainer.getChildren().indexOf(btnAddKhachHang);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index,btnDeleteKhachHang);
        }
        else {
            System.out.println("nút thêm không tồn tại ");
        }
        //xong
    }
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu không cần chờ
    }
    public void listenerChangeValuesOfKhachHang(){
        List<TextField> fieldsKhachHang = Arrays.asList(txt_maKhachHang,txt_hoKhachHang,txt_tenKhachHang,
                txt_diaChiKhachHang,txt_emailKhachHang,txt_sdtKhachHang);
        fieldsKhachHang.forEach(textField -> {
            if (textField == null){
                System.err.println("Một TextField chưa được inject (null)!");
            }
            else {
                // textProperty() trả về một đối tượng kiểu StringProperty,
                // đại diện cho giá trị văn bản trong TextField.
                // thường kết hợp với listener
                textField.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(textField.getId()+"thay đổi :"+newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhachHang);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index,btnUpdateKhachHang);
                    }else {
                        System.err.println("không có nút xóa để thay thế");
                    }
                });

            }
        });
        //xong
    }
    public void timKiem(){
        String find=txt_timKiemKhachHang.getText();
        CallApi callApi=new CallApi();
        String json=callApi.callPostRequestParam("http://localhost:8080/KhachHang/timKiemKhachHang","find=",find);
        data=FXCollections.observableArrayList(convertJSONToListKhachHang(json));
        tableView.setItems(data);
    }


    public Button getBtn_timKiemKhachHang() {
        return btn_timKiemKhachHang;
    }

    public void setBtn_timKiemKhachHang(Button btn_timKiemKhachHang) {
        this.btn_timKiemKhachHang = btn_timKiemKhachHang;
    }
}
