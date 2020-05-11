



import javafx.geometry.HPos;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * @author Dominik Bednář
 *
 * Inicializuje GUI a dle potřeby jej aktualizuje.
 *
 */

public class GUI {

    HBox main_gui;
    GridPane gridpane;
    VBox play_box;
    Button[] controls;
    GridPane notation;
    boolean long_notation;
    ArrayList<HBox> notation_moves;

    /**
     * Konstuktor pro vytvoření základniho GUI.
     */

    GUI()
    {
        notation_moves = new ArrayList<>();
        main_gui = new HBox();
        main_gui.setAlignment(Pos.CENTER);
        //  test.setPrefSize(1200,850);

    }

    /**
     * Vytvoří GUI šachovnice.
     */

    void board_GUI()
    {
        gridpane = new GridPane();

        Rectangle[] black_fields = new Rectangle[32];
        Rectangle[] borders = new Rectangle[16];
        Rectangle[] black_corners = new Rectangle[4];
        Rectangle[] numbering = new Rectangle[16];
        Rectangle[] grey_corner = new Rectangle[5];

        for(int i = 0; i<4;i++)
        {
            black_corners[i] = new Rectangle(3,3);
            black_corners[i].setFill(Color.BLACK);
        }

        gridpane.add(black_corners[0],1,0);
        gridpane.add(black_corners[1],1,9);
        gridpane.add(black_corners[2],10,0);
        gridpane.add(black_corners[3],10,9);

        for(int i = 0; i < 32; i++)
        {
            Rectangle black_field = new Rectangle(80,80);
            black_field.setFill(Color.GRAY);
            black_fields[i] = black_field;
        }

        gridpane.getColumnConstraints().add(new ColumnConstraints(25));
        gridpane.getColumnConstraints().add(new ColumnConstraints(3));

        for(int i = 0;i<8;i++)
        {
            borders[i] = new Rectangle(3,80);
            borders[i+1] = new Rectangle(3,80);
            borders[i+2] = new Rectangle(80,3);
            borders[i+3] = new Rectangle(80,3);
            numbering[i] = new Rectangle(25,80);
            numbering[i+1] = new Rectangle(80,25);
            numbering[i].setFill(Color.web("#d9d9d9"));
            numbering[i+1].setFill(Color.web("#d9d9d9"));

            for(int j = 0; j<4;j++)
            {
                borders[i+j].setFill(Color.BLACK);
            }
            gridpane.add(borders[i],1,i+1);
            gridpane.add(borders[i+1],10,i+1);

            gridpane.add(borders[i+2],i+2,0);
            gridpane.add(borders[i+3],i+2,9);

            gridpane.add(numbering[i], 0, i+1);
            gridpane.add(numbering[i+1], i+2, 10);
        }


        grey_corner[0] = new Rectangle(25,25);
        grey_corner[1] = new Rectangle(25,3);
        grey_corner[2] = new Rectangle(3,25);
        grey_corner[3] = new Rectangle(3,25);
        grey_corner[4] = new Rectangle(25,3);
        for(int i = 0; i<5;i++)
        {
            grey_corner[i].setFill(Color.web("#d9d9d9"));
        }

        gridpane.add(grey_corner[0],0,10);
        gridpane.add(grey_corner[1],0,9);
        gridpane.add(grey_corner[2],1,10);
        gridpane.add(grey_corner[3],10,10);
        gridpane.add(grey_corner[4],0,0);




        Text[] i_letters = new Text[8];
        Text[] i_numbers = new Text[8];

        i_letters[0] = new Text("a");
        i_letters[1] = new Text("b");
        i_letters[2] = new Text("c");
        i_letters[3] = new Text("d");
        i_letters[4] = new Text("e");
        i_letters[5] = new Text("f");
        i_letters[6] = new Text("g");
        i_letters[7] = new Text("h");

        for(int i =0;i<8;i++)
        {
            i_letters[i].setFont(new Font(20));
            gridpane.add(i_letters[i],i+2,10);
            GridPane.setHalignment(i_letters[i], HPos.CENTER);

            i_numbers[i] = new Text(Integer.toString(i+1));
            i_numbers[i].setFont(new Font(20));

            gridpane.add(i_numbers[i],0,8-i);
            GridPane.setHalignment(i_numbers[i], HPos.CENTER);
        }

//        gridpane.addColumn(10);

        int k = 0;
        for(int i = 2; i < 10; i++)
        {
            for(int j = 1; j < 9; j++)
            {
                if((i + j) % 2 == 0)
                {
                    gridpane.add(black_fields[k],i,j);
                    k++;
                }
            }
        }


    }

    /**
     * položí na GUI prázdná políčka
     * @param board abstraktní šachovnice
     *
     */

