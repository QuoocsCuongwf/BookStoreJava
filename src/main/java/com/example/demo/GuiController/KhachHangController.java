package com.example.demo.GuiController;

import com.example.demo.model.KhachHang;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Controller
public class KhachHangController implements Initializable {

    @FXML private Pane khachHangPane;
    @FXML private Pane inforContainer;
    @FXML private HBox inforFormButtonContainer;
    @FXML private TableView<KhachHang> tableView;
    @FXML private TableColumn<KhachHang, String> maKhachHangColumn, hoKhachHangColumn, tenKhachHangColumn, diaChiKhachHangColumn, emailKhachHangColumn, sdtKhachHangColumn;
    @FXML private TextField textFieldMaKhachHang, textFieldHoKhachHang, textFieldTenKhachHang, textFieldDiaChiKhachHang, textFieldEmailKhachHang, textFieldSdtKhachHang, textFieldTimKiem;
    @FXML private Button btnThemKhachHang, btnAddKhachHang, btnThoatFormKhachHang;
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnTheLoai, btnNhaXuatBan;

    private Button btnDeleteKhachHang = new Button("Xóa");
    private Button btnUpdateKhachHang = new Button("Cập nhật");
    private ObservableList<KhachHang> data;
    private List<KhachHang> khachHangList = new ArrayList<>();
    private LeftMenuController leftMenuController = new LeftMenuController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai);

        inforContainer.setVisible(false);

        maKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("makh"));
        hoKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("hokh"));
        tenKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("tenkh"));
        diaChiKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        emailKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        sdtKhachHangColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));

        btnDeleteKhachHang.setId("delete-button");
        btnUpdateKhachHang.setId("update-button");

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                showSelectedItems(newVal);
                listenerChangeValuesOfKhachHang();
            } else {
                System.out.println("No item selected");
            }
        });

        CallApi callApi = new CallApi();
        String json;
        try {
            json = callApi.callGetApi("http://localhost:8080/KhachHang/getAllKhachHang");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        khachHangList = convertJSONToListKhachHang(json);
        data = FXCollections.observableArrayList(khachHangList);
        tableView.setItems(data);

        btnDeleteKhachHang.setOnAction(event -> deleteKhachHang());
        btnUpdateKhachHang.setOnAction(event -> updateKhachHang());
        btnAddKhachHang.setOnAction(event -> addKhachHang());
    }

    public List<KhachHang> convertJSONToListKhachHang(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<KhachHang> khachHangListTemp = new ArrayList<>();
        try {
            khachHangListTemp = mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return khachHangListTemp;
    }

    public String convertKhachHangToJSON(KhachHang khachHang) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json;
        try {
            json = mapper.writeValueAsString(khachHang);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void deleteKhachHang() {
        int indexSelected = tableView.getSelectionModel().getSelectedIndex();
        if (indexSelected >= 0 && indexSelected < data.size()) {
            KhachHang khachHang = data.get(indexSelected);
            System.out.println("Khach hang selected: " + khachHang.getMakh());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/KhachHang/deleteKhachHang", "maKhachHang=", khachHang.getMakh());
            if (result.contains("Success")) {
                data.remove(indexSelected);
                khachHangList.remove(indexSelected);
                tableView.getSelectionModel().clearSelection();
            } else {
                showMessage("Error", "Delete Failed", "Xóa khách hàng thất bại. Vui lòng thử lại!");
            }
        } else {
            showMessage("Error", "No Selection", "Vui lòng chọn một khách hàng để xóa!");
        }
    }

    public void addKhachHang() {
        KhachHang khachHang = new KhachHang();
        List<TextField> textFields = Arrays.asList(textFieldMaKhachHang, textFieldHoKhachHang, textFieldTenKhachHang, textFieldDiaChiKhachHang, textFieldEmailKhachHang, textFieldSdtKhachHang);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Empty", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }
        khachHang.setMakh(textFieldMaKhachHang.getText());
        khachHang.setHokh(textFieldHoKhachHang.getText());
        khachHang.setTenkh(textFieldTenKhachHang.getText());
        khachHang.setDiachi(textFieldDiaChiKhachHang.getText());
        khachHang.setEmail(textFieldEmailKhachHang.getText());
        khachHang.setSdt(textFieldSdtKhachHang.getText());

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KhachHang/addKhachHang", convertKhachHangToJSON(khachHang));
        if (result.contains("success")) {
            khachHangList.add(khachHang);
            data.add(khachHang);
            showMessage("Add Khach Hang", "Success", "Khách hàng đã được thêm!");
            closeInforContainer();
        } else {
            showMessage("Add Khach Hang", "Fail", "Thêm khách hàng thất bại: " + result);
        }
    }

    public void updateKhachHang() {
        int indexSelected = tableView.getSelectionModel().getSelectedIndex();
        if (indexSelected < 0 || indexSelected >= data.size()) {
            showMessage("Error", "No Selection", "Vui lòng chọn một khách hàng để cập nhật!");
            return;
        }

        KhachHang khachHang = data.get(indexSelected);
        List<TextField> textFields = Arrays.asList(textFieldMaKhachHang, textFieldHoKhachHang, textFieldTenKhachHang, textFieldDiaChiKhachHang, textFieldEmailKhachHang, textFieldSdtKhachHang);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Empty", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        khachHang.setMakh(textFieldMaKhachHang.getText());
        khachHang.setHokh(textFieldHoKhachHang.getText());
        khachHang.setTenkh(textFieldTenKhachHang.getText());
        khachHang.setDiachi(textFieldDiaChiKhachHang.getText());
        khachHang.setEmail(textFieldEmailKhachHang.getText());
        khachHang.setSdt(textFieldSdtKhachHang.getText());

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KhachHang/updateKhachHang", convertKhachHangToJSON(khachHang));
        if (result.contains("Success")) {
            for (int i = 0; i < khachHangList.size(); i++) {
                if (khachHangList.get(i).getMakh().equals(khachHang.getMakh())) {
                    khachHangList.set(i, khachHang);
                    break;
                }
            }
            data.set(indexSelected, khachHang);
            tableView.refresh();
            showMessage("Update Khach Hang", "Success", "Cập nhật khách hàng thành công!");
            closeInforContainer();
        } else {
            showMessage("Update Khach Hang", "Fail", "Cập nhật khách hàng thất bại: " + result);
        }
    }

    public void openInforContainer() {
        textFieldMaKhachHang.setText("KH" + (khachHangList.size() + 1));
        textFieldHoKhachHang.setText("");
        textFieldTenKhachHang.setText("");
        textFieldDiaChiKhachHang.setText("");
        textFieldEmailKhachHang.setText("");
        textFieldSdtKhachHang.setText("");
        inforContainer.setVisible(true);
    }

    public void closeInforContainer() {
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhachHang);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddKhachHang);
        }
        inforContainer.setVisible(false);
    }

    public void showSelectedItems(KhachHang khachHang) {
        openInforContainer();
        textFieldMaKhachHang.setText(khachHang.getMakh());
        textFieldHoKhachHang.setText(khachHang.getHokh());
        textFieldTenKhachHang.setText(khachHang.getTenkh());
        textFieldDiaChiKhachHang.setText(khachHang.getDiachi());
        textFieldEmailKhachHang.setText(khachHang.getEmail());
        textFieldSdtKhachHang.setText(khachHang.getSdt());

        int index = inforFormButtonContainer.getChildren().indexOf(btnAddKhachHang);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteKhachHang);
        } else {
            index = inforFormButtonContainer.getChildren().indexOf(btnUpdateKhachHang);
            if (index >= 0) {
                inforFormButtonContainer.getChildren().set(index, btnDeleteKhachHang);
            } else {
                System.err.println("btnAddKhachHang or btnUpdateKhachHang not found in inforFormButtonContainer!");
            }
        }
    }

    public void timKiem() {
        String find = textFieldTimKiem.getText();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/KhachHang/timKiemKhachHang", "find=", find);
        data = FXCollections.observableArrayList(convertJSONToListKhachHang(json));
        tableView.setItems(data);
    }

    public void listenerChangeValuesOfKhachHang() {
        List<TextField> fields = Arrays.asList(textFieldMaKhachHang, textFieldHoKhachHang, textFieldTenKhachHang, textFieldDiaChiKhachHang, textFieldEmailKhachHang, textFieldSdtKhachHang);
        fields.forEach(textField -> {
            if (textField == null) {
                System.err.println("A TextField is not injected (null)!");
            } else {
                textField.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(textField.getId() + " changed: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKhachHang);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateKhachHang);
                    } else {
                        System.err.println("btnDeleteKhachHang not found in inforFormButtonContainer!");
                    }
                });
            }
        });
    }

    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}