/**
 * @author Dominik Bednář
 * Hlavní logika pohybu figurek, rozhoduje zda se figurka smí či nesmí pohnout.
 *
 */

import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;


public class Space {

    boolean is_empty;
    int row;
    int col;
    Space[] neighbors;
    Figure figure;
    BorderPane background;
    boolean chosen;

    Space(int row, int col) {
        chosen = false;
        background = new BorderPane();
        this.row = row;
        this.col = col;
        is_empty = true;
        this.neighbors = new Space[8];
        figure = null;
    }

    void put_figure(Figure figure) {
        this.figure = figure;
        this.is_empty = false;
    }

    private void put_figure_unknown(Figure figure)
    {
        this.figure = figure;
        if(this.figure.type == Figure.Type.EMPTY)
            this.is_empty = true;
        else
            this.is_empty = false;
    }

    void put_empty_figure(Figure figure) {
        this.figure = figure;
        this.is_empty = true;
    }

    public Figure get_figure() {
        return this.figure;
    }

    public void remove_figure() {
        this.is_empty = true;
    }

    void add_next_space(Direction dir, Space space) {
        int i = 0;
        for (Direction d : Direction.values())
        {
            if (dir == d)
                break;
            i++;
        }
        this.neighbors[i] = space;
    }

    private Space get_left() {
        return this.neighbors[1];
    }

    private Space get_right() {
        return this.neighbors[4];
    }


    private Space get_up() {
        return this.neighbors[7];
    }

    private Space get_down() {
        return this.neighbors[0];
    }

    private Space get_up_left() {
        return this.neighbors[3];
    }

    private Space get_down_left() {
        return this.neighbors[2];
    }

    private Space get_up_right() {
        return this.neighbors[6];
    }

    private Space get_down_right() {
        return this.neighbors[5];
    }

    enum Direction {

        D,
        L,
        LD,
        LU,
        R,
        RD,
        RU,
        U
    }

    boolean try_move_not(GUI chess_board, Figure.Type type, Figure.Type change, boolean check, boolean checkmate, boolean takes, int move_from_col, int move_from_row, boolean white, Board board, Moved moved) throws Exception {
        Space source;
        if(Figure.Type.ROOK == type)
        {
            source = this.not_move_rook(move_from_col, white, move_from_row);
            if(source != null)
            {
                source.save(this,board,moved);
                source.swap(this);
                chess_board.change_figures(source,this);
                return true;
            }
        }
        else if(Figure.Type.BISHOP == type)
        {
            source = this.not_move_bishop(move_from_col, white, move_from_row);
            if(source != null)
            {
                source.save(this,board,moved);
                source.swap(this);
                chess_board.change_figures(source,this);
                return true;
            }
        }
        else if(Figure.Type.PAWN == type)
        {
            source = this.not_move_pawn(move_from_col, white, move_from_row, takes);
            if(source != null)
            {
                source.save(this,board,moved);
                source.swap(this);
                chess_board.change_figures(source,this);
                return true;
            }
        }
        else if(Figure.Type.KNIGHT == type)
        {
            source = this.not_move_knight(move_from_col, white, move_from_row);
            if(source != null)
            {
                source.save(this,board,moved);
                source.swap(this);
                chess_board.change_figures(source,this);
                return true;
            }
        }
        else if(Figure.Type.KING == type)
        {
            source = this.not_move_king(move_from_col, white, move_from_row);
            if(source != null)
            {
                source.save(this,board,moved);
                source.swap(this);
                chess_board.change_figures(source,this);
                return true;
            }
        }

        else if(Figure.Type.QUEEN == type)
        {
            source = this.not_move_queen(move_from_col, white, move_from_row);
            if(source != null)
            {
                source.save(this,board,moved);
                source.swap(this);
                chess_board.change_figures(source,this);
                return true;
            }
        }

        return false;
    }

    private Space not_move_queen(int move_from_col, boolean w, int move_from_row)
    {
        Space temp;
        temp = not_move_rook(move_from_col,w,move_from_row);
        if(temp != null)
            return temp;

        temp = this.get_up_left();
        while (temp != null)
        {
            if (!temp.is_empty)
            {
                if(temp.figure.type == Figure.Type.QUEEN && temp.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(move_from_col == temp.col)
                            return temp;
                    }
                    else if(move_from_row > -1)
                    {
                        if(move_from_row == temp.row)
                            return temp;
                    }
                }

            }
            temp = temp.get_up_left();
        }

