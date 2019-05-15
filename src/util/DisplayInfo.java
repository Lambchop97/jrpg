package util;

import java.awt.*;

public class DisplayInfo {

    public static final Dimension DIM = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int SCALE = 5;

    public static final Dimension GAME_DIM = new Dimension(DIM.width/SCALE, DIM.height/SCALE);

    public static final int updatesPerSecond = 60;

}
