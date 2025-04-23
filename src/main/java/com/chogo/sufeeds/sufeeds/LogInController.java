package com.chogo.sufeeds.sufeeds;

//Corban Chogo, ICS 1.2 Group B, 165558, 13/11/2024

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInController {

    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btn_login_login;

    @FXML
    private Button btn_signup_login;

    @FXML
    private VBox login_VBox;

    @FXML
    private PasswordField txt_password_login;

    @FXML
    private TextField txt_student_id_login;

    Alert alert;


    @FXML
    void logIn(ActionEvent event) throws Exception {

        String query = "SELECT `student_regno`, `password` FROM `tbl_users` WHERE `student_regno` = ? and `password` = ?";
        con = DBConnection.getCon();

        if(txt_student_id_login.getText().isEmpty() || txt_password_login.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the input fields");
            alert.showAndWait();
        }else{
            st = con.prepareStatement(query);
            st.setString(1,txt_student_id_login.getText());
            st.setString(2,txt_password_login.getText());
            rs = st.executeQuery();

            if(rs.next()){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Log In Successful.");
                alert.showAndWait();

                new SceneSwitch(login_VBox,"classes.fxml");

                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Welcome to SU Feeds.");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect student registration number or password.");
                alert.showAndWait();
            }
        }


    }

    @FXML
    void switchToSignUp(ActionEvent event) throws Exception {
        new SceneSwitch(login_VBox,"sign-up.fxml");
    }

}
