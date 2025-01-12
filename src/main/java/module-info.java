module gui.interpreter {
    requires javafx.controls;
    requires javafx.fxml;


    opens gui.interpreter to javafx.fxml;
    exports gui.interpreter;
}