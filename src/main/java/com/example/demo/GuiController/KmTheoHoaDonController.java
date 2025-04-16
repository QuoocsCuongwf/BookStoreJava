package com.example.demo.GuiController;

import com.example.demo.GuiController.CallApi;
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

import java.util.*;
@Component
@Controller

public class KmTheoHoaDonController implements Initializable {

    @FXML
    private Pane inforContainer;
    @FXML
    private Pane khuyenMaiTheoHoaDonPane;
    @FXML
    private TableView<KmTheoHoaDon> tableView;
    @FXML
    private TableColumn<KmTheoHoaDon, Integer> maChuongTrinhKMColumn;
    @FXML
    private TableColumn<KmTheoHoaDon, String> tongTienColumn;
    @FXML
    private TableColumn<KmTheoHoaDon, String> phanTramKMColumn;

    private  ObservableList<KmTheoHoaDon> data;
    List<KmTheoHoaDon> kmTheoHoaDonList=new ArrayList<>();

    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private TextField txt_maChuongTrinhKhuyenMai, txt_tongTien, txt_phanTramKhuyenMai;
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


    private Button btnDeleteKmTheoHoaDon = new Button("    Xóa    ");
    private Button btnUpdateKmTheoHoaDon = new Button("Cập nhật");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inforContainer.setVisible(false);
        maChuongTrinhKMColumn.setCellValueFactory(new PropertyValueFactory<>("mactkm"));
        tongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        phanTramKMColumn.setCellValueFactory(new PropertyValueFactory<>("phantramkhuyenmai"));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfKmTheoHoaDon();
            } else {
                System.out.println("No item selected");
            }
        });

        CallApi callApi = new CallApi();
        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/KmTheoHoaDon/getAllKmTheoHoaDon");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        kmTheoHoaDonList = convertJsonToListKmTheoHoaDon(json);
        System.out.println(kmTheoHoaDonList);
        data = FXCollections.observableArrayList(kmTheoHoaDonList);
        tableView.setItems(data);
        btnDeleteKmTheoHoaDon.setOnAction(event -> btnDeleteKmTheoHoaDon());
    }

    public List<KmTheoHoaDon> convertJsonToListKmTheoHoaDon(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<KmTheoHoaDon> kmtheoHoaDonList = new ArrayList<>();
        System.out.println("json: " + json);
        try {
            kmtheoHoaDonList = objectMapper.readValue(json, new TypeReference<List<KmTheoHoaDon>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return kmtheoHoaDonList;

    }

    public String convertKmTheoHoaDonToJson(KmTheoHoaDon kmtheoHoaDon) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(kmtheoHoaDon);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenerChangeValuesOfKmTheoHoaDon() {
        List<TextField> fields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai, txt_tongTien, txt_phanTramKhuyenMai
                );
        fields.forEach(f -> {
            if (f != null) {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKmTheoHoaDon);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateKmTheoHoaDon);
                    } else {
                        System.err.println("btnDeleteKmTheoHoaDon không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }
    public void btnDeleteKmTheoHoaDon() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            KmTheoHoaDon kmTheoHoaDon = data.get(selectedIndex);
            System.out.println("Khuyen Mai Theo Hoa Don selected " + kmTheoHoaDon.getMactkm());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/KmtheoHoaDon/Delete", "maKmTheoHoaDon=",kmTheoHoaDon.getMactkm());
            data.remove(selectedIndex);
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void openInforContainer() {
        txt_maChuongTrinhKhuyenMai.setText("");
        txt_tongTien.setText("");
        txt_phanTramKhuyenMai.setText("");
        inforContainer.setVisible(true);
    }
    public void clossInforContainer() {
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteKmTheoHoaDon);
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
        List<TextField> textFields = Arrays.asList(
                txt_maChuongTrinhKhuyenMai,txt_tongTien,txt_phanTramKhuyenMai
        );
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
        }
        KmTheoHoaDon kmTheoHoaDon = new KmTheoHoaDon();

        kmTheoHoaDon.setMactkm(txt_maChuongTrinhKhuyenMai.getText());

        Double phanTram = Double.parseDouble(txt_phanTramKhuyenMai.getText());
        Double tongTien = Double.parseDouble(txt_tongTien.getText());

        kmTheoHoaDon.setPhantramkhuyenmai(phanTram);
        kmTheoHoaDon.setTongtien(tongTien);

        kmTheoHoaDonList.add(kmTheoHoaDon);
        data.add(kmTheoHoaDon);
        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/KmTheoHoaDon/Add", convertKmTheoHoaDonToJson(kmTheoHoaDon));
        System.out.println(result);
    }
    public void showSelectedItem(KmTheoHoaDon kmTheoHoaDon) {
        openInforContainer();
        txt_maChuongTrinhKhuyenMai.setText(kmTheoHoaDon.getMactkm());
        txt_tongTien.setText(String.valueOf(kmTheoHoaDon.getTongtien()));
        txt_phanTramKhuyenMai.setText(String.valueOf(kmTheoHoaDon.getPhantramkhuyenmai()));



        int index = inforFormButtonContainer.getChildren().indexOf(btnAddKM);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteKmTheoHoaDon);
        } else {
            System.err.println("btnAddKhuyenMai không tồn tại trong inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateKmTheoHoaDon);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteKmTheoHoaDon);
        } else {
            System.err.println("btnDeleteKmTheoHoaDon không tồn tại trong inforFormButtonContainer !");

        }
    }

    public void timKiem() {
        String find = textFieldTimKiem.getText();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/KmTheoHoaDon/timKiem", "find=", find);
        data = FXCollections.observableArrayList(convertJsonToListKmTheoHoaDon(json));
        tableView.setItems(data);
    }

}
