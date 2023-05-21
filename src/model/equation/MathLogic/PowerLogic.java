package model.equation.MathLogic;

import model.equation.Logic;

/**
 * Логика оператора возведения левого операнда в степень,
 * равную правому операнду
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class PowerLogic extends Logic {
    @Override
    public double calculate(double left, double right) {
        return Math.pow(left, right);
    }
}
