import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawGraph extends BodyApp{
    public static void draw(ArrayList<Double> data, String label){
        XYSeries series1 = new XYSeries("Похибка");

        double start = a;
        for(double i : data){
            if(start <= b) {
                series1.add(start, i);
                start += 0.01;
            }
        }

        XYSeriesCollection dataset1 = new XYSeriesCollection();

        dataset1.addSeries(series1);

// Створення графіку
        JFreeChart chart = ChartFactory.createXYLineChart(
                label,              // Назва графіку
                "X",                    // Назва осі X
                "-lg|delta_n|",                    // Назва осі Y
                null,                   // Дані для графіку
                PlotOrientation.VERTICAL,// Орієнтація графіку
                true,                   // Легенда
                true,                   // Підказки
                false                   // Мережка
        );

        XYPlot plot = chart.getXYPlot();
        plot.setDataset(0, dataset1);
        plot.setBackgroundPaint(Color.gray);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.blue);
        renderer1.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer1.setSeriesShapesVisible(0, false);

        plot.setRenderer(0, renderer1);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
