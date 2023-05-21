package model.service;

import model.equation.Equation;
import model.equation.Logic;
import model.equation.MathLogic.*;
import model.equation.Node;
import model.equation.OperatorLevel;
import model.exception.InvalidEquationException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Класс для создания уравнений
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class EquationManager {
    /**
     * Коллекция содержащая строку с символами операторов в зависимости от их приоритета
     */
    private static final Map<OperatorLevel, String> OPERATORS_SIGNS = new HashMap<>();
    /**
     * Коллекция содержащая класс логики оператора в зависимости от его символа
     */
    private static final Map<Character, Logic> OPERATORS = new HashMap<>();
    /**
     * Единственный экземпляр класса
     */
    private static final EquationManager INSTANCE = new EquationManager();

    /**
     * Конструтор класса инициализирующий коллекции с константами
     */
    private EquationManager() {
        OPERATORS_SIGNS.put(OperatorLevel.LOW_PRIORITY, "+-");
        OPERATORS_SIGNS.put(OperatorLevel.MEDIUM_PRIORITY, "*/");
        OPERATORS_SIGNS.put(OperatorLevel.HIGH_PRIORITY, "^");

        OPERATORS.put('+', new PlusLogic());
        OPERATORS.put('-', new MinusLogic());
        OPERATORS.put('*', new MultiplyLogic());
        OPERATORS.put('/', new DivideLogic());
        OPERATORS.put('^', new PowerLogic());
    }

    /**
     * Возвращает ссылку на единственный экземпляр класса
     */
    public static EquationManager getInstance() {
        return INSTANCE;
    }

    /**
     * Преобразует выражение из строки в уравнение представленное графом
     *
     * @param equationStr строка с выражением
     * @return возвращает экземпляр уравнения {@link Equation}
     * в случае успешного парсинга, иначе null
     * @throws InvalidEquationException ошибка формата уравнения
     */
    public Equation tryParseEquation(String equationStr) throws InvalidEquationException {
        equationStr = equationStr.
                replace(" ", "")
                .replace(",", ".")
                .toUpperCase();
        if (!isValid(equationStr)) {
            throw new InvalidEquationException("Неверное количество скобок");
        }
        equationStr = trimBrackets(equationStr);
        Node variable = new Node();
        variable.setLogic(new VariableLogic());
        Node parent = parse(equationStr, variable);
        Equation equation = new Equation();
        equation.setRootNode(parent);
        equation.setVariableNode(variable);
        return equation;
    }

    /**
     * Рекурсивный метод парсинга строки на выражение
     *
     * @param str подстрока с выражением
     * @param variable узел графа содержащий аргумент функции
     * @return узел содержащий выражение из строки
     * @throws InvalidEquationException ошибка формата уравнения
     */
    private Node parse(String str, Node variable) throws InvalidEquationException {
        Node node = new Node();
        str = trimBrackets(str);
        boolean findOperator = false;
        //Поиск операторов в строке от наименьшего до наибольшего приоритета
        for (OperatorLevel level : OperatorLevel.values()) {
            if (level == OperatorLevel.VALUE) break;
            // кол-во открытых скобок
            int brackets = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '(') {
                    brackets++;
                    continue;
                } else if (str.charAt(i) == ')') {
                    brackets--;
                    continue;
                } else if (brackets > 0) continue; // игнорирование операторов в скобках
                if (isOperator(str.charAt(i), level)) {
                    if (i == 0 || i == str.length() - 1)
                        throw new InvalidEquationException("Неверный формат уравнения");
                    node.setLogic(getOperatorBySign(str.charAt(i)));

                    // разбиение левой подстроки на выражение
                    Node left = parse(str.substring(0, i), variable);
                    node.leftChild = left;

                    // разбиение правой подстроки на выражение
                    Node right = parse(str.substring(i + 1), variable);
                    node.rightChild = right;

                    findOperator = true;
                    break;
                }
            }
            if (findOperator) return node;
        }

        //Оставшееся выражение должно хранить либо число, либо аргумент функции
        if (isNumber(str)) {
            node.setLogic(new ValueLogic(str));
        } else if (str.equals("X")) {
            node = variable;
        } else throw new InvalidEquationException("Неверный ввод данных");

        return node;
    }

    /**
     * Проверка на то, является ли символ оператором заданного приоритета
     *
     * @param ch проверяемый символ
     * @param level приоритет
     * @return true - если симлов является оператором, иначе false
     * @throws InvalidEquationException ошибка формата уравнения
     */
    private boolean isOperator(Character ch, OperatorLevel level) throws InvalidEquationException {
        String signs = OPERATORS_SIGNS.get(level);
        if (signs == null)
            throw new InvalidEquationException("Несуществующий оператор");
        return signs.contains(ch.toString());
    }

    /**
     * Поиск оператора по символу
     *
     * @param ch символ оператора
     * @return логический оператор
     * @throws InvalidEquationException несуществующий оператор
     */
    private Logic getOperatorBySign(Character ch) throws InvalidEquationException {
        Logic logic = OPERATORS.get(ch);
        if (logic == null)
            throw new InvalidEquationException("Несуществующий оператор");
        return logic;
    }

    /**
     * Обрезает скобки, если всё выражение заключено в них
     *
     * @param str исходная строка
     * @return строка без скобок
     */
    private String trimBrackets(String str) {
        if (str.charAt(0) != '(' || str.charAt(str.length() - 1) != ')') {
            return str;
        }
        int openCount = 0;
        //случай когда выражение имеет вид (...)(...), а не (.(...)..)
        boolean doubleBracketsCase = false;
        for (int i = 1; i < str.length() - 1; i++) {
            if (str.charAt(i) == '(') {
                openCount++;
            } else if (str.charAt(i) == ')') {
                if (openCount == 0) {
                    doubleBracketsCase = true;
                    break;
                }
                openCount--;
            }
        }
        if (!doubleBracketsCase) {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }

    /**
     * Проверка строки на то, является ли она числом
     * @return true - если число, иначе false
     */
    private boolean isNumber(String str) {
        final String pattern = "0123456789.";//возможные знаки в числе
        for (int i = 0; i < str.length(); i++) {
            if (!pattern.contains(
                    Character.toString(str.charAt(i))))
                return false;
        }
        return true;
    }

    /**
     * Проверка строки на правильность
     * @param str
     * @return true - если строка правильная, иначе false
     */
    private boolean isValid(String str) {
        if (str == null || str.length() == 0) return false;
        int openCount = 0;//количество открытых скобок
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                openCount++;
            }
            if (str.charAt(i) == ')') {
                if (openCount == 0) return false;
                openCount--;
            }
        }
        return openCount == 0;
    }
}
