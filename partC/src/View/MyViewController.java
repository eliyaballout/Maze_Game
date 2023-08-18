package View;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Model.MyModel;
import Server.Configurations;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.*;
import java.util.*;



public class MyViewController implements Observer, IView {

    public GridPane mainEntry;
    public BorderPane border;
    public GridPane data;
    public Pane pane;
    public javafx.scene.control.Button startMain;
    public javafx.scene.control.Label welcomeMain;
    @FXML
    private MyViewModel viewModel;
    private static MyViewController mainController;
    public static Stage mainStage;
    public MazeDisplayer mazeDisplayer;
    public javafx.scene.control.Label lbl_rowsNum;
    public javafx.scene.control.Label lbl_columnsNum;
    public javafx.scene.control.Label lbl_CurColNum;
    public javafx.scene.control.Label lbl_CurRowsNum;
    public javafx.scene.control.Button btn_generateMaze;
    public javafx.scene.control.Button solutionButton;
    public javafx.scene.control.TextField columnsNumber;
    public javafx.scene.control.TextField rowsNumber;
    public javafx.scene.control.MenuItem saveMenu;
    public javafx.scene.control.MenuItem loadMenu;
    public StringProperty characterPositionRow = new SimpleStringProperty();
    public StringProperty characterPositionColumn = new SimpleStringProperty();
    private boolean ifDisplayMaze;
    private Media media2 = new Media(getClass().getResource("/resources/music/inGame.mp3").toExternalForm());
    private Media media3 = new Media(getClass().getResource("/resources/music/finish.mp3").toExternalForm());
    private MediaPlayer inGameMusic = new MediaPlayer(media2);
    private MediaPlayer finishMusic = new MediaPlayer(media3);


//######################################################################################################################
//######################################################################################################################
    //function for clicking the start button from the main stage.

    public void mainNewAction(ActionEvent actionEvent){
        Stage stage = new Stage();
        try{
            this.start(stage);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public void start(Stage primaryStage) throws Exception{
        MyModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        mainStage = primaryStage;
        //--------------
        primaryStage.setTitle("Maze Game");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MainScreen.fxml"));
        String path = getClass().getResource("/resources/images/icon.jpg").toExternalForm();
        primaryStage.getIcons().add(new Image(path));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        //--------------
        MyViewController view = fxmlLoader.getController();
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        this.viewModel = viewModel;
        mainController = view;
        //--------------
        SetStageCloseEvent(primaryStage, model);
        primaryStage.show();
        Main.mainStage.close();
    }


//######################################################################################################################
//######################################################################################################################
    //method for handling the 'X' button(close).

    private void SetStageCloseEvent(Stage primaryStage, MyModel myModel) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    // Close program
                    myModel.stopServers();
                    primaryStage.close();
                    Platform.exit();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }


//######################################################################################################################
//######################################################################################################################
    //Observable method for updating and notifying.

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof Boolean && ((Boolean) arg)) {
            mazeDisplayer.setCharacterPosition(viewModel.getPlayerRowPosition(), viewModel.getPlayerColPosition());
            Main.entryMusic.stop();
            inGameMusic.stop();
            finishMusic.play();
            solutionButton.setDisable(true);
            mazeDisplayer.finishDisplay();
            rowsNumber.setDisable(false);
            columnsNumber.setDisable(false);
        }

        else if (arg != null && arg instanceof Solution) {
            Solution mazeSolution = (Solution) arg;
            mazeDisplayer.displaySolution(mazeSolution.getSolutionPath());
        }

        else {
            if (ifDisplayMaze) { //start game draw both board maze game and character
                displayMaze(viewModel.getMaze());
                ifDisplayMaze = false;
            }
            else { //draw only character after change
                if (mazeDisplayer.getMaze() == null)
                    mazeDisplayer.setMaze(viewModel.getMaze());

                mazeDisplayer.setCharacterPosition(viewModel.getPlayerRowPosition(), viewModel.getPlayerColPosition());
            }
        }
    }


    @Override
    public void displayMaze(Maze maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = maze.getStartPosition().getRowIndex();
        int characterPositionColumn = maze.getStartPosition().getColumnIndex();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");
    }



