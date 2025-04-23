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

public class TopicsController implements Initializable {

    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btn_select_class;

    @FXML
    private Button btn_select_regno;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_create_topics;

    @FXML
    private Button btn_delete_topics;

    @FXML
    private Button btn_update_topics;

    @FXML
    private Button logout_topics;

    @FXML
    private Button nav_to_classes_topics;

    @FXML
    private Button nav_to_home_topics;

    @FXML
    private Button nav_to_topics_topics;

    @FXML
    private TextArea txt_comment;

    @FXML
    private TextField txt_topic;

    @FXML
    private ComboBox<?> combo_box_topics;

    @FXML
    private ComboBox<?> combo_box_regno;

    @FXML
    private TableColumn<Topics, String> col_class_code;

    @FXML
    private TableColumn<Topics, String> col_comment;

    @FXML
    private TableColumn<Topics, String> col_topic;

    @FXML
    private TableColumn<Topics, String> col_student_regno;

    @FXML
    private TableColumn<Topics, Integer> col_topic_id;


    @FXML
    private Label lbl_selected;

    @FXML
    private Label lbl_selectedregno;


    @FXML
    private TableView<Topics> tbl_topics;

    @FXML
    private VBox topics_VBox;

    int topic_id;
    Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showTopics();
            pickRegno();
            pickClassComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Topics> getTopics() throws SQLException, ClassNotFoundException {
        ObservableList<Topics> topicslist = FXCollections.observableArrayList();

        String query = "SELECT * FROM `tbl_topics`";
        con = DBConnection.getCon();
        st = con.prepareStatement(query);
        rs = st.executeQuery();
        while(rs.next()){
            Topics tpcs = new Topics();
            tpcs.setTopic_id(rs.getInt("topic_id"));
            tpcs.setTopic(rs.getString("topic"));
            tpcs.setComment(rs.getString("comment"));
            tpcs.setClass_code(rs.getString("class_code"));
            tpcs.setStudent_regno(rs.getString("student_regno"));
            topicslist.add(tpcs);
        }
        return topicslist;
    }

    public void showTopics() throws SQLException, ClassNotFoundException {
        ObservableList<Topics> list = getTopics();
        tbl_topics.setItems(list);
        col_topic_id.setCellValueFactory(new PropertyValueFactory<Topics, Integer>("topic_id"));
        col_topic.setCellValueFactory(new PropertyValueFactory<Topics, String>("topic"));
        col_comment.setCellValueFactory(new PropertyValueFactory<Topics,String>("comment"));
        col_class_code.setCellValueFactory(new PropertyValueFactory<Topics,String>("class_code"));
        col_student_regno.setCellValueFactory(new PropertyValueFactory<Topics,String>("student_regno"));
    }


    @FXML
    void createTopics(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(txt_topic.getText().isEmpty() || txt_comment.getText().isEmpty() || lbl_selected.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the input fields");
            alert.showAndWait();
        }else {
                String insert = "INSERT INTO `tbl_topics`(`topic`, `comment`, `class_code`,`student_regno`) VALUES (?,?,?,?)";
                con = DBConnection.getCon();
                st = con.prepareStatement(insert);
                st.setString(1,txt_topic.getText());
                st.setString(2,txt_comment.getText());
                st.setString(3, lbl_selected.getText());
                st.setString(4, lbl_selectedregno.getText());
                st.executeUpdate();
                clear_topics();
                showTopics();
        }




    }

    @FXML
    void updateTopics(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(txt_topic.getText().isEmpty() || txt_comment.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the input fields");
            alert.showAndWait();
        }
        else{
            String update = "UPDATE `tbl_topics` SET `topic`= ?,`comment`= ? WHERE `topic_id` = ?";
            con = DBConnection.getCon();
            st = con.prepareStatement(update);
            st.setString(1,txt_topic.getText());
            st.setString(2, txt_comment.getText());
            st.setInt(3, topic_id);
            st.executeUpdate();
            showTopics();
            clear_topics();
        }
    }

    @FXML
    void deleteTopics(ActionEvent event) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM `tbl_topics` WHERE topic_id = ?";
        con = DBConnection.getCon();
        st = con.prepareStatement(delete);
        st.setInt(1,topic_id);
        st.executeUpdate();
        showTopics();
        clear_topics();

    }

    @FXML
    void logOut_topics(ActionEvent event) throws Exception {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Log Out Successful.");
        alert.showAndWait();
        new SceneSwitch(topics_VBox,"log-in.fxml");
    }

    @FXML
    void onSwitchClasses_topics(ActionEvent event) throws Exception {
        new SceneSwitch(topics_VBox,"classes.fxml");
    }


    @FXML
    void onSwitchTopics_topics(ActionEvent event) throws Exception {
        new SceneSwitch(topics_VBox,"topics.fxml");
    }


    @FXML
    void pickClassComboBox() throws SQLException, ClassNotFoundException {

        String query = "SELECT `class_code` FROM `tbl_classes`";
        con = DBConnection.getCon();
        st = con.prepareStatement(query);
        rs = st.executeQuery();

        ObservableList list = FXCollections.observableArrayList();

        while(rs.next()){
            String item = rs.getString("class_code");
            list.add(item);
        }
        combo_box_topics.setItems(list);

    }

    @FXML
    void pickRegno() throws SQLException, ClassNotFoundException {
        String query = "SELECT `student_regno` FROM `tbl_users`";
        con = DBConnection.getCon();
        st = con.prepareStatement(query);
        rs = st.executeQuery();

        ObservableList list = FXCollections.observableArrayList();

        while(rs.next()){
            String item = rs.getString("student_regno");
            list.add(item);
        }
        combo_box_regno.setItems(list);
    }

    @FXML
    void getSelected(ActionEvent event) {
        String selected = combo_box_topics.getSelectionModel().getSelectedItem().toString();
        lbl_selected.setText(selected);
    }

    @FXML
    void getSelected_regno(ActionEvent event) {
        String selected_regno = combo_box_regno.getSelectionModel().getSelectedItem().toString();
        lbl_selectedregno.setText(selected_regno);
    }

    void clear_topics(){
        lbl_selected.setText(null);
        lbl_selectedregno.setText(null);
        txt_topic.setText(null);
        txt_comment.setText(null);
    }

    @FXML
    void clearField_topics(ActionEvent event) {
        clear_topics();
    }

    @FXML
    void getData_topics(MouseEvent event) {

        Topics topics = tbl_topics.getSelectionModel().getSelectedItem();

        topic_id = topics.getTopic_id();
        lbl_selected.setText(topics.getClass_code());
        lbl_selectedregno.setText(topics.getStudent_regno());
        txt_topic.setText(topics.getTopic());
        txt_comment.setText(topics.getComment());
    }

}
