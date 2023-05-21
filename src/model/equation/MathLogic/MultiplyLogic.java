package model.equation.MathLogic;

import model.equation.Logic;

/**
 * Логика оператора произведения двух чисел
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class MultiplyLogic extends Logic {
    @Override
    public double calculate(double left, double right) {
        return left * right;
    }
}
