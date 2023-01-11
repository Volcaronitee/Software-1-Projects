import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to take in a text file of terms and definitions and make it into a
 * html page.
 *
 * @author Allen Zheng
 *
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * fileOutputs the "opening" tags in the generated HTML file.
     *
     * @param fileOut
     *            the fileOutput stream
     * @param term
     *            name of the term
     * @param definition
     *            definition of the term
     * @param link
     *            link to return to index
     * @updates fileOut.content
     * @requires fileOut.is_open and not null
     * @ensures fileOut.content = complete html definition page
     */
    static void termPage(SimpleWriter fileOut, String term, String definition,
            String link) {
        assert fileOut != null : "Violation of: fileOut is not null";
        assert fileOut.isOpen() : "Violation of: fileOut.is_open";

        // Title
        fileOut.println("<html> <head> <title> " + term + " </title> </head>");

        // Header
        fileOut.println("<body> <h2> <b> <i> <font color=\"red\"> " + term);
        fileOut.println("</font> </i> </b> </h2>");

        // Definition
        fileOut.println("<blockquote> " + definition + "</blockquote> <hr>");

        // Return Link
        fileOut.println("<p> Return to <a href=\"" + link);
        fileOut.println("index.html\">index</a>. </p> </body> </html>");
    }

    /**
     * Gather the data from user input file and return as a Map.
     *
     * @param file
     *            user input file
     * @param data
     *            data map to copy file onto
     * @updates data.content
     * @requires file.is_open and not null
     * @ensures file content copied as word/definition pairs onto data
     */
    static void getData(SimpleReader file, Map<String, String> data) {
        assert file != null : "Violation of: file is not null";
        assert file.isOpen() : "Violation of: file.is_open";
        String word = file.nextLine();
        String definition = file.nextLine();
        String buffer = file.nextLine();

        while (!file.atEOS()) {
            if (buffer.equals("")) {
                data.add(word, definition);
                word = file.nextLine();
                buffer = file.nextLine();
            } else {
                definition = buffer;
                buffer = file.nextLine();

                while (buffer.length() > 0 && !file.atEOS()) {
                    definition = definition.concat(" " + buffer);
                    buffer = file.nextLine();
                }
            }
        }
        data.add(word, definition);
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
        String fileName;

        // File Directory
        out.print("Enter the directory of the current java file "
                + "(ex: file:///C:/Users/volca/Desktop/OsuCseWsTemplate"
                + "/workspace/Glossary/): ");
        String link = in.nextLine();

        out.print("Enter the name of the input data file: ");
        fileName = in.nextLine();
        SimpleReader file = new SimpleReader1L(fileName);

        Map<String, String> data = new Map1L<>();
        getData(file, data);

        // Making an alphabetical queue
        Queue<String> key = new Queue1L<>();
        for (Pair<String, String> line : data) {
            key.enqueue(line.key());
        }
        Comparator<String> cs = new StringLT();
        key.sort(cs);

        // Begin HTML page
        SimpleWriter index = new SimpleWriter1L("index.html");
        SimpleWriter term = new SimpleWriter1L();
        index.println("<html> <head> <title>Index</title> </head>");
        index.println("<body> <h2>Glossary</h2> <hr> <h3>Index</h3> <ol>");

        // Term pages + links to them
        for (String word : key) {
            String definition = data.value(word);
            term = new SimpleWriter1L(word + ".html");
            termPage(term, word, definition, link);
            index.print("<li> <a href = \"" + link + word + ".html\">");
            index.println(word + "</a> </li>");
        }

        // Footer
        index.println("</ol> </body> </html>");

        in.close();
        out.close();
        index.close();
        term.close();
    }
}
