package com.example.demo.GuiController;

import com.example.demo.model.NhaXuatBan;
import com.example.demo.model.SanPham;
import com.example.demo.model.TacGia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private TableColumn<SanPham, TacGia> tacGiaColumn; // Cột "Tác giả"

    @FXML
    private TextField textFieldMaSach, textFieldTenSach, textFieldDonGia, textFieldMaTG, textFieldMaNXB, textFieldSoTrang, textFieldMaTL;
    private ObservableList<SanPham> data;

    @FXML
    private ImageView iconfolder;
    @FXML
    private Button btnChonFile;
    @FXML
    private Button btnThoatFormThemSach;
    @FXML
    private Button btnThemSach;
    @FXML
    private Pane inforBookContainer;

    private List<SanPham> listSanPham=new ArrayList<SanPham>();
    private String pathImage = "";

    @FXML
    public void initialize() {

        anhBia.setCellFactory(column -> createImgCellFactory());
        tacGiaColumn.setCellFactory(column -> new TableCell<SanPham, TacGia>() {
            @Override
            protected void updateItem(TacGia item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getHotg()+" "+item.getTentg()); // hoặc getCity(), getStreet(), tùy bạn
                }
            }
        });
        maSachColumn.setCellValueFactory(new PropertyValueFactory<>("masp"));
        tenSachColumn.setCellValueFactory(new PropertyValueFactory<>("tensp"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("dongia"));
        tacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("matg"));

        anhBia.setCellValueFactory(new PropertyValueFactory<>("anhbia"));

        data=FXCollections.observableArrayList(listSanPham);
        tableView.setItems(data);

        inforBookContainer.setVisible(false);

        btnThoatFormThemSach.setOnAction(event -> thoatFormThemSach(event));
        btnThemSach.setOnAction(event -> inforBookContainer.setVisible(true));
        Image image = new Image("file:/D:\\java\\BookStoreJava\\src\\main\\resources\\asset\\img\\folder.png");
        if (image.isError()) {
            System.out.println("Error loading image: " + image.getException());
        }

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

    public void thoatFormThemSach(ActionEvent actionEvent) {
        inforBookContainer.setVisible(false);
    }

    public void themSach() {

        SanPham sanPham = new SanPham();
        sanPham.setMasp(textFieldMaSach.getText());
        sanPham.setTensp(textFieldTenSach.getText());
        sanPham.setSl(0);
        sanPham.setDongia(Integer.parseInt(textFieldDonGia.getText()));
        NhaXuatBan nhaXuatBan=new NhaXuatBan();
        nhaXuatBan.setManxb(textFieldMaSach.getText());
        sanPham.setManxb(nhaXuatBan);
        sanPham.setAnhbia(pathImage);
        TacGia tacGia = new TacGia();
        tacGia.setMatg(textFieldMaTG.getText());
        sanPham.setMatg(tacGia);
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
    public void loadTacGiaFXML(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TacGia.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}

