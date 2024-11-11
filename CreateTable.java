import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Vector;

public class CreateTable extends BodyApp {
    static ArrayList<ArrayList<Double>> myTable = new ArrayList<>();

    static ArrayList<ArrayList<Double>> tab1 = new ArrayList<>();
    static ArrayList<ArrayList<Double>> tab2 = new ArrayList<>();
    public static void table(ArrayList<ArrayList<Double>> arr){
        myTable.clear();
        tab1.clear();
        tab2.clear();
        for (int i = 0; i < n; i++) {
            ArrayList<Double> ls = new ArrayList<>();
            tab1.clear();
            tab2.clear();

            for (int j = 0; j <= i; j++) {
                tab1.add(arr.get(j));
            }

            for (int k = 0; k <= i + 1; k++) {
                tab2.add(arr.get(k));
            }

            BigDecimal pn = BigDecimal.valueOf(PolynimialLagrange.lagrange(tab1, InX));
            BigDecimal pn1 = BigDecimal.valueOf(PolynimialLagrange.lagrange(tab2, InX));
            BigDecimal diff = pn.subtract(pn1);

            BigDecimal original = BigDecimal.valueOf(BodyApp.sinSquared(InX));
            BigDecimal diff2 = pn.subtract(original);

            BigDecimal blured;
            if (diff.compareTo(BigDecimal.ZERO) == 0) {
                blured = BigDecimal.ZERO;
            } else {
                blured = diff2.divide(diff, MathContext.DECIMAL128);
            }

            ls.add((double) i + 1);
            ls.add(diff.doubleValue());
            ls.add(diff2.doubleValue());
            ls.add(1 - blured.doubleValue());

            myTable.add(ls);
        }
        findY = PolynimialLagrange.lagrange(xyKnots, InX);
        yResultTextField2.setText(Double.toString(findY));

        accuracyTextField2.setText(Double.toString(myTable.get(myTable.size()-1).get(2)));

        ROPTextField2.setText(Double.toString(myTable.get(myTable.size()-1).get(3)));


        Vector<Vector<Object>> rowData = new Vector<>();
        for (ArrayList<Double> row : myTable) {
            Vector<Object> newRow = new Vector<>(row);
            rowData.add(newRow);
        }

// Define column names
        Vector<String> columnNames = new Vector<>();
        columnNames.add("n");
        columnNames.add("delta_n");
        columnNames.add("exact_delta_n");
        columnNames.add("k");

// Create a DefaultTableModel and JTable using rowData and columnNames
        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
        JTable table = new JTable(tableModel);

// Display the JTable in a JFrame
        JFrame frame = new JFrame("Interpolation Error Table");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
