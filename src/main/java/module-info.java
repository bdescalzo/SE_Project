module eus.ehu.TxikIA {
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires org.kordamp.ikonli.material2;
    requires com.h2database;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires javafx.web;
    requires org.apache.logging.log4j;
    requires okhttp3;
    requires com.google.gson;


    opens eus.ehu.TxikIA.domain to org.hibernate.orm.core, javafx.base;
    opens eus.ehu.TxikIA.presentation to javafx.base, javafx.fxml;
    exports eus.ehu.TxikIA.presentation;
}