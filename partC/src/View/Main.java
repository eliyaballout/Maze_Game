package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;



public class Main extends Application {

    public static Stage mainStage;
    private static Media media1 = new Media(Main.class.getResource("/resources/music/entry.mp3").toExternalForm());
    public static MediaPlayer entryMusic = new MediaPlayer(media1);


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Maze Game");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MyView.fxml"));
        String path = Main.class.getResource("/resources/images/icon.jpg").toExternalForm();
        primaryStage.getIcons().add(new Image(path));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage = primaryStage;
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        entryMusic.play();
    }

}
