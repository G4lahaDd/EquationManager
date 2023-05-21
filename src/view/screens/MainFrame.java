package view.screens;

import model.equation.Equation;
import model.exception.InvalidEquationException;
import model.finding.*;
import model.service.EquationManager;
import org.apache.commons.io.FilenameUtils;
import view.Main;
import view.components.*;
import view.components.Plot.PlotCanvas;
import view.components.Plot.PlotColor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс главного окна программы
 *
 * @author Kazyro I.A.
 * @version 1.0 20.02.2023
 */
public class MainFrame extends JFrame {
    /**
     * Менеджер для создания уранений
     */
    private static final EquationManager equationManager = EquationManager.getInstance();


    /**
     * Текстовое поле для ввода уравнения.
     */
    private JTextField jtfEquation;
    /**
     * Текстовое поле для ввода начального значения диапазона.
     */
    private JTextField jtfIntervalStart;
    /**
     * Текстовое поле для ввода конечного значения диапазона.
     */
    private JTextField jtfIntervalEnd;
    /**
     * Текстовое поле для ввода желаемой точности.
     */
    private JTextField jtfAccuracy;
    /**
     * Выпадающий список для выбора метода решения уравнения.
     */
    private JComboBox<FindingType> jcbFindingMethod;
    /**
     * Выпадающий список для выбора типа графика.
     */
    private JComboBox<String> jcbPlotType;
    /**
     * Объект для отображения графика уравнения.
     */
    private PlotCanvas plotCanvas;
    /**
     * Последнее введенное уравнение.
     */
    private String lastEquation = "";

    /**
     * Стандартный конструктор класса
     */
    public MainFrame() {
        super();
        Main.setCurrentFrame(this);
        initialize();
    }

    /**
     * Инициализация графических компонентов окна
     */
    private void initialize() {
        GridBagLayout layout = new GridBagLayout(); //Создание менеджера компоновки в виде гибкой таблицы
        layout.rowWeights = new double[]{0.04, 0.07, 0.04, 0.07, 0.71, 0.07}; //Задание в процентах высоты строк
        layout.columnWeights = new double[]{0.2, 0.2, 0.2, 0.2, 0.2};//Задание в процентах ширины столбцов
        setLayout(layout); //Установление менеджера компоновки

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH; //Размеры компонентов относительно ячейки таблицы
        constraints.insets = new Insets(5, 5, 0, 5); //Отступы компонентов

        //Меню
        JMenuBar jMenuBar = new JMenuBar();

        JMenu jmFile = new JMenu("File");
        JMenuItem jmSave = new JMenuItem("Сохранить");
        jmSave.addActionListener(e -> onSave());
        JMenuItem jmOpen = new JMenuItem("Открыть");
        jmOpen.addActionListener(e -> onOpen());
        JMenuItem jmExit = new JMenuItem("Закрыть");
        jmExit.addActionListener(e -> onExit());
        jmFile.add(jmSave);
        jmFile.add(jmOpen);
        jmFile.add(jmExit);
        jMenuBar.add(jmFile);

        JMenu jmAbout = new JMenu("About");
        JMenuItem jmAboutAuthor = new JMenuItem("Об авторе");
        jmAboutAuthor.addActionListener(e -> onAboutAuthor());
        JMenuItem jmAboutProgram = new JMenuItem("О программе");
        jmAboutProgram.addActionListener(e -> onAboutProgram());
        jmAbout.add(jmAboutAuthor);
        jmAbout.add(jmAboutProgram);
        jMenuBar.add(jmAbout);
        setJMenuBar(jMenuBar);

        //Строка названий парамметров уравнения
        constraints.gridwidth = 3;
        add(new JLabel("Уравнение:"), constraints);
        constraints.gridwidth = 1;
        constraints.gridx = 3;
        add(new JLabel("Метод:"), constraints);

        //Строка параметров уравнения
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridwidth = 3;
        constraints.gridy = 1;
        constraints.gridx = 0;
        jtfEquation = new EquationField();
        add(jtfEquation, constraints);
        constraints.gridwidth = 1;
        constraints.gridx = 3;
        jcbFindingMethod = new JComboBox<>();
        for (FindingType method : FindingType.values())
            jcbFindingMethod.addItem(method);
        add(jcbFindingMethod, constraints);
        constraints.gridx = 4;
        JButton jbtnExecute = new JButton("Решить");
        jbtnExecute.addActionListener(e -> executeEquation());
        add(jbtnExecute, constraints);

        //Строка названий параметров области определения
        constraints.insets = new Insets(5, 5, 0, 5);
        constraints.gridy = 2;
        constraints.gridx = 0;
        add(new JLabel("Начало области:"), constraints);
        constraints.gridx = 1;
        add(new JLabel("Конец области:"), constraints);
        constraints.gridx = 2;
        add(new JLabel("Точность:"), constraints);
        constraints.gridx = 4;
        add(new JLabel("График:"), constraints);

        //строка для ввода параметров области определения
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = 3;
        constraints.gridx = 0;
        jtfIntervalStart = new JTextField("0");
        add(jtfIntervalStart, constraints);
        constraints.gridx = 1;
        jtfIntervalEnd = new JTextField("5");
        add(jtfIntervalEnd, constraints);
        constraints.gridx = 2;
        jtfAccuracy = new JTextField("0.1");
        add(jtfAccuracy, constraints);
        constraints.gridx = 4;
        jcbPlotType = new JComboBox<>();
        jcbPlotType.addItem("Светлый");
        jcbPlotType.addItem("Тёмный");
        jcbPlotType.addActionListener(e -> selectPlotType());
        add(jcbPlotType, constraints);


        //Строка с графиком
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 5;
        plotCanvas = new PlotCanvas(jtfIntervalStart, jtfIntervalEnd);
        add(plotCanvas, constraints);

        //Строка кнопок меню
        constraints.gridwidth = 1;
        constraints.gridy = 5;
        constraints.gridx = 0;
        JButton jbtnAuthorInfo = new JButton("Об авторе");
        add(jbtnAuthorInfo, constraints);
        constraints.gridx = 1;
        JButton jbtnProgramInfo = new JButton("О программе");
        add(jbtnProgramInfo, constraints);
        constraints.gridx = 4;
        JButton jbtnExit = new JButton("Выход");
        add(jbtnExit, constraints);

        jbtnExit.addActionListener(e -> onExit());
        jbtnAuthorInfo.addActionListener(e -> onAboutAuthor());
        jbtnProgramInfo.addActionListener(e -> onAboutProgram());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        getContentPane().revalidate();//Обновить положение компонентов
    }

