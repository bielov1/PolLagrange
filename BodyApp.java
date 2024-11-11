import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;


import javax.swing.*;
public class BodyApp extends Display {
    static JFrame myFrame = getFrame();
    static ArrayList<ArrayList<Double>> xyKnots =  new ArrayList<>();
    static ArrayList<ArrayList<Double>> sinSqResults = new ArrayList<>();
    static ArrayList<ArrayList<Double>> xyKnotsIntpolated = new ArrayList<>();
    static ArrayList<ArrayList<Double>> sinArr = new ArrayList<>();
    static ArrayList<ArrayList<Double>> sinArrIntropolated = new ArrayList<>();
    static ArrayList<ArrayList<Double>> sinResults = new ArrayList<>();
    static ArrayList<Double> accuracySinArrIntropolated = new ArrayList<>();
    static ArrayList<Double> accuracyXYKnotsIntropolated = new ArrayList<>();
    static ArrayList<Double> exactAccuracyXYKnotsIntropolated = new ArrayList<>();
    static ArrayList<Double> bluredXYKnotsIntropolated = new ArrayList<>();
    static ArrayList<Double> accuracySinResults = new ArrayList<>();
    static ArrayList<Double> exactAccuracySinArrIntropolated = new ArrayList<>();
    static ArrayList<Double> bluredSinArrIntropolated = new ArrayList<>();
    static int n = 10;
    static int a = 0;
    static int b = 2;
    static double InX = 0;
    static double findY = 0;

    public final static double EPSILON = 1e-15;

    static double h = (double)(b-a)/10;
    public static void main(String[] args){

    }

