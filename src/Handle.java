/**
 * @author Dominik Bednář
 * Obsluhuje interakci uživatele.
 *
 */


import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;

import java.io.*;

import java.util.ArrayList;



class Handle {

    private boolean turn_white;
    boolean move_succes;
    private int act_move;
    private int move_to_row;
    private int move_to_col;
    private int move_from_row;
    private int move_from_col;
    private boolean takes;
    private boolean check;
    private boolean checkmate;
    private Figure.Type change;
    private Figure.Type type;
    boolean end;
    private boolean white;
    EventHandler<MouseEvent> eventHandler;
    private int player_moves;
    private String done;
    private String done_whole;
    private Figure.Type type_notation;
    private int player_sets;
    private boolean odd;

    Handle()
    {
        player_moves = 0;
        act_move = 0;
        player_sets = 0;
        odd = false;
    }

    void handle_game(GUI chess_board, Board board, ArrayList<String> moves, Moved moved, ArrayList<String> pl_moves)
    {

        turn_white = true;

            eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    Object source = e.getSource();

                    for(int i = 0;i<chess_board.notation_moves.size();i++)
                    {
                        if(source == chess_board.notation_moves.get(i))
                            jump(chess_board,moves,board,moved,i);
                    }


                    if (source == chess_board.controls[2])
                    {

                        step(chess_board, moves, board, moved);
                    }
                    else if( source == chess_board.controls[1])
                    {
                        play_game(chess_board,moved,board,moves);
                    }
                    else if (source == chess_board.controls[5])
                    {
                        player_redo_move(chess_board,moved,board);
                    }

                    else if (source == chess_board.controls[4])
                    {
                        player_undo_move(chess_board,moved,board);
                    }

                    else if (source == chess_board.controls[3])
                    {

                        step_back(chess_board, moves, board, moved);
                    }
                    else
                    {

                        for (int i = 0; i < 8; i++)
                        {

                            for (int j = 0; j < 8; j++)
                            {
                                if (source == board.spaces[i][j].background)
                                {
                                    if (!board.spaces[i][j].is_empty)
                                    {
                                        if (turn_white && board.spaces[i][j].figure.is_white)
                                        {

                                            cancel_chosen(board);
                                            board.spaces[i][j].figure.background.setStyle("-fx-background-color: #ffffcc;");
                                            board.spaces[i][j].chosen = true;
                                        } else if (!turn_white && !board.spaces[i][j].figure.is_white)
                                        {
                                            cancel_chosen(board);
                                            board.spaces[i][j].figure.background.setStyle("-fx-background-color: #ffffcc;");
                                            board.spaces[i][j].chosen = true;
                                        } else
                                        {
                                            try
                                            {
                                                if (find_chosen_and_try_move(board, turn_white, board.spaces[i][j], chess_board, moved, moves, pl_moves))
                                                {
                                                    cancel_chosen(board);


                                                    change_turn();
                                                }
                                            } catch (Exception ex)
                                            {
                                                ex.printStackTrace();
                                            }
                                        }
                                    } else
                                    {
                                        try
                                        {
                                            if (find_chosen_and_try_move(board, turn_white, board.spaces[i][j], chess_board, moved, moves, pl_moves))
                                            {
                                                cancel_chosen(board);
                                                change_turn();
                                            }
                                        } catch (Exception ex)
                                        {
                                            ex.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }




                }
            };

            for(int i = 0; i<8;i++)
            {
                for(int j = 0;j<8;j++)
                {
                    board.spaces[i][j].background.setOnMouseClicked(eventHandler);
                }
            }

            chess_board.controls[1].setOnMouseClicked(eventHandler);
            chess_board.controls[2].setOnMouseClicked(eventHandler);
            chess_board.controls[3].setOnMouseClicked(eventHandler);
            chess_board.controls[4].setOnMouseClicked(eventHandler);
            chess_board.controls[5].setOnMouseClicked(eventHandler);



    }



    private boolean find_chosen_and_try_move(Board board, boolean turn_white, Space target, GUI chess_board, Moved moved, ArrayList<String> moves, ArrayList<String> pl_moves) throws Exception
    {
        for(int i = 0;i<8;i++)
        {
            for(int j =0;j<8;j++)
            {
                if(board.spaces[i][j].chosen)
                {
                    if(board.spaces[i][j].try_move(target, turn_white, board, moved))
                    {
                        int rowt = 8 -target.row;
                        int colt = target.col;

                        int rowf = 8 -board.spaces[i][j].row;
                        int colf = board.spaces[i][j].col;

                        type_notation =target.figure.type;


                        target.figure.background.setStyle("-fx-background-color: transparent;");
                        chess_board.change_figures(board.spaces[i][j], target);
                        check_and_remove_player_notations(board, chess_board,pl_moves);
                        board.moves_undo = 0;
                        chess_board.controls[5].setDisable(true);
                        player_moves++;
                        chess_board.controls[4].setDisable(false);
                        check_and_remove_notation(moves, chess_board);




                        String l_type = type_to_letter(type_notation);
                        if(turn_white)
                        {
                            player_sets++;
                            odd = true;
                            int line = chess_board.notation_moves.size();
                            String row_str = Integer.toString(rowt);
                            String col_str = col_to_letter(colt);
                            String row_strf = Integer.toString(rowf);
                            String col_strf = col_to_letter(colf);
                            String index = Integer.toString(line+1);
                            if(l_type.equals("A"))
                                done = index + " " + col_strf + row_strf + col_str + row_str;
                            else
                                done = index + " " + l_type + col_strf + row_strf + col_str + row_str;


                            chess_board.add_move_player(done,line,pl_moves,this);
                            if(player_sets > 1)
                            {
                                if(player_sets % 2 == 0)
                                    chess_board.notation_moves.get(act_move+player_sets-2).setStyle("-fx-background-color: WHITE;");
                                else
                                   chess_board.notation_moves.get(act_move+player_sets-2).setStyle("-fx-background-color: #d9d9d9;");
                            }

                            chess_board.notation_moves.get(act_move+player_sets-1).setStyle("-fx-background-color: #ffffcc;");

                        }
                        else
                        {
                            odd = false;

                            chess_board.notation_moves.remove(chess_board.notation_moves.size()-1);
                            int line = chess_board.notation_moves.size();
                            String row_str = Integer.toString(rowt);
                            String col_str = col_to_letter(colt);
                            String row_strf = Integer.toString(rowf);
                            String col_strf = col_to_letter(colf);
                            if(l_type.equals("A"))
                                done_whole =  done + " " + col_strf + row_strf + col_str + row_str;
                            else
                                done_whole =  done + " " + l_type + col_strf + row_strf + col_str + row_str;
                            chess_board.add_move_player(done_whole,line,pl_moves,this);
                            chess_board.notation_moves.get(act_move+player_sets-1).setStyle("-fx-background-color: #ffffcc;");
                        }
                        return true;
                    }
                }
            }

        }
        return false;
    }


    private String type_to_letter(Figure.Type type)
    {
        if(type == Figure.Type.PAWN)
            return "A";
        else if(type == Figure.Type.ROOK)
            return "V";
        else if (type == Figure.Type.QUEEN)
            return "D";
        else if (type == Figure.Type.KING)
            return "K";
        else if (type == Figure.Type.BISHOP)
            return "S";
        else if (type == Figure.Type.KNIGHT)
            return "J";
        else
            return "Z";
    }

    private void check_and_remove_notation(ArrayList<String> moves, GUI chess_board)
    {
        int in_notation = moves.size();

        int to_remove = in_notation - act_move;

        for(int i = 0; i<to_remove;i++)
        {
            chess_board.notation.getChildren().remove(moves.size()-1);
            moves.remove(moves.size()-1);
            chess_board.notation_moves.remove(chess_board.notation_moves.size()-1);
        }

        chess_board.controls[2].setDisable(true);
        chess_board.controls[3].setDisable(true);

    }

    private void check_and_remove_player_notations(Board board, GUI chess_board, ArrayList<String> pl_moves)
    {


       for(int i = 0; i <board.moves_undo;i++)
       {
           chess_board.notation.getChildren().clear();
           if(chess_board.notation_moves.size() != 0)
           {

               chess_board.notation_moves.remove(chess_board.notation_moves.size()-1);
           }


       }
    }


    private void cancel_chosen(Board board)
    {
        for (int i = 0;i<8;i++)
        {
            for(int j =0;j<8;j++)
            {
                if(board.spaces[i][j].chosen)
                {
                    board.spaces[i][j].figure.background.setStyle("-fx-background-color: transparent;");
                    board.spaces[i][j].chosen = false;
                }
            }
        }


    }

    private void change_turn()
    {
        turn_white = !turn_white;
    }

    void handle_buton_file(GUI gui, ArrayList<String> moves, Handle handle, Board board, Moved moved)
    {
        FileChooser filec = new FileChooser();
        filec.setTitle("Open Chess game");

        EventHandler<MouseEvent> event_handler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e)
            {
                File file = filec.showOpenDialog(null);
                if(file != null)
                {
                    handle.act_move = 0;
                    gui.notation.getChildren().removeAll(gui.notation.getChildren());
                    moves.clear();

                    try
                    {
                        board.Place_figures_again(gui);
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    gui.place_empty_figures_again(board);
                    gui.notation_moves.clear();
                    board.move_number = 0;
                    turn_white = true;
                    gui.controls[3].setDisable(true);
                    cancel_chosen(board);
                    board.moves_undo = 0;
                    handle.player_moves = 0;
                    handle.player_sets = 0;

                    gui.controls[4].setDisable(true);
                    gui.controls[5].setDisable(true);


                    String str_line;
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));

                        str_line = br.readLine();
                        int line_number = 0;
                        while (str_line != null)
                        {
                            if(!gui.check_notation(str_line))
                            {
                                show_alert();
                                return;
                            }

                            str_line = br.readLine();
                            line_number++;
                        }


                        br = new BufferedReader(new FileReader(file));
                        str_line = br.readLine();
                        line_number = 0;

                        while (str_line != null)
                        {
                            gui.add_line(str_line, line_number,moves,handle);
                            str_line = br.readLine();
                            line_number++;

                        }




                        for(int i = 1;i<3;i++)
                        {
                            gui.controls[i].setDisable(false);
                        }
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

        };


        gui.controls[0].setOnMouseClicked(event_handler);
    }

    private void play_game(GUI chess_board, Moved moved, Board board, ArrayList<String> moves)
    {

        chess_board.controls[0].setDisable(true);
        chess_board.controls[1].setDisable(true);
        chess_board.controls[2].setDisable(true);
        chess_board.controls[3].setDisable(true);
        chess_board.controls[4].setDisable(true);
        chess_board.controls[5].setDisable(true);

        if(chess_board.controls[1].getText().equals("Play"))
        {

/*
            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {

                            Thread.sleep(2000);
                            step(chess_board,moves,board,moved);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            thread.start();

            chess_board.controls[6].setOnMouseClicked((e) -> {

            thread.interrupt();
            });
        }

        chess_board.controls[0].setDisable(false);
        chess_board.controls[3].setDisable(false);
*/
        }
    }



    // param i -> kam kliknul uživatel

    private void jump(GUI chess_board, ArrayList<String> moves, Board board, Moved moved, int i)
    {
        int in_notation = moves.size();

//        int sum_of_moves = act_move *2 + player_moves;

  //      if(i > act_move)
     //   {
    //      int steps = i - act_move;
     //       if(steps >= 0)
       //     {
         //       for(int j = 0; j <= steps; j++)
           //     {
             //       step(chess_board,moves,board,moved);
               // }
           // }
          //  else
           // //{
             //   for(int j =0; j>steps+1;j--)
              //  {
               //     step_back(chess_board,moves,board, moved);
               // }
          //  }
       // }


        {
            int steps = i - act_move;
            if(steps >= 0)
            {
                for(int j = 0; j <= steps; j++)
                {
                    step(chess_board,moves,board,moved);
                }
            }
            else
            {
                for(int j =0; j>steps+1;j--)
                {
                    step_back(chess_board,moves,board, moved);
                }
            }
        }









    }



    private void step(GUI chess_board, ArrayList<String> moves, Board board, Moved moved)
    {

                cancel_chosen(board);
                String move = moves.get(act_move);

                chess_board.notation_moves.get(act_move).setStyle("-fx-background-color: #ffffcc;");
                if(act_move > 0)
                {
                    if(act_move % 2 == 1)
                    {
                        chess_board.notation_moves.get(act_move-1).setStyle("-fx-background-color: #d9d9d9;");
                    }
                    else
                        chess_board.notation_moves.get(act_move-1).setStyle("-fx-background-color: transparent;");
                }


                int move_number = Integer.parseInt(move.substring(0,1));
                if(move_number != act_move+1)
                {
                   show_alert();
                    return;
                }

                if(chess_board.long_notation)
                {
                    String[] split = move.split(" ");
                    move = split[1] + " ";

                    String move_second = split[2] + " ";
                    if(long_notation(move))
                    {
                        Space move_to = board.get_space(move_to_row,move_to_col);
                        Space move_from = board.get_space(move_from_row,move_from_col);
                        white = true;
                        try
                        {
                            if(!move_from.try_move(move_to,true,board,moved))
                            {

                                move_from.show_alert_move();
                                return;
                            }
                            else
                                chess_board.change_figures(move_from,move_to);
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        if(long_notation(move_second))
                        {
                            white = false;
                            move_to = board.get_space(move_to_row, move_to_col);
                            move_from = board.get_space(move_from_row, move_from_col);


                            try
                            {

                                if (!move_from.try_move(move_to, false, board, moved))
                                {

                                    move_from.show_alert_move();
                                    return;
                                } else
                                    chess_board.change_figures(move_from, move_to);
                            } catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                else
                {   String[] split = move.split(" ");
                    move = split[1] + " ";
                    String move_second = split[2] + " ";
                    if(short_notation(move))
                    {

                        Space move_to = board.get_space(move_to_row,move_to_col);
                        white = true;
                        try
                        {
                            if(!move_to.try_move_not(chess_board, type, change,check,checkmate,takes,move_from_col,move_from_row, white,board,moved))

                                return;
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        if(short_notation(move_second))
                        {
                            white = false;
                            move_to = board.get_space(move_to_row,move_to_col);
                            try
                            {

                                if(!move_to.try_move_not(chess_board, type, change,check,checkmate,takes,move_from_col,move_from_row, white,board,moved))
                                    return;
                            } catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                act_move++;
            if(moves.size() == act_move)
            {
                chess_board.controls[2].setDisable(true);
            }
        chess_board.controls[3].setDisable(false);

    }



    private void player_redo_move(GUI chess_board, Moved moved, Board board)
    {
        board.moves_undo--;
        int i = board.moves_undo;
        int sr = moved.rows_from_f[i];
        int sc = moved.cols_from_f[i];


        int tr = moved.rows_to_f[i];
        int tc = moved.cols_to_f[i];



        board.spaces[sr][sc].swap(board.spaces[tr][tc]);

        try
        {
            chess_board.change_figures(board.spaces[sr][sc],board.spaces[tr][tc]);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        board.move_number++;
        player_moves++;
        change_turn();
        if(board.moves_undo == 0)
        {
            chess_board.controls[5].setDisable(true);
        }

        chess_board.controls[4].setDisable(false);
    }

    private void player_undo_move(GUI chess_board, Moved moved, Board board)
    {

        board.move_number--;
        int i = board.move_number;
        int sr = moved.rows_from[i];
        int sc = moved.cols_from[i];


        int tr = moved.rows_to[i];
        int tc = moved.cols_to[i];

        int tt = moved.type_to[i];

        moved.future_move(board.spaces[sr][sc],board.spaces[tr][tc],board);

        board.spaces[sr][sc].swap(board.spaces[tr][tc]);

        try
        {
            chess_board.change_figures_back(board.spaces[sr][sc],board.spaces[tr][tc],tt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if(board.spaces[sr][sc].figure.type == Figure.Type.PAWN)
        {
            if(board.spaces[sr][sc].row == 6 && board.spaces[sr][sc].figure.is_white)
                board.spaces[sr][sc].figure.moved = false;
            if(board.spaces[sr][sc].row == 1 && !board.spaces[sr][sc].figure.is_white)
                board.spaces[sr][sc].figure.moved = false;
        }

        change_turn();
        odd = !odd;
        if(turn_white)
        {
            player_sets--;
            if(player_sets > -1)
            {

                if(player_sets % 2 == 0)
                    chess_board.notation_moves.get(act_move+player_sets).setStyle("-fx-background-color: WHITE;");
                else
                    chess_board.notation_moves.get(act_move+player_sets).setStyle("-fx-background-color: #d9d9d9;");
                if(!(player_sets == 0))
                    chess_board.notation_moves.get(act_move+player_sets-1).setStyle("-fx-background-color: #ffffcc;");
            }


        }


        player_moves--;
        if(player_moves == 0)
        {
            chess_board.controls[4].setDisable(true);
        }
        chess_board.controls[5].setDisable(false);


    }

    private void step_back(GUI chess_board, ArrayList<String> moves, Board board, Moved moved)
    {


        cancel_chosen(board);
        for(int j = 0;j<2;j++)
        {
            board.move_number--;
            int i = board.move_number;
            int sr = moved.rows_from[i];
            int sc = moved.cols_from[i];


            int tr = moved.rows_to[i];
            int tc = moved.cols_to[i];

            int tt = moved.type_to[i];


            board.spaces[sr][sc].swap(board.spaces[tr][tc]);

            try
            {
                chess_board.change_figures_back(board.spaces[sr][sc],board.spaces[tr][tc],tt);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            if(board.spaces[sr][sc].figure.type == Figure.Type.PAWN)
            {
                if(board.spaces[sr][sc].row == 6 && board.spaces[sr][sc].figure.is_white)
                    board.spaces[sr][sc].figure.moved = false;
                if(board.spaces[sr][sc].row == 1 && !board.spaces[sr][sc].figure.is_white)
                    board.spaces[sr][sc].figure.moved = false;
            }

        }


        chess_board.controls[2].setDisable(false);

        if(board.move_number == 0)
        {
            chess_board.controls[3].setDisable(true);
        }
        act_move--;
        if(act_move > 0)
        {
            chess_board.notation_moves.get(act_move-1).setStyle("-fx-background-color: #ffffcc;");
        }

        if(act_move % 2 == 0)
        {
            chess_board.notation_moves.get(act_move).setStyle("-fx-background-color: #d9d9d9;");
        }
        else
            chess_board.notation_moves.get(act_move).setStyle("-fx-background-color: transparent;");
    }




    private void show_alert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Neplatný formát notace");
        alert.showAndWait();
    }

    private int letter_to_col(String s)
    {
        switch (s)
        {
            case ("a"):
                return 0;
            case ("b"):
                return 1;
            case ("c"):
                return 2;
            case ("d"):
                return 3;
            case ("e"):
                return 4;
            case ("f"):
                return 5;
            case ("g"):
                return 6;
            case ("h"):
                return 7;

        }
        return -1;
    }

    private String col_to_letter(int i)
    {
        switch (i)
        {
            case (0):
                return "a";
            case (1):
                return "b";
            case (2):
                return "c";
            case (3):
                return "d";
            case (4):
                return "e";
            case (5):
                return "f";
            case (6):
                return "g";
            case (7):
                return "h";

        }
        return "z";
    }


    private boolean long_notation(String move)
    {
        String[] moves = new String[7];

        for(int i = 0 ; i<7;i++)
        {
            moves[i] = "z";
        }

        for(int i = 0 ; i<move.length();i++)
        {
            moves[i] = move.substring(i,i+1);
        }

        takes = false;
        check = false;
        checkmate = false;
        change = Figure.Type.EMPTY;
        move_to_row = -1;
        move_to_col = -1;
        move_from_col = -1;
        move_from_row = -1;

        switch(moves[0]) {
            case("V"):
                type = Figure.Type.ROOK;
                break;
            case("J"):
                type = Figure.Type.KNIGHT;
                break;
            case("S"):
                type = Figure.Type.BISHOP;
                break;
            case("D"):
                type = Figure.Type.QUEEN;
                break;
            case("K"):
                type = Figure.Type.KING;
                break;
            default:
                if(moves[0].matches("[a-h]"))
                {
                    type = Figure.Type.PAWN;
                    move_from_col = letter_to_col(moves[0]);
                }

                else
                {
                    show_alert();
                    return false;
                }
                break;
        }

        int return_value;
        if(type == Figure.Type.PAWN)
        {
            if(moves[1].matches("[0-8]"))
            {
                move_from_row = 8 -Integer.parseInt(moves[1]);
                return_value = parse_long_pawn(moves[2], moves[3], moves[4], moves[5]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
            }

            if(moves[2].equals("x"))
            {
                takes = true;
                move_from_row = 8 -Integer.parseInt(moves[1]);
                return_value = parse_long_pawn(moves[3], moves[4], moves[5], moves[6]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
            }
            else
            {

                show_alert();
                return false;
            }
        }
        else
        {
            if(moves[1].matches("[a-h]") && moves[2].matches("[0-8]"))
            {
                move_from_col = letter_to_col(moves[1]);
                move_from_row = 8 - Integer.parseInt(moves[2]);
                return_value = check_next_type_figure(moves[3], moves[4], moves[5]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
            }
            if(moves[3].equals("x"))
            {
                takes = true;
                return_value = check_next_type_figure(moves[4], moves[5], moves[6]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
            }
        }
        return false;

    }

    private int parse_long_pawn(String s2, String s3, String s4, String s5)
    {
        if(s2.matches("[a-h]") && s3.matches("[0-8]"))
        {
            move_to_col = letter_to_col(s2);
            move_to_row = 8 - Integer.parseInt(s3);
            switch (s4)
            {
                case (" "):
                    break;
                case ("+"):
                    check = true;
                    break;
                case ("#"):
                    checkmate = true;
                    break;
                case ("V"):
                    if (!check_if_next(s5))
                        return 1;
                    change = Figure.Type.ROOK;
                    break;
                case ("J"):
                    if (!check_if_next(s5))
                        return 1;
                    change = Figure.Type.KNIGHT;
                    break;
                case ("S"):
                    if (!check_if_next(s5))
                        return 1;
                    change = Figure.Type.BISHOP;
                    break;
                case ("D"):
                    if (!check_if_next(s5))
                        return 1;
                    change = Figure.Type.QUEEN;
                    break;
                default:

                    show_alert();
                    return 1;
            }
            return 0;
        }
        else
            return 2;
    }



    private boolean short_notation(String move)
    {
        String[] moves = new String[6];

        for(int i = 0 ; i<6;i++)
        {
            moves[i] = "z";
        }

        for(int i = 0 ; i<move.length();i++)
        {
            moves[i] = move.substring(i,i+1);
        }


        takes = false;
        check = false;
        checkmate = false;
        change = Figure.Type.EMPTY;
        move_to_row = -1;
        move_to_col = -1;
        move_from_col = -1;
        move_from_row = -1;

        switch(moves[0]) {
            case("V"):
                type = Figure.Type.ROOK;
                break;
            case("J"):
                type = Figure.Type.KNIGHT;
                break;
            case("S"):
                type = Figure.Type.BISHOP;
                break;
            case("D"):
                type = Figure.Type.QUEEN;
                break;
            case("K"):
                type = Figure.Type.KING;
                break;
            default:
                if(moves[0].matches("[a-h]"))
                    type = Figure.Type.PAWN;
                else
                {
                    show_alert();
                    return false;
                }
                break;
        }




        int return_value;
        if(type == Figure.Type.PAWN)
        {
            if((return_value = check_next_type_pawn(moves[0],moves[1],moves[2],moves[3])) == 0)
                return true;
            else if(return_value == 1)
                return false;

            if(moves[1].equals("x") && moves[2].matches("[a-h]"))
            {
                takes = true;
                move_from_col = letter_to_col(moves[0]);
                if(check_next_type_pawn(moves[2],moves[3],moves[4],moves[5]) == 0)
                    return true;
                else
                    return false;
            }
            else
            {

                show_alert();
                return false;
            }
        }
        else
        {
            if(moves[1].matches("[1-8]"))
            {
                move_from_row = 8 - Integer.parseInt(moves[1]);

                return_value = check_next_type_figure(moves[2],moves[3],moves[4]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
                else if(moves[2].equals("x"))
                {
                    takes = true;

                    return_value = check_next_type_figure(moves[3],moves[4],moves[5]);
                    if(return_value == 0)
                        return true;
                    else if(return_value == 1)
                        return false;
                }
            }

            if(moves[1].equals("x"))
            {
                takes = true;
                return_value = check_next_type_figure(moves[2],moves[3],moves[4]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
            }
            if(moves[1].matches("[a-h]"))
            {
                move_from_col = letter_to_col(moves[1]);
                return_value = check_next_type_figure(moves[2],moves[3],moves[4]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
            }
            else if(moves[1].equals("x"))
            {
                takes = true;
                return_value = check_next_type_figure(moves[2],moves[3],moves[4]);
                if(return_value == 0)
                    return true;
                else if(return_value == 1)
                    return false;
            }
            else if(check_next_type_figure(moves[1],moves[2],moves[3]) == 0)
                return true;
        }
        return false;
    }

    private int check_next_type_pawn(String first, String second, String third, String check_mate)
    {
        if(second.matches("[1-8]"))
        {
            move_to_row = 8 - Integer.parseInt(second);
            move_to_col = letter_to_col(first);
            switch (third) {
                case(" "):
                    break;
                case("+"):
                    check = true;
                    break;
                case("#"):
                    checkmate = true;
                    break;
                case("V"):
                    if(!check_if_next(check_mate))
                        return 1;
                    change = Figure.Type.ROOK;
                    break;
                case("J"):
                    if(!check_if_next(check_mate))
                        return 1;
                    change = Figure.Type.KNIGHT;
                    break;
                case("S"):
                    if(!check_if_next(check_mate))
                        return 1;
                    change = Figure.Type.BISHOP;
                    break;
                case("D"):
                    if(!check_if_next(check_mate))
                        return 1;
                    change = Figure.Type.QUEEN;
                    break;
                default:

                    show_alert();
                    return 1;
            }
            return 0;
        }
        else
            return 2;
    }

    private boolean check_if_next(String next)
    {
        switch (next) {
            case("+"):
                check = true;
                return true;
            case("#") :
                checkmate = true;
                return true;
            case(" "):
                return true;
            default:
                show_alert();
                return false;
        }
    }

    private int check_next_type_figure(String first, String second, String third)
    {
        if(first.matches("[a-h]"))
        {
            move_to_col = letter_to_col(first);
            if(second.matches("[1-8]"))
            {
                move_to_row = 8 - Integer.parseInt(second);
                if(check_if_next(third))
                    return 0;
                else
                    return 1;
            }
            else
            {
                show_alert();
                return 1;
            }

        }
        else
        {

            return 2;
        }

    }

}


/*
1. e4 e5
2. Sc4 Df6

1. e2e4 e7e5
2. Sf1c4 Dd8f6*/