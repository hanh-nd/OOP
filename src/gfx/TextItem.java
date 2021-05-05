package gfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextItem {

    public static void drawString(GraphicsContext g, String text, int xPos, int yPos,
                                  Color c, Font font){
        g.setFill(c);
        g.setFont(font);
        int x = xPos;
        int y = yPos;

        g.fillText(text, x, y);
    }

}
