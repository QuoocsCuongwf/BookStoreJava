package com.example.demo.GuiController;

import com.example.demo.BUS.services.Excel;
import com.example.demo.BUS.services.SanPhamServices;
import com.example.demo.model.NhaXuatBan;
import com.example.demo.model.NhanVien;
import com.example.demo.model.SanPham;
import com.example.demo.model.TacGia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Component
public class SanPhamController implements Initializable {
    @FXML
    private TableView<SanPham> tableView; // Bảng chính

    @FXML
    private TableColumn<SanPham, String> anhBia; // Cột "Ảnh bìa"

    @FXML
    private TableColumn<SanPham, String> maSachColumn; // Cột "Mã sách"

    @FXML
    private TableColumn<SanPham, String> tenSachColumn; // Cột "Tên sách"

    @FXML
    private TableColumn<SanPham, Integer> soLuongColumn; // Cột "Số lượng"

    @FXML
    private TableColumn<SanPham, Double> donGiaColumn; // Cột "Đơn giá"

    @FXML
    private TableColumn<SanPham, String> tacGiaColumn; // Cột "Tác giả"
    @FXML Button btnSave;
    @FXML
    private TextField textFieldMaSach, textFieldTenSach, textFieldDonGia, textFieldMaTG, textFieldMaNXB, textFieldSoTrang, textFieldMaTL;
    private ObservableList<SanPham> data;
    @FXML
    private TextField textFieldTimKiem,giaMin,giaMax;
    @FXML
    private ImageView iconfolder;
    @FXML
    private Button btnChonFile;
    @FXML
    private Button btnThoatFormThemSach;
    @FXML
    private Button btnThemSach;
    @FXML
    private Button btnAddBook;
    @FXML
    private Pane inforContainer;
    @FXML
    private HBox inforFormButtonContainer;
    @FXML
    private Label labelAnhBia;
    @FXML
    private ImageView imgAnhBia;
    private static List<SanPham> listSanPham=new ArrayList<SanPham>();
    private String pathImage = "";
    List<SanPham> danhSachExcel=new ArrayList<>();
    SanPhamServices sanPhamServices=new SanPhamServices();
    private Button btnDeleteBook=new Button("    Xóa    ");
    private Button btnUpdateBook=new Button("Cập nhật");
    private LeftMenuController leftMenuController=new LeftMenuController();
    @FXML private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien, btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai, btnPhieuNhap,btnTaoPhieuNhap,btnNhaXuatBan,btnTheLoai;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD,  btnKhuyenMai,
                btnTheLoai, btnNhaXuatBan, btnPhieuNhap,
                btnTaoPhieuNhap);
        btnSave.setDisable(!danhSachExcel.isEmpty());
        Map<String, TacGia> tacGiaMap = new HashMap<>();
        TacGiaController tacGiaController=new TacGiaController();
        List<TacGia> listTacGia = tacGiaController.getListTacGia(); // gọi 1 API hoặc DAO lấy tất cả
        for (TacGia tg : listTacGia) {
            tacGiaMap.put(tg.getMatg(), tg); // ánh xạ MATG → TacGia
        }

        anhBia.setCellFactory(column -> createImgCellFactory());
        tacGiaColumn.setCellFactory(column -> new TableCell<SanPham, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    TacGia tg = tacGiaMap.get(item);// "item" là mã tác giả (MATG)
                    if (tg != null) {
                        setText(tg.getHotg() + " " + tg.getTentg());
                    } else {
                        setText("Không rõ");
                    }
                }
            }
        });
        maSachColumn.setCellValueFactory(new PropertyValueFactory<>("masp"));
        tenSachColumn.setCellValueFactory(new PropertyValueFactory<>("tensp"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        tacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("matg"));

        anhBia.setCellValueFactory(new PropertyValueFactory<>("anhbia"));
        btnDeleteBook.setOnAction(event->deleteSanPham());
        btnUpdateBook.setOnAction(event->updateSanPham());
        data=FXCollections.observableArrayList(listSanPham);
        tableView.setItems(data);
        inforContainer.setVisible(false);

        btnThemSach.setOnAction(event -> inforContainer.setVisible(true));
        Image image = new Image("file:/D:\\java\\BookStoreJava\\src\\main\\resources\\asset\\img\\folder.png");
        if (image.isError()) {
            System.out.println("Error loading image: " + image.getException());
        }

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfSanPham();
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });


        CallApi callApi=new CallApi();
        String json;
        try {
            json=callApi.callGetApi("http://localhost:8080/sanPham/getAllSanPham");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        data = FXCollections.observableArrayList(convertJsonToSanPham(json));
        tableView.setItems(data);



        System.out.println(image.getWidth());
        iconfolder.setImage(image);
        System.out.println(iconfolder.getImage().getWidth());

    }
    public void listenerChangeValuesOfSanPham(){
        List<TextField> fieldsSP = Arrays.asList(textFieldMaSach,textFieldTenSach,textFieldDonGia,textFieldMaTG, textFieldMaNXB, textFieldSoTrang, textFieldMaTL);
        fieldsSP.forEach(field -> {
            if (field.getText().equals("")) {
                showMessage("LítenerChangeValuesOfSanPham","FAIL","TEXTFIELDS không được bỏ trống");
                return;
            }else {
                field.textProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(field.getId()+" thay đổi "+newValue);
                    int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteBook);
                    if (index >= 0){
                        inforFormButtonContainer.getChildren().set(index,btnUpdateBook);
                    }else {
                        System.out.println("No button btnDelete selected");
                    }
                });
            }
        });

    }
    public List<SanPham> convertJsonToSanPham(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<SanPham> sanPhamList=new ArrayList<>();
        try {
            listSanPham=mapper.readValue(json, new TypeReference<List<SanPham>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return listSanPham;
    }
    public void chonFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll();
        File file = fileChooser.showOpenDialog(iconfolder.getScene().getWindow());
        if (file != null) {
            System.out.println("File được chọn: " + file.getAbsolutePath());
            pathImage=file.getAbsolutePath().replace("\\", "/");
            System.out.println(pathImage);
        } else {
            System.out.println("Không có file nào được chọn.");
        }
    }

    public void clossInforContainer() {
        int index = inforFormButtonContainer.getChildren().indexOf(btnDeleteBook);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnAddBook);
        }
        inforContainer.setVisible(false);
    }
    public void openInforContainer() {
        textFieldMaSach.setText("");
        textFieldTenSach.setText("");
        textFieldDonGia.setText("");
        textFieldMaNXB.setText("");
        textFieldMaTG.setText("");
        textFieldSoTrang.setText("");
        textFieldMaTL.setText("");
        inforContainer.setVisible(true);
        pathImage ="";
    }


     public TableCell<SanPham,String> createImgCellFactory(){
        return new TableCell<SanPham,String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image("file:" + imagePath, 50, 50, true, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        };
     }
    public void themSach() {
//        //test
//        System.out.println("++++++++++++++++++++++++++");
//        System.err.println(" HÀM THÊM SÁCH ĐÃ ĐƯỢC GỌI"+pathImage);
//        System.out.println("++++++++++++++++++++++++++");
//        //test
        List<TextField> listTextField = Arrays.asList(textFieldMaSach,textFieldTenSach,textFieldDonGia,textFieldMaTG, textFieldMaNXB, textFieldSoTrang, textFieldMaTL);
        for (TextField textField : listTextField) {
            if (textField.getText().trim().isEmpty() || textField.getText() == null) {
                showMessage("THÊM SẢN PHẨM ","FAIL","vui lòng nhập đủ thông tin");
                return;
            }
        }
        if (pathImage.isEmpty() || pathImage == null) {
            showMessage("THÊM SẢN PHẨM ","FAIL","vui lòng chọn ảnh bìa ");
            return;
        }
        // kiểm tra dữ liệu giá tiền
        try{
            Double.parseDouble(textFieldDonGia.getText());
        }catch (NumberFormatException e){
            showMessage("THÊM SẢN PHẨM","FAIL","giá tiền nhập vào không hợp lệ");
            return;
        }
        //
        try{
            Integer.parseInt(textFieldSoTrang.getText());
        }catch (NumberFormatException e){
            showMessage("THÊM SẢN PHẨM","FAIL","số trang nhập vào không hợp lệ");
            return;
        }

        try{
            SanPham sanPham = new SanPham();
            sanPham.setMasp(textFieldMaSach.getText());
            sanPham.setTensp(textFieldTenSach.getText());
            sanPham.setSl(0);
            sanPham.setMatl(textFieldMaTL.getText());
            sanPham.setMatg(textFieldMaTG.getText());
            sanPham.setManxb(textFieldMaNXB.getText());
            sanPham.setNamxb(10);
            sanPham.setDongia(Integer.parseInt(textFieldDonGia.getText()));
            sanPham.setSotrang(Integer.parseInt(textFieldSoTrang.getText()));
            sanPham.setAnhbia(pathImage);

            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestBody("http://localhost:8080/sanPham/insert",convertSanPhamToJson(sanPham));
            System.out.println("DUYEN:"+result);
                showMessage("InsertSanPham","SUCCESS","Thêm sản phẩm "+sanPham.getMasp()+" thành công !");
                listSanPham.add(sanPham);
                data.add(sanPham);
                pathImage = "";
            tableView.setItems(data);
        }catch (JsonProcessingException e){
            showMessage("LỖI HÀM","hàm insertSanPham","Lỗi chuyển đổi dữ liệu sang JSON");
        }catch (IOException e){
            showMessage("LỖI HÀM","hàm insertSanPham","không thể kết nối đến server");
        }catch (Exception e){
            showMessage("LỖI HÀM","hàm insertSanPham","Lỗi không xác định");
            e.getMessage();
            e.printStackTrace();
        };
    }
    public void updateSanPham(){
        //
        List<TextField> listTextField = Arrays.asList(textFieldMaSach,textFieldTenSach,textFieldDonGia,textFieldMaTG, textFieldMaNXB, textFieldSoTrang, textFieldMaTL);
        for (TextField textField : listTextField) {
            if (textField.getText().trim().isEmpty() || textField.getText() == null) {
                showMessage("CẬP NHẬT SẢN PHẨM ","FAIL","vui lòng nhập đủ thông tin");
                return;
            }
        }
        if (pathImage.isEmpty() || pathImage == null) {
            showMessage("CẬP NHẬT SẢN PHẨM ","FAIL","vui lòng chọn ảnh bìa ");
            return;
        }
        // kiểm tra dữ liệu giá tiền
        try{
            Double.parseDouble(textFieldDonGia.getText());
        }catch (NumberFormatException e){
            showMessage("CẬP NHẬT SẢN PHẨM","FAIL","giá tiền nhập vào không hợp lệ");
            return;
        }
        //
        try{
            Integer.parseInt(textFieldSoTrang.getText());
        }catch (NumberFormatException e){
            showMessage("CẬP NHẬT SẢN PHẨM","FAIL","số trang nhập vào không hợp lệ");
            return;
        }

        try{
            SanPham sanPham = new SanPham();
            sanPham.setMasp(textFieldMaSach.getText());
            sanPham.setTensp(textFieldTenSach.getText());
            sanPham.setSl(0);
            sanPham.setMatl(textFieldMaTL.getText());
            sanPham.setMatg(textFieldMaTG.getText());
            sanPham.setManxb(textFieldMaNXB.getText());
            sanPham.setNamxb(10);
            sanPham.setDongia(Integer.parseInt(textFieldDonGia.getText()));
            sanPham.setSotrang(Integer.parseInt(textFieldSoTrang.getText()));
            sanPham.setAnhbia(pathImage);

            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestBody(" http://localhost:8080/sanPham/update",convertSanPhamToJson(sanPham));
            System.out.println("DUYEN HAHAHAHHAHAHAHA:"+result);
            for (int i = 0 ; i <listSanPham.size() ; i++){
                if (listSanPham.get(i).getMasp().equals(sanPham.getMasp())){
                    listSanPham.set(i, sanPham);
                    data =FXCollections.observableArrayList();
                    tableView.setItems(data);
                    break;
                }
            }
            showMessage("UpdateSanPham","SUCCESS","Thêm sản phẩm "+sanPham.getMasp()+" thành công !");


        }catch (JsonProcessingException e){
            showMessage("LỖI HÀM","hàm UpdateSanPham","Lỗi chuyển đổi dữ liệu sang JSON");
        }catch (IOException e){
            showMessage("LỖI HÀM","hàm UpdateSanPham","không thể kết nối đến server");
        }catch (Exception e){
            showMessage("LỖI HÀM","hàm UpdateSanPham","Lỗi không xác định");
            e.getMessage();
            e.printStackTrace();
        };
    }

    public String convertSanPhamToJson(SanPham sanPham) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(sanPham);
    }

    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // hoặc .show() nếu không cần chờ
    }
    public void showSelectedItem(SanPham sanPham) {
        inforContainer.setVisible(true);
        pathImage=sanPham.getAnhbia();
        textFieldMaSach.setText(sanPham.getMasp());
        textFieldMaTG.setText(sanPham.getMatg());
        textFieldTenSach.setText(sanPham.getTensp());
        textFieldMaTL.setText(sanPham.getMatl());
        textFieldMaNXB.setText(sanPham.getManxb());
        textFieldSoTrang.setText(sanPham.getSotrang().toString());
        textFieldDonGia.setText(sanPham.getDongia().toString());
        labelAnhBia.setText("");
        imgAnhBia.setImage(new Image("file:"+sanPham.getAnhbia()));
        imgAnhBia.setFitHeight(60);
        imgAnhBia.setFitWidth(40);
        int index = inforFormButtonContainer.getChildren().indexOf(btnAddBook);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteBook);
        } else {
            System.err.println(" error sbtnAddBook không tồn tại trong inforFormButtonContainer!");
        }
        index = inforFormButtonContainer.getChildren().indexOf(btnUpdateBook);
        if (index >= 0) {
            inforFormButtonContainer.getChildren().set(index, btnDeleteBook);
        } else {
            System.err.println("btnDeleteBook không tồn tại trong inforFormButtonContainer!");
        }
    }


   public void timKiem(){
        if(giaMin.getText()=="" && giaMax.getText()==""){
            CallApi callApi=new CallApi();
            String json=callApi.callPostRequestParam("http://localhost:8080/sanPham/search","find=",textFieldTimKiem.getText());
            data=FXCollections.observableArrayList(convertJsonToSanPham(json));
            tableView.setItems(data);
        }
        if(giaMin.getText()!="" && giaMax.getText()!=""){
            System.out.println("tim kiem nang cao");
            int giaMinInt=Integer.parseInt(giaMin.getText());
            int giaMaxInt=Integer.parseInt(giaMax.getText());
            SanPhamServices sanPhamServices=new SanPhamServices();
            data=FXCollections.observableArrayList(sanPhamServices.timKiemNangCao(textFieldTimKiem.getText(),giaMinInt,giaMaxInt));
            tableView.setItems(data);
        }

    }
   public void deleteSanPham(){
       int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
       if (selectedIndex >= 0 && selectedIndex < data.size()) {
           SanPham sanPham = data.get(selectedIndex);
           System.out.println("Nhan vien selected "+sanPham.getMasp());
           CallApi callApi=new CallApi();
           String result=callApi.callPostRequestParam("http://localhost:8080/sanPham/delete","maSanPham=",sanPham.getMasp());
           data.remove(selectedIndex);// Optional: remove from ObservableList to update the table
           listSanPham.remove(selectedIndex);
           tableView.getSelectionModel().clearSelection();
       } else {
           System.out.println("No valid selection!");
       }
   }
   public List<SanPham> getListSanPham(){
        if(listSanPham.isEmpty()){
            CallApi callApi=new CallApi();
            String json;
            try {
                json=callApi.callGetApi("http://localhost:8080/sanPham/getAllSanPham");
                System.out.println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            listSanPham.addAll(convertJsonToSanPham(json));
        }
        return listSanPham;
   }
    public void xuatExcel(){
        CallApi callApi=new CallApi();
        int result=callApi.callPostRequestBody("http://localhost:8080/sanPham/xuatExcel");
        if (result==200){
            showMessage("Success","Xuất Excel Thành công","Đã xuất thành công file Excel");
        }
    }
    @FXML
    private void handleImportExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                // Đọc dữ liệu từ file
                danhSachExcel = Excel.readSanPhamFromExcel(fis);

                // Tạo danh sách sản phẩm không trùng mã
                List<SanPham> sanPhamMoi = new ArrayList<>();
                Set<String> maDaTonTai = listSanPham.stream()
                        .map(SanPham::getMasp)
                        .collect(Collectors.toSet());

                for (SanPham sp : danhSachExcel) {
                    if (!maDaTonTai.contains(sp.getMasp())) {
                        sanPhamMoi.add(sp);
                        listSanPham.add(sp);
                        data.add(sp); // nếu data là ObservableList
                    } else {
                        System.out.println("San pham trung: " + sp.getMasp());
                    }
                }
                tableView.setItems(data);
                System.out.println("Đã thêm " + sanPhamMoi.size() + " sản phẩm mới từ file Excel.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveExcel(){
        sanPhamServices.insertListSanPham(danhSachExcel);
        danhSachExcel.clear();
    }

}

