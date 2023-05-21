package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * Класс для создания изображений с заданной высотой и шириной
 *
 * @author Kazyro I.A.
 * @version 1.0 18.02.2023
 */
public class SizedImage{
    private SizedImage(){}

    /**
     * Создание изображения с заданной высотой и шириной
     * @param path путь к изображению
     * @param w ширина
     * @param h высота
     */
    public static ImageIcon create(String path, int w, int h){
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaled = originalIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
