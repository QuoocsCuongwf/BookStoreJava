package com.example.demo.GuiController;

import com.example.demo.model.NhanVien;
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
public class NhanVienController implements Initializable {

    @FXML
    private Pane inforContainer;

    @FXML
    private Pane nhanVienPane;
    @FXML
    private TableView<NhanVien> tableView;
    @FXML
    private TableColumn<NhanVien, Integer> maNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> tenNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> hoNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> chucVuNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, Integer> luongNhanVienColumn;
    @FXML
    private TableColumn<NhanVien, String> sdtNhanVienColumn;
    private ObservableList<NhanVien> data;
    List<NhanVien> nhanVienList=new ArrayList<>();
    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private TextField textFieldMaNhanVien, textFieldTenNhanVien, textFieldSoCCCD, textFieldHoNhanVien, textFieldLuongNhanVien, textFieldChucVu,textFieldThongTinLienLac;
    @FXML
    private DatePicker datePickerNgayVaoLam;
    @FXML
    private HBox inforFormButtonContainer;
    @FXML
    private Button btnAddNhanVien;

    private Button btnDeleteNhanVien=new Button("    Xóa    ");
    private Button btnUpdateNhanVien=new Button("Cập nhật");
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnPhieuNhap,btnTaoPhieuNhap,btnNhaXuatBan,btnTheLoai;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeftMenuController leftMenuController = new LeftMenuController();
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD,  btnKhuyenMai,
                btnTheLoai, btnNhaXuatBan, btnPhieuNhap,
                btnTaoPhieuNhap);
        inforContainer.setVisible(false);
        maNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("manv"));
        tenNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("tennv"));
        hoNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("honv"));
        chucVuNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("chucvu"));
        luongNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("luong"));
        sdtNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfNhanVien();
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });
        CallApi callApi=new CallApi();
        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/nhanVien/getAllNhanVien");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nhanVienList=convertJsonToListNhanVien(json);
        System.out.println(nhanVienList);
        data = FXCollections.observableArrayList(nhanVienList);
        tableView.setItems(data);
        btnDeleteNhanVien.setOnAction(event -> deleteNhanVien(btnDeleteNhanVien));
        btnUpdateNhanVien.setOnAction(event -> updateNhanVien());
    }
    public List<NhanVien> getNhanVienList() {
        CallApi callApi = new CallApi();
        List<NhanVien> tmp = new ArrayList<>();
        try {
            String json = callApi.callGetApi("http://localhost:8080/nhanVien/getAllNhanVien");
            tmp = convertJsonToListNhanVien(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmp;

    }
    public List<NhanVien> convertJsonToListNhanVien(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<NhanVien> nhanVienList = new ArrayList<NhanVien>();
        System.out.println("json: " + json);
        try {
            nhanVienList = objectMapper.readValue(json, new TypeReference<List<NhanVien>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return nhanVienList;
    }
    public String convertNhanVienToJson(NhanVien nhanVien)  {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json= null;
        try {
            json = mapper.writeValueAsString(nhanVien);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void listenerChangeValuesOfNhanVien(){
        List<TextField> fields = Arrays.asList(
                textFieldMaNhanVien, textFieldTenNhanVien, textFieldSoCCCD,
                textFieldHoNhanVien, textFieldLuongNhanVien,
                textFieldChucVu, textFieldThongTinLienLac
        );

        fields.forEach(f -> {
            if (f == null) {
                System.err.println("Một TextField chưa được inject (null)!");
            } else {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteNhanVien);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateNhanVien);
                    } else {
                        System.err.println("btnDeleteNhanVien không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }

    public void deleteNhanVien(Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            NhanVien nhanVien = data.get(selectedIndex);
            System.out.println("Nhan vien selected "+nhanVien.getManv());
            CallApi callApi=new CallApi();
            String result=callApi.callPostRequestParam("http://localhost:8080/nhanVien/Delete","maNhanVien=",nhanVien.getManv());
            data.remove(selectedIndex); // Optional: remove from ObservableList to update the table
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void updateNhanVien() {
        NhanVien nhanVien = new NhanVien();
        List<TextField> textFields=Arrays.asList(textFieldMaNhanVien, textFieldTenNhanVien, textFieldSoCCCD,
                textFieldHoNhanVien, textFieldLuongNhanVien,
                textFieldChucVu, textFieldThongTinLienLac);
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        nhanVien.setManv(textFieldMaNhanVien.getText());
        nhanVien.setMail(textFieldThongTinLienLac.getText());
        nhanVien.setTennv(textFieldTenNhanVien.getText());
        nhanVien.setHonv(textFieldHoNhanVien.getText());
        nhanVien.setChucvu(textFieldChucVu.getText());
        nhanVien.setCccd(textFieldSoCCCD.getText());
        nhanVien.setNgayvaolam(datePickerNgayVaoLam.getValue());
        nhanVien.setLuong(Integer.parseInt(textFieldLuongNhanVien.getText()));
        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/nhanVien/Update",convertNhanVienToJson(nhanVien));
        if (resultApi.contains("Success")) {
            for (int i = 0; i < nhanVienList.size(); i++) {
                if (nhanVienList.get(i).getManv().equals(nhanVien.getManv())) {
                    nhanVienList.set(i, nhanVien); // thay thế đúng phần tử
                    break;
                }
            }
            showMessage("Success","Sua sach thanh cong",resultApi);
            data = FXCollections.observableArrayList(nhanVienList);
            tableView.setItems(data);
        }
    }
    public void openInforContainer(){
        textFieldMaNhanVien.setText("NV"+nhanVienList.size()+1);
        textFieldTenNhanVien.setText("");
        textFieldHoNhanVien.setText("");
        textFieldChucVu.setText("");
        textFieldSoCCCD.setText("");
        textFieldThongTinLienLac.setText("");
        textFieldLuongNhanVien.setText("");
        datePickerNgayVaoLam.setValue(LocalDate.now());
        inforContainer.setVisible(true);

    }
    public void clossInforContainer(){
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteNhanVien);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddNhanVien);
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
    public void addNhanVien(){
        NhanVien nhanVien = new NhanVien();
        List<TextField> textFields=Arrays.asList(textFieldMaNhanVien, textFieldTenNhanVien, textFieldSoCCCD,
                textFieldHoNhanVien, textFieldLuongNhanVien,
                textFieldChucVu, textFieldThongTinLienLac);
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        nhanVien.setManv(textFieldMaNhanVien.getText());
        nhanVien.setMail(textFieldThongTinLienLac.getText());
        nhanVien.setTennv(textFieldTenNhanVien.getText());
        nhanVien.setHonv(textFieldHoNhanVien.getText());
        nhanVien.setChucvu(textFieldChucVu.getText());
        nhanVien.setCccd(textFieldSoCCCD.getText());
        nhanVien.setNgayvaolam(datePickerNgayVaoLam.getValue());
        nhanVien.setLuong(Integer.parseInt(textFieldLuongNhanVien.getText()));
        CallApi callApi=new CallApi();
        String result=callApi.callPostRequestBody("http://localhost:8080/nhanVien/Add",convertNhanVienToJson(nhanVien));
        System.out.println(result);
        if (result.contains("Success")){
            nhanVienList.add(nhanVien);
            data.add(nhanVien);
        }


    }

    public void showSelectedItem(NhanVien nhanVien) {
        openInforContainer();
        textFieldMaNhanVien.setEditable(false);
        textFieldMaNhanVien.setText(nhanVien.getManv());
        textFieldTenNhanVien.setText(nhanVien.getTennv());
        textFieldHoNhanVien.setText(nhanVien.getHonv());
        textFieldChucVu.setText(nhanVien.getChucvu());
        textFieldSoCCCD.setText(nhanVien.getCccd());
        textFieldThongTinLienLac.setText(nhanVien.getMail());
        textFieldLuongNhanVien.setText(nhanVien.getLuong().toString());
        datePickerNgayVaoLam.setValue(nhanVien.getNgayvaolam());

        int index = inforFormButtonContainer.getChildren().indexOf(btnAddNhanVien);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteNhanVien);
        } else {
            System.err.println(" error sbtnAddNhanVien không tồn tại trong inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateNhanVien);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteNhanVien);
        } else {
            System.err.println("btnDeleteNhanVien không tồn tại trong inforFormButtonContainer!");
        }
    }

    public void timKiem(){
        String find=textFieldTimKiem.getText();
        CallApi callApi=new CallApi();
        String json=callApi.callPostRequestParam("http://localhost:8080/nhanVien/timKiem","find=",find);
        data=FXCollections.observableArrayList(convertJsonToListNhanVien(json));
        tableView.setItems(data);
    }


}
