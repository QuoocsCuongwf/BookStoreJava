package com.example.demo.GuiController;

import com.example.demo.model.TheLoai;
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
public class TheLoaiController implements Initializable {
    @FXML private Pane theLoaiPane;
    @FXML private Pane inforContainer;
    @FXML private TableView<TheLoai> tableView;
    @FXML private TableColumn<TheLoai, String> maTheLoaiColumn;
    @FXML private TableColumn<TheLoai, String> tenTheLoaiColumn;
    @FXML private TextField textFieldMaTheLoai, textFieldTenTheLoai, textFieldTimKiem;
    @FXML private HBox inforFormButtonContainer;
    @FXML private Button btnAddTheLoai, btnThemTheLoai, btnThoatFormTheLoai;

    private final Button btnDeleteTheLoai = new Button("Xóa");
    private final Button btnUpdateTheLoai = new Button("Cập nhật");

    private ObservableList<TheLoai> data;
    private List<TheLoai> listTheLoai = new ArrayList<>();
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

        maTheLoaiColumn.setCellValueFactory(new PropertyValueFactory<>("maTheLoai"));
        tenTheLoaiColumn.setCellValueFactory(new PropertyValueFactory<>("tenTheLoai"));

        btnDeleteTheLoai.setId("delete-button");
        btnUpdateTheLoai.setId("update-button");

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                showSelectedItem(newVal);
                listenerChangeValuesTheLoai();
            } else {
                System.out.println("No item selected");
            }
        });

        CallApi callApi = new CallApi();
        String json;
        try {
            json = callApi.callGetApi("http://localhost:8080/theLoai/getAllTheLoai");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listTheLoai = convertJsonToList(json);
        System.out.println(listTheLoai);
        data = FXCollections.observableArrayList(listTheLoai);
        tableView.setItems(data);

        btnDeleteTheLoai.setOnAction(event -> deleteTheLoai());
        btnUpdateTheLoai.setOnAction(event -> updateTheLoai());
        btnAddTheLoai.setOnAction(event -> addTheLoai());
    }

    public List<TheLoai> convertJsonToList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<TheLoai> theLoaiList = new ArrayList<>();
        try {
            theLoaiList = mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return theLoaiList;
    }

    public String convertTheLoaiToJson(TheLoai theLoai) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json;
        try {
            json = mapper.writeValueAsString(theLoai);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void listenerChangeValuesTheLoai() {
        List<TextField> fields = Arrays.asList(textFieldMaTheLoai, textFieldTenTheLoai);
        fields.forEach(f -> {
            if (f == null) {
                System.err.println("A TextField is not injected (null)!");
            } else {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " changed: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteTheLoai);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateTheLoai);
                    } else {
                        System.err.println("btnDeleteTheLoai not found in inforFormButtonContainer!");
                    }
                });
            }
        });
    }

    public void deleteTheLoai() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            TheLoai theLoai = data.get(selectedIndex);
            System.out.println("The loai selected: " + theLoai.getMaTheLoai());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/theLoai/Delete", "maTheLoai=", theLoai.getMaTheLoai());
            if (result.contains("Success")) {
                data.remove(selectedIndex);
                tableView.getSelectionModel().clearSelection();
            } else {
                showMessage("Error", "Delete Failed", "Xóa thể loại thất bại. Vui lòng thử lại!");
            }
        } else {
            showMessage("Error", "No Selection", "Vui lòng chọn một thể loại để xóa!");
        }
    }

    public void updateTheLoai() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= data.size()) {
            showMessage("Error", "No Selection", "Vui lòng chọn một thể loại để cập nhật!");
            return;
        }

        TheLoai theLoai = data.get(selectedIndex);

        List<TextField> textFields = Arrays.asList(textFieldMaTheLoai, textFieldTenTheLoai);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Empty", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        theLoai.setTenTheLoai(textFieldTenTheLoai.getText());

        CallApi callApi = new CallApi();
        String resultApi = callApi.callPostRequestBody("http://localhost:8080/theLoai/Update", convertTheLoaiToJson(theLoai));

        if (resultApi.contains("Success")) {
            for (int i = 0; i < listTheLoai.size(); i++) {
                if (listTheLoai.get(i).getMaTheLoai().equals(theLoai.getMaTheLoai())) {
                    listTheLoai.set(i, theLoai);
                    break;
                }
            }
            data.set(selectedIndex, theLoai);
            tableView.refresh();
        } else {
            showMessage("Error", "Update Failed", "Cập nhật thể loại thất bại. Vui lòng thử lại!");
        }
    }

    public void addTheLoai() {
        TheLoai theLoai = new TheLoai();
        List<TextField> textFields = Arrays.asList(textFieldMaTheLoai, textFieldTenTheLoai);
        for (TextField tf : textFields) {
            if (tf.getText().isEmpty()) {
                showMessage("Error", "Text Field Empty", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }

        theLoai.setMaTheLoai(textFieldMaTheLoai.getText());
        theLoai.setTenTheLoai(textFieldTenTheLoai.getText());

        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/theLoai/Add", convertTheLoaiToJson(theLoai));
        if (result.contains("Success")) {
            listTheLoai.add(theLoai);
            data.add(theLoai);
            clossInforContainer();
        } else {
            showMessage("Error", "Add Failed", "Thêm thể loại thất bại: " + result);
        }
    }

    public void showSelectedItem(TheLoai theLoai) {
        openInforContainer();
        textFieldMaTheLoai.setEditable(true);
        textFieldMaTheLoai.setText(theLoai.getMaTheLoai());
        textFieldTenTheLoai.setText(theLoai.getTenTheLoai());

        int index = inforFormButtonContainer.getChildren().indexOf(btnAddTheLoai);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteTheLoai);
        } else {
            System.err.println("btnAddTheLoai not found in inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateTheLoai);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteTheLoai);
        }
    }

    @FXML
    public void openInforContainer() {
        textFieldMaTheLoai.setText("");
        textFieldTenTheLoai.setText("");
        inforContainer.setVisible(true);
    }

    @FXML
    public void clossInforContainer() {
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteTheLoai);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddTheLoai);
        }
        inforContainer.setVisible(false);
    }

    @FXML
    public void timKiem() {
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/theLoai/timKiem", "find=", textFieldTimKiem.getText());
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