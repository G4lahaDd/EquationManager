package view.components.Plot;

import model.equation.Equation;
import model.exception.MathException;
import model.finding.FindingMethod;
import model.finding.Point;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс компонента для отображения графиков функции
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public class PlotCanvas extends Canvas {

    /**
     * Интерфейс метода поиска корней уравнения
     */
    private FindingMethod findingMethod;
    /**
     * Уравнение заданной функции
     */
    private Equation equation;
    /**
     * Посчитанные значения функции
     */
    private final List<Point> lastPlot;
    /**
     * Корень уравнения, при null - значение не найдено
     */
    private Point equationRoot;
    /**
     * Описание ошибки возникшей при вычислении значений функции
     */
    private String exceptionMessage; //

    /**
     * Поле для ввода начала интервала
     */
    private JTextField startJTxtF;
    /**
     * Поле для ввода конца интервала
     */
    private JTextField endJTxtF;

    /**
     * Начало области определения функции
     */
    private double start = 0;
    /**
     * Конец области определения функции
     */
    private double end = 5;
    /**
     * Начало области значения функции
     */
    private double min = 0;
    /**
     * Конец области значения функции
     */
    private double max = 10;
    /**
     * Точность метода поиска корней уравнения
     */
    private double accuracy = 0.1;


    //region Коэффициенты для отрисовки
    /**
     * Высота области для рисования в пикселях
     */
    int pixelHeight;

    /**
     * Ширина области для рисования в пикселях
     */
    int pixelWidth;

    //Размер области для рисования в ОЕ (с отступами в 20%)
    /**
     * Высота области для рисования в относительных еденицах
     */
    double height;

    /**
     * Ширина области для рисования в относительных еденицах
     */
    double width;

    /**
     * Отношение относительных едениц к пикселям
     */
    double unit2Pixel;

    /**
     * центр координатной плоскости в пикселях
     */
    Point center;
    //endregion

    /**
     * @param startJTxtF Поле для ввода начала интервала
     * @param endJTxtF Поле для ввода конца  интервала
     */
    public PlotCanvas(JTextField startJTxtF, JTextField endJTxtF) {
        lastPlot = new ArrayList<>();
        addMouseListener(new PlotMouseListener());
        this.startJTxtF = startJTxtF;
        this.endJTxtF = endJTxtF;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(PlotColor.BG);
        g.fillRect(0, 0, getWidth(), getHeight());
        updateDrawingParams();
        drawAxis(g);
        drawFunction(g);
        drawEquationRoot(g);
    }

    /**
     * Вычисление коэффициентов и параметров графика для отрисовки
     */
    private void updateDrawingParams() {
        //Размер области для рисования в пикселях
        pixelHeight = getHeight();
        pixelWidth = getWidth();
        //Размер области для рисования в относительных еденицах(ОЕ) (с отступами в 20%)
        height = (max - min) * 1.2;
        width = (end - start) * 1.2;
        //Вписывание графика в окно по соотношению сторон
        if (pixelHeight / height < pixelWidth / width) {
            unit2Pixel = pixelHeight / height;
            width = pixelWidth / unit2Pixel;
        } else {
            unit2Pixel = pixelWidth / width;
            height = pixelHeight / unit2Pixel;
        }
        //Отступы области функции от области рисования в ОЕ
        double vertOffset = (height - (max - min)) / 2;
        double horOffset = (width - (end - start)) / 2;
        //центр координатной плоскости в пикселях
        center = new Point();
        center.y = (vertOffset + max) * unit2Pixel;
        center.x = (horOffset - start) * unit2Pixel;
    }

    /**
     * Отрисовка осей и сетки
     */
    private void drawAxis(Graphics g) {
        DecimalFormat formatter = new DecimalFormat("#.#");
        formatter.format(0.01);

        //Размер динамической сетки в пикслелях, для ОЕ кратных 10^n
        double gridLength = 1;
        while (gridLength <= width * 1.2 || gridLength <= height * 1.2) {
            gridLength *= 5;
        }
        double gridUnit = gridLength * 0.04;//Размер сетки в ОЕ
        gridLength *= 0.04 * unit2Pixel;


        //Отрисовка линий сетки по оси У
        int startIndex = (int) ((0 - center.x) / gridLength);
        int endIndex = (int) ((pixelWidth - center.x) / gridLength);
        for (int i = startIndex; i <= endIndex; i++) {
            g.setColor(PlotColor.GRID_LINE);
            g.drawLine((int) (center.x + i * gridLength), 0, (int) (center.x + i * gridLength), pixelHeight);
            g.setColor(PlotColor.GRID_TEXT);
            // Подписи на оси Х
            if (center.y > 0 && center.y < pixelHeight)
                g.drawString(formatter.format(i * gridUnit), (int) (center.x + i * gridLength) + 2, (int) center.y - 2);
            else
                g.drawString(formatter.format(i * gridUnit), (int) (center.x + i * gridLength) + 2, pixelHeight - 5);
        }
        //Отрисовка линий сетки по оси X
        startIndex = (int) ((0 - center.y) / gridLength);
        endIndex = (int) ((pixelHeight - center.y) / gridLength);
        for (int i = startIndex; i <= endIndex; i++) {
            g.setColor(PlotColor.GRID_LINE);
            g.drawLine(0, (int) (center.y + i * gridLength), pixelWidth, (int) (center.y + i * gridLength));
            if (i == 0) continue;
            g.setColor(PlotColor.GRID_TEXT);
            // Подписи на оси У
            if (center.x > 0 && center.x < pixelWidth)
                g.drawString(formatter.format(-i * gridUnit), (int) (center.x - 30), (int) (center.y + i * gridLength));
            else
                g.drawString(formatter.format(-i * gridUnit), 5, (int) (center.y + i * gridLength));
        }

        g.setColor(PlotColor.AXIS_Y);
        //Отрисовка осей с условием их видимости
        if (center.x > 0 && center.x < pixelWidth)
            g.drawLine((int) center.x, 0, (int) center.x, pixelHeight);
        g.setColor(PlotColor.AXIS_X);
        if (center.y > 0 && center.y < pixelHeight)
            g.drawLine(0, (int) center.y, pixelWidth, (int) center.y);
    }

    /**
     * Отрисовка функции
     */
    private void drawFunction(Graphics g) {
        if (lastPlot.size() <= 0) return;
        double screenStart = -center.x / unit2Pixel;
        double screenEnd = (pixelWidth-center.x) / unit2Pixel;
        List<Point> prefix = calculateOnInterval(start, screenStart);
        List<Point> postfix = calculateOnInterval(end, screenEnd);
        g.setColor(PlotColor.FUNCTION);

        Graphics2D g2D = (Graphics2D) g;
        Stroke oldStroke = g2D.getStroke();
        //Отрисовка начала интервала
        int x1, x2, y1, y2;
        x1 = (int) (center.x + start * unit2Pixel);
        y1 = (int) (center.y - lastPlot.get(0).y * unit2Pixel);
        x2 = (int) (center.x + start * unit2Pixel);
        y2 = (int) (center.y);
        g.drawLine(x1, y1, x2, y2);
        //Отрисовка конца интервала
        x1 = (int) (center.x + end * unit2Pixel);
        y1 = (int) (center.y - lastPlot.get(lastPlot.size() - 1).y * unit2Pixel);
        x2 = (int) (center.x + end * unit2Pixel);
        y2 = (int) (center.y);
        g.drawLine(x1, y1, x2, y2);
        //Отрисовка функции
        g2D.setStroke(new BasicStroke(3));
        if(prefix != null) drawListOfPoints(prefix, g);
        drawListOfPoints(lastPlot, g);
        if(postfix != null) drawListOfPoints(postfix, g);
        g2D.setStroke(oldStroke);
        // Вывод сообщения об ошибке вычисления значений функции
        if(exceptionMessage == null) return;
        System.out.println("ex: " + exceptionMessage);
        g.setColor(PlotColor.ERROR);
        g.drawString(exceptionMessage, 5,20);
    }

    /**
     * Отрисовка ломанной линии по точкам
     * @param points Коллекция точек
     * @param g Контекст графики для рисования
     */
    private void drawListOfPoints(List<Point> points, Graphics g){
        int x1, x2, y1, y2;
        for (int i = 0; i < points.size() - 1; i++) {
            x1 = (int) Math.round(center.x + points.get(i).x * unit2Pixel);
            y1 = (int) Math.round(center.y - points.get(i).y * unit2Pixel);
            x2 = (int) Math.round(center.x + points.get(i + 1).x * unit2Pixel);
            y2 = (int) Math.round(center.y - points.get(i + 1).y * unit2Pixel);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * Отрисовка корня уравнения
     */
    private void drawEquationRoot(Graphics g){
        if (equationRoot == null) return;
        g.setColor(PlotColor.POINT);
        System.out.println("Equation root (x, y): " + equationRoot);
        g.fillOval((int) (center.x + equationRoot.x * unit2Pixel) - 5, (int) (center.y - equationRoot.y * unit2Pixel) - 5, 10, 10);
        g.drawString("Решение (x, y):   " + equationRoot,5,pixelHeight - 5);
    }

    /**
     * Пересчёт значений функции на отрезке от {@link PlotCanvas#start} до {@link PlotCanvas#end}
     */
    public void recalculate() {
        exceptionMessage = null;
        if (equation == null) return;
        if (findingMethod == null) return;
        double step = end - start;
        if (Math.abs(step) < 0.000000001) return;
        //расчёт шага в зависимости от ширины окна
        step /= getWidth() * 0.25;

        lastPlot.clear();
        double x = start - step;
        double y = 0;
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        // вычисление значений функции и её максимума и минимума
        while (x <= end) {
            x += step;
            try {
                y = equation.getValue(x);
                if (y > max) max = y;
                if (y < min) min = y;
            } catch (MathException ex) {
                exceptionMessage = ex.getMessage() + " в точке х = " + x;
                System.out.println(exceptionMessage);
            }
            lastPlot.add(new Point(x, y));
        }

        // поиск корня уравнения
        equationRoot = findingMethod.findEquationRoot(equation, start, end, accuracy);
        if(equationRoot != null){
            JOptionPane.showMessageDialog(this, "Решение: х = " + equationRoot.x + ", y = " + equationRoot.y);
        }
        else{
            JOptionPane.showMessageDialog(this, "На выбраном интервале не существует решения или не может быть найдено");
        }
        repaint();
    }

    /**
     * Расчёт значений функции на заданном отрезке
     * @param start Начало интервала
     * @param end Конец интервала
     */
    private List<Point> calculateOnInterval(double start, double end){
        List<Point> result = new ArrayList<>();

        if (equation == null) return null;
        if (findingMethod == null) return null;
        double step = end - start;
        if (Math.abs(step) < 0.000000001) return null;
        //расчёт шага в зависимости от ширины окна
        step /= getWidth() * 0.25;

        double x = start - step;
        double y = 0;
        // вычисление значений функции и её максимума и минимума
        while ((start < end) ? x <= end : x >= end) {
            x += step;
            try {
                y = equation.getValue(x);
                if(center.y - y * unit2Pixel < 0 && center.y - y * unit2Pixel > getHeight()){
                    break;
                }
            } catch (MathException ex) {
                exceptionMessage = ex.getMessage() + " в точке х = " + x;
                System.out.println(exceptionMessage);
            }
            result.add(new Point(x, y));
        }
        return result;
    }

    /**
     * Возвращает метод решения уравнения.
     * @return метод решения уравнения
     */
    public FindingMethod getFindingMethod() {
        return findingMethod;
    }

    /**
     * Устанавливает метод решения уравнения.
     * @param findingMethod метод решения уравнения
     */
    public void setFindingMethod(FindingMethod findingMethod) {
        this.findingMethod = findingMethod;
    }
    /**
     * Возвращает уравнение для решения.
     * @return уравнение для решения
     */
    public Equation getEquation() {
        return equation;
    }
    /**
     * Устанавливает уравнение для решения.
     * @param equation уравнение для решения
     */
    public void setEquation(Equation equation) {
        this.equation = equation;
    }
    /**
     * Возвращает начальное значение интервала поиска решения.
     * @return начальное значение интервала поиска решения
     */
    public double getStart() {
        return start;
    }
    /**
     * Устанавливает начальное значение интервала поиска решения.
     * @param start начальное значение интервала поиска решения
     */
    public void setStart(double start) {
        this.start = start;
    }
    /**
     * Возвращает конечное значение интервала поиска решения.
     * @return конечное значение интервала поиска решения
     */
    public double getEnd() {
        return end;
    }
    /**
     * Устанавливает конечное значение интервала поиска решения.
     * @param end конечное значение интервала поиска решения
     */
    public void setEnd(double end) {
        this.end = end;
    }
    /**
     * Возвращает точность вычисления решения.
     * @return точность вычисления решения
     */
    public double getAccuracy() {
        return accuracy;
    }
    /**
     * Устанавливает точность вычисления решения.
     * @param accuracy точность вычисления решения
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }


    /**
     * Класс слушателя событий нажатия мышкой на график
     * устанавливает границы области определения функции
     * в зависимости от выбранной точки на экране
     *
     * @version 1.0 06.04.2023
     */
    private class PlotMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {
            //Координаты нажатия по оси Х в относительных еденицах
            double x = (e.getX() - center.x) / unit2Pixel;

            //Проверка на нажатие левой и правой кнопки мыши
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (x > end) {//Случай когда новая левая граница больше правой
                    start = end;
                    end = x;
                } else {
                    start = x;
                }
                recalculate();
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                if (x < start) {//Случай когда новая правая граница меньше левой
                    end = start;
                    start = x;
                } else {
                    end = x;
                }
                recalculate();
            }
            startJTxtF.setText(Double.toString(start));
            endJTxtF.setText(Double.toString(end));
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