//######################################################################################################################
//######################################################################################################################
    //function for clicking the "generate maze" and "solve maze".

    public void startPlay(ActionEvent actionEvent) {
        this.viewModel = mainController.viewModel;
        viewModel.setFinish(false);
        mazeDisplayer.setIfDisplaySolution(false);
        rowsNumber.setDisable(true);
        columnsNumber.setDisable(true);
        mazeDisplayer.setVisible(true);
        saveMenu.setDisable(false);
        loadMenu.setDisable(false);
        ifDisplayMaze = true;

        try {
            Integer.parseInt(rowsNumber.getText());
            Integer.parseInt(columnsNumber.getText());
            int height = Integer.parseInt(rowsNumber.getText());
            int width = Integer.parseInt(columnsNumber.getText());
            if (height >= 3 && width >= 3) {
                if (finishMusic != null) {
                    finishMusic.stop();
                    Main.entryMusic.stop();
                    inGameMusic.play();
                }
                solutionButton.setDisable(false);
                viewModel.generateMaze(height, width);
            }
            else { //wrong size of lines and columns
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid number of rows and columns!\n We will create a default Maze in size of 10*10"); //edit
                alert.show();
                if (finishMusic != null) {
                    finishMusic.stop();
                    Main.entryMusic.stop();
                    inGameMusic.play();
                }
                solutionButton.setDisable(false);
                viewModel.generateMaze(10, 10);
            }

        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please try again!"); //edit
            alert.show();
            mazeDisplayer.setVisible(false);
            rowsNumber.setDisable(false);
            columnsNumber.setDisable(false);
        }
    }


    public void showSolutionAction(ActionEvent actionEvent) {
        viewModel.showSolution();
        solutionButton.setDisable(true);
    }



//######################################################################################################################
//######################################################################################################################
    //File menu options

    public void newAction(ActionEvent actionEvent) {
        if (finishMusic != null || inGameMusic != null) {
            finishMusic.stop();
            inGameMusic.stop();
            Main.entryMusic.play();
        }

        solutionButton.setDisable(true);
        saveMenu.setDisable(true);
        viewModel.setFinish(false);
        columnsNumber.setVisible(true);
        rowsNumber.setVisible(true);
        lbl_rowsNum.setVisible(true);
        lbl_columnsNum.setVisible(true);
        btn_generateMaze.setVisible(true);
        mazeDisplayer.setVisible(false);
        if (columnsNumber.isDisable()) {
            rowsNumber.setDisable(false);
            columnsNumber.setDisable(false);
        }
    }


    public void saveGameAction(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Maze...");
        fc.getExtensionFilters().add(new ExtensionFilter("Maze files (*.maze)", "*.maze"));
        File chosen = fc.showSaveDialog(mainStage);
        if (chosen != null){
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(chosen.getAbsolutePath()));
            out.write(mazeDisplayer.getMaze().toByteArray());
            out.flush();
            out.close();
        }
    }


    public void loadGameAction(ActionEvent actionEvent) throws IOException {
        if (Main.entryMusic != null || inGameMusic != null || finishMusic != null) {
            Main.entryMusic.stop();
            inGameMusic.stop();
            finishMusic.stop();
        }

        viewModel.setFinish(false);
        columnsNumber.setDisable(true);
        rowsNumber.setDisable(true);
        solutionButton.setDisable(false);
        saveMenu.setDisable(false);

        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new ExtensionFilter("Maze files (*.maze)", "*.maze"));
        File chosen = fc.showOpenDialog(mainStage);

        InputStream tmpIn = new MyDecompressorInputStream(new FileInputStream(chosen));
        byte[] savedMazeBytes = new byte[12];
        tmpIn.read(savedMazeBytes);
        int rows = (int)savedMazeBytes[0] * 127 + (int)savedMazeBytes[1];
        int cols = (int)savedMazeBytes[2] * 127 + (int)savedMazeBytes[3];
        tmpIn.close();

        InputStream in = new MyDecompressorInputStream(new FileInputStream(chosen));
        savedMazeBytes = new byte[rows * cols + 12];
        in.read(savedMazeBytes);
        in.close();

        Maze maze = new Maze(savedMazeBytes);
        mazeDisplayer.setMaze(maze);
        mazeDisplayer.setCharacterPositionRow(maze.getStartPosition().getRowIndex());
        mazeDisplayer.setCharacterPositionCol(maze.getStartPosition().getColumnIndex());
        mazeDisplayer.setGoalPositionRow(maze.getGoalPosition().getRowIndex());
        mazeDisplayer.setGoalPositionCol(maze.getGoalPosition().getColumnIndex());
        viewModel.setModelMaze(maze);
        viewModel.setModelPlayerRowPos(maze.getStartPosition().getRowIndex());
        viewModel.setModelPlayerColPos(maze.getStartPosition().getColumnIndex());

        viewModel.updateForLoad();
        if (ifDisplayMaze) { //start game draw both board maze game and character
            displayMaze(viewModel.getMaze());
            ifDisplayMaze = false;
        }
        else { //draw only character after change
            if (mazeDisplayer.getMaze() == null)
                mazeDisplayer.setMaze(viewModel.getMaze());

            mazeDisplayer.setCharacterPosition(viewModel.getPlayerRowPosition(), viewModel.getPlayerColPosition());
        }

        mazeDisplayer.redraw();
        if (mazeDisplayer.isVisible() == false)
            mazeDisplayer.setVisible(true);

        if (mazeDisplayer != null)
            inGameMusic.play();

        mazeDisplayer.redrawCharacter(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
    }


    /**
     * method which responsible on exit and closing the program
     * @param actionEvent: action event.
     */
    public void exitAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (Main.entryMusic.isAutoPlay() || inGameMusic.isAutoPlay()) {
                Main.entryMusic.stop();
                inGameMusic.stop();
            }

            viewModel.exit();
            Platform.exit();
        }
    }



