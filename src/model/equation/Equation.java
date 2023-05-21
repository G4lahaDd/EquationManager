package model.equation;

import model.equation.MathLogic.VariableLogic;
import model.exception.MathException;

/**
 * Класс для описания уравнения
 */
public class Equation {
    /**
     * Главный узел дерева уравнения (основание графа)
     */
    private Node rootNode;
    /**
     * Узел для аргумента функции
     */
    private Node variableNode;

    /**
     * Возвращает главный узел уравнения
     */
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * Устанавливает главный узел уравнения
     */
    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Возвращает узел аргумента функции
     */
    public Node getVariableNode() {
        return variableNode;
    }

    /**
     * Установка узла аргумента функции
     */
    public void setVariableNode(Node variableNode) {
        this.variableNode = variableNode;
    }

    /**
     * Вычисление значения функции в точке Х
     * @param x значение аргумента
     * @return значение функции
     * @throws MathException Ошибка вычисления
     */
    public double getValue(double x) throws MathException {
        VariableLogic variableLogic = (VariableLogic) variableNode.getLogic();
        variableLogic.setValue(x);
        return rootNode.calculate();
    }
}
