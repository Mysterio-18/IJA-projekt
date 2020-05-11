

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
/**
 * @author Dominik Bednar
 * Zakladni trida programu.
 *
 */


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        ArrayList<String> moves = new ArrayList<>();
        ArrayList<String> pl_moves = new ArrayList<>();

        Board board = new Board();
        board.create_figures();


        Moved moved = new Moved();

        GUI gui = new GUI();
        gui.board_GUI();
        gui.add_background_figures(board);
        gui.play_gui();

        board.Place_figures(gui);
        gui.place_empty_figures(board);

        gui.main_gui.getChildren().add(gui.gridpane);
        gui.main_gui.getChildren().add(gui.play_box);



        Handle handle = new Handle();
        handle.handle_game(gui, board, moves,moved, pl_moves);
        handle.handle_buton_file(gui, moves, handle, board, moved);


        Scene scene = new Scene(gui.main_gui);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }



}