    static JFrame getFrame(){
        JFrame jFrame = new JFrame();
        jFrame.setBounds(100, 100, 200, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Display contents = new Display();
        contents.setPreferredSize(new Dimension(620, 620));

        jFrame.getContentPane().add(contents);
        jFrame.pack();
        jFrame.setVisible(true);

        return jFrame;
    }

    public static double sinSquared(double X){
        double res = Math.round(Math.pow(Math.sin(X), Math.sin(X*X)) * 1000);
        return res/1000;
    }

    public static void saveData(){
        try{
            n = Integer.parseInt(nTextField.getText());
            a = Integer.parseInt(periodTextField1.getText());
            b = Integer.parseInt(periodTextField2.getText());
            h = (double)(b-a)/n;
            InX = Double.parseDouble(divTextField.getText());

        } catch (Exception e){
            System.out.println("Ви не ввели значення!");
        }

    }

    public static void fillTheArr(){
        clearArrays();
        for(int i = 0; i <= n; i++){
            ArrayList<Double> doubleList = new ArrayList<>();
            BigDecimal x1 = BigDecimal.valueOf(h);
            BigDecimal x2 = x1.multiply(BigDecimal.valueOf(i));
            double x = a + x2.doubleValue();
            double y = sinSquared(x);
            doubleList.add(x);
            doubleList.add(y);
            xyKnots.add(doubleList);
        }

        BigDecimal end = BigDecimal.valueOf(b);
        for (BigDecimal i = BigDecimal.valueOf(a); i.compareTo(end) <= 0; i = i.add(BigDecimal.valueOf(0.01))) {
            ArrayList<Double> doubleList = new ArrayList<>();
            double x = i.doubleValue();
            double y = PolynimialLagrange.lagrange(xyKnots, x);
            doubleList.add(x);
            doubleList.add(y);
            xyKnotsIntpolated.add(doubleList);
        }

        for (BigDecimal i = BigDecimal.valueOf(a); i.compareTo(end) <= 0; i = i.add(BigDecimal.valueOf(0.01))) {
            ArrayList<Double> doubleList = new ArrayList<>();
            double x = i.doubleValue();
            double y = sinSquared(x);
            doubleList.add(x);
            doubleList.add(y);
            sinSqResults.add(doubleList);

        }


        //Розрахунок похибки
        //похибка інтерполяції(використовуємо інтерпольовані значення)
        for(int i = 0 ; i < xyKnotsIntpolated.size()-1; i++){
            BigDecimal pn = BigDecimal.valueOf(xyKnotsIntpolated.get(i).get(1));
            BigDecimal pn1 = BigDecimal.valueOf(xyKnotsIntpolated.get(i+1).get(1));
            BigDecimal diff = pn.subtract(pn1).abs();
            double res = -Math.log10(diff.doubleValue());
            accuracyXYKnotsIntropolated.add(res);
        }
        //цей код для визначення абсолютнної похибки - delta exact n -
        // різниця між інтерпольованим і точним значеннями;
        for (int i = 0; i < xyKnotsIntpolated.size(); i++) {
            BigDecimal pn = BigDecimal.valueOf(xyKnotsIntpolated.get(i).get(1));
            BigDecimal pn1 = BigDecimal.valueOf(sinSqResults.get(i).get(1));
            BigDecimal diff = pn.subtract(pn1).abs();
            double res = -Math.log10(diff.doubleValue() + EPSILON);
            exactAccuracyXYKnotsIntropolated.add(res);
        }
        System.out.println(exactAccuracyXYKnotsIntropolated.size());
        System.out.println(accuracyXYKnotsIntropolated.size());

        //обчислення коефіцієнт уточнення інтервального значення, тобто розмитість оцінки
        for (int i = 0; i < accuracyXYKnotsIntropolated.size(); i++) {
            BigDecimal deltaExN = BigDecimal.valueOf(exactAccuracyXYKnotsIntropolated.get(i));
            BigDecimal deltaN = BigDecimal.valueOf(accuracyXYKnotsIntropolated.get(i));

            if (deltaN.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException("Division by zero is not allowed.");
            }

            BigDecimal diff = deltaExN.divide(deltaN, MathContext.DECIMAL128);
            double res = (double)Math.round(1 - diff.doubleValue()*100)/100;
            bluredXYKnotsIntropolated.add(res);
        }

        System.out.println(bluredXYKnotsIntropolated);
        System.out.println(accuracyXYKnotsIntropolated);
        MyGraph.graph(sinSqResults, "Функція sin(x^2)",
                      xyKnotsIntpolated,"Інтерполяція функції",
                      xyKnots, "Вузли");
        DrawGraph.draw(exactAccuracyXYKnotsIntropolated, "Абсолютна похибка інтерполяційного многочлена sin(x^2)");
        DrawGraph.draw(accuracyXYKnotsIntropolated, "Похибка інтерполяційного многочлена sin(x^2)");
        DrawGraph.draw(bluredXYKnotsIntropolated, "Розмитість оцінки похибки");
        CreateTable.table(xyKnots);


    }

    /*
     * Num experiment of f(x) = sin(x)
     * xj = j*Pi/m*2, yj = f(xj), j = 0,....,m.
     */
    public static void experiment(){
        clearArraysForExperiment();
        for(int j = 0; j <= n; j++){
            System.out.println(n);
            ArrayList<Double> doubleList = new ArrayList<>();
            double x = a + (double) Math.round((j / (double) n) * (Math.PI / 2) * 100) / 100;
            double y = sinFunc(x);
            doubleList.add(x);
            doubleList.add(y);
            sinArr.add(doubleList);
        }

        BigDecimal end = BigDecimal.valueOf(b);
        for (BigDecimal i = BigDecimal.valueOf(a); i.compareTo(end) <= 0; i = i.add(BigDecimal.valueOf(0.01))) {
            ArrayList<Double> doubleList = new ArrayList<>();
            double x = i.doubleValue();
            double y = PolynimialLagrange.lagrange(sinArr, x);
            doubleList.add(x);
            doubleList.add(y);
            sinArrIntropolated.add(doubleList);
        }

        for (BigDecimal i = BigDecimal.valueOf(a); i.compareTo(end) <= 0; i = i.add(BigDecimal.valueOf(0.01))) {
            ArrayList<Double> doubleList = new ArrayList<>();
            double x = i.doubleValue();
            double y = sinFunc(x);
            doubleList.add(x);
            doubleList.add(y);
            sinResults.add(doubleList);
        }

        //Розрахуємо похибки
        //похибка інтерполяції(використовуємо інтерпольовані значення)
        for (int i = 0; i < sinArrIntropolated.size()-1; i++) {
            BigDecimal pn = BigDecimal.valueOf(sinArrIntropolated.get(i).get(1));
            BigDecimal pn1 = BigDecimal.valueOf(sinArrIntropolated.get(i+1).get(1));
            BigDecimal diff = pn.subtract(pn1).abs();
            double res = -Math.log10(diff.doubleValue());
            accuracySinArrIntropolated.add(res);
        }

        for (int i = 0; i < sinResults.size()-1; i++) {
            BigDecimal pn = BigDecimal.valueOf(sinResults.get(i).get(1));
            BigDecimal pn1 = BigDecimal.valueOf(sinResults.get(i+1).get(1));
            BigDecimal diff = pn.subtract(pn1).abs();
            double res = -Math.log10(diff.doubleValue());
            accuracySinResults.add(res);
        }
        //цей код для визначення абсолютнної похибки - delta exact n -
        // різниця між інтерпольованим і точним значеннями;
        for (int i = 0; i < sinArrIntropolated.size(); i++) {
            BigDecimal pn = BigDecimal.valueOf(sinArrIntropolated.get(i).get(1));
            BigDecimal pn1 = BigDecimal.valueOf(sinResults.get(i).get(1));
            BigDecimal diff = pn.subtract(pn1).abs();
            double res = -Math.log10(diff.doubleValue() + EPSILON);
            exactAccuracySinArrIntropolated.add(res);
        }

        //обчислення коефіцієнт уточнення інтервального значення, тобто розмитість оцінки
        for (int i = 0; i < accuracySinResults.size(); i++) {
            BigDecimal deltaExN = BigDecimal.valueOf(exactAccuracySinArrIntropolated.get(i));
            BigDecimal deltaN = BigDecimal.valueOf(accuracySinResults.get(i));

            if (deltaN.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException("Division by zero is not allowed.");
            }

            BigDecimal diff = deltaExN.divide(deltaN, MathContext.DECIMAL128);
            double res = (double)Math.round(1 - diff.doubleValue()*100)/100;
            bluredSinArrIntropolated.add(res);
        }

        System.out.println(accuracySinArrIntropolated);
        MyGraph.graph(sinResults,  "Функція sin(x)",
                      sinArrIntropolated, "Інтерполяція функції",
                      sinArr, "Вузли");
        DrawGraph.draw(accuracySinArrIntropolated, "Похибка для Інтерпольованого Чисельного експерименту sin(x)");
        DrawGraph.draw(accuracySinResults, "Похибка для Чисельного експерименту sin(x)");
        DrawGraph.draw(exactAccuracySinArrIntropolated, "Графік абсолютної похибки Чисельного експерименту sin(x)");
        DrawGraph.draw(bluredSinArrIntropolated, "Графік розмитості оцінки похибки");
        CreateTable.table(sinArr);
    }

    public static double sinFunc(double X){
        return 1 * Math.pow(X, 25.0);
    }

    private static void clearArrays(){
        xyKnots.clear();
        xyKnotsIntpolated.clear();
        sinSqResults.clear();
        accuracyXYKnotsIntropolated.clear();
        exactAccuracyXYKnotsIntropolated.clear();
    }

    private static void clearArraysForExperiment(){
        sinArr.clear();
        sinArrIntropolated.clear();
        sinResults.clear();
        accuracySinArrIntropolated.clear();
        accuracySinResults.clear();
        exactAccuracySinArrIntropolated.clear();
    }


}
