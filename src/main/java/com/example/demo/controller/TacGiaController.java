package com.example.demo.controller;

import com.example.demo.model.TacGia;
import com.example.demo.model.NhanVien;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TacGiaController implements Initializable {

    @FXML
    private Pane tacGiaPane;

    @FXML
    private Pane inforContainerTacGia;
    @FXML
    private ObservableList<TacGia> data;

    @FXML
    private TableView<?> tableView;
    @FXML
    private Pane formTacGia;





    @FXML
    private TableColumn<TacGia, String> hoTacGiaColumn,
            tenTacGiaColumn,namSinhTacGiaColumn,QueQuanTGColumn;
    @FXML
    private TableColumn<TacGia,Integer> maTacGiaColumn;

    @FXML
    private TextField textFieldmaTacGia,textFieldHoTG,textFieldTenTG,textFieldNamSinhTG,
            textFieldQueQuanTG;

    @FXML
    private Button addTacGia,btnAddTG,dongContainerTG;



    public void themTacGia(ActionEvent actionEvent) {}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inforContainerTacGia.setVisible(false);
    }
    public void openInforContainer(){
        inforContainerTacGia.setVisible(true);
    }
public void addTacGia(){}
    public void clossInforContainer(){}



}
