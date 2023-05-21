package model.equation;

import model.exception.MathException;

/**
 * Абстрактный класс для описания логики математических операторов
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public abstract class Logic {
    /**
     * Вычисление значения оператора
     *
     * @param left значение левого аргумента
     * @param right значение правого аргумента
     * @return результат математической операции
     * @throws MathException Ошибка вычисления
     */
    public abstract double calculate(double left, double right) throws MathException;
}
