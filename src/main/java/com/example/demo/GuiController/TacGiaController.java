
package com.example.demo.GuiController;

import com.example.demo.model.TacGia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class TacGiaController implements Initializable {

    @FXML
    private Pane inforContainer;

    @FXML
    private Pane tacGiaPane;
    @FXML
    private TableView<TacGia> tableView;
    @FXML
    private TableColumn<TacGia, String> maTacGiaColumn;
    @FXML
    private TableColumn<TacGia, String> hoTacGiaColumn;
    @FXML
    private TableColumn<TacGia, String> tenTacGiaColumn;
    @FXML
    private TableColumn<TacGia, String> queQuanTacGiaColumn;
    @FXML
    private TableColumn<TacGia, Integer> namSinhTacGiaColumn;

    private ObservableList<TacGia> data;
    static List<TacGia> tacGiaList = new ArrayList<>();

    @FXML
    private TextField textFieldTimKiem;
    @FXML
    private TextField txt_MaTacGia, txt_HoTacGia, txt_TenTacGia, txt_NamSinhTacGia, txt_QueQuanTacGia;
    @FXML
    private Pane inforFormTacGia;

    @FXML
    private Button btnAddTacGia;
    @FXML
    private Button timKiem;
    @FXML
    private Button openInforContainer;
    @FXML
    private Button clossInforContainer;
    @FXML
    private HBox inforButtonContainer;



    private Button btnDeleteTacGia = new Button("    Xóa    ");
    private Button btnUpdateTacGia = new Button("Cập nhật");
    @FXML
    private Button btnThongKe, btnKhachHang, btnSanPham, btnNhanVien,
            btnNCC, btnTacGia, btnHoaDon, btnTHD, btnKhuyenMai;
    LeftMenuController leftMenuController=new LeftMenuController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leftMenuController.bindHandlers(btnThongKe, btnKhachHang, btnSanPham,
                btnNhanVien, btnNCC, btnTacGia,
                btnHoaDon, btnTHD, btnKhuyenMai);
        inforContainer.setVisible(false);
        maTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("matg"));
        hoTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("hotg"));
        tenTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("tentg"));
        queQuanTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("quequan"));
        namSinhTacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("namsinh"));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
                listenerChangeValuesOfTacGia();
            } else {
                System.out.println("No item selected!");
            }
        });

        CallApi callApi = new CallApi();
        String json = null;
        try {
            json = callApi.callGetApi("http://localhost:8080/tacGia/getAllTacGia");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tacGiaList = convertJsonToListTacGia(json);
        System.out.println(tacGiaList);
        data = FXCollections.observableArrayList(tacGiaList);
        tableView.setItems(data);
        btnDeleteTacGia.setOnAction(event -> deleteTacGia(btnDeleteTacGia));
        btnUpdateTacGia.setOnAction(event -> updateTacGia());
    }
    public List<TacGia> getListTacGia() {
        System.out.println("List tac gia: "+tacGiaList);
        return tacGiaList;
    }
    public List<TacGia> convertJsonToListTacGia(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<TacGia> tacGiaList = new ArrayList<>();
        System.out.println("json: " + json);
        try {
            tacGiaList = objectMapper.readValue(json, new TypeReference<List<TacGia>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return tacGiaList;
    }

    public String convertTacGiaToJson(TacGia tacGia) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = null;
        try {
            json = mapper.writeValueAsString(tacGia);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public void listenerChangeValuesOfTacGia() {
        List<TextField> fields = Arrays.asList(
                txt_MaTacGia, txt_HoTacGia, txt_TenTacGia,
                txt_NamSinhTacGia, txt_QueQuanTacGia
        );

        fields.forEach(f -> {
            if (f == null) {
                System.out.println("Một TextField chưa được inject (null)!");
            } else{
                f.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(f.getId() + " thay đổi: " + newVal);
                    int index = inforButtonContainer.getChildren().indexOf(btnDeleteTacGia);
                    if (index >= 0) {
                        inforButtonContainer.getChildren().set(index, btnUpdateTacGia);
                    } else {
                        System.err.println("btnDeleteTacGia không tồn tại trong inforFormButtonContainer!");
                    }
                });
            }
        });
    }

    public void deleteTacGia( Button button) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < data.size()) {
            TacGia tacGia = data.get(selectedIndex);
            System.out.println("Tac Gia selected " + tacGia.getMatg());
            CallApi callApi = new CallApi();
            String result = callApi.callPostRequestParam("http://localhost:8080/tacGia/Delete", "maTacGia=",tacGia.getMatg());
            data.remove(selectedIndex);
            tableView.getSelectionModel().clearSelection();
        } else {
            System.out.println("No valid selection!");
        }
    }
    public void updateTacGia() {
        TacGia tacGia = new TacGia();
        List<TextField> textFields=Arrays.asList(txt_MaTacGia, txt_HoTacGia, txt_TenTacGia,
                txt_QueQuanTacGia, txt_NamSinhTacGia
                );
        for(TextField tf:textFields) {
            if(tf.getText().equals("")){
                showMessage("Error","Text Field Null","Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        };
        tacGia.setMatg(txt_MaTacGia.getText());
        tacGia.setHotg(txt_HoTacGia.getText());
        tacGia.setTentg(txt_TenTacGia.getText());
        tacGia.setQuequan(txt_QueQuanTacGia.getText());
        tacGia.setNamsinh(Integer.parseInt( txt_NamSinhTacGia.getText()));

        CallApi callApi=new CallApi();
        String resultApi=callApi.callPostRequestBody("http://localhost:8080/tacGia/Update",convertTacGiaToJson(tacGia));
        if (resultApi.contains("Success")) {
            for( int i = 0; i < tacGiaList.size(); i++ ) {
                if(tacGiaList.get(i).getMatg().equals(tacGia.getMatg())) {
                    tacGiaList.set(i,tacGia); // thay the dung phan tu
                    break;
                }
            }
            showMessage("Success"," Sua tac gia thanh cong", resultApi);
            data = FXCollections.observableArrayList(tacGiaList);
            tableView.setItems(data);

        }
    }
    public void openInforContainer() {
        txt_MaTacGia.setText("TG"+tacGiaList.size()+1);
        txt_HoTacGia.setText("");
        txt_TenTacGia.setText("");
        txt_QueQuanTacGia.setText("");
        txt_NamSinhTacGia.setText("");
        inforContainer.setVisible(true);
    }

    public void clossInforContainer() {
        int index = inforButtonContainer.getChildren().indexOf(btnDeleteTacGia);
        if (index >= 0) {
            inforButtonContainer.getChildren().set(index, btnAddTacGia);
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

    public void addTacGia() {
        TacGia tacGia = new TacGia();
        List<TextField> textFields = Arrays.asList(
                txt_MaTacGia, txt_HoTacGia, txt_TenTacGia,
                txt_NamSinhTacGia, txt_QueQuanTacGia
        );
        for (TextField tf : textFields) {
            if (tf.getText().equals("")) {
                showMessage("Error", "Text Field Null", "Vui lòng nhập đầy đủ thông tin!");
                System.out.println("Text Field Null");
                return;
            }
        }
        tacGia.setMatg(txt_MaTacGia.getText());
        tacGia.setHotg(txt_HoTacGia.getText());
        tacGia.setTentg(txt_TenTacGia.getText());
        tacGia.setQuequan(txt_QueQuanTacGia.getText());
        tacGia.setNamsinh(Integer.parseInt( txt_NamSinhTacGia.getText()));
        tacGiaList.add(tacGia);
        data.add(tacGia);
        CallApi callApi = new CallApi();
        String result = callApi.callPostRequestBody("http://localhost:8080/tacGia/Add", convertTacGiaToJson(tacGia));
        System.out.println(result);
    }

    public void showSelectedItem(TacGia tacGia) {
        openInforContainer();
        txt_MaTacGia.setEditable(false);
        txt_MaTacGia.setText(tacGia.getMatg());
        txt_HoTacGia.setText(tacGia.getHotg());
        txt_TenTacGia.setText(tacGia.getTentg());
        txt_QueQuanTacGia.setText(tacGia.getQuequan());
        txt_NamSinhTacGia.setText(tacGia.getNamsinh().toString());

        int index = inforButtonContainer.getChildren().indexOf(btnAddTacGia);
        if (index >= 0) {
            inforButtonContainer.getChildren().set(index, btnDeleteTacGia);
        } else {
            System.err.println("btnAddTacGia không tồn tại trong inforFormButtonContainer!");
        }
         index = inforButtonContainer.getChildren().indexOf(btnUpdateTacGia);
        if (index >= 0) {
            inforButtonContainer.getChildren().set(index, btnDeleteTacGia);
        } else {
            System.err.println("btnDeleteTacGia không tồn tại trong in4FormTacGia !");

        }
    }

    public void timKiem() {
        String find = textFieldTimKiem.getText();
        CallApi callApi = new CallApi();
        String json = callApi.callPostRequestParam("http://localhost:8080/tacGia/timKiem", "find=", find);
        data = FXCollections.observableArrayList(convertJsonToListTacGia(json));
        tableView.setItems(data);
    }
}
