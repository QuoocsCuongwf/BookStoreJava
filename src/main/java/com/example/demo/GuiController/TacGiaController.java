package com.example.demo.GuiController;

import com.example.demo.model.TacGia;
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
import java.util.*;

@Component
@Controller
public class TacGiaController implements Initializable {

    @FXML private Pane inforContainer;
    @FXML private Pane tacGiaPane;
    @FXML private TableView<TacGia> tableView;
    @FXML private TableColumn<TacGia, String> maTacGiaColumn, hoTacGiaColumn, tenTacGiaColumn, queQuanTacGiaColumn;
    @FXML private TableColumn<TacGia, Integer> namSinhTacGiaColumn;
    @FXML private TextField textFieldTimKiem, txt_MaTacGia, txt_HoTacGia, txt_TenTacGia, txt_NamSinhTacGia, txt_QueQuanTacGia;
    @FXML private Button btnAddTacGia, timKiem, openInforContainer, clossInforContainer;
    @FXML private HBox inforButtonContainer;
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnTheLoai, btnNhaXuatBan;

    private Button btnDeleteTacGia = new Button("Xóa");
    private Button btnUpdateTacGia = new Button("Cập nhật");
    private ObservableList<TacGia> data;
    private static List<TacGia> tacGiaList = new ArrayList<>();
    private LeftMenuController leftMenuController = new LeftMenuController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnTheLoai, btnNhaXuatBan);
        inforContainer.setVisible(false);

        maTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("matg"));
        hoTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("hotg"));
        tenTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("tentg"));
        queQuanTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("quequan"));
        namSinhTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("namsinh"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfTacGia();
            } else {
                System.out.println("No item selected!");
            }
        });

        refreshTacGiaList(); // Làm mới danh sách ban đầu

        data = FXCollections.observableArrayList(tacGiaList);
        tableView.setItems(data);

        btnDeleteTacGia.setOnAction(event -> deleteTacGia());
        btnUpdateTacGia.setOnAction(event -> updateTacGia());
        btnAddTacGia.setOnAction(event -> addTacGia());
    }

    public List<TacGia> getListTacGia() {
        if (tacGiaList.isEmpty()) {
            CallApi callApi = new CallApi();
            String json;
            try {
                json = callApi.callGetApi("http://localhost:8080/TacGia/getAllTacGia");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            tacGiaList = convertJsonToListTacGia(json);
        }
        System.out.println("List tac gia: " + tacGiaList);
        return tacGiaList;
    }

    private void refreshTacGiaList() {
        CallApi callApi = new CallApi();
        String json;
        try {
            json = callApi.callGetApi("http://localhost:8080/TacGia/getAllTacGia");
            tacGiaList = convertJsonToListTacGia(json);
        } catch (IOException e) {
            System.err.println("Error refreshing tacGiaList: " + e.getMessage());
            tacGiaList = new ArrayList<>();
        }
    }

    public List<TacGia> convertJsonToListTacGia(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<TacGia> tacGiaList = new ArrayList<>();
        try {
            tacGiaList = objectMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return tacGiaList;
    }

    public String convertTacGiaToJson(TacGia tacGia) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(tacGia);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenerChangeValuesOfTacGia() {
        List<TextField> fields = Arrays.asList(txt_MaTacGia, txt_HoTacGia, txt_TenTacGia, txt_NamSinhTacGia, txt_QueQuanTacGia);
        fields.forEach(f -> {
            if (f != null) {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforButtonContainer.getChildren().indexOf(btnDeleteTacGia);
                    if (index >= 0) {
                        inforButtonContainer.getChildren().set(index, btnUpdateTacGia);
                    } else {
                        System.err.println("btnDeleteTacGia không tồn tại trong inforButtonContainer!");
                    }
                });
            }
        });
    }

    public void deleteTacGia() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            TacGia tacGia = data.get(selectedIndex);
            System.out.println("Tac Gia selected: " + tacGia.getMatg());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/TacGia/Delete", "maTacGia=", tacGia.getMatg());
            if (result.contains("Success")) {
                data.remove(selectedIndex);
                tacGiaList.remove(selectedIndex);
                tableView.getSelectionModel().clearSelection();
                showMessage("Success", "Xóa thành công", "Tác giả đã được xóa!");
            } else {
                showMessage("Error", "Xóa thất bại", "Không thể xóa tác giả: " + result);
            }
        } else {
            showMessage("Error", "Không có lựa chọn", "Vui lòng chọn một tác giả để xóa!");
        }
    }

    public void updateTacGia() {
        List<TextField> textFields = Arrays.asList(txt_MaTacGia, txt_HoTacGia, txt_TenTacGia, txt_QueQuanTacGia, txt_NamSinhTacGia);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        int namSinh;
        try {
            namSinh = Integer.parseInt(txt_NamSinhTacGia.getText());
        } catch (NumberFormatException e) {
            showMessage("Error", "Năm sinh không hợp lệ", "Vui lòng nhập một số hợp lệ cho năm sinh!");
            return;
        }

        TacGia tacGia = new TacGia();
        tacGia.setMatg(txt_MaTacGia.getText());
        tacGia.setHotg(txt_HoTacGia.getText());
        tacGia.setTentg(txt_TenTacGia.getText());
        tacGia.setQuequan(txt_QueQuanTacGia.getText());
        tacGia.setNamsinh(namSinh);

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/TacGia/Update", convertTacGiaToJson(tacGia));
        System.out.println("Update response: " + result);
        if (result.contains("success")) {
            refreshTacGiaList(); // Làm mới danh sách từ API
            data.setAll(tacGiaList);
            tableView.refresh();
            showMessage("Success", "Sửa tác giả thành công", "Tác giả đã được cập nhật!");
            closeInforContainer();
        } else {
            showMessage("Error", "Sửa tác giả thất bại", "Không thể cập nhật tác giả: " + result);
        }
    }

    public void openInforContainer() {
        txt_MaTacGia.setText("TG" + (tacGiaList.size() + 1));
        txt_HoTacGia.setText("");
        txt_TenTacGia.setText("");
        txt_QueQuanTacGia.setText("");
        txt_NamSinhTacGia.setText("");
        inforContainer.setVisible(true);
    }

    public void closeInforContainer() {
        int index = inforButtonContainer.getChildren().indexOf(btnDeleteTacGia);
        if (index >= 0) {
            inforButtonContainer.getChildren().set(index, btnAddTacGia);
        }
        inforContainer.setVisible(false);
    }

    public void addTacGia() {
        List<TextField> textFields = Arrays.asList(txt_MaTacGia, txt_HoTacGia, txt_TenTacGia, txt_NamSinhTacGia, txt_QueQuanTacGia);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        int namSinh;
        try {
            namSinh = Integer.parseInt(txt_NamSinhTacGia.getText());
        } catch (NumberFormatException e) {
            showMessage("Error", "Năm sinh không hợp lệ", "Vui lòng nhập một số hợp lệ cho năm sinh!");
            return;
        }

        TacGia tacGia = new TacGia();
        tacGia.setMatg(txt_MaTacGia.getText());
        tacGia.setHotg(txt_HoTacGia.getText());
        tacGia.setTentg(txt_TenTacGia.getText());
        tacGia.setQuequan(txt_QueQuanTacGia.getText());
        tacGia.setNamsinh(namSinh);

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/TacGia/Add", convertTacGiaToJson(tacGia));
        if (result.contains("Success")) {
            refreshTacGiaList(); // Làm mới danh sách từ API
            tacGiaList.add(tacGia);
            data.add(tacGia);
            tableView.refresh();
            showMessage("Success", "Thêm tác giả thành công", "Tác giả đã được thêm!");
            closeInforContainer();
        } else {
            showMessage("Error", "Thêm tác giả thất bại", "Không thể thêm tác giả: " + result);
        }
    }

    public void showSelectedItem(TacGia tacGia) {
        openInforContainer();
        txt_MaTacGia.setText(tacGia.getMatg());
        txt_HoTacGia.setText(tacGia.getHotg());
        txt_TenTacGia.setText(tacGia.getTentg());
        txt_QueQuanTacGia.setText(tacGia.getQuequan());
        txt_NamSinhTacGia.setText(tacGia.getNamsinh().toString());

        int index = inforButtonContainer.getChildren().indexOf(btnAddTacGia);
        if (index >= 0) {
            inforButtonContainer.getChildren().set(index, btnDeleteTacGia);
        } else {
            index = inforButtonContainer.getChildren().indexOf(btnUpdateTacGia);
            if (index >= 0) {
                inforButtonContainer.getChildren().set(index, btnDeleteTacGia);
            } else {
                System.err.println("btnAddTacGia or btnUpdateTacGia not found in inforButtonContainer!");
            }
        }
    }

    public void timKiem() {
        String find = textFieldTimKiem.getText();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/TacGia/timKiem", "find=", find);
        data = FXCollections.observableArrayList(convertJsonToListTacGia(json));
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