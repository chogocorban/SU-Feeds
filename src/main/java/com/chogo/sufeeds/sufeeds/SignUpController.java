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

public class SignUpController {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;



    @FXML
    private VBox sign_up_VBox;

    @FXML
    private Button btn_login_signup;

    @FXML
    private Button btn_signup_signup;

    @FXML
    private TextField txt_first_name;

    @FXML
    private TextField txt_last_name;

    @FXML
    private PasswordField txt_password_signup;

    @FXML
    private TextField txt_student_id_signup;

    Alert alert;

    @FXML
    void signUp(ActionEvent event) throws Exception {

        String insert = "INSERT INTO `tbl_users`(`student_regno`, `fname`, `lname`, `password`) VALUES (?,?,?,?)";
        con = DBConnection.getCon();

        if(txt_student_id_signup.getText().isEmpty() || txt_first_name.getText().isEmpty() || txt_last_name.getText().isEmpty() || txt_password_signup.getText().isEmpty() ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the input fields");
            alert.showAndWait();
        }else{
            if(txt_password_signup.getText().length() < 8){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password should be at least 8 characters.");
                alert.showAndWait();
            }else{
                String checkData = "SELECT  `student_regno`, `fname`,`lname`, `password` FROM `tbl_users` WHERE `student_regno` = ? ";
                con = DBConnection.getCon();
                st = con.prepareStatement(checkData);
                st.setString(1, txt_student_id_signup.getText());
                rs = st.executeQuery();
                if(rs.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("A user with registration number "+ txt_student_id_signup.getText()+" already exists.");
                    alert.showAndWait();
                }else {

                    st = con.prepareStatement(insert);
                    st.setString(1,txt_student_id_signup.getText());
                    st.setString(2,txt_first_name.getText());
                    st.setString(3,txt_last_name.getText());
                    st.setString(4,txt_password_signup.getText());
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sign up successful.");
                    alert.showAndWait();

                    new SceneSwitch(sign_up_VBox,"log-in.fxml");

                }
            }


        }


    }

    @FXML
    void switchToLogin(ActionEvent event) throws Exception {
        new SceneSwitch(sign_up_VBox,"log-in.fxml");
    }

}
