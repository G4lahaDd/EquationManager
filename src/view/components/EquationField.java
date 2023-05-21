package view.components;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Компонент для ввода уравнения
 * вводит только разрешённые уравнением символы
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class EquationField extends JTextField {
    /**
     * Текст поля ввода
     */
    String text;

    /**
     * Стандартный конструктор
     */
    public EquationField() {
        text = "";
        addKeyListener(new EquationListener());
    }

    /**
     * Класс слушателя ввода символов
     *
     * @author Maruk Ivan
     * @version 1.0
     */
    private class EquationListener implements KeyListener {
        private static final String PATTERN = "0123456789()+-*/^.,xX";

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            Character ch = e.getKeyChar();
            text = getText();
            // Если введённый символ не соответствует паттерну, то он удаляется
            if (!PATTERN.contains(ch.toString()) && !e.isActionKey()) {
                text = text.replace(ch.toString(), "");
                int pos = getCaretPosition();
                setText(text);
                if (pos > text.length() && pos != 0) pos = text.length();
                setCaretPosition(pos);//сдвиг каретки редактирования при удалении символа
            }
        }
    }
}
