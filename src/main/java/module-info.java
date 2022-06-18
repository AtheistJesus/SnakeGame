module atheistjesus.snakegame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;

    opens atheistjesus.snakegame to javafx.fxml;
    exports atheistjesus.snakegame;
}