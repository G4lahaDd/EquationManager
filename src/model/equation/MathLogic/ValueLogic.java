package model.equation.MathLogic;

import model.equation.Logic;
import model.exception.MathException;
/**
 * Логика для численного значения
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class ValueLogic extends Logic {
    /**
     * Численное значение
     */
    private double value;

    /**
     * Создание логики для заданного численного значения
     * если невозмозможно распарсить число - то значение равно 0
     *
     * @param value строка с численным значением
     */
    public ValueLogic(String value) {
        try {
            this.value = Double.parseDouble(value);
        }catch(Exception ex){
            System.out.printf("Failed to parse \"%s\"\n", value);
            this.value = 0;
        }
    }

    @Override
    public double calculate(double left, double right) throws MathException {
        return value;
    }
}
