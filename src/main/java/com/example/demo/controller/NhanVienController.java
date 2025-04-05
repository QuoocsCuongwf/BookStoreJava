package com.example.demo.controller;

import com.example.demo.model.NhanVien;
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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        String json = callApi("http://localhost:8080/nhanVien/getAllNhanVien");
        nhanVienList=convertJsonToListNhanVien(json);
        System.out.println(nhanVienList);
        data = FXCollections.observableArrayList(nhanVienList);
        tableView.setItems(data);
        btnDeleteNhanVien.setOnAction(event -> deleteNhanVien());
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

     public String callApi(String urlApi) {
        String values="";
        try {
            URL url = new URL(urlApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            values=response.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         return values;
     }

    public void deleteNhanVien() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            NhanVien nhanVien = data.get(selectedIndex);
            System.out.println("Nhan vien selected "+nhanVien.getManv());
            String result=callApiPost("http://localhost:8080/nhanVien/Delete","maNhanVien=",nhanVien.getManv());
            data.remove(selectedIndex); // Optional: remove from ObservableList to update the table
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }

    public void openInforContainer(){
        textFieldMaNhanVien.setText("");
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
    public void addNhanVien(){
        NhanVien nhanVien = new NhanVien();
        nhanVien.setManv(textFieldMaNhanVien.getText());
        nhanVien.setMail(textFieldThongTinLienLac.getText());
        nhanVien.setTennv(textFieldTenNhanVien.getText());
        nhanVien.setHonv(textFieldHoNhanVien.getText());
        nhanVien.setChucvu(textFieldChucVu.getText());
        nhanVien.setCccd(textFieldSoCCCD.getText());
        nhanVien.setNgayvaolam(datePickerNgayVaoLam.getValue());
        nhanVien.setLuong(Integer.parseInt(textFieldLuongNhanVien.getText()));
        nhanVienList.add(nhanVien);
        data.add(nhanVien);

    }

    public void showSelectedItem(NhanVien nhanVien) {
        openInforContainer();
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
        String json=callApiPost("http://localhost:8080/nhanVien/timKiem","find=",find);
        data=FXCollections.observableArrayList(convertJsonToListNhanVien(json));
        tableView.setItems(data);
    }

    public String callApiPost(String api, String key, String param) {
        String values = "";
        try {
            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // ❌ Đừng dùng ? ở đây
            String params =key + URLEncoder.encode(param, StandardCharsets.UTF_8); // encode là tốt nhất
            try (OutputStream os = conn.getOutputStream()) {
                os.write(params.getBytes(StandardCharsets.UTF_8));
            }

            int status = conn.getResponseCode();
            if (status >= 400) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.err.println("ERROR: " + line);
                    }
                }
                throw new RuntimeException("HTTP error: " + status);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            values = response.toString();
            System.out.println("Response: " + values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return values;
    }




}
