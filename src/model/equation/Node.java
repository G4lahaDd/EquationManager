package model.equation;

import model.exception.MathException;

/**
 * Класс для описания логического узла дерева уравнения
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class Node {
    /**
     * Объект с логикой оператора
     */
    private Logic logic;
    /**
     * Cсылка на левую ветвь дерева
     */
    public Node leftChild;
    /**
     * Ссылка на правую ветвь дерева
     */
    public Node rightChild;

    /**
     * Расчёт результата операции между левым и правым операндом
     * @return Результат вычисления операции
     * @throws MathException Ошибка вычисления
     */
    public double calculate() throws MathException {
        double left = (leftChild == null) ? 0 : leftChild.calculate();
        double right = (rightChild == null) ? 0 : rightChild.calculate();
        return logic.calculate(left, right);
    }

    /**
     * @return Объект логики узла
     */
    public Logic getLogic() {
        return logic;
    }

    /**
     * Установление объекта логики для узла
     * @param logic
     */
    public void setLogic(Logic logic) {
        this.logic = logic;
    }
}
