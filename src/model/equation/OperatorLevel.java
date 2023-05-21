package model.equation;


/**
 * Перечисление уровней приоритета операторов
 * от наименьшего приотритета к наибольшему
 */
public enum OperatorLevel {
    LOW_PRIORITY,
    MEDIUM_PRIORITY,
    HIGH_PRIORITY,
    VALUE,
    VARIABLE
}
