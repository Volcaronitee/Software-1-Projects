import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        int answer = 0;
        int num1 = 0;
        int num2 = 0;

        // Get num1
        if (exp.child(0).label().equals("number")) {
            num1 = Integer.parseInt(exp.child(0).attributeValue("value"));
        } else {
            num1 = evaluate(exp.child(0));
        }

        // Get num2
        if (exp.child(1).label().equals("number")) {
            num2 = Integer.parseInt(exp.child(1).attributeValue("value"));
        } else {
            num2 = evaluate(exp.child(1));
        }

        if (exp.label().equals("plus")) {
            answer = num1 + num2;
        } else if (exp.label().equals("minus")) {
            answer = num1 - num2;
        } else if (exp.label().equals("times")) {
            answer = num1 * num2;
        } else if (exp.label().equals("divide")) {
            answer = num1 / num2;
        }

        return answer;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
