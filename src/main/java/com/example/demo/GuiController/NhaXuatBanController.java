package com.example.demo.GuiController;

import com.example.demo.model.NhaXuatBan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class NhaXuatBanController implements Initializable {
    @FXML private Pane nhaXuatBanPane;
    @FXML private Pane inforContainer;
    @FXML private TableView<NhaXuatBan> tableView;
    @FXML private TableColumn<NhaXuatBan, String> maNXBColumn;
    @FXML private TableColumn<NhaXuatBan, String> tenNXBColumn;
    @FXML private TableColumn<NhaXuatBan, String> diaChiColumn;
    @FXML private TableColumn<NhaXuatBan, String> sdtColumn;
    @FXML private TableColumn<NhaXuatBan, String> emailColumn;
    @FXML private TextField textFieldMaNXB, textFieldTenNXB, textFieldDiaChi, textFieldSDT, textFieldEmail, textFieldTimKiem;
    @FXML private HBox inforFormButtonContainer;
    @FXML private Button btnAddNhaXuatBan, btnThemNXB, btnThoatFormNXB;

    private final Button btnDeleteNhaXuatBan = new Button("Xóa");
    private final Button btnUpdateNhaXuatBan = new Button("Cập nhật");

    private ObservableList<NhaXuatBan> data;
    private List<NhaXuatBan> listNhaXuatBan = new ArrayList<>();
    private final LeftMenuController leftMenuController = new LeftMenuController();
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnPhieuNhap,btnTaoPhieuNhap,btnNhaXuatBan,btnTheLoai;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD,  btnKhuyenMai,
                btnTheLoai, btnNhaXuatBan, btnPhieuNhap,
                btnTaoPhieuNhap);

        inforContainer.setVisible(false);

        // Set up TableView columns
        maNXBColumn.setCellValueFactory(new PropertyValueFactory<>("maNhaXuatBan"));
        tenNXBColumn.setCellValueFactory(new PropertyValueFactory<>("tenNhaXuatBan"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Set button IDs
        btnDeleteNhaXuatBan.setId("delete-button");
        btnUpdateNhaXuatBan.setId("update-button");

        // Handle selection in TableView
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                showSelectedItem(newVal);
                listenerChangeValuesNhaXuatBan();
            } else {
                System.out.println("No item selected");
            }
        });

        // Load initial data
        CallApi callApi = new CallApi();
        String json;
        try {
            json = callApi.callGetApi("http://localhost:8080/nhaXuatBan/getAllNhaXuatBan");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listNhaXuatBan = convertJsonToList(json);
        System.out.println(listNhaXuatBan);
        data = FXCollections.observableArrayList(listNhaXuatBan);
        tableView.setItems(data);

        // Set button actions
        btnDeleteNhaXuatBan.setOnAction(event -> deleteNhaXuatBan());
        btnUpdateNhaXuatBan.setOnAction(event -> updateNhaXuatBan());
        btnAddNhaXuatBan.setOnAction(event -> addNhaXuatBan());
    }

    public List<NhaXuatBan> convertJsonToList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<NhaXuatBan> nhaXuatBanList = new ArrayList<>();
        try {
            nhaXuatBanList = mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return nhaXuatBanList;
    }

    public String convertNhaXuatBanToJson(NhaXuatBan nhaXuatBan) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json;
        try {
            json = mapper.writeValueAsString(nhaXuatBan);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void listenerChangeValuesNhaXuatBan() {
        List<TextField> fields = Arrays.asList(textFieldMaNXB, textFieldTenNXB, textFieldSDT, textFieldDiaChi, textFieldEmail);
        fields.forEach(f -> {
            if (f == null) {
                System.err.println("A TextField is not injected (null)!");
            } else {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " changed: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteNhaXuatBan);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateNhaXuatBan);
                    } else {
                        System.err.println("btnDeleteNhaXuatBan not found in inforFormButtonContainer!");
                    }
                });
            }
        });
    }

    public void deleteNhaXuatBan() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            NhaXuatBan nhaXuatBan = data.get(selectedIndex);
            System.out.println("Nha xuat ban selected: " + nhaXuatBan.getMaNhaXuatBan());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/nhaXuatBan/Delete", "maNhaXuatBan=", nhaXuatBan.getMaNhaXuatBan());
            if (result.contains("Success")) {
                data.remove(selectedIndex);
                tableView.getSelectionModel().clearSelection();
            } else {
                showMessage("Error", "Delete Failed", "Xóa nhà xuất bản thất bại. Vui lòng thử lại!");
            }
        } else {
            showMessage("Error", "No Selection", "Vui lòng chọn một nhà xuất bản để xóa!");
        }
    }

    public void updateNhaXuatBan() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= data.size()) {
            showMessage("Error", "No Selection", "Vui lòng chọn một nhà xuất bản để cập nhật!");
            return;
        }

        NhaXuatBan nhaXuatBan = data.get(selectedIndex);

        List<TextField> textFields = Arrays.asList(textFieldMaNXB, textFieldTenNXB, textFieldSDT, textFieldDiaChi, textFieldEmail);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Empty", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        nhaXuatBan.setTenNhaXuatBan(textFieldTenNXB.getText());
        nhaXuatBan.setSdt(textFieldSDT.getText());
        nhaXuatBan.setDiaChi(textFieldDiaChi.getText());
        nhaXuatBan.setEmail(textFieldEmail.getText());

        CallApi callApi = new CallApi();
        String resultApi = callApi.callPostRequestBody("http://localhost:8080/nhaXuatBan/Update", convertNhaXuatBanToJson(nhaXuatBan));

        if (resultApi.contains("Success")) {
            for (int i = 0; i < listNhaXuatBan.size(); i++) {
                if (listNhaXuatBan.get(i).getMaNhaXuatBan().equals(nhaXuatBan.getMaNhaXuatBan())) {
                    listNhaXuatBan.set(i, nhaXuatBan);
                    break;
                }
            }
            data.set(selectedIndex, nhaXuatBan);
            tableView.refresh();
        } else {
            showMessage("Error", "Update Failed", "Cập nhật nhà xuất bản thất bại. Vui lòng thử lại!");
        }
    }

    public void addNhaXuatBan() {
        NhaXuatBan nhaXuatBan = new NhaXuatBan();
        List<TextField> textFields = Arrays.asList(textFieldMaNXB, textFieldTenNXB, textFieldSDT, textFieldDiaChi, textFieldEmail);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Empty", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        nhaXuatBan.setMaNhaXuatBan(textFieldMaNXB.getText());
        nhaXuatBan.setTenNhaXuatBan(textFieldTenNXB.getText());
        nhaXuatBan.setSdt(textFieldSDT.getText());
        nhaXuatBan.setDiaChi(textFieldDiaChi.getText());
        nhaXuatBan.setEmail(textFieldEmail.getText());

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/nhaXuatBan/Add", convertNhaXuatBanToJson(nhaXuatBan));
        if (result.contains("Success")) {
            listNhaXuatBan.add(nhaXuatBan);
            data.add(nhaXuatBan);
            clossInforContainer();
        } else {
            showMessage("Error", "Add Failed", "Thêm nhà xuất bản thất bại: " + result);
        }
    }

    public void showSelectedItem(NhaXuatBan nhaXuatBan) {
        openInforContainer();
        textFieldMaNXB.setEditable(true);
        textFieldMaNXB.setText(nhaXuatBan.getMaNhaXuatBan());
        textFieldTenNXB.setText(nhaXuatBan.getTenNhaXuatBan());
        textFieldSDT.setText(nhaXuatBan.getSdt());
        textFieldDiaChi.setText(nhaXuatBan.getDiaChi());
        textFieldEmail.setText(nhaXuatBan.getEmail());

        int index = inforFormButtonContainer.getChildren().indexOf(btnAddNhaXuatBan);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteNhaXuatBan);
        } else {
            System.err.println("btnAddNhaXuatBan not found in inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateNhaXuatBan);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteNhaXuatBan);
        }
    }

    @FXML
    public void openInforContainer() {
        textFieldMaNXB.setText("");
        textFieldTenNXB.setText("");
        textFieldSDT.setText("");
        textFieldDiaChi.setText("");
        textFieldEmail.setText("");
        inforContainer.setVisible(true);
    }

    @FXML
    public void clossInforContainer() {
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteNhaXuatBan);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddNhaXuatBan);
        }
        inforContainer.setVisible(false);
    }

    public void timKiem() {
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/nhaXuatBan/timKiem", "find=", textFieldTimKiem.getText());
        data = FXCollections.observableArrayList(convertJsonToList(json));
        tableView.setItems(data);
    }

    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}