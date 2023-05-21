package model.finding;

import model.equation.Equation;
import model.exception.MathException;

/**
 * Класс описывающий поиск корня уравнения методом половинного деления
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class HalvingMethod implements FindingMethod{
    @Override
    public Point findEquationRoot(Equation equation, double start, double end, double accuracy) {
        if (end - start <= 0 || equation == null) return null;
        if (accuracy <= 0) {
            accuracy = 0.01;
        }
        try {
            if(equation.getValue(start) * equation.getValue(end) > 0)
                return null;
            //Координаты анализируемой точки
            double x;
            double y;
            while((end - start) > accuracy){
                x = (start + end)/2;
                y = equation.getValue(x);
                if(Math.abs(y) < accuracy) return new Point(x,y);
                if(equation.getValue(end) * y < 0)
                    start = x;
                else
                    end = x;
            }
            x = (start + end)/2;
            return new Point(x, equation.getValue(x));
        } catch (MathException e) {
            System.out.println("\"" + e.getMessage() + "\" while find equation root with halving method");
            return null;
        }
    }
}