    void place_empty_figures(Board board)
    {
        int k = 32;
        for(int i = 2;i<10;i++)
        {
            gridpane.add(board.figures[k].background,i,3);
            k++;
        }
        for(int i = 2;i<10;i++)
        {
            gridpane.add(board.figures[k].background,i,4);
            k++;
        }
        for(int i = 2;i<10;i++)
        {
            gridpane.add(board.figures[k].background,i,5);
            k++;
        }
        for(int i = 2;i<10;i++)
        {
            gridpane.add(board.figures[k].background,i,6);
            k++;
        }

        for(int i = 0;i<8;i++)
        {
            for(int j =0;j<8;j++)
            {
                gridpane.add(board.spaces[j][i].background,i+2,j+1);
            }
        }
    }

    /**
     * položí na GUI prázdná políčka, při reinicializaci
     * @param board abstraktní šachovnice
     *
     */
    void place_empty_figures_again(Board board)
    {


        int k = 32;
        for(int i = 2;i<10;i++)
        {
            GridPane.setColumnIndex(board.figures[k].background,i);
            GridPane.setRowIndex(board.figures[k].background,3);
            k++;
        }
        for(int i = 2;i<10;i++)
        {
            GridPane.setColumnIndex(board.figures[k].background,i);
            GridPane.setRowIndex(board.figures[k].background,4);
            k++;
        }
        for(int i = 2;i<10;i++)
        {
            GridPane.setColumnIndex(board.figures[k].background,i);
            GridPane.setRowIndex(board.figures[k].background,5);
            k++;
        }
        for(int i = 2;i<10;i++)
        {
            GridPane.setColumnIndex(board.figures[k].background,i);
            GridPane.setRowIndex(board.figures[k].background,6);
            k++;
        }

        for(int i = 0;i<8;i++)
        {
            for(int j =0;j<8;j++)
            {
                GridPane.setColumnIndex(board.spaces[j][i].background,i+2);
                GridPane.setRowIndex(board.spaces[j][i].background,j+1);
            }
        }
    }

    /**
     *
     * @param board abstraktní šachovnice
     * @throws Exception
     */

    void add_background_figures(Board board) throws Exception
    {

        board.figures[0].add_background("white","rook");
        board.figures[1].add_background("white","knight");
        board.figures[2].add_background("white","bishop");
        board.figures[3].add_background("white","queen");
        board.figures[4].add_background("white","king");
        board.figures[5].add_background("white","bishop");
        board.figures[6].add_background("white","knight");
        board.figures[7].add_background("white","rook");

        for(int i = 0; i<8; i++)
        {
            board.figures[i+8].add_background("white","pawn");
            board.figures[i+24].add_background("black","pawn");
        }

        board.figures[16].add_background("black","rook");
        board.figures[17].add_background("black","knight");
        board.figures[18].add_background("black","bishop");
        board.figures[19].add_background("black","queen");
        board.figures[20].add_background("black","king");
        board.figures[21].add_background("black","bishop");
        board.figures[22].add_background("black","knight");
        board.figures[23].add_background("black","rook");

        for(int i = 32; i<64; i++)
        {
            board.figures[i].add_empty_background();
        }


    }





    public void change_place_figure(Figure chosen, Figure figure)
    {
        GridPane.setColumnIndex(chosen.background,chosen.col+2);
        GridPane.setRowIndex(chosen.background,chosen.row+1);

        GridPane.setColumnIndex(figure.background,figure.col+2);
        GridPane.setRowIndex(figure.background,figure.row+1);
        if(figure.type != Figure.Type.EMPTY)
        {
            figure.type = Figure.Type.EMPTY;
            figure.remove_image();
        }
    }

    void change_figures(Space source, Space target)
    {


        GridPane.setColumnIndex(source.figure.background, source.col+2);
        GridPane.setRowIndex(source.figure.background, source.row+1);


        GridPane.setColumnIndex(target.figure.background, target.col+2);
        GridPane.setRowIndex(target.figure.background, target.row+1);

        if(source.figure.type != Figure.Type.EMPTY)
        {
            source.figure.type = Figure.Type.EMPTY;
            source.figure.remove_image();
            source.is_empty = true;
        }
    }

