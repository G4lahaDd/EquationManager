package model.equation.MathLogic;

import model.equation.Logic;

/**
 * Логика оператора разницы двух чисел
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class MinusLogic extends Logic {
    @Override
    public double calculate(double left, double right) {
        return left - right;
    }
}
