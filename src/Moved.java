public class Moved {


    int[] rows_from;
    int[] cols_from;
    int[] rows_to;
    int[] cols_to;
    int[] type_to;

    int[] rows_from_f;
    int[] cols_from_f;
    int[] rows_to_f;
    int[] cols_to_f;
    int[] type_to_f;

    public Moved()
    {
        rows_from = new int[50];
        cols_from = new int[50];
        rows_to = new int[50];
        cols_to = new int[50];
        type_to = new int[50];

        rows_from_f = new int[50];
        cols_from_f = new int[50];
        rows_to_f = new int[50];
        cols_to_f = new int[50];
        type_to_f = new int[50];
    }

    public void future_move(Space from, Space to, Board board)
    {
        int i = board.moves_undo;

        this.rows_from_f[i] = from.row;
        this.cols_from_f[i] = from.col;

        if(to.figure.is_white)
        {
            if(to.figure.type == Figure.Type.ROOK)
                this.type_to_f[i] = 0;
            else if(to.figure.type == Figure.Type.KNIGHT)
                this.type_to_f[i] = 1;
            else if(to.figure.type == Figure.Type.BISHOP)
                this.type_to_f[i] = 2;
            else if(to.figure.type == Figure.Type.QUEEN)
                this.type_to_f[i] = 3;
            else if(to.figure.type == Figure.Type.KING)
                this.type_to_f[i] = 4;
            else if(to.figure.type == Figure.Type.PAWN)
                this.type_to_f[i] = 5;
            else
                this.type_to_f[i] = -1;
        }
        else
        {
            if(to.figure.type == Figure.Type.ROOK)
                this.type_to_f[i] = 6;
            else if(to.figure.type == Figure.Type.KNIGHT)
                this.type_to_f[i] = 7;
            else if(to.figure.type == Figure.Type.BISHOP)
                this.type_to_f[i] = 8;
            else if(to.figure.type == Figure.Type.QUEEN)
                this.type_to_f[i] = 9;
            else if(to.figure.type == Figure.Type.KING)
                this.type_to_f[i] = 10;
            else if(to.figure.type == Figure.Type.PAWN)
                this.type_to_f[i] = 11;
            else
                this.type_to_f[i] = -1;
        }
            this.rows_to_f[i] = to.row;
            this.cols_to_f[i] = to.col;




        board.moves_undo++;
    }



    public void save_move(Space from, Space to, Board board)
    {
        int i = board.move_number;


        this.rows_from[i] = from.row;
        this.cols_from[i] = from.col;
        if(to.figure.is_white)
        {
            if(to.figure.type == Figure.Type.ROOK)
                this.type_to[i] = 0;
            else if(to.figure.type == Figure.Type.KNIGHT)
                this.type_to[i] = 1;
            else if(to.figure.type == Figure.Type.BISHOP)
                this.type_to[i] = 2;
            else if(to.figure.type == Figure.Type.QUEEN)
                this.type_to[i] = 3;
            else if(to.figure.type == Figure.Type.KING)
                this.type_to[i] = 4;
            else if(to.figure.type == Figure.Type.PAWN)
                this.type_to[i] = 5;
            else
                this.type_to[i] = -1;
        }
        else
        {
            if(to.figure.type == Figure.Type.ROOK)
                this.type_to[i] = 6;
            else if(to.figure.type == Figure.Type.KNIGHT)
                this.type_to[i] = 7;
            else if(to.figure.type == Figure.Type.BISHOP)
                this.type_to[i] = 8;
            else if(to.figure.type == Figure.Type.QUEEN)
                this.type_to[i] = 9;
            else if(to.figure.type == Figure.Type.KING)
                this.type_to[i] = 10;
            else if(to.figure.type == Figure.Type.PAWN)
                this.type_to[i] = 11;
            else
                this.type_to[i] = -1;

        }





        this.rows_to[i] = to.row;
        this.cols_to[i] = to.col;

        board.move_number++;

    }



}
