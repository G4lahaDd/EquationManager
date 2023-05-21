package view.screens;

import view.components.SizedImage;
import view.components.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Стартовое окно
 *
 * @author Kazyro I.A.
 * @version 1.0 18.02.2023
 */
public class SplashScreenFrame extends JFrame {
    /**
     * Таймер выключения окна при бездействии
     */
    Timer timer;

    /**
     * Стандартный конструктор класса
     */
    public SplashScreenFrame(){
        super("Equation manager");
        initialize();

        //Запуск таймера для закрытия приложения через 1 мин
        TimerTask exitTask = new TimerTask() {
            @Override
            public void run() {
                onExit();
            }
        };
        timer = new Timer();
        timer.schedule(exitTask,60000);
    }

    /**
     * Инициализация графических компонентов окна
     */
    private void initialize(){
        GridBagLayout layout = new GridBagLayout(); //Создание менеджера компоновки в виде гибкой таблицы
        layout.rowWeights = new double[]{0.05, 0.05, 0.06, 0.06, 0.05, 0.05, 0.63, 0.05}; //Задание в процентах высоты строк
        layout.columnWeights = new double[]{1};//Задание в процентах ширины столбцов
        setLayout(layout); //Установление менеджера компоновки

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH; //Размеры компонентов относительно ячейки таблицы
        constraints.insets = new Insets(5,5,5,5); //Отступы компонентов
        constraints.gridx = 0;

        //Установление номера строки для компонента
        constraints.gridy = 0;
        //Добавление компонента
        add(new TitleLabel("Белорусский национальный технический университет",18,1), constraints);
        constraints.gridy = 1;
        add(new TitleLabel("Факультет информационных технологий и робототехники",16), constraints);
        constraints.gridy = 3;
        add(new TitleLabel("Курсовая работа",18,1), constraints);
        constraints.gridy = 4;
        add(new TitleLabel("По дисциплине \"Программирование на языке Java\"",16), constraints);
        constraints.gridy = 5;
        add(new TitleLabel("Вычисление корней уравнений методом половинного деления",16), constraints);
        constraints.gridy = 7;
        add(new TitleLabel("Минск, 2023",14), constraints);

        //Панель для размещения изображения и информации о работе
        JPanel jpnlInfo = new JPanel();
        GridBagLayout infoLayout = new GridBagLayout();
        infoLayout.rowWeights = new double[]{0.36, 0.07, 0.07, 0.06, 0.07, 0.07, 0.15, 0.15};
        infoLayout.columnWeights = new double[]{0.5, 0.5};
        jpnlInfo.setLayout(infoLayout);
        JLabel jlblImageView = new JLabel();

        // Размещение элементов на панели информации об работе
        jlblImageView.setIcon(SizedImage.create("resource/Logo.png",200,200));
        jlblImageView.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridheight = 7;
        constraints.gridx = 0;
        constraints.gridy = 0;
        jpnlInfo.add(jlblImageView, constraints);
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 1;
        jpnlInfo.add(new JLabel("Выполнил: студент гр.10702320"), constraints);
        constraints.gridy = 2;
        jpnlInfo.add(new JLabel("Казыро Илья Александрович"), constraints);
        constraints.gridy = 4;
        jpnlInfo.add(new JLabel("Преподаватель: к.ф.-м.н., доц."), constraints);
        constraints.gridy = 5;
        jpnlInfo.add(new JLabel("Сидорик Валерий Владимирович"), constraints);

        JButton jbtnExit = new JButton("Выход");
        JButton jbtnContinue = new JButton("Продолжить");
        constraints.gridy = 7;
        jpnlInfo.add(jbtnExit, constraints);
        constraints.gridx = 0;
        jpnlInfo.add(jbtnContinue, constraints);

        //Добавление слушателя на нажатие кнопок
        jbtnExit.addActionListener(e -> onExit());
        jbtnContinue.addActionListener(e -> onContinue());

        constraints.gridy = 6;
        add(jpnlInfo, constraints);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,600);// Установка размера окна
        setLocationRelativeTo(null);// Установка положения по середине экрана
        setVisible(true);
    }

    /**
     * Обработка нажатия на кнопку продолжения
     */
    private void onContinue(){
        timer.cancel();
        new MainFrame();
        this.dispose();
    }

    /**
     * Обработка нажатия на кнопку выхода
     */
    private void onExit(){
        System.exit(0);
    }
}
