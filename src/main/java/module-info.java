module eus.ehu.ui_mockup {
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires org.kordamp.ikonli.material2;
    requires com.h2database;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires javafx.web;


    opens eus.ehu.ui_mockup.domain to org.hibernate.orm.core, javafx.base;
    opens eus.ehu.ui_mockup.presentation to javafx.base, javafx.fxml;
    exports eus.ehu.ui_mockup.presentation;
}