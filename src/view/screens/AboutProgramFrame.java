package view.screens;

import view.Main;
import view.components.SizedImage;
import view.components.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Окно с информацией об программе
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class AboutProgramFrame extends JFrame {

    private static final String PROGRAM_INFO = "Программа позволяет находить корень уравнения с помощью метода Половинного деления или метода Хорд на заданном интервале с заданной точностью.";
    private static final String HALVING_METHOD_INFO = "Метод деления пополам позволяет исключать в точности половину интервала на каждой итерации." +
            " При использовании метода считается, что функция непрерывна и имеет на концах интервала разный знак." +
            " После вычисления значения функции в середине интервала одна часть интервала отбрасывается так," +
            " чтобы функция имела разный знак на концах оставшейся части. Итерации метода деления пополам прекращаются," +
            " если интервал становится достаточно малым.";
    private static final String CHORD_METHOD_INFO = "Метод хорд(метод также известен как Метод секущих) один из методов решения нелинейных уравнений" +
            " и основан на последовательном сужении интервала, содержащего единственный корень уравнения y=0. Итерационный процесс выполняется до того момента," +
            " пока не будет достигнута заданная точность\n"+
            "В отличие от метода половинного деления, метод хорд предлагает, что деление рассматриваемого интервала будет выполняться не в его середине," +
            " а в точке пересечения хорды с осью абсцисс (ось - Х)";

    private static final String USER_GUIDE = "Для использования программы требуется задать:\n" +
            "1. Уравнение \n" +
            "2. Начало интервала\n" +
            "3. Конец интервала\n" +
            "4. Точность решения\n" +
            "5. Тип поиска";

    private static final String USER_GUIDE_FUNCTION_PART_1 = "1. Для задания уравнения используется ввод уравнения в текстовое поле рисунок 1.";
    private static final String USER_GUIDE_FUNCTION_PART_2 = "Ввод поддерживает следующие символы:\n" +
            "- операторы: +, -, *, /, ^;\n" +
            "- сиволы разделения '(', ')';\n" +
            "- цифры 0-9;\n" +
            "- разделитель дробной части '.', ',';\n" +
            "- аргумент функции 'х', 'Х';";
    private static final String USER_GUIDE_INTERVAL = "2. Для задания начала и конца интервала существует два метода:" +
            " задание значений в текстовых полях (рисунок 2) или указание интервала с помощью компьютерной мыши.\n" +
            "Для указания начала интервала с помощью компьютерной мыши требуется нажать на левую кнопку мыши." +
            " Для указания конца интервала - правую кнопку мыши.\n";
    private static final String USER_GUIDE_ACCURACY = "3. Точность указывается с помощью ввода в текстовое поле (рисунок 3).";
    private static final String USER_GUIDE_FINDING_METHOD = "4. Метод поиска выбирается с помощью выпадающего списка " +
            "с перечеслением доступных методов (рисунок 4).";


    /**
     * Панель с содержимым окна
     */
    private JPanel jpContent;


    public AboutProgramFrame() {
        super("О программе");
        initialize();
    }

    /**
     * Инициализация графических компонентов окна
     */
    public void initialize() {
        jpContent = new JPanel();
        GridBagLayout layout = new GridBagLayout(); //создание менеджера компоновки
        jpContent.setLayout(layout);
        layout.columnWeights = new double[]{1};
        GridBagConstraints constraints  = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);//отступы компонентов
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridy = 0;
        JLabel jlblAboutProgram = new TitleLabel("О программе",18,1);
        jpContent.add(jlblAboutProgram, constraints);

        constraints.gridy = 1;
        addParagraph(PROGRAM_INFO, constraints);

        constraints.gridy = 2;
        JLabel jlblHalvingTitle = new TitleLabel("Метод половинного деления",16,1);
        jpContent.add(jlblHalvingTitle, constraints);

        constraints.gridy = 3;
        addImage(SizedImage.create("resource/HalvingMethod.png", 300, 225), constraints);

        constraints.gridy = 4;
        addParagraph(HALVING_METHOD_INFO, constraints);

        constraints.gridy = 5;
        JLabel jlblChordTitle = new TitleLabel("Метод Хорд",16,1);
        jpContent.add(jlblChordTitle, constraints);

        constraints.gridy = 6;
        addImage(SizedImage.create("resource/ChordMethod.png", 300, 225), constraints);

        constraints.gridy = 7;
        addParagraph(CHORD_METHOD_INFO, constraints);

        constraints.gridy = 9;
        JLabel jlblUserGuide = new TitleLabel("Руководство пользователя",18,1);
        jpContent.add(jlblUserGuide, constraints);

        constraints.gridy = 10;
        addParagraph(USER_GUIDE, constraints);

        constraints.gridy = 11;
        addParagraph(USER_GUIDE_FUNCTION_PART_1, constraints);

        constraints.gridy = 12;
        addImage(SizedImage.create("resource/UserGuide_Function.png", 600, 180), constraints);
        constraints.gridy = 13;
        JLabel jlblImage1 = new TitleLabel("Рисунок 1 - Поле ввода функции",12,0);
        jpContent.add(jlblImage1, constraints);

        constraints.gridy = 14;
        addParagraph(USER_GUIDE_FUNCTION_PART_2, constraints);

        constraints.gridy = 15;
        addParagraph(USER_GUIDE_INTERVAL, constraints);

        constraints.gridy = 16;
        addImage(SizedImage.create("resource/UserGuide_Interval.png", 600, 180), constraints);
        constraints.gridy = 17;
        JLabel jlblImage2 = new TitleLabel("Рисунок 2 - Поля для ввода границ интервала",12,0);
        jpContent.add(jlblImage2, constraints);

        constraints.gridy = 18;
        addParagraph(USER_GUIDE_ACCURACY, constraints);

        constraints.gridy = 19;
        addImage(SizedImage.create("resource/UserGuide_Accuracy.png", 600, 180), constraints);
        constraints.gridy = 20;
        JLabel jlblImage3 = new TitleLabel("Рисунок 3 - Поле для ввода точности",12,0);
        jpContent.add(jlblImage3, constraints);

        constraints.gridy = 21;
        addParagraph(USER_GUIDE_FINDING_METHOD, constraints);

        constraints.gridy = 22;
        addImage(SizedImage.create("resource/UserGuide_FindingMethod.png", 600, 180), constraints);
        constraints.gridy = 23;
        JLabel jlblImage4 = new TitleLabel("Рисунок 4 - Выпадающий список с типами поиска",12,0);
        jpContent.add(jlblImage4, constraints);

        jpContent.setBackground(Color.WHITE);
        //Добавление содержимого в панель с прокруткой
        JScrollPane jScrollPane = new JScrollPane(jpContent);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        GridBagLayout frameLayout = new GridBagLayout();
        frameLayout.rowWeights = new double[]{0.97, 0.03};
        frameLayout.columnWeights = new double[]{0.5, 0.5};
        setLayout(frameLayout);
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(jScrollPane, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(new JLabel("Версия 1.0"), constraints);

        constraints.gridx = 1;
        JButton jbtnBack = new JButton("Назад");
        jbtnBack.addActionListener(e -> onBack()); //Добавление слушателя нажатия
        add(jbtnBack, constraints);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 700);
        setLocationRelativeTo(null);
        //Перемещение содержимого в начало
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                jScrollPane.getVerticalScrollBar().setValue(0);
            }
        });
        setVisible(true);
    }

    /**
     * Добавляет в содержимое окна текстовое поле
     * @param content текст
     * @param constraints Настройки положения текстового поля в GridBagLayout
     */
    private void addParagraph(String content, Object constraints){
        JTextArea jtxtArea = new JTextArea();
        jtxtArea.setFont(new Font("Arial",Font.PLAIN,14));
        jtxtArea.setText(content);
        jtxtArea.setLineWrap(true);
        jtxtArea.setEditable(false);
        jpContent.add(jtxtArea, constraints);
    }

    /**
     * Добавляет изображение в содержимое окна
     * @param image изображение
     * @param constraints Настройки положения изображения в GridBagLayout
     */
    private void addImage(ImageIcon image, Object constraints){
        JLabel jlblImageView = new JLabel();
        jlblImageView.setIcon(image);
        jlblImageView.setHorizontalAlignment(SwingConstants.CENTER);
        jpContent.add(jlblImageView, constraints);
    }

    /**
     * Обработка нажатия на кнопку "Назад"
     */
    private void onBack(){
        Main.getCurrentFrame().setVisible(true);
        this.dispose();
    }
}
