/**
 * @author Dominik Bednář
 * Vytvori pole pro reprezentaci sachovnice a polozi na nej figury.
 *
 */


import javafx.scene.layout.GridPane;


class Board {

    Space[][] spaces;
    Figure[] figures;
    int move_number;
    int moves_undo;


    Board() {

        moves_undo = 0;
        move_number = 0;

        spaces = new Space[8][8];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                spaces[i][j] = new Space(i, j);
            }


        }

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {

                if (i != 0 && j != 0)
                    spaces[i][j].add_next_space(Space.Direction.LU, this.spaces[i - 1][j - 1]);

                if (i != 0)
                    spaces[i][j].add_next_space(Space.Direction.U, this.spaces[i - 1][j]);

                if (i != 0 && j != 7)
                    spaces[i][j].add_next_space(Space.Direction.RU, this.spaces[i - 1][j + 1]);

                if (j != 0)
                    spaces[i][j].add_next_space(Space.Direction.L, this.spaces[i][j - 1]);

                if (j != 7)
                    spaces[i][j].add_next_space(Space.Direction.R, this.spaces[i][j + 1]);

                if (i != 7 && j != 0)
                    spaces[i][j].add_next_space(Space.Direction.LD, this.spaces[i + 1][j - 1]);
                if (i != 7)
                    spaces[i][j].add_next_space(Space.Direction.D, this.spaces[i + 1][j]);
                if (i != 7 && j != 7)
                    spaces[i][j].add_next_space(Space.Direction.RD, this.spaces[i + 1][j + 1]);
            }

        }
    }

    void create_figures() throws Exception
    {
        figures = new Figure[64];

        figures[0] = new Figure(true,7,0,this.spaces[7][0], Figure.Type.ROOK,0);
        figures[1] = new Figure(true,7,1,this.spaces[7][1], Figure.Type.KNIGHT,1);
        figures[2] = new Figure(true,7,2,this.spaces[7][2], Figure.Type.BISHOP,2);
        figures[3] = new Figure(true,7,3,this.spaces[7][3], Figure.Type.QUEEN,3);
        figures[4] = new Figure(true,7,4,this.spaces[7][4], Figure.Type.KING,4);
        figures[5] = new Figure(true,7,5,this.spaces[7][5], Figure.Type.BISHOP,5);
        figures[6] = new Figure(true,7,6,this.spaces[7][6], Figure.Type.KNIGHT,6);
        figures[7] = new Figure(true,7,7,this.spaces[7][7], Figure.Type.ROOK,7);

        for(int i=0;i<8;i++)
        {
            figures[i+8] = new Figure(true,6,i,this.spaces[6][i], Figure.Type.PAWN,i+8);
            figures[i+24] = new Figure(false,1,i,this.spaces[1][i], Figure.Type.PAWN,i+24);
        }

        figures[16] = new Figure(false,0,0,this.spaces[0][0], Figure.Type.ROOK,16);
        figures[17] = new Figure(false,0,1,this.spaces[0][1], Figure.Type.KNIGHT,17);
        figures[18] = new Figure(false,0,2,this.spaces[0][2], Figure.Type.BISHOP,18);
        figures[19] = new Figure(false,0,3,this.spaces[0][3], Figure.Type.QUEEN,19);
        figures[20] = new Figure(false,0,4,this.spaces[0][4], Figure.Type.KING,20);
        figures[21] = new Figure(false,0,5,this.spaces[0][5], Figure.Type.BISHOP,21);
        figures[22] = new Figure(false,0,6,this.spaces[0][6], Figure.Type.KNIGHT,22);
        figures[23] = new Figure(false,0,7,this.spaces[0][7], Figure.Type.ROOK,23);

        int k =32;
        for(int i = 0; i<8;i++)
        {
            figures[k] = new Figure(true,2,i,this.spaces[2][i],Figure.Type.EMPTY,k);
                k++;
        }
        for(int i = 0; i<8;i++)
        {
            figures[k] = new Figure(true,3,i,this.spaces[3][i],Figure.Type.EMPTY,k);
            k++;
        }
        for(int i = 0; i<8;i++)
        {
            figures[k] = new Figure(true,4,i,this.spaces[4][i],Figure.Type.EMPTY,k);
            k++;
        }
        for(int i = 0; i<8;i++)
        {
            figures[k] = new Figure(true,5,i,this.spaces[5][i],Figure.Type.EMPTY,k);
            k++;
        }

    }



    void Place_figures(GUI chess_board)
    {
        int k =0;
        for(int i = 7; i > 5; i--)
        {
            for(int j =0;j<8;j++)
            {
                this.spaces[i][j].put_figure(this.figures[k]);
                k++;
            }
        }

        for(int i = 0; i < 2; i++)
        {
            for(int j =0;j<8;j++)
            {
                this.spaces[i][j].put_figure(this.figures[k]);
                k++;
            }

        }

        for(int i = 2; i<6;i++)
        {
            for(int j = 0;j<8;j++)
            {
                this.spaces[i][j].put_empty_figure(this.figures[k]);
                k++;
            }
        }





        chess_board.gridpane.add(this.figures[0].background, 2,8);
        chess_board.gridpane.add(this.figures[1].background,3,8);
        chess_board.gridpane.add(this.figures[2].background,4,8);
        chess_board.gridpane.add(this.figures[3].background,5,8);
        chess_board.gridpane.add(this.figures[4].background,6,8);
        chess_board.gridpane.add(this.figures[5].background,7,8);
        chess_board.gridpane.add(this.figures[6].background,8,8);
        chess_board.gridpane.add(this.figures[7].background,9,8);

        for(int i = 0; i<8; i++)
        {
            chess_board.gridpane.add(this.figures[i+8].background,i+2,7);
        }

        chess_board.gridpane.add(this.figures[16].background, 2,1);
        chess_board.gridpane.add(this.figures[17].background,3,1);
        chess_board.gridpane.add(this.figures[18].background,4,1);
        chess_board.gridpane.add(this.figures[19].background,5,1);
        chess_board.gridpane.add(this.figures[20].background,6,1);
        chess_board.gridpane.add(this.figures[21].background,7,1);
        chess_board.gridpane.add(this.figures[22].background,8,1);
        chess_board.gridpane.add(this.figures[23].background,9,1);

        for(int i = 0; i<8; i++)
        {
            chess_board.gridpane.add(this.figures[i+24].background,i+2,2);
        }



    }

    void Place_figures_again(GUI chess_board)
    {
        for(int i =0; i<64;i++)
        {
            this.figures[i].moved =false;
            this.figures[i].chosen = false;
        }


        int k =0;
        for(int i = 7; i > 5; i--)
        {
            for(int j =0;j<8;j++)
            {
                this.spaces[i][j].put_figure(this.figures[k]);
                k++;
            }
        }

        for(int i = 0; i < 2; i++)
        {
            for(int j =0;j<8;j++)
            {
                this.spaces[i][j].put_figure(this.figures[k]);
                k++;
            }

        }

        for(int i = 2; i<6;i++)
        {
            for(int j = 0;j<8;j++)
            {
                this.spaces[i][j].put_empty_figure(this.figures[k]);
                k++;
            }
        }

        for(int i = 0; i<8; i++)
        {
            GridPane.setColumnIndex(this.figures[i].background,i+2);
            GridPane.setRowIndex(this.figures[i].background,8);
            GridPane.setColumnIndex(this.figures[i+8].background,i+2);
            GridPane.setRowIndex(this.figures[i+8].background,7);

            GridPane.setColumnIndex(this.figures[i+16].background,i+2);
            GridPane.setRowIndex(this.figures[i+16].background,1);

            GridPane.setColumnIndex(this.figures[i+24].background,i+2);
            GridPane.setRowIndex(this.figures[i+24].background,2);
        }



    }

    Space get_space(int row, int col)
    {
        return this.spaces[row][col];
    }
}
