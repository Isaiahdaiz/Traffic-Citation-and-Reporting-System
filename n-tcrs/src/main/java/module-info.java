module tcrs {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens tcrs to javafx.fxml;
    exports tcrs;
}
