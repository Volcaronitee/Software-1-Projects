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
public final class ABCDGuesser2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser2() {
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
     * Calculates the relative error between a guess and the user inputed value.
     *
     * @param real
     *            the user inputed value
     * @param estimate
     *            the guess calculated by program
     * @return the decimal error
     */
    private static double calcError(double real, double estimate) {
        return Math.abs((estimate - real) / real);
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

        double error = percentage;
        double tempError;
        double approximation = 1;
        double tempApproximation;

        for (double jagerW : jagerNums) {
            for (double jagerX : jagerNums) {
                for (double jagerY : jagerNums) {
                    for (double jagerZ : jagerNums) {
                        tempApproximation = Math.pow(w, jagerW)
                                * Math.pow(x, jagerX) * Math.pow(y, jagerY)
                                * Math.pow(z, jagerZ);
                        tempError = calcError(mu, tempApproximation)
                                * percentage;

                        if (tempError < error) {
                            error = tempError;
                            approximation = tempApproximation;
                            a = jagerW;
                            b = jagerX;
                            c = jagerY;
                            d = jagerZ;
                        }
                    }
                }
            }
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