        temp = this.get_down_left();
        while (temp != null)
        {
            if (!temp.is_empty)
            {
                if(temp.figure.type == Figure.Type.QUEEN && temp.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(move_from_col == temp.col)
                            return temp;
                    }
                    else if(move_from_row > -1)
                    {
                        if(move_from_row == temp.row)
                            return temp;
                    }
                }
            }
            temp = temp.get_down_left();
        }

        temp = this.get_up_right();
        while (temp != null)
        {
            if (!temp.is_empty)
            {
                if(temp.figure.type == Figure.Type.QUEEN && temp.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(move_from_col == temp.col)
                            return temp;
                    }
                    else if(move_from_row > -1)
                    {
                        if(move_from_row == temp.row)
                            return temp;
                    }
                }
            }
            temp = temp.get_up_right();
        }

        temp = this.get_down_right();
        while (temp != null)
        {
            if (!temp.is_empty)
            {
                if(temp.figure.type == Figure.Type.QUEEN && temp.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(move_from_col == temp.col)
                            return temp;
                    }
                    else if(move_from_row > -1)
                    {
                        if(move_from_row == temp.row)
                            return temp;
                    }
                }
            }
            temp = temp.get_down_right();

        }
        show_alert_move();
        return null;
    }



    private Space not_move_king(int move_from_col, boolean w, int move_from_row)
    {
        Space temp;

        temp = this.get_up();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }

        temp = this.get_down();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }

        temp = this.get_left();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }

        temp = this.get_right();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }
        temp = this.get_up_left();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }
        temp = this.get_up_right();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }
        temp = this.get_down_left();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }
        temp = this.get_down_right();
        if(temp != null)
        {
            if(temp.figure.type == Figure.Type.KING && temp.figure.is_white == w)
                return temp;
        }
        show_alert_move();
        return  null;

    }


    private Space not_move_knight(int move_from_col, boolean w, int move_from_row)
    {
        Space temp;
        Space temp_next;

        temp = this.get_up();
        if(temp != null)
        {
            temp_next = temp.get_up_left();
            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }
            temp_next = temp.get_up_right();
            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }
        }

        temp = this.get_left();
        if(temp != null)
        {
            temp_next = temp.get_up_left();
            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }

            temp_next = temp.get_down_left();
            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }
        }
        temp = this.get_down();
        if(temp != null)
        {
            temp_next = temp.get_down_left();
            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }
            temp_next = temp.get_down_right();

            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }
        }
        temp = this.get_right();
        if(temp != null)
        {
            temp_next = temp.get_down_right();
            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }
            temp_next = temp.get_up_right();
            if(temp_next != null)
            {
                if(temp_next.figure.type == Figure.Type.KNIGHT && temp_next.figure.is_white == w)
                {
                    if(move_from_col > - 1)
                    {
                        if(temp_next.col == move_from_col)
                            return temp_next;
                    }
                    else if(move_from_row > -1)
                    {
                        if(temp_next.row == move_from_row)
                            return temp_next;
                    }
                    else
                        return temp_next;
                }
            }
        }
        show_alert_move();
        return null;
    }


    private Space not_move_pawn(int move_from_col, boolean w, int move_from_row, boolean takes)
    {
        Space temp;
        if(!takes)
        {
            if(w)
            {
                temp = this.get_down();
                if(temp != null)
                {
                    if(!temp.is_empty)
                    {
                        if(temp.figure.type == Figure.Type.PAWN && temp.figure.is_white)
                            return temp;
                    }
                    else
                    {
                        temp = temp.get_down();
                        if(temp != null)
                        {
                            if(temp.figure.type == Figure.Type.PAWN && temp.figure.is_white && !temp.figure.moved)
                                return temp;
                        }
                    }
                }
            }
            else
            {
                temp = this.get_up();
                if(temp != null)
                {
                    if(!temp.is_empty)
                    {
                        if(temp.figure.type == Figure.Type.PAWN && !temp.figure.is_white)
                            return temp;
                    }
                    else
                    {
                        temp = temp.get_up();
                        if(temp != null)
                        {
                            if(temp.figure.type == Figure.Type.PAWN && !temp.figure.is_white && !temp.figure.moved)
                                return temp;
                        }
                    }
                }
            }
        }
        else
        {
            if (w)
            {
                if(!this.is_empty && !this.figure.is_white)
                {
                    temp = this.get_down_right();
                    if(temp != null)
                    {
                        if(temp.figure.type == Figure.Type.PAWN && temp.figure.is_white && move_from_col == temp.col)
                            return temp;
                    }
                    temp = this.get_down_left();
                    if(temp != null)
                    {
                        if(temp.figure.type == Figure.Type.PAWN && temp.figure.is_white && move_from_col == temp.col)
                            return temp;
                    }
                }
            }
            else
            {
                if(!this.is_empty && this.figure.is_white)
                {
                    temp = this.get_up_right();
                    if(temp != null)
                    {
                        if(temp.figure.type == Figure.Type.PAWN && !temp.figure.is_white && move_from_col == temp.col)
                            return temp;
                    }
                    temp = this.get_up_left();
                    if(temp != null)
                    {
                        if(temp.figure.type == Figure.Type.PAWN && !temp.figure.is_white && move_from_col == temp.col)
                            return temp;
                    }
                }
            }
        }
        show_alert_move();
        return null;
    }

    private Space not_move_bishop(int move_from_col, boolean w, int move_from_row)
    {
        Space temp;
            temp = this.get_up_left();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.BISHOP && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_up_left();
            }

            temp = this.get_down_left();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.BISHOP && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_down_left();
            }

            temp = this.get_up_right();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.BISHOP && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_up_right();
            }

            temp = this.get_down_right();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.BISHOP && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_down_right();

            }
        show_alert_move();
        return null;
    }


    private Space not_move_rook(int move_from_col, boolean w, int move_from_row) {
        Space temp;
        if (move_from_col > -1 || move_from_row > -1)
        {
            if (move_from_col == this.col || move_from_row != this.row)
            {
                temp = this.get_up();
                while (temp != null)
                {
                    if (!temp.is_empty)
                    {
                        if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                            return temp;
                    }
                    temp = temp.get_up();
                }

                temp = this.get_down();
                while (temp != null)
                {
                    if (!temp.is_empty)
                    {
                        if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                            return temp;
                    }
                    temp = temp.get_down();
                }
            } else
            {
                temp = this.get_right();
                while (temp != null)
                {
                    if (!temp.is_empty)
                    {
                        if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                            return temp;
                    }
                    temp = temp.get_right();
                }

                temp = this.get_left();
                while (temp != null)
                {
                    if (!temp.is_empty)
                    {
                        if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                            return temp;
                    }
                    temp = temp.get_left();
                }
            }
        }
        else
        {
            temp = this.get_up();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_up();
            }

            temp = this.get_down();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_down();
            }

            temp = this.get_right();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_right();
            }

            temp = this.get_left();
            while (temp != null)
            {
                if (!temp.is_empty)
                {
                    if(temp.figure.type == Figure.Type.ROOK && temp.figure.is_white == w)
                        return temp;
                }
                temp = temp.get_left();

            }
        }
        show_alert_move();
        return null;
    }


    void show_alert_move()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Neplatný tah");
        alert.showAndWait();
    }

    boolean try_move(Space target, boolean turn_white, Board board, Moved moved)
    {
        if(this.figure.type == Figure.Type.ROOK)
        {
            if(this.try_move_rook(target, turn_white))
            {
                this.save(target,board,moved);
                this.swap(target);
                return true;
            }
        }
        else if(this.figure.type == Figure.Type.BISHOP)
        {
            if(this.try_move_bishop(target, turn_white))
            {
                this.save(target,board,moved);
                this.swap(target);
                return true;
            }
        }
        else if(this.figure.type == Figure.Type.QUEEN)
        {
            if(this.try_move_queen(target, turn_white))
            {
                this.save(target,board,moved);
                this.swap(target);
                return true;
            }
        }
        else if(this.figure.type == Figure.Type.PAWN)
        {
            if(this.try_move_pawn(target, turn_white))
            {

                this.save(target,board,moved);
                this.swap(target);
                return true;
            }
        }
        else if(this.figure.type == Figure.Type.KING)
        {
            if(this.try_move_king(target, turn_white))
            {
                this.save(target,board,moved);
                this.swap(target);
                return true;
            }
        }
        else if(this.figure.type == Figure.Type.KNIGHT)
        {
            if(this.try_move_knight(target, turn_white))
            {
                this.save(target,board,moved);
                this.swap(target);
                return true;
            }
        }
        return false;
    }


    private boolean try_move_knight(Space target, boolean turn_white)
    {
        Space temp;
        Space temp_next;


        temp = this.get_up();
        if(temp != null)
        {
            temp_next = temp.get_up_left();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }

            temp_next = temp.get_up_right();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }
        }

        temp = this.get_down();
        if(temp != null)
        {
            temp_next = temp.get_down_left();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }

            temp_next = temp.get_down_right();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }
        }

        temp = this.get_left();
        if(temp != null)
        {
            temp_next = temp.get_up_left();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }

            temp_next = temp.get_down_left();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }
        }

        temp = this.get_right();
        if(temp != null)
        {
            temp_next = temp.get_down_right();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }

            temp_next = temp.get_up_right();
            if(temp_next != null)
            {
                if(temp_next == target)
                {
                    if(temp_next.is_empty)
                        return true;
                    else
                        return !(this.figure.is_white == target.figure.is_white);
                }
            }
        }
        return false;
    }


    private boolean try_move_king(Space target, boolean turn_white)
    {
        Space temp = this.get_up();
        if(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
            }
            else
            {
                if(temp == target)
                    return true;
            }

        }

        temp = this.get_down();
        if(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
            }
            else
            {
                if(temp == target)
                    return true;
            }


        }

        temp = this.get_left();
        if(temp != null)
        {  if(!temp.is_empty)
        {
            if(temp == target)
                return !(temp.figure.is_white == turn_white);
        }
        else
        {
            if(temp == target)
                return true;
        }

        }
        temp = this.get_right();
        if(temp != null)
        {

            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
            }
            else
            {
                if(temp == target)
                    return true;
            }
        }
        temp = this.get_up_left();
        if(temp != null)
        {

            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
            }
            else
            {
                if(temp == target)
                    return true;
            }
        }

        temp = this.get_up_right();
        if(temp != null)
        {


            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
            }
            else
            {
                if(temp == target)
                    return true;
            }
        }



        temp = this.get_down_left();
        if(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
            }
            else
            {
                if(temp == target)
                    return true;
            }
        }


        temp = this.get_down_right();
        if(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
            }
            else
            {
                if(temp == target)
                    return true;
            }
        }



        return false;
    }

    private boolean try_move_pawn(Space target, boolean turn_white)
    {
        Space temp;
        if(turn_white)
        {



            temp = this.get_up();
            if(temp != null)
            {
                if(temp == target)
                {
                    return temp.is_empty;
                }

                else if(temp.is_empty && !this.figure.moved)
                {
                    temp = temp.get_up();
                    if(temp != null)
                    {
                        if(temp == target)
                            return temp.is_empty;
                    }
                }
            }
            temp = this.get_up_right();
            if(temp != null)
            {
                if(temp == target)
                    return (!temp.is_empty && !temp.figure.is_white);
            }
            temp = this.get_up_left();
            if(temp != null)
            {
                if(temp == target)
                    return (!temp.is_empty && !temp.figure.is_white);
            }

        }
        else
        {
            temp = this.get_down();
            if(temp != null)
            {
                if(temp == target)
                    return temp.is_empty;
                else if(temp.is_empty && !this.figure.moved)
                {
                    temp = temp.get_down();
                    if(temp != null)
                    {
                        if(temp == target)
                            return temp.is_empty;
                    }
                }
            }
            temp = this.get_down_right();
            if(temp != null)
            {
                if(temp == target)
                    return (!temp.is_empty && temp.figure.is_white);
            }
            temp = this.get_down_left();
            if(temp != null)
            {
                if(temp == target)
                    return (!temp.is_empty && temp.figure.is_white);
            }

        }
        return false;
    }


    private boolean try_move_queen(Space target, boolean turn_white)
    {
        if(try_move_rook(target, turn_white))
            return true;
        else
            return try_move_bishop(target,turn_white);
    }

    private boolean try_move_rook(Space target, boolean turn_white)
    {

        Space temp = this.get_up();
        while(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
                else
                    break;
            }
            else
            {
                if(temp == target)
                    return true;
            }
            temp = temp.get_up();
        }

        temp = this.get_down();
        while(temp != null)
        {if(!temp.is_empty)
        {
            if(temp == target)
                return !(temp.figure.is_white == turn_white);
            else
                break;
        }
        else
        {
            if(temp == target)
                return true;
        }
            temp = temp.get_down();
        }

        temp = this.get_left();
        while(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
                else
                    break;
            }
            else
            {
                if(temp == target)
                    return true;
            }
            temp = temp.get_left();
        }

        temp = this.get_right();
        while(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
                else
                    break;
            }
            else
            {
                if(temp == target)
                    return true;
            }
            temp = temp.get_right();
        }
        return false;
    }

    private boolean try_move_bishop(Space target, boolean turn_white)
    {

        Space temp = this.get_up_left();
        while(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
                else
                    break;
            }
            else
            {
                if(temp == target)
                    return true;
            }
            temp = temp.get_up_left();
        }

        temp = this.get_down_left();
        while(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
                else
                    break;
            }
            else
            {
                if(temp == target)
                    return true;
            }
            temp = temp.get_down_left();
        }

        temp = this.get_up_right();
        while(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
                else
                    break;
            }
            else
            {
                if(temp == target)
                    return true;
            }
            temp = temp.get_up_right();
        }

        temp = this.get_down_right();
        while(temp != null)
        {
            if(!temp.is_empty)
            {
                if(temp == target)
                    return !(temp.figure.is_white == turn_white);
                else
                    break;
            }
            else
            {
                if(temp == target)
                    return true;
            }
            temp = temp.get_down_right();
        }
        return false;
    }


    void swap(Space target)
    {

       Figure temp = this.figure;

       this.put_figure_unknown(target.figure);
       target.put_figure_unknown(temp);

       this.figure.moved = true;
       target.figure.moved = true;

    }

    private void save(Space target, Board board, Moved moved)
    {
        moved.save_move(this,target,board);
    }

}
