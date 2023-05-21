package model.finding;

import model.equation.Equation;

/**
 * Класс для поиска корней уравнения
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public interface FindingMethod {
    /**
     * Поиск корня уравнения
     *
     * @param equation анализируемое уравнение
     * @param start начало интервала поиска
     * @param end конец интервала поиска
     * @param accuracy точность метода поиска
     * @return координаты найденной точки, если такая существует, иначе null
     */
    public Point findEquationRoot(Equation equation, double start, double end, double accuracy);
}
