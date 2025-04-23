package com.chogo.sufeeds.sufeeds;

//Corban Chogo, ICS 1.2 Group B, 165558, 13/11/2024

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;


    @FXML
    private TableColumn<Classes, String> col_class_code;

    @FXML
    private TableColumn<Classes, String> col_class_name;
    @FXML
    private TableColumn<Classes, Integer> col_class_id;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_update;

    @FXML
    private Button logout_classes;

    @FXML
    private Button nav_to_classes_classes;

    @FXML
    private Button nav_to_home_classes;

    @FXML
    private Button nav_to_topics_classes;

    @FXML
    private TextField txt_class_code;

    @FXML
    private TextField txt_class_name;

    @FXML
    private VBox classes_VBox;

    @FXML
    private TableView<Classes> tbl_classes;

    int class_id;
    Alert alert;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            showClasses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<Classes> getClasses() throws SQLException, ClassNotFoundException {
        ObservableList<Classes> classeslist = FXCollections.observableArrayList();

        String query = "SELECT * FROM `tbl_classes`";
        con = DBConnection.getCon();
        st = con.prepareStatement(query);
        rs = st.executeQuery();
        while(rs.next()){
            Classes cl = new Classes();
            cl.setClass_id(rs.getInt("class_id"));
            cl.setClass_code(rs.getString("class_code"));
            cl.setClass_name(rs.getString("class_name"));
            classeslist.add(cl);
        }
        return classeslist;
    }

    public void showClasses() throws SQLException, ClassNotFoundException {
        ObservableList<Classes> list = getClasses();
        tbl_classes.setItems(list);
        col_class_id.setCellValueFactory(new PropertyValueFactory<Classes, Integer>("class_id"));
        col_class_code.setCellValueFactory(new PropertyValueFactory<Classes,String>("class_code"));
        col_class_name.setCellValueFactory(new PropertyValueFactory<Classes,String>("class_name"));

    }


    void clear(){
        txt_class_code.setText(null);
        txt_class_name.setText(null);
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void createClasses(ActionEvent event) throws SQLException, ClassNotFoundException {


          if(txt_class_code.getText().isEmpty() || txt_class_name.getText().isEmpty() ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the input fields");
            alert.showAndWait();
        } else{
              String checkData = "SELECT  `class_code`, `class_name` FROM `tbl_classes` WHERE `class_code` = ? ";
              con = DBConnection.getCon();
              st = con.prepareStatement(checkData);
              st.setString(1, txt_class_code.getText());
              rs = st.executeQuery();
              if(rs.next()){
                  alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error Message");
                  alert.setHeaderText(null);
                  alert.setContentText("A class with the code: "+ txt_class_code.getText()+" already exists.");
                  alert.showAndWait();
              }
              else {
                  String insert = "INSERT INTO `tbl_classes`(`class_code`,`class_name`) VALUES (?,?)";
                  con = DBConnection.getCon();
                  st = con.prepareStatement(insert);
                  st.setString(1, txt_class_code.getText());
                  st.setString(2, txt_class_name.getText());
                  st.executeUpdate();
                  clear();
                  showClasses();
              }
          }

    }



    @FXML
    void deleteClasses(ActionEvent event) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM `tbl_classes` WHERE class_id = ?";
        con = DBConnection.getCon();
        st = con.prepareStatement(delete);
        st.setInt(1,class_id);
        st.executeUpdate();
        showClasses();
        clear();
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Log Out Successful.");
        alert.showAndWait();
        new SceneSwitch(classes_VBox,"log-in.fxml");

    }

    @FXML
    void onSwitchClasses(ActionEvent event) throws Exception{

        new SceneSwitch(classes_VBox,"classes.fxml");
    }


    @FXML
    void onSwitchTopics(ActionEvent event) throws Exception{

        new SceneSwitch(classes_VBox,"topics.fxml");
    }



    @FXML
    void getData(MouseEvent event) throws SQLException {
        Classes classes = tbl_classes.getSelectionModel().getSelectedItem();

        class_id = classes.getClass_id();
        txt_class_code.setText(classes.getClass_code());
        txt_class_name.setText(classes.getClass_name());
        //btn_add.setDisable(true);
        //txt_class_code.setDisable(true);
    }

    @FXML
    void updateClasses(ActionEvent event) throws SQLException, ClassNotFoundException {

        if( txt_class_name.getText().isEmpty() || txt_class_code.getText().isEmpty() ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the input fields");
            alert.showAndWait();
        } else {
            String update = "UPDATE `tbl_classes` SET `class_name`= ? WHERE `class_id`= ?";
            con = DBConnection.getCon();
            st = con.prepareStatement(update);
            st.setString(1, txt_class_name.getText());
            st.setInt(2, class_id);
            st.executeUpdate();
            showClasses();
            clear();
        }
    }


}






