package view.components.Plot;

import java.awt.*;

/**
 * Класс хранящий цветовую схему для графика
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class PlotColor {
    /**
     * Цвет фона графика.
     */
    public static Color BG;

    /**
     * Цвет оси X.
     */
    public static Color AXIS_X;

    /**
     * Цвет оси Y.
     */
    public static Color AXIS_Y;

    /**
     * Цвет линий сетки.
     */
    public static Color GRID_LINE;

    /**
     * Цвет текста сетки.
     */
    public static Color GRID_TEXT;

    /**
     * Цвет графика функции.
     */
    public static Color FUNCTION;

    /**
     * Цвет точек на графике.
     */
    public static Color POINT;

    /**
     * Цвет для отображения ошибок.
     */
    public static Color ERROR;

    static {
        setLightTheme();
    }

    /**
     * Установление светлой темы
     */
    public static void setLightTheme(){
        BG = new Color(220,220,220);
        AXIS_X = new Color(180, 0, 0);
        AXIS_Y = new Color(0, 180, 0);
        GRID_LINE = new Color(200,200,200);
        GRID_TEXT = new Color(120, 120, 120);
        FUNCTION = Color.BLACK;
        ERROR = new Color(180,50,50);
        POINT = new Color(189, 94, 15);
    }

    /**
     * Установление тёмной темы
     */
    public static void setDarkTheme(){
        BG = new Color(55, 55, 55);
        AXIS_X = new Color(255, 100, 100);
        AXIS_Y = new Color(100, 255, 200);
        GRID_LINE = new Color(50, 50, 50);
        GRID_TEXT = new Color(120, 120, 120);
        FUNCTION = Color.WHITE;
        ERROR = new Color(255,60,60);
        POINT = Color.ORANGE;
    }
}
