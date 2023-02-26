module com.souryuu.multiclipboard {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.desktop;
    requires com.github.kwhat.jnativehook;

    opens com.souryuu.multiclipboard to javafx.fxml;
    exports com.souryuu.multiclipboard;
}