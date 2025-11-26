module com.solrac.computers {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.slf4j;
    requires com.google.gson;
    requires java.net.http;

    opens com.solrac.computers to javafx.fxml;
    opens com.solrac.computers.controller to javafx.fxml;
    opens com.solrac.computers.model to javafx.base;

    exports com.solrac.computers;
    exports com.solrac.computers.controller;
    exports com.solrac.computers.model;
    exports com.solrac.computers.service;
    exports com.solrac.computers.util;
}

