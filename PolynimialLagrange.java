import java.util.ArrayList;

public class PolynimialLagrange {

    public static double lagrange(ArrayList<ArrayList<Double>> arr, double findY){
        double result = 0;
        for(int i = 0; i < arr.size(); i++){
            double term = arr.get(i).get(1);
            for(int j = 0; j < arr.size(); j++){
                if(i != j){
                    term *= (findY - arr.get(j).get(0)) / (arr.get(i).get(0) - arr.get(j).get(0));
                }
            }
            result += term;
        }
        return result;
    }
}
