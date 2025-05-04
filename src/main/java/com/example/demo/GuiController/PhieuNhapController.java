package com.example.demo.GuiController;



import com.example.demo.model.PhieuNhap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
@Component
@Controller
public class PhieuNhapController  implements Initializable {
    @FXML
    private Pane inforContainer;

    @FXML
    private Pane phieuNhapPane;
    @FXML
    private TableView<PhieuNhap> tableView;
    @FXML
    private TableColumn<PhieuNhap, String> maPhieuNhapColumn;
    @FXML
    private TableColumn<PhieuNhap, String> maNhanVienColumn;
    @FXML
    private TableColumn<PhieuNhap, String> maNhaCungCapColumn;
    @FXML
    private TableColumn<PhieuNhap, String> tongTienColumn;

    private ObservableList<PhieuNhap> data;
    List<PhieuNhap> phieuNhapList=new ArrayList<>();
    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private TextField txt_MaPhieuNhap,txt_MaNhanVien,txt_MaNhaCungCap,txt_TongTien;
    @FXML
    private DatePicker datePickerNgayNhap;
    @FXML
    private HBox inforFormButtonContainer;
    @FXML
    private Button btnAddPhieuNhap;
    @FXML
    private HBox inforButtonContainer;

    private Button btnDeletePhieuNhap=new Button("    Xóa    ");
    private Button btnUpdatePhieuNhap=new Button("Cập nhật");
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
        maPhieuNhapColumn.setCellValueFactory(new PropertyValueFactory<>("mapn"));
        maNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("manv"));
        maNhaCungCapColumn.setCellValueFactory(new PropertyValueFactory<>("mancc"));
        tongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongtien"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfPhieuNhap();
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });
        CallApi callApi=new CallApi();
        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/phieuNhap/getAllPhieuNhap");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        phieuNhapList=convertJsonToListPhieuNhap(json);
        System.out.println(phieuNhapList);
        data = FXCollections.observableArrayList(phieuNhapList);
        tableView.setItems(data);
        btnDeletePhieuNhap.setOnAction(event -> deletePhieuNhap(btnDeletePhieuNhap));
        btnUpdatePhieuNhap.setOnAction(event -> updatePhieuNhap());
    }
    public List<PhieuNhap> convertJsonToListPhieuNhap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<PhieuNhap> phieuNhapList = new ArrayList<PhieuNhap>();
        System.out.println("json: " + json);
        try {
            phieuNhapList = objectMapper.readValue(json, new TypeReference<List<PhieuNhap>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return phieuNhapList;
    }
    public String convertPhieuNhapToJson(PhieuNhap phieuNhap)  {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json= null;
        try {
            json = mapper.writeValueAsString(phieuNhap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void listenerChangeValuesOfPhieuNhap(){
        List<TextField> fields = Arrays.asList(
                txt_MaPhieuNhap,txt_MaNhanVien,txt_MaNhaCungCap,txt_TongTien
        );

        fields.forEach(f -> {
            if (f == null) {
                System.err.println("Một TextField chưa được inject (null)!");
            } else {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeletePhieuNhap);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdatePhieuNhap);
                    } else {
                        System.err.println("btnDeletePhieuNhap không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }

    public void deletePhieuNhap(Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            PhieuNhap phieuNhap = data.get(selectedIndex);
            System.out.println("Phieu nhap selected "+phieuNhap.getManv());
            CallApi callApi=new CallApi();
            String result=callApi.callPostRequestParam("http://localhost:8080/phieuNhap/Delete","maNhanVien=",phieuNhap.getManv());
            data.remove(selectedIndex); // Optional: remove from ObservableList to update the table
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void updatePhieuNhap() {
        PhieuNhap phieuNhap = new PhieuNhap();
        List<TextField> textFields=Arrays.asList(txt_MaPhieuNhap,txt_MaNhanVien,txt_MaNhaCungCap,txt_TongTien);
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        phieuNhap.setMapn(txt_MaPhieuNhap.getText());
        phieuNhap.setNgaynhap(datePickerNgayNhap.getValue());
        phieuNhap.setManv(txt_MaNhanVien.getText());
        phieuNhap.setMancc(txt_MaNhaCungCap.getText());

        Double tongTien = Double.parseDouble(txt_TongTien.getText());
        phieuNhap.setTongtien(tongTien);


        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/phieuNhap/Update",convertPhieuNhapToJson(phieuNhap));
        if (resultApi.contains("Success")) {
            for (int i = 0; i < phieuNhapList.size(); i++) {
                if (phieuNhapList.get(i).getManv().equals(phieuNhap.getManv())) {
                    phieuNhapList.set(i, phieuNhap); // thay thế đúng phần tử
                    break;
                }
            }
            showMessage("Success","Sua sach thanh cong",resultApi);
            data = FXCollections.observableArrayList(phieuNhapList);
            tableView.setItems(data);
        }
    }
    public void openInforContainer(){
        txt_MaPhieuNhap.setText("NV"+phieuNhapList.size()+1);
        datePickerNgayNhap.setValue(LocalDate.now());
        txt_MaNhanVien.setText("");
        txt_MaNhaCungCap.setText("");
        txt_TongTien.setText("");
        inforContainer.setVisible(true);
    }
    public void clossInforContainer(){
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeletePhieuNhap);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddPhieuNhap);
        }
        inforContainer.setVisible(false);
    }
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu không cần chờ
    }
    public void addPhieuNhap(){
        PhieuNhap phieuNhap = new PhieuNhap();
        List<TextField> textFields=Arrays.asList(txt_MaPhieuNhap,txt_MaNhanVien,txt_MaNhaCungCap,txt_TongTien);
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        phieuNhap.setMapn(txt_MaPhieuNhap.getText());
        phieuNhap.setNgaynhap(datePickerNgayNhap.getValue());
        phieuNhap.setManv(txt_MaNhanVien.getText());
        phieuNhap.setMancc(txt_MaNhaCungCap.getText());

        Double tongTien = Double.parseDouble(txt_TongTien.getText());
        phieuNhap.setTongtien(tongTien);


        CallApi callApi=new CallApi();
        String result=callApi.callPostRequestBody("http://localhost:8080/phieuNhap/Add",convertPhieuNhapToJson(phieuNhap));
        System.out.println(result);
        if (result.contains("Success")){
            phieuNhapList.add(phieuNhap);
            data.add(phieuNhap);
        }
    }

    public void showSelectedItem(PhieuNhap phieuNhap) {
        openInforContainer();

        txt_MaPhieuNhap.setEditable(false);
        txt_MaPhieuNhap.setText(phieuNhap.getMapn());
        datePickerNgayNhap.setValue(phieuNhap.getNgaynhap());
        txt_MaNhanVien.setText(phieuNhap.getManv());
        txt_MaNhaCungCap.setText(phieuNhap.getMancc());

        Double tongTien = Double.parseDouble(txt_TongTien.getText());
        phieuNhap.setTongtien(tongTien);


        int index = inforFormButtonContainer.getChildren().indexOf(btnAddPhieuNhap);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeletePhieuNhap);
        } else {
            System.err.println(" error sbtnAddPhieuNhap không tồn tại trong inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdatePhieuNhap);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeletePhieuNhap);
        } else {
            System.err.println("btnDeletePhieuNhap không tồn tại trong inforFormButtonContainer!");
        }
    }

    public void timKiem(){
        String find=textFieldTimKiem.getText();
        CallApi callApi=new CallApi();
        String json=callApi.callPostRequestParam("http://localhost:8080/phieuNhap/timKiem","find=",find);
        data=FXCollections.observableArrayList(convertJsonToListPhieuNhap(json));
        tableView.setItems(data);
    }



}