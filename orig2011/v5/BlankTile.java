package orig2011.v5;

import java.awt.*;

public class BlankTile implements GameTile {

    @Override
    public void draw(Graphics g, int x, int y, Dimension d) {
        // The default GameTile is transparent,
        // therefore no drawing is performed.
    }
}
