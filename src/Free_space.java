import javafx.scene.layout.BorderPane;

public class Free_space {

    BorderPane free_space;
    int row;
    int col;
    Space space;
    int pos;

    public Free_space(int row, int col, Space space, int k)
    {
        free_space = new BorderPane();
        this.row = row;
        this.col = col;
        this.space = space;
        this.pos = k;
    }


}
