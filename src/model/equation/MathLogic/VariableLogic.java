package model.equation.MathLogic;

import model.equation.Logic;
import model.exception.MathException;

/**
 * Логика для варьируемого значения функции
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class VariableLogic extends Logic {
    /**
     * Устанавливаемое численное значение
     */
    private double value;

    @Override
    public double calculate(double left, double right) throws MathException {
        return value;
    }

    /**
     * Установление численного значения
     */
    public void setValue(double value) {
        this.value = value;
    }
}
