package model.finding;

/**
 * Класс для описания геометрической точки
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class Point {
    /**
     * Координата Х
     */
    public double x;
    /**
     * Координата У
     */
    public double y;

    /**
     * Стандартный конструктор класса
     * Создаёт объект точки с координатами (0, 0)
     */
    public Point() {
        x = 0;
        y = 0;
    }

    /**
     * Конструктор класса
     * @param x - координата Х
     * @param y - координата У
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "[" + x + ", " + y +']';
    }
}
