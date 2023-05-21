package view;

import view.screens.MainFrame;
import view.screens.SplashScreenFrame;

import javax.swing.*;

/**
 * Главный класс программы
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class Main {
    /**
     * Сслыка на текущее окно
     */
    private static JFrame currentFrame;

    /**
     * Метод с которого начинается запуск программы
     */
    public static void main(String[] args) {
        new MainFrame();
    }

    /**
     * Метод для получения текущего JFrame окна.
     * @return текущее JFrame окно
     */
    public static JFrame getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Метод для установки текущего JFrame окна.
     * @param currentFrame устанавливаемое JFrame окно
     */
    public static void setCurrentFrame(JFrame currentFrame) {
        Main.currentFrame = currentFrame;
    }
}