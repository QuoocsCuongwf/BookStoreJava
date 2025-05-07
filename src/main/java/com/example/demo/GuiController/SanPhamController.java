package com.example.demo.GuiController;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Component
public class SanPhamController {
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

    @FXML
    private TextField textFieldMaSach, textFieldTenSach, textFieldDonGia, textFieldMaTG, textFieldMaNXB, textFieldSoTrang, textFieldMaTL;
    private ObservableList<SanPham> data;
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai,  btnTheLoai,  btnNhaXuatBan;
    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private ImageView iconfolder;
    @FXML
    private Button btnChonFile;
    @FXML
    private Button btnThoatFormThemSach;
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
    private List<SanPham> listSanPham=new ArrayList<SanPham>();
    private String pathImage = "";

    private Button btnDeleteBook=new Button("    Xóa    ");
    private Button btnUpdateBook=new Button("Cập nhật");
    private LeftMenuController leftMenuController=new LeftMenuController();
    @FXML
    public void initialize() {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai, btnTheLoai, btnNhaXuatBan);
        Map<String, TacGia> tacGiaMap = new HashMap<>();
        TacGiaController tacGiaController=new TacGiaController();
        List<TacGia> listTacGia = tacGiaController.getListTacGia(); // gọi 1 API hoặc DAO lấy tất cả
        System.out.println("Tac gia: "+listTacGia.size());
        for (TacGia tg : listTacGia) {
            System.out.println(tg.toString());
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
                    System.out.println("Item: " + item);
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
        data=FXCollections.observableArrayList(listSanPham);
        tableView.setItems(data);
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai);
        inforContainer.setVisible(false);

        btnAddBook.setOnAction(event -> inforContainer.setVisible(true));
        Image image = new Image("file:/D:\\java\\BookStoreJava\\src\\main\\resources\\asset\\img\\folder.png");
        if (image.isError()) {
            System.out.println("Error loading image: " + image.getException());
        }

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
//                listenerChangeValuesOfBook();
                // Thực hiện các hành động khác với dữ kiện được chọn
            } else {
                System.out.println("No item selected!");
            }
        });


        CallApi callApi=new CallApi();
        String json;
        try {
            json=callApi.callGetApi("http://localhost:8080/sanPham/getAllSanPham");
            System.out.println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        data = FXCollections.observableArrayList(convertJsonToSanPham(json));
        tableView.setItems(data);



        System.out.println(image.getWidth());
        iconfolder.setImage(image);
        System.out.println(iconfolder.getImage().getWidth());

    }

    public List<SanPham> convertJsonToSanPham(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<SanPham> sanPhamList=new ArrayList<>();
        try {
            listSanPham=mapper.readValue(json, new TypeReference<List<SanPham>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(listSanPham);
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
            pathImage=file.getAbsolutePath().replace("\\", "\\\\");
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
    }

    public void themSach() {

        SanPham sanPham = new SanPham();
        sanPham.setMasp(textFieldMaSach.getText());
        sanPham.setTensp(textFieldTenSach.getText());
        sanPham.setSl(0);
        sanPham.setDongia(Integer.parseInt(textFieldDonGia.getText()));
        NhaXuatBan nhaXuatBan=new NhaXuatBan();

        sanPham.setAnhbia(pathImage);
        sanPham.setMatg(textFieldMaTG.getText());
        if (sanPham!=null) listSanPham.add(sanPham);
        data=FXCollections.observableArrayList(listSanPham);
        tableView.setItems(data);
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
    public void insertSanPham() {
        SanPham sanPham = new SanPham();
        sanPham.setMasp(textFieldMaSach.getText());
        sanPham.setTensp(textFieldTenSach.getText());
        sanPham.setSl(0);
        sanPham.setDongia(Integer.parseInt(textFieldDonGia.getText()));
        sanPham.setAnhbia(pathImage);
        TacGia tacGia = new TacGia();
        CallApi callApi=new CallApi();
        String json=callApi.callPostRequestParam("http://localhost:8080/TacGia/timKiem","find=",textFieldMaTG.getText());
        TacGiaController tacGiaController=new TacGiaController();



    }
    public void showSelectedItem(SanPham sanPham) {
        inforContainer.setVisible(true);
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
        CallApi callApi=new CallApi();
        String json=callApi.callPostRequestParam("http://localhost:8080/sanPham/search","find=",textFieldTimKiem.getText());
        data=FXCollections.observableArrayList(convertJsonToSanPham(json));
        tableView.setItems(data);
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



}

