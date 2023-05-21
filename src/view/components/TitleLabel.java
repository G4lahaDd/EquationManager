package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * Класс для текста с заданной высотой и жирностью шрифта
 *
 * @author Kazyro I.A.
 * @version 1.0 18.02.2023
 */
public class TitleLabel extends JLabel {
    /**
     * @param text текст метки
     */
    public TitleLabel(String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * @param text текст метки
     * @param fontSize размер шрифта
     */
    public TitleLabel(String text, int fontSize) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(getFont().deriveFont(Font.PLAIN,(float) fontSize));
    }

    /**
     * @param text текст метки
     * @param fontSize размер шрифта
     * @param fontWidth жирность шрифта
     */
    public TitleLabel(String text, int fontSize, int fontWidth) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(getFont().deriveFont(fontWidth,(float) fontSize));
    }
}
