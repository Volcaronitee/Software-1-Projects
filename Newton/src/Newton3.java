import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Newton3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton3() {
    }

    /**
     * Computes estimate of square root of x to within relative error of 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @param errorMargin
     *            the smallest acceptable margin of error
     * @return estimate of square root
     */
    private static double sqrt(double x, double errorMargin) {
        double r = x;
        if (r != 0) {
            double error = ((r * r) - x) / x;

            while (error > (errorMargin * errorMargin)) {
                r = (r + x / r) / 2;
                if ((r * r) - x > 0) {
                    error = ((r * r) - x) / x;
                } else { // "Takes" the absolute value of numerator
                    error = -((r * r) - x) / x;
                }
            }
        }

        return r;
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
        String run = "y";
        out.print("Enter the relative error: ");
        double errorMargin = in.nextDouble();
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        while (run.equals("y")) {
            out.print("Enter a positive number to take the square root of: ");
            double num = in.nextDouble();
            double numSqrt = sqrt(num, errorMargin);
            out.println("The square root of " + num + " is " + numSqrt);
            out.print("Do you wish to calculate another square root (y/n)? ");
            run = in.nextLine();
            out.println("");
        }

        out.println("Goodbye!");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
