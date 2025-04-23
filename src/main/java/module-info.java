module com.chogo.sufeeds.sufeeds {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.chogo.sufeeds.sufeeds to javafx.fxml;
    exports com.chogo.sufeeds.sufeeds;
}