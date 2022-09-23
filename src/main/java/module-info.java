module com.zerui.csproject.Model {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;
    requires google.cloud.storage;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires google.cloud.core;
    requires java.desktop;
    requires java.mail;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires commons.validator;
    requires net.harawata.appdirs;
    requires org.apache.commons.io;
    opens com.zerui.csproject.Controller to javafx.fxml;
    exports com.zerui.csproject;
}