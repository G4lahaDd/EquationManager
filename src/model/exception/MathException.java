package model.exception;

/**
 * Класс исключения вычисления значения функции
 */
public class MathException extends Exception{
    public MathException(String message) {
        super(message);
    }
}