    /**
     * Выход из программы
     */
    private void onExit() {
        System.exit(0);
    }

    /**
     * Переход к окну об авторе
     */
    private void onAboutAuthor() {
        setVisible(false);
        new AuthorInfoFrame();
    }

    /**
     * Переход к окну о программе
     */
    private void onAboutProgram() {
        setVisible(false);
        new AboutProgramFrame();
    }

    /**
     * Сохранение текущего уравнения в файл
     */
    private void onSave(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDir = fileChooser.getSelectedFile();
            try {
                FileOutputStream fileOutput;
                if(!selectedDir.getAbsolutePath().contains(".txt")){
                    fileOutput = new FileOutputStream(selectedDir.getAbsolutePath() + ".txt");
                }
                else{
                    fileOutput = new FileOutputStream(selectedDir);
                }
                fileOutput.write(jtfEquation.getText().getBytes());
                fileOutput.close();
            } catch (IOException ex){
                System.out.println(ex.getMessage());
            }

        }
    }

    /**
     * Загрузка уравнения из файла
     */
    private void onOpen(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                //проверка расширения файла
                String extension = FilenameUtils.getExtension(selectedFile.getName());
                System.out.println("file extension: " + extension);
                if(!selectedFile.canRead() || !extension.equals("txt")) throw new IOException("Невозможно открыть файл");
                FileInputStream fileInput = new FileInputStream(selectedFile);
                String text = new String(fileInput.readAllBytes(), StandardCharsets.UTF_8);
                jtfEquation.setText(text);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка открытия файла");
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Обработка и отображение заданного графика
     */
    private void executeEquation() {
        System.out.println(jtfEquation.getText());
        try {
            // Если уравнение отличается от предыдущего, вычисляем его
            if (!jtfEquation.getText().equals(lastEquation)) {
                lastEquation = jtfEquation.getText();
                Equation equation = equationManager.tryParseEquation(lastEquation);
                plotCanvas.setEquation(equation);
            }
            FindingType selectedType = (FindingType) jcbFindingMethod.getSelectedItem();
            plotCanvas.setFindingMethod(selectedType.getMethod());
            plotCanvas.setStart(tryGetDouble(jtfIntervalStart));
            plotCanvas.setEnd(tryGetDouble(jtfIntervalEnd));
            plotCanvas.setAccuracy(tryGetDouble(jtfAccuracy));
            plotCanvas.recalculate();
        } catch (InvalidEquationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Обработчик выбора типа графика
     */
    private void selectPlotType(){
        if(jcbPlotType.getSelectedIndex() == 0){
            PlotColor.setLightTheme();
        }
        else{
            PlotColor.setDarkTheme();
        }
        plotCanvas.repaint();
    }

    /**
     * Парсинг строки текстового поля в число с плавующей точкой
     * @param textField текствое поле ввода
     * @return если перевод возможен - число из строки, иначе 0
     */
    private double tryGetDouble(JTextField textField) {
        String str = textField.getText()
                .replace(" ", "")
                .replace(",", ".");
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            textField.setText("0");
            return 0;
        }
    }

}
