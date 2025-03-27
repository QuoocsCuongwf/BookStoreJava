package com.example.demo.controller;

import com.example.demo.model.SanPham;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
@Component
public class SanPhamController {
    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> anhBia;
    @FXML
    private TableColumn<?, ?> hoNhanVienColumn;
    @FXML
    private TableColumn<?, ?> chucVuNhanVienColumn;
    @FXML
    private TableColumn<?, ?> luongNhanVienColumn;
    @FXML
    private TableColumn<?, ?> sdtNhanVienColumn;
    @FXML
    private TableColumn<?, ?> tenNhanVienColumn;

    @FXML
    private TextField textFieldMaSach, textFieldTenSach, textFieldDonGia, textFieldMaTG, textFieldMaNXB, textFieldSoTrang, textFieldMaTL;

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

    @FXML
    public void initialize() {
        inforBookContainer.setVisible(false);
        btnThoatFormThemSach.setOnAction(event -> thoatFormThemSach(event));
        btnThemSach.setOnAction(event -> inforBookContainer.setVisible(true));
        Image image = new Image("file:/E:\\project\\BookStoreJava\\src\\main\\resources\\asset\\img\\folder.png");
        if (image.isError()) {
            System.out.println("Error loading image: " + image.getException());
        }
        System.out.println(image.getWidth());
        iconfolder.setImage(image);
        System.out.println(iconfolder.getImage().getWidth());

    }

    public void chonFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll();
        File file = fileChooser.showOpenDialog(iconfolder.getScene().getWindow());
        if (file != null) {
            System.out.println("File được chọn: " + file.getAbsolutePath());
        } else {
            System.out.println("Không có file nào được chọn.");
        }

    }

    public void thoatFormThemSach(ActionEvent actionEvent) {
        inforBookContainer.setVisible(false);
    }

    public void themSach() {
        SanPham sanPham = new SanPham();
    }


}
