package com.example.demo.GuiController;

import com.example.demo.model.NhaCungCap;
import com.example.demo.model.PhieuNhap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
public class NhaCungCapController implements Initializable {
    //duyen
//    @FXML private Button btnThongKe,btnNhanVien,btnSanPham,btnKhachHang,btnNCC,btnTacGia,btnHoaDon,btnTHD;
//          private LeftMenuController leftMenuController;
//    @FXML private AnchorPane anchorPaneMenu;
    // duyen

    @FXML private Pane inforContainer;
    @FXML private TableView<NhaCungCap> tableView;
    @FXML private TableColumn<NhaCungCap, String> maNCCColumn;
    @FXML private TableColumn<NhaCungCap, String> tenNCCColumn;
    @FXML private TableColumn<NhaCungCap, String> diaChiColumn;
    @FXML private TableColumn<NhaCungCap, String> sdtColumn;
    @FXML private TableColumn<NhaCungCap, String> emailColumn;
    @FXML private TextField textFieldMaNCC, textFieldTenNCC, textFieldDiaChi, textFieldSDT, textFieldEmail, textFieldTimKiem;
    @FXML private HBox inforFormButtonContainer;
    @FXML private Button btnAddNhaCungCap;
    @FXML private Button btnNhanVien;
    private Button btnDeleteNhaCungCap = new Button("Xóa");
    private Button btnUpdateNhaCungCap = new Button("Cập nhật");

    private ObservableList<NhaCungCap> data;
    private List<NhaCungCap> listNhaCungCap = new ArrayList<>();
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap;
    LeftMenuController leftMenuController=new LeftMenuController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap);
        inforContainer.setVisible(false);
        maNCCColumn.setCellValueFactory(new PropertyValueFactory<>("maNhaCungCap"));
        tenNCCColumn.setCellValueFactory(new PropertyValueFactory<>("tenNhaCungCap"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        btnDeleteNhaCungCap.setId("delete-button");
        btnUpdateNhaCungCap.setId("update-button");

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) {
                showSelectedItem(newVal);
                listenerChangeValuesNhaCungCap();
            } else {
                System.out.println("No item Selected");
            }
        });

        CallApi callApi = new CallApi();
        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/nhaCungCap/getAllNhaCungCap");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listNhaCungCap = convertJsonToList(json);
        System.out.println(listNhaCungCap);
        data = FXCollections.observableArrayList(listNhaCungCap);
        tableView.setItems(data);

        btnDeleteNhaCungCap.setOnAction(event -> deleteNhaCungCap());
        btnUpdateNhaCungCap.setOnAction(event -> updateNhaCungCap());

    }
    public List<NhaCungCap> getListNhaCungCap() {
        CallApi callApi = new CallApi();
        String json = null;
        List<NhaCungCap> listNhaCungCap = new ArrayList<>();
        try {
            json = callApi.callGetApi("http://localhost:8080/nhaCungCap/getAllNhaCungCap");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listNhaCungCap = convertJsonToList(json);
        return listNhaCungCap;
    }
    public List<NhaCungCap> convertJsonToList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<NhaCungCap> nhaCungCapList = new ArrayList<NhaCungCap>();
        try {
            nhaCungCapList = mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return nhaCungCapList;
    }

    public String convertNhaCungCapToJson(NhaCungCap nhaCungCap) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = mapper.writeValueAsString(nhaCungCap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void listenerChangeValuesNhaCungCap() {
        List<TextField> fields = Arrays.asList(textFieldMaNCC, textFieldTenNCC, textFieldSDT, textFieldDiaChi, textFieldEmail);
        fields.forEach(f -> {
            if (f == null) {
                System.err.println("Một TextField chưa được inject (null)!");
            } else {
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteNhaCungCap);
                    if (index >= 0) {
                        inforFormButtonContainer.getChildren().set(index, btnUpdateNhaCungCap);
                    } else {
                        System.err.println("btnDeleteNhaCungCap không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }

    public void deleteNhaCungCap() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            NhaCungCap nhanCungCap = data.get(selectedIndex);
            System.out.println("Nhan cung cap selected "+nhanCungCap.getMaNhaCungCap());
            CallApi callApi=new CallApi();
            String result=callApi.callPostRequestParam("http://localhost:8080/nhaCungCap/Delete","maNhaCungCap=",nhanCungCap.getMaNhaCungCap());
            data.remove(selectedIndex); // Optional: remove from ObservableList to update the table
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }

    }

    public void updateNhaCungCap() {
        // Lấy chỉ số của nhà cung cấp được chọn
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= data.size()) {
            showMessage("Error", "No Selection", "Vui lòng chọn một nhà cung cấp để cập nhật!");
            return;
        }

        // Lấy nhà cung cấp hiện tại từ data
        NhaCungCap nhaCungCap = data.get(selectedIndex);

        // Kiểm tra các TextField
        List<TextField> textFields = Arrays.asList(textFieldMaNCC, textFieldTenNCC, textFieldSDT, textFieldDiaChi, textFieldEmail);
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        }

        // Cập nhật thông tin nhà cung cấp
        nhaCungCap.setTenNhaCungCap(textFieldTenNCC.getText());
        nhaCungCap.setSdt(textFieldSDT.getText());
        nhaCungCap.setDiaChi(textFieldDiaChi.getText());
        nhaCungCap.setEmail(textFieldEmail.getText());
        // Lưu ý: maNhaCungCap không được thay đổi vì nó là khóa chính và TextField bị khóa (setEditable(false))

        // Gọi API để cập nhật
        CallApi callApi = new CallApi();
        String resultApi = callApi.callPostRequestBody("http://localhost:8080/nhaCungCap/Update", convertNhaCungCapToJson(nhaCungCap));

        if (resultApi.contains("Success")) {
            // Cập nhật danh sách listNhaCungCap (nếu cần)
            for (int i = 0; i < listNhaCungCap.size(); i++) {
                if (listNhaCungCap.get(i).getMaNhaCungCap().equals(nhaCungCap.getMaNhaCungCap())) {
                    listNhaCungCap.set(i, nhaCungCap);
                    break;
                }
            }

            // Cập nhật TableView
            data.set(selectedIndex, nhaCungCap); // Thay thế đối tượng cũ bằng đối tượng đã cập nhật
            tableView.refresh(); // Làm mới giao diện để hiển thị dữ liệu mới
        } else {
            showMessage("Error", "Update Failed", "Cập nhật nhà cung cấp thất bại. Vui lòng thử lại!");
        }
    }

    public void addNhaCungCap() {
        NhaCungCap nhaCungCap = new NhaCungCap();
        List<TextField> textFields=Arrays.asList(textFieldMaNCC, textFieldTenNCC, textFieldSDT, textFieldDiaChi, textFieldEmail);
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        nhaCungCap.setMaNhaCungCap(textFieldMaNCC.getText());
        nhaCungCap.setTenNhaCungCap(textFieldTenNCC.getText());
        nhaCungCap.setSdt(textFieldSDT.getText());
        nhaCungCap.setDiaChi(textFieldDiaChi.getText());
        nhaCungCap.setEmail(textFieldEmail.getText());
        CallApi callApi=new CallApi();
        String result=callApi.callPostRequestBody("http://localhost:8080/nhaCungCap/Add",convertNhaCungCapToJson(nhaCungCap));
        System.out.println(result);
        if (result.contains("Success")){
            listNhaCungCap.add(nhaCungCap);
            data.add(nhaCungCap);
        }
    }

    public void showSelectedItem(NhaCungCap nhaCungCap) {
        openInforContainer();
        textFieldMaNCC.setEditable(true);
        textFieldMaNCC.setText(nhaCungCap.getMaNhaCungCap());
        textFieldTenNCC.setText(nhaCungCap.getTenNhaCungCap());
        textFieldSDT.setText(nhaCungCap.getSdt());
        textFieldDiaChi.setText(nhaCungCap.getDiaChi());
        textFieldEmail.setText(nhaCungCap.getEmail());

        int index = inforFormButtonContainer.getChildren().indexOf(btnAddNhaCungCap);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteNhaCungCap);
        } else {
            System.err.println(" error sbtnAddNhaCungCap không tồn tại trong inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateNhaCungCap);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteNhaCungCap);
        } else {
            System.err.println("btnDeleteNhaCungCap không tồn tại trong inforFormButtonContainer!");
        }
    }

    public void openInforContainer() {
        textFieldMaNCC.setText("");
        textFieldTenNCC.setText("");
        textFieldSDT.setText("");
        textFieldDiaChi.setText("");
        textFieldEmail.setText("");
        inforContainer.setVisible(true);
    }

    public void clossInforContainer() { int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteNhaCungCap);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddNhaCungCap);
        }
        inforContainer.setVisible(false);
    }

    public void timKiem() {
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/nhaCungCap/timKiem", "find=", textFieldTimKiem.getText());
        data = FXCollections.observableArrayList(convertJsonToList(json));
        tableView.setItems(data);
    }

    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); alert.setHeaderText(header); alert.setContentText(content);
        alert.showAndWait();
    }
    //duyen
    public void addLeftMenuListInClass(){
//        leftMenuController.addButtonStorageList(btnNhanVien,"/NhanVien.fxml");
//        leftMenuController.addButtonStorageList(btnSanPham,"/SanPham.fxml");
//        leftMenuController.addButtonStorageList(btnKhachHang,"/KhachHang.fxml");
//        leftMenuController.addButtonStorageList(btnNCC,"/NhaCungCap.fxml");
//        leftMenuController.addButtonStorageList(btnTacGia,"/TacGia.fxml");
    }
//    public void callMenu(ActionEvent actionEvent) {
//        Button buttonClicked = (Button) actionEvent.getSource();
//        leftMenuController.linkButtonStorageToFxml(buttonClicked);
//    }
}
