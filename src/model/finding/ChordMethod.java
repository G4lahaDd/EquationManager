package model.finding;

import model.equation.Equation;
import model.exception.MathException;

/**
 * Класс описывающий поиск корня уравнения методом хорд
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class ChordMethod implements FindingMethod {
    @Override
    public Point findEquationRoot(Equation equation, double start, double end, double accuracy) {
        if (end - start <= 0 || equation == null) return null;
        if (accuracy <= 0) {
            accuracy = 0.01;
        }
        try {
            if (equation.getValue(start) * equation.getValue(end) > 0)
                return null;
            double len = end - start; // длинна исследуемого отрезка
            double yStart = equation.getValue(start); // значение фунции в начале интервала
            double yEnd = equation.getValue(end); // значение функции в конце интервала
            // кооридинаты исследуемой точки
            double x;
            double y;
            while (len > accuracy) {
                len = end - start;
                x = start - yStart * len / (yEnd - yStart); //координата пересечения хорды с осью ОХ
                y = equation.getValue(x);
                if (Math.abs(y) < accuracy) return new Point(x, y);
                if (yEnd * y < 0) {
                    start = x;
                    yStart = y;
                } else {
                    end = x;
                    yEnd = y;
                }
            }
            x = (start + end) / 2;
            return new Point(x, equation.getValue(x));
        } catch (MathException e) {
            System.out.println("\"" + e.getMessage() + "\" while find equation root with Chord method");
            return null;
        }
    }
}
