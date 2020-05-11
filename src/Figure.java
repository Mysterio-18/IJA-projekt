/**
 * @author Dominik Bednář
 * Vytváří jednotlivé figurky.
 *
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.*;

public class Figure {

    boolean chosen;
    boolean is_white;
    boolean moved;
    BorderPane background;
    int row;
    int col;
    Type type;
    private ImageView image;



    Figure(boolean is_white, int r, int c, Space s, Type t, int p) throws Exception
    {
        chosen = false;
        this.is_white = is_white;
        moved = false;
        row = r;
        col = c;
        type = t;
    }

    enum Type{
        PAWN,
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING,
        EMPTY
    }

    void add_background(String color, String type) throws Exception
    {
        String path = "lib/" + color + "_" + type + ".png";
        background = new BorderPane();
        background.setPrefSize(80,80);
        image = new ImageView(new Image(new FileInputStream(path)));
        background.setCenter(image);
    }

    void add_empty_background() throws Exception
    {
        this.background = new BorderPane();
        background.setPrefSize(80,80);
    }

    void add_image(String color, String type) throws Exception
    {
        String path = "lib/" + color + "_" + type + ".png";
        image = new ImageView(new Image(new FileInputStream(path)));
        background.setCenter(image);
    }


    void remove_image()
    {
        this.background.getChildren().remove(image);
    }

    public void choose_change(boolean change)
    {
        this.chosen = change;
    }

    public boolean is_chosen()
    {
        return this.chosen;
    }


/*
    public boolean check_knight(Figure target, boolean w)
    {
        Space shift;
        Space shift_next;

        if((shift = target.space.get_up()) != null)
        {
            if ((shift_next = shift.get_up_right()) != null)
            {
                if(!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
            if ((shift_next = shift.get_up_left()) != null)
            {
                if (!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
        }

        if((shift = target.space.get_down()) != null)
        {
            if ((shift_next = shift.get_down_right()) != null)
            {
                if(!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
            if ((shift_next = shift.get_down_left()) != null)
            {
                if (!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
        }
        if((shift = target.space.get_left()) != null)
        {
            if ((shift_next = shift.get_up_left()) != null)
            {
                if(!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
            if ((shift_next = shift.get_down_left()) != null)
            {
                if (!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
        }

        if((shift = target.space.get_right()) != null)
        {
            if ((shift_next = shift.get_up_right()) != null)
            {
                if(!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
            if ((shift_next = shift.get_down_right()) != null)
            {
                if (!shift_next.is_empty)
                {
                    if(shift_next.figure.is_white == !w && shift_next.figure.type == Type.KNIGHT)
                        return true;
                }
            }
        }

        return false;
    }

    public boolean check_up_left(Figure target, boolean w)
    {
        Space temp_target = null;
        int col = target.col;
        int row = target.row;
        int check;

        if(col != 0 && row != 0)
        {
            temp_target = target.space.get_up_left();
            if((check = this.check_side_one_diag_up(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col--;
            row--;
        }
        while(col > 0 && row > 0)
        {
            temp_target = temp_target.get_up_left();
            if ((check = check_side_more_diag(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col--;
            row--;
        }
        return false;
    }

    public boolean check_up_right(Figure target, boolean w)
    {
        Space temp_target = null;
        int col = target.col;
        int row = target.row;
        int check;

        if(col != 7 && row != 0)
        {
            temp_target = target.space.get_up_right();
            if((check = this.check_side_one_diag_up(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col++;
            row--;
        }
        while(col < 7 && row > 0)
        {
            temp_target = temp_target.get_up_right();
            if ((check = check_side_more_diag(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col++;
            row--;
        }
        return false;
    }

    public boolean check_down_right(Figure target, boolean w)
    {
        Space temp_target = null;
        int col = target.col;
        int row = target.row;
        int check;

        if(col != 7 && row != 7)
        {
            temp_target = target.space.get_down_right();
            if((check = this.check_side_one_diag_down(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col++;
            row++;
        }
        while(col < 7 && row < 7)
        {
            temp_target = temp_target.get_down_right();
            if ((check = check_side_more_diag(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col++;
            row++;
        }
        return false;
    }

    public boolean check_down_left(Figure target, boolean w)
    {
        Space temp_target = null;
        int col = target.col;
        int row = target.row;
        int check;

        if(col != 0 && row != 7)
        {
            temp_target = target.space.get_down_left();
            if((check = this.check_side_one_diag_down(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col--;
            row++;
        }
        while(col != 0 && row != 7)
        {
            temp_target = temp_target.get_down_left();
            if ((check = check_side_more_diag(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col--;
            row++;
        }
        return false;
    }


    public boolean check_right(Figure target, boolean w)
    {
        Space temp_target = null;
        int col = target.col;
        int check;
        if(col != 7)
        {
            temp_target = target.space.get_right();
            if((check = this.check_side_one(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col++;
        }

        while(col < 7)
        {
            temp_target = temp_target.get_right();
            if ((check = check_side_more(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col++;
        }
        return false;
    }

    public boolean check_left(Figure target, boolean w)
    {
        Space temp_target = null;
        int col = target.col;
        int check;
        if(col != 0)
        {
            temp_target = target.space.get_left();
            if((check = this.check_side_one(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col--;
        }

        while(col > 0)
        {
            temp_target = temp_target.get_left();
            if ((check = check_side_more(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            col--;
        }
        return false;
    }

    public boolean check_up(Figure target, boolean w)
    {
        Space temp_target = null;
        int row = target.row;
        int check;
        if(row != 0)
        {
            temp_target = target.space.get_up();
            if((check = this.check_side_one(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            row--;
        }

        while(row > 0)
        {
            temp_target = temp_target.get_up();
            if ((check = check_side_more(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            row--;
        }
        return false;
    }

    public boolean check_down(Figure target, boolean w)
    {

        Space temp_target = null;
        int row = target.row;
        int check;
        if(row != 7)
        {
            temp_target = target.space.get_down();
            if((check = this.check_side_one(temp_target,w)) == 0)
                return true;
            else if(check == 1)
                return false;
            row++;
        }
        while(row < 7)
        {
            temp_target = temp_target.get_down();
            if ((check = check_side_more(temp_target, w)) == 0)
                return true;
            else if(check == 1)
                return false;
            row++;
        }
        return false;
    }

    public int check_side_one(Space target, boolean w)
    {
        boolean wn;
        Figure.Type t;
        if(!target.is_empty)
        {
            wn = target.figure.is_white;
            if(!wn == w)
            {
                t = target.figure.type;
                if(t == Type.KING || t == Type.QUEEN || t == Type.ROOK)
                    return 0;
                else
                    return 1;
            }
            else if(target.figure == this)
                return 2;
            else
                return 1;
        }
        return 2;
    }

    public int check_side_one_diag_down(Space target, boolean w)
    {
        boolean wn;
        Figure.Type t;
        if(!target.is_empty)
        {
            wn = target.figure.is_white;
            if(!wn == w)
            {
                t = target.figure.type;
                if(t == Type.KING || t == Type.QUEEN || t == Type.BISHOP)
                    return 0;
                else if(t == Type.PAWN && !w)
                    return 0;
                else
                    return 1;
            }
            else if(target.figure == this)
                return 2;
            else
                return 1;
        }
        return 2;
    }

    public int check_side_one_diag_up(Space target, boolean w)
    {
        boolean wn;
        Figure.Type t;
        if(!target.is_empty)
        {
            wn = target.figure.is_white;
            if(!wn == w)
            {
                t = target.figure.type;
                if(t == Type.KING || t == Type.QUEEN || t == Type.BISHOP)
                    return 0;
                else if(t == Type.PAWN && w)
                    return 0;
                else
                    return 1;
            }
            else if(target.figure == this)
                return 2;
            else
                return 1;
        }
        return 2;
    }


    public int check_side_more(Space target, boolean w)
    {
        boolean wn;
        Figure.Type t;

        if(!target.is_empty)
        {
            wn = target.figure.is_white;
            if(!wn == w)
            {
                t = target.figure.type;
                if(t == Type.QUEEN || t == Type.ROOK)
                    return 0;
                else
                    return 1;
            }
            else
                return 1;
        }
        return 2;
    }

    public int check_side_more_diag(Space target, boolean w)
    {
        boolean wn;
        Figure.Type t;

        if(!target.is_empty)
        {
            wn = target.figure.is_white;
            if(!wn == w)
            {
                t = target.figure.type;
                if(t == Type.QUEEN || t == Type.BISHOP)
                    return 0;
                else
                    return 1;
            }
            else
                return 1;
        }
        return 2;
    }


    public int check_space(Space start, Space target, boolean w)
    {
        if(start.is_empty)
        {
            if(start == target)
                return 1;
        }
        else
        {
            if((start.figure.is_white && w) || (!start.figure.is_white && !w))
                return 0;
            else
            {
                if(start == target)
                    return 1;
                else
                    return 0;
            }

        }
        return 2;
    }


*/








}
