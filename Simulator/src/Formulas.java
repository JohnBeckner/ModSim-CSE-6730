import java.util.Arrays;

public class Formulas {

    /*
     * Gets standard deviation
     */
    public static double standardDeviation(double mean, double[] data) {
        if (data.length < 1) {
            return -1;
        }

        double total = 0;
        for (int i = 0; i < data.length; i++) {
            total += Math.pow((data[i] - mean), 2);
        }

        return Math.sqrt(total / data.length);
    }

    /*
     * Gets mean
     */
    public static double mean(double[] data) {
        if (data.length < 1) {
            return -1;
        }

        double total = 0;
        for (int i = 0; i < data.length; i++) {
            total += data[i];
        }

        return total / data.length;
    }

    /*
     * Gets median
     */
    public static double median(double[] data) {
        Arrays.sort(data);
        if (data.length % 2 != 0) {
            return data[data.length / 2];
        }

        return (data[(data.length - 1) / 2] + data[data.length / 2]) / 2.0;
    }
}
