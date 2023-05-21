package model.exception;

/**
 * Класс исключений возникающих при составлении уравнения
 */
public class InvalidEquationException extends Exception{
    public InvalidEquationException(String message) {
        super(message);
    }
}
