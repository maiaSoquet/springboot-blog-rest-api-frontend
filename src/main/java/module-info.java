module springboot.blog.rest.api {
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Pour Jackson 3.x, les noms de modules sont souvent liés aux artifacts
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires javafx.base;



    opens com.maia to javafx.graphics, javafx.fxml;
    opens com.maia.controllers to javafx.fxml;
    opens com.maia.models to com.fasterxml.jackson.databind, javafx.base;
    exports com.maia;
}