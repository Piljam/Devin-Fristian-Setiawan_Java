module TEST_GUI {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires jfxtras.labs.samples;
	requires java.sql;
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