//######################################################################################################################
//######################################################################################################################
    //Options menu options

    /**
     * method which responsible on open and showing the properties section
     * @param actionEvent: action event.
     */
    public void openProperties(ActionEvent actionEvent) {
        try {
            Properties prop = new Properties();
            prop.load(Configurations.class.getClassLoader().getResourceAsStream("resources/config.properties"));
            String value1 = prop.getProperty("mazeGeneratingAlgorithm");
            String value2 = prop.getProperty("mazeSearchingAlgorithm");
            String value3 = prop.getProperty("threadPoolSize");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Properties");
            String str = "We used '" + value1 + "' generator algorithm." + "\n";
            str = str + "We used '" + value2 + "' algorithm for solving the maze." + "\n";
            str = str + "And we used " + value3 + " threads in our application.";
            alert.setContentText(str); //edit
            alert.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//######################################################################################################################
//######################################################################################################################
    //Help menu options

    /**
     * method which responsible on open and showing the properties section
     * @param actionEvent: action event.
     */
    public void helpAction(ActionEvent actionEvent) {
        String message = "This is a maze runner game.\nFirst you need to define the maze size and hit" +
                " the 'Generate Maze' button.\nUse the arrows and the NumPad keys and help the survivor" +
                " to get out of the maze alive!\nYou can use the 'Solve Maze' button\nIf you are unable to solve the maze!\n\n\n" +
                "Keys Guide:\n\n" + "regular buttons:\n" + "UP : arrow up | number 8.\n" + "DOWN : arrow down | number 2.\n" + "RIGHT : arrow right | number 6.\n" +
                "LEFT : arrow left | number 4.\n\n" + "Diagonal buttons:\n" + "RIGHT UP : number 9.\n" + "RIGHT DOWN : number 3.\n" + "LEFT UP : number 7.\n" + "LEFT DOWN : number 1.";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("User's Guide:");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.initStyle(StageStyle.DECORATED);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }


    /**
     * method which responsible on showing the "About" information.
     * @param actionEvent: action event.
     */
    public void aboutAction(ActionEvent actionEvent) {
        String str = "This game was developed by Eliya Ballout.\n\n" + "We used two generating algorithms: 1) SimpleGenerator that is generates a simple maze.\n" +
                "2) MyGenerator: its generates a maze based on randomized Prim's algorithm.\n\n" + "For solving the maze we used 3 algorithms: BFS, DFS, Best First Search(BFS).\n\n" +
                "We are still new to this so we apologize for any technical issues you might encounter while running it, but we promise that we'll fix it if you contact us and let us know.\n";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Programmers Info");
        alert.setContentText(str);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.initStyle(StageStyle.DECORATED);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }



//######################################################################################################################
//######################################################################################################################
    //getters and setters.

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        bindProperties(viewModel);
    }


    public String getCharacterPositionRow() {
        return characterPositionRow.get();
    }


    public StringProperty characterPositionRowProperty() {
        return characterPositionRow;
    }


    public String getCharacterPositionColumn() {
        return characterPositionColumn.get();
    }


    public StringProperty characterPositionColumnProperty() {
        return characterPositionColumn;
    }


    private void bindProperties(MyViewModel viewModel) {
        lbl_CurRowsNum.textProperty().bind(viewModel.strPlayerRowPos );
        lbl_CurColNum.textProperty().bind(viewModel.strPlayerColPos);
    }



//######################################################################################################################
//######################################################################################################################
    //other method for handling key events and resizing window.

    public void KeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Ctrl")) {
            mazeDisplayer.zoom();
        }
        else if (mazeDisplayer.getMaze() != null) {
            viewModel.moveCharacter(keyEvent.getCode());
            keyEvent.consume();
        }
    }


    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }


    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                mainEntry.prefWidthProperty().bind(scene.widthProperty());
                border.prefWidthProperty().bind(scene.widthProperty());
                pane.prefWidthProperty().bind(scene.widthProperty());
                mazeDisplayer.widthProperty().bind(pane.widthProperty());
                if (mazeDisplayer.getMaze() != null){
                    mazeDisplayer.redraw();
                    mazeDisplayer.redrawCharacter(mazeDisplayer.getCharacterPositionRow(), mazeDisplayer.getCharacterPositionColumn());
                }

            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                mainEntry.prefHeightProperty().bind(scene.heightProperty());
                border.prefHeightProperty().bind(scene.heightProperty());
                data.prefHeightProperty().bind(scene.heightProperty());
                pane.prefHeightProperty().bind(scene.heightProperty());
                mazeDisplayer.heightProperty().bind(pane.heightProperty());
                if (mazeDisplayer.getMaze() != null){
                    mazeDisplayer.redraw();
                    mazeDisplayer.redrawCharacter(mazeDisplayer.getCharacterPositionRow(), mazeDisplayer.getCharacterPositionColumn());
                }
            }
        });
    }

}

