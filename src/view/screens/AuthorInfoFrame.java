package view.screens;

import view.Main;
import view.components.SizedImage;
import view.components.TitleLabel;

import javax.swing.*;
import java.awt.*;


/**
 * Окно с информацией об авторе
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class AuthorInfoFrame extends JFrame {

    public AuthorInfoFrame(){
        super("Об авторе");
        initialize();
    }
    /**
     * Инициализация графических компонентов окна
     */
    public void initialize(){
        GridBagLayout layout = new GridBagLayout(); //Создание менеджера компоновки в виде гибкой таблицы
        layout.rowWeights = new double[]{0.6, 0.08, 0.08, 0.08, 0.08, 0.08}; //Задание в процентах высоты строк
        layout.columnWeights = new double[]{1};//Задание в процентах ширины столбцов
        setLayout(layout); //Установление менеджера компоновки

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH; //Размеры компонентов относительно ячейки таблицы
        constraints.insets = new Insets(5, 5, 0, 5); //Отступы компонентов

        // Размещение элементов на панели информации об работе
        constraints.gridy = 0;
        //фото
        JLabel jlblImageView = new JLabel();
        jlblImageView.setIcon(SizedImage.create("resource/Author.png",290,290));
        jlblImageView.setHorizontalAlignment(SwingConstants.CENTER);
        add(jlblImageView, constraints);
        constraints.gridy = 1;
        add(new TitleLabel("Автор",16), constraints);
        constraints.gridy = 2;
        add(new TitleLabel("Студент группы 10702320"), constraints);
        constraints.gridy = 3;
        add(new TitleLabel("Казыро Илья Александрович"), constraints);
        constraints.gridy = 4;
        add(new TitleLabel("ilyakazyro@gmail.com"), constraints);
        constraints.gridy = 5;
        constraints.insets = new Insets(5, 5, 5, 5);
        JButton jbtnBack = new JButton("Назад");
        add(jbtnBack, constraints);
        jbtnBack.addActionListener(e -> {
            Main.getCurrentFrame().setVisible(true);
            dispose();
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
