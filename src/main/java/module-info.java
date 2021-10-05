module com.dictionary.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires en.us;
    requires freetts;
    requires java.sql;


    opens com.dictionary.dictionary to javafx.fxml;
    exports com.dictionary.dictionary;
}