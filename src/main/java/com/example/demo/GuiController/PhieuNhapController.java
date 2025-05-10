package com.example.demo.GuiController;



import com.example.demo.BUS.services.PhieuNhapServices;
import com.example.demo.model.ChiTietPhieuNhap;
import com.example.demo.model.PhieuNhap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
public class PhieuNhapController implements Initializable {
    @FXML private Pane inforContainer;
    @FXML private Pane phieuNhapPane;
    @FXML private TableView<PhieuNhap> tableView;
    @FXML private TableView<ChiTietPhieuNhap> chiTietPhieuNhapTable;
    @FXML private TableColumn<PhieuNhap, String> maPhieuNhapColumn;
    @FXML private TableColumn<PhieuNhap, String> ngayNhapColumn;
    @FXML private TableColumn<PhieuNhap, String> maNhanVienColumn;
    @FXML private TableColumn<PhieuNhap, String> maNhaCungCapColumn;
    @FXML private TableColumn<PhieuNhap, String> tongTienColumn;
    @FXML private TableColumn<ChiTietPhieuNhap,String> maSanPhamColum;
    @FXML private TableColumn<ChiTietPhieuNhap,Integer> thanhTienColumn,soLuongColumn;
    private ObservableList<PhieuNhap> data = FXCollections.observableArrayList();
    private ObservableList<ChiTietPhieuNhap> ob_danhSachChiTiet = FXCollections.observableArrayList();
    static List<PhieuNhap> phieuNhapList=new ArrayList<>();

    @FXML private PhieuNhap phieuNhapDuocChon;
    @FXML private TextField textFieldTimKiem;
    @FXML private TextField txt_MaPhieuNhap,txt_MaNhanVien,txt_MaNhaCungCap,txt_TongTien,txt_MaSach;
    @FXML private DatePicker datePickerNgayNhap;
    @FXML private HBox inforButtonContainer;
    @FXML private Button btnAdd;
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap,btnTaoPhieuNhap;

    LeftMenuController leftMenuController=new LeftMenuController();

    //xong
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai,btnPhieuNhap);
        leftMenuController.newButtonClicked(btnTaoPhieuNhap);
        maPhieuNhapColumn.setCellValueFactory(new PropertyValueFactory<>("mapn"));
        ngayNhapColumn.setCellValueFactory(new PropertyValueFactory<>("ngaynhap"));
        maNhanVienColumn.setCellValueFactory(new PropertyValueFactory<>("manv"));
        maNhaCungCapColumn.setCellValueFactory(new PropertyValueFactory<>("mancc"));
        tongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongtien"));


        maSanPhamColum.setCellValueFactory(new PropertyValueFactory<>("masp"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
        CallApi callApi=new CallApi();
        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/phieuNhap/getAllPhieuNhap");
            phieuNhapList = convertJsonToListPhieuNhap(json);
        } catch (IOException e) {
            System.err.println("PHIEUNHAP_CONTROLLER_BUG : hàm init");
            throw new RuntimeException(e);
        }

        System.out.println(phieuNhapList);
        data = FXCollections.observableArrayList(phieuNhapList);
        tableView.setItems(data);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    phieuNhapDuocChon = newValue;
                    showSelectedItem(newValue);
                } catch (RuntimeException e) {
                    System.err.println("PHIEUNHAP_CONTROLLER_BUG : hàm init");
                    throw new RuntimeException(e);
                }
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });
        chiTietPhieuNhapTable.setItems(ob_danhSachChiTiet); // hiển thị trống từ đầu , khi được gọi ở showselected thì hiển thị danh sách ctpn
    }
    //xong convertJSON
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
    //xong showSelec
    public void showSelectedItem(PhieuNhap newValue) throws RuntimeException {
        CallApi callApi=new CallApi();
        String json = null;
        List<ChiTietPhieuNhap> chiTietPhieuNhapList=new ArrayList<>();
        json = callApi.callPostRequestParam("http://localhost:8080/ChiTietPhieuNhap/getByMaPhieuNhap","find=", newValue.getMapn());
        System.err.println("DUYEN SHOW json: " + json);
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           chiTietPhieuNhapList = objectMapper.readValue(json, new TypeReference<List<ChiTietPhieuNhap>>() {});
       }catch (JsonProcessingException e) {
           System.out.println("BUGGGGGG");
           throw new RuntimeException(e);
       }

        ob_danhSachChiTiet = FXCollections.observableArrayList(chiTietPhieuNhapList);
        chiTietPhieuNhapTable.setItems(ob_danhSachChiTiet);
    }
    //xong getPhieuNhapList
    public List<PhieuNhap> getPhieuNhapList(){
        if (phieuNhapList.isEmpty() || phieuNhapList == null) {
            CallApi callApi=new CallApi();
            try {
                phieuNhapList = convertJsonToListPhieuNhap(callApi.callGetApi("http://localhost:8080/phieuNhap/getAllPhieuNhap"));
            } catch (IOException e) {
                System.err.println("PN_Controller BUG: lỗi hàm getPhieuNhapList");
                throw new RuntimeException(e);
            }
        }
        return phieuNhapList;
    }
    //xong
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
    //xong
    public void deletePhieuNhap() {
        if ( phieuNhapDuocChon != null){
            CallApi callApi=new CallApi();
            String result = null;
            result = callApi.callPostRequestParam("http://localhost:8080/phieuNhap/Delete","maPhieuNhap=",phieuNhapDuocChon.getMapn());
            if (result.contains("success")){
                data.remove(phieuNhapDuocChon);
                tableView.setItems(data);
                    showMessage("PhieuNhapController","SUCCESS","XÓA THÀNH CÔNG");
            }else {
                showMessage("PhieuNhapController","FAIL","XÓA PHIẾU NHẬP THẤT BẠI");
            }
        }
    }
    //xong
    @FXML
    private void updatePhieuNhap(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TaoPhieuNhap.fxml"));
        Parent root = loader.load();
        ChiTietPhieuNhapController controller=loader.getController();
        controller.updatePhieuNhapInfor(phieuNhapDuocChon);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/asset/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu không cần chờ
    }

    public void timKiem(){
        String find=textFieldTimKiem.getText();
        CallApi callApi=new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/phieuNhap/Search","find=",find);
        phieuNhapList = convertJsonToListPhieuNhap(json);
        data = FXCollections.observableArrayList(phieuNhapList);
        tableView.setItems(data);
    }



}