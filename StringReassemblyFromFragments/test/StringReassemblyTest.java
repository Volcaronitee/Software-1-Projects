import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * JUnit test case to test all the methods implemented within StringReassembly.
 */
public class StringReassemblyTest {

    /*
     * No need to test addToSetAvoidingSubstrings as the only time it is called
     * is within the linesFromInput method
     */

    /**
     * Test linesFromInput with input file cheer.txt.
     */
    @Test
    public void linesFromInputTest() {
        SimpleReader file = new SimpleReader1L("cheer.txt");
        Set<String> set = StringReassembly.linesFromInput(file);
        Set<String> setExpected = new Set1L<>();
        setExpected.add("Bucks -- Beat");
        setExpected.add("Go Bucks");
        setExpected.add("Beat Mich");
        setExpected.add("Michigan~");
        setExpected.add("o Bucks -- B");

        assertEquals(set, setExpected);

        file.close();
    }

    /**
     * Test printWithLineSeparators with input file load.txt.
     */
    @Test
    public void printWithLineSeparatorsTest1() {
        String str = "Hello World~This is on a new line";

        SimpleWriter load = new SimpleWriter1L("load.txt");
        StringReassembly.printWithLineSeparators(str, load);

        SimpleReader file = new SimpleReader1L("test.txt");
        SimpleReader fileExpected = new SimpleReader1L("expected.txt");

        while (!file.atEOS()) {
            assertEquals(file.nextLine(), fileExpected.nextLine());
        }

        load.close();
        file.close();
        fileExpected.close();
    }
}