    void change_figures_back(Space source, Space target, int tt) throws Exception
    {


        GridPane.setColumnIndex(source.figure.background, source.col+2);
        GridPane.setRowIndex(source.figure.background, source.row+1);


        GridPane.setColumnIndex(target.figure.background, target.col+2);
        GridPane.setRowIndex(target.figure.background, target.row+1);

            switch (tt)
            {
                case (0):
                    target.figure.add_image("white", "rook");
                    target.figure.type = Figure.Type.ROOK;
                    target.is_empty = false;
                    break;
                case (1):
                    target.figure.add_image("white", "knight");
                    target.figure.type = Figure.Type.KNIGHT;
                    target.is_empty = false;
                    break;
                case (2):
                    target.figure.add_image("white", "bishop");
                    target.figure.type = Figure.Type.BISHOP;
                    target.is_empty = false;
                    break;
                case (3):
                    target.figure.add_image("white", "queen");
                    target.figure.type = Figure.Type.QUEEN;
                    target.is_empty = false;
                    break;
                case (4):
                    target.figure.add_image("white", "king");
                    target.figure.type = Figure.Type.KING;
                    target.is_empty = false;
                    break;
                case (5):
                    target.figure.add_image("white", "pawn");
                    target.figure.type = Figure.Type.PAWN;
                    target.is_empty = false;
                    break;
                case (6):
                    target.figure.add_image("black", "rook");
                    target.figure.type = Figure.Type.ROOK;
                    target.is_empty = false;
                    break;
                case (7):
                    target.figure.add_image("black", "knight");
                    target.figure.type = Figure.Type.KNIGHT;
                    target.is_empty = false;
                    break;
                case (8):
                    target.figure.add_image("black", "bishop");
                    target.figure.type = Figure.Type.BISHOP;
                    target.is_empty = false;
                    break;
                case (9):
                    target.figure.add_image("black", "queen");
                    target.figure.type = Figure.Type.QUEEN;
                    target.is_empty = false;
                    break;
                case (10):
                    target.figure.add_image("black", "king");
                    target.figure.type = Figure.Type.KING;
                    target.is_empty = false;
                    break;
                case (11):
                    target.figure.add_image("black", "pawn");
                    target.figure.type = Figure.Type.PAWN;
                    target.is_empty = false;
                    break;
            }



        /*


        if(source.figure.type != Figure.Type.EMPTY)
        {
            source.figure.type = Figure.Type.EMPTY;
            source.figure.remove_image();
            source.is_empty = true;
        }*/
    }

    void play_gui()
    {
        HBox buttons = new HBox();
        controls = new Button[7];
        play_box = new VBox();
        play_box.setPrefSize(300,600);

        play_box.setAlignment(Pos.CENTER);
        play_box.setSpacing(20);

        for(int i = 0;i<7;i++)
            controls[i] = new Button();

        controls[0] = new Button();
        controls[0].setText("Open File");

        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setMinSize(200,350);
        scrollpane.setMaxSize(200,350);

        play_box.getChildren().add(scrollpane);
        play_box.getChildren().add(controls[0]);

        notation = new GridPane();
        scrollpane.setContent(notation);

        notation.getColumnConstraints().add(new ColumnConstraints(195));

        controls[1].setText("Play");
        controls[2].setText("Forward");
        controls[3].setText("Backward");

        for(int i = 1;i<6;i++)
        {
            controls[i].setDisable(true);
            buttons.getChildren().add(controls[i]);
        }


        buttons.setAlignment(Pos.CENTER);

        buttons.setSpacing(20);
        play_box.getChildren().add(buttons);


        controls[4].setText("Undo");
        controls[5].setText("Redo");

        HBox undo_redo = new HBox();
        undo_redo.getChildren().addAll(controls[6], controls[4],controls[5]);

        undo_redo.setSpacing(30);
        undo_redo.setAlignment(Pos.CENTER);
        play_box.getChildren().add(undo_redo);

        controls[6].setText("Stop");




    }


    void add_line(String line, int line_number, ArrayList<String> moves, Handle handle)
    {


        if(line_number == 0)
        {
            if(line.length() < 10)
                long_notation = false;
            else
                long_notation = true;
        }
        HBox not_line = new HBox();


        not_line.setMaxWidth(195);
        not_line.setMinWidth(195);
        not_line.setMinHeight(20);
        not_line.setAlignment(Pos.CENTER_LEFT);
        if(line_number % 2 == 0)
        {
            not_line.setStyle("-fx-background-color: #d9d9d9;");
        }

        Text text = new Text(line);
        not_line.getChildren().add(text);
        this.notation_moves.add(not_line);
        this.notation.add(not_line,0,line_number);
        moves.add(line);

        not_line.setOnMouseClicked(handle.eventHandler);

    }

    void add_move_player(String line, int line_number, ArrayList<String> pl_moves, Handle handle)
    {
        HBox not_line = new HBox();


        not_line.setMaxWidth(195);
        not_line.setMinWidth(195);
        not_line.setMinHeight(20);
        not_line.setAlignment(Pos.CENTER_LEFT);
        if(this.notation_moves.size() % 2 == 0)
        {
            not_line.setStyle("-fx-background-color: #d9d9d9;");
        }

        Text text = new Text(line);
        not_line.getChildren().add(text);

        this.notation_moves.add(not_line);
        this.notation.add(not_line,0,line_number);

        pl_moves.add(line);
        not_line.setOnMouseClicked(handle.eventHandler);
    }

    boolean check_notation(String line)
    {
        return line.matches("^\\d\\. \\w{2,}\\+?#? \\w{2,}\\+?#?$");
    }

}
