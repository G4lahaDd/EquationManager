package model.equation.MathLogic;

import model.exception.MathException;
import model.equation.Logic;

/**
 * Логика оператора частного двух чисел
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class DivideLogic extends Logic {
    @Override
    public double calculate(double left, double right) throws MathException {
        if(right == 0 || Math.abs(right) < 0.0000001) throw new MathException("Деление на ноль");
        return left / right;
    }
}
