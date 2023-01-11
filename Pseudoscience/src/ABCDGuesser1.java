import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Runs the Jager formula for user inputed numbers to closest relative error.
 *
 * @author Allen Zheng
 *
 */
public final class ABCDGuesser1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double num = 0;

        while (num <= 0) {
            out.print("Enter a positive number, μ: ");
            String input = in.nextLine();

            if (FormatChecker.canParseDouble(input)
                    && Double.parseDouble(input) > 0) {
                num = Double.parseDouble(input);
            } else {
                out.println("That is not a positive number!\n");
            }
        }

        return num;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double num = 0;

        while (num <= 0 || num == 1) {
            out.print("Enter a positive number not equal to 1: ");
            String input = in.nextLine();

            if (FormatChecker.canParseDouble(input)
                    && Double.parseDouble(input) > 0
                    && Double.parseDouble(input) != 1) {
                num = Double.parseDouble(input);
            } else {
                out.println("That is not a positive number not equal to 1!\n");
            }
        }

        return num;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        final double[] jagerNums = { -5, -4, -3, -2, -1, -1.0 / 2, -1.0 / 3,
                -1.0 / 4, 0, 1.0 / 4, 1.0 / 3, 1.0 / 2, 1, 2, 3, 4, 5 };
        final int jagerSize = jagerNums.length;
        final int percentage = 100;

        double mu = getPositiveDouble(in, out);
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);
        double a = 0;
        double b = 0;
        double c = 0;
        double d = 0;
        int jagerW = 0;
        int jagerX = 0;
        int jagerY = 0;
        int jagerZ = 0;

        double error = percentage;
        double tempError;
        double approximation = 1;
        double tempApproximation;

        // I have no idea why we have to use while loops, but here it is :)
        while (jagerW < jagerSize) {
            while (jagerX < jagerSize) {
                while (jagerY < jagerSize) {
                    while (jagerZ < jagerSize) {
                        tempApproximation = Math.pow(w, jagerNums[jagerW])
                                * Math.pow(x, jagerNums[jagerX])
                                * Math.pow(y, jagerNums[jagerY])
                                * Math.pow(z, jagerNums[jagerZ]);
                        tempError = Math.abs((tempApproximation - mu) / mu)
                                * percentage;

                        if (tempError < error) {
                            error = tempError;
                            approximation = tempApproximation;
                            a = jagerNums[jagerW];
                            b = jagerNums[jagerX];
                            c = jagerNums[jagerY];
                            d = jagerNums[jagerZ];
                        }
                        jagerZ++;
                    }
                    jagerY++;
                    jagerZ = 0;
                }
                jagerX++;
                jagerY = 0;
                jagerZ = 0;
            }
            jagerW++;
            jagerX = 0;
            jagerY = 0;
            jagerZ = 0;
        }

        out.println("\nApproximation: " + approximation);
        out.println("Relative Error: " + error);
        out.println("A: " + a);
        out.println("B: " + b);
        out.println("C: " + c);
        out.println("D: " + d);

        in.close();
        out.close();
    }

}
