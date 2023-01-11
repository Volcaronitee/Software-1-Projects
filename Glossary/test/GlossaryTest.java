import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Allen Zheng
 *
 */
public class GlossaryTest {

    /**
     * Test an example complete definition page.
     */
    @Test
    public void termPageTest1() {
        SimpleWriter file = new SimpleWriter1L("termPageTest1.html");
        SimpleReader fileExpected = new SimpleReader1L("termPageTest1Exp.html");
        String term = "TEST";
        String termExpected = "TEST";
        String definition = "THIS IS JUST A TEST";
        String definitionExpected = "THIS IS JUST A TEST";
        String link = "no link";
        String linkExpected = "no link";

        Glossary.termPage(file, term, definition, link);
        SimpleReader fileResult = new SimpleReader1L("termPageTest1.html");
        while (!fileResult.atEOS()) {
            assertEquals(fileResult.nextLine(), fileExpected.nextLine());
        }
        assertEquals(term, termExpected);
        assertEquals(definition, definitionExpected);
        assertEquals(link, linkExpected);
        file.close();
        fileExpected.close();
        fileResult.close();
    }

    /**
     * Test link to index on term html page.
     */
    @Test
    public void termPageTest2() {
        SimpleWriter file = new SimpleWriter1L("termPageTest2.html");
        SimpleReader fileExpected = new SimpleReader1L("termPageTest2Exp.html");
        String term = "TEST";
        String termExpected = "TEST";
        String definition = "THIS IS JUST A TEST";
        String definitionExpected = "THIS IS JUST A TEST";
        String link = "file:///C:/Users/volca/Desktop/OsuCseWsTemplate/"
                + "workspace/Glossary/";
        String linkExpected = "file:///C:/Users/volca/Desktop/OsuCseWsTemplate/"
                + "workspace/Glossary/";

        Glossary.termPage(file, term, definition, link);
        SimpleReader fileResult = new SimpleReader1L("termPageTest2.html");
        while (!fileResult.atEOS()) {
            assertEquals(fileResult.nextLine(), fileExpected.nextLine());
        }
        assertEquals(term, termExpected);
        assertEquals(definition, definitionExpected);
        assertEquals(link, linkExpected);
        file.close();
        fileExpected.close();
        fileResult.close();
    }

    /**
     * Test all word definition combinations within a simple input file.
     */
    @Test
    public void getDataTest1() {
        SimpleReader file = new SimpleReader1L("sample.txt");
        SimpleReader fileExpected = new SimpleReader1L("sample.txt");
        Map<String, String> data = new Map1L<>();
        Map<String, String> dataExpected = new Map1L<>();
        dataExpected.add("WORD", "DEFINITION");
        dataExpected.add("WORD2 WORD2", "DEFINITION");
        dataExpected.add("WORD3", "DEFINITION DEFINTION");
        dataExpected.add("WORD4", "DEFINITION DEFINTION DEFINITION DEFINITION "
                + "DEFINTION DEFINITION DEFINITION");
        Glossary.getData(file, data);
        while (!file.atEOS()) {
            assertEquals(file.nextLine(), fileExpected.nextLine());
        }
        assertEquals(data, dataExpected);
        file.close();
        fileExpected.close();
    }
}
