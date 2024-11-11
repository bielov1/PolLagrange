import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class MyGraph extends BodyApp {

    public static void graph(ArrayList<ArrayList<Double>> data1, String txt1,
                             ArrayList<ArrayList<Double>> data2, String txt2,
                             ArrayList<ArrayList<Double>> data3, String txt3){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeriesCollection dataset1 = new XYSeriesCollection();
        XYSeriesCollection dataset2 = new XYSeriesCollection();

        XYSeries series = new XYSeries(txt1);
        for (ArrayList<Double> sin : data1) {
            series.add(sin.get(0), sin.get(1));
        }
        dataset.addSeries(series);

        XYSeries series1 = new XYSeries(txt2);
        for (ArrayList<Double> xyKnotIn : data2) { //xyKnotsIntpolated
            series1.add(xyKnotIn.get(0), xyKnotIn.get(1));
        }
        dataset1.addSeries(series1);

        XYSeries series2 = new XYSeries("Вузли");
        for (ArrayList<Double> xyKnot : data3) {
            series2.add(xyKnot.get(0), xyKnot.get(1));
        }
        dataset2.addSeries(series2);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Two Functions Chart",
                "X",
                "f(x)",
                null,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        plot.setDataset(0, dataset);
        plot.setDataset(1, dataset1);
        plot.setDataset(2, dataset2);
        plot.setBackgroundPaint(Color.gray);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesShapesVisible(0, false);

        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.green);
        renderer1.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer1.setSeriesShapesVisible(0, false);

        XYItemRenderer renderer2 = new XYLineAndShapeRenderer(false, true);
        renderer2.setSeriesPaint(0, Color.pink);
        renderer2.setSeriesShape(0, new Ellipse2D.Double(-6, -6, 8, 8));

        plot.setRenderer(0, renderer);
        plot.setRenderer(1, renderer1);
        plot.setRenderer(2, renderer2);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
