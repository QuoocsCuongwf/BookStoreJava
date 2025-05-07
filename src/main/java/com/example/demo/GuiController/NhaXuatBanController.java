package com.example.demo.GuiController;

import com.example.demo.GuiController.LeftMenuController;
import com.example.demo.model.NhaXuatBan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class NhaXuatBanController implements Initializable {

    @FXML
    private Pane inforContainer;
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap;
    LeftMenuController leftMenuController=new LeftMenuController();
    @FXML
    private Pane nhanVienPane;
    @FXML
    private TableView<NhaXuatBan> tableView;
    @FXML
    private TableColumn<NhaXuatBan, Integer> maNhaXuatBanColumn;
    @FXML
    private TableColumn<NhaXuatBan, String> tenNhaXuatBanColumn;
    @FXML
    private TableColumn<NhaXuatBan, String> diaChiColumn;
    @FXML
    private TableColumn<NhaXuatBan, String> soDienThoaiColumn;
    @FXML

    private ObservableList<NhaXuatBan> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inforContainer.setVisible(false);

        maNhaXuatBanColumn.setCellValueFactory(new PropertyValueFactory<>("manxb"));
        tenNhaXuatBanColumn.setCellValueFactory(new PropertyValueFactory<>("tennxb"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        soDienThoaiColumn.setCellValueFactory(new PropertyValueFactory<>("sodienthoai"));

        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Item: " + newValue.getTennxb());


                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });
        String json = callApi("http://localhost:8080/nhaXuatBan/getAllNhaXuatBan");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<NhaXuatBan> nhaXBList = new ArrayList<NhaXuatBan>();
        System.out.println("json: " + json);
        try {
            nhaXBList = objectMapper.readValue(json, new TypeReference<List<NhaXuatBan>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nhaXBList);
        data = FXCollections.observableArrayList(nhaXBList);
        tableView.setItems(data);
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

    public void deleteNhaXB() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            NhaXuatBan nhaXuatBan = data.get(selectedIndex);
            data.remove(selectedIndex); // Optional: remove from ObservableList to update the table
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }

    public void openInforContainer(){
        inforContainer.setVisible(true);
    }
    public void clossInforContainer(){
        inforContainer.setVisible(false);
    }
    public void addNNhaXuatBan(){

    }

}
