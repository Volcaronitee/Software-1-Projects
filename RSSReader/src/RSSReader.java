import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML fileOutput file.
 *
 * @author Put your name here
 *
 */
public final class RSSReader {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSReader() {
    }

    /**
     * fileOutputs the "opening" tags in the generated HTML file.
     *
     * @param channel
     *            the channel element XMLTree
     * @param fileOut
     *            the fileOutput stream
     * @updates fileOut.content
     * @requires [the root of channel is a <channel> tag] and fileOut.is_open
     * @ensures fileOut.content = #fileOut.content * [the HTML "opening" tags]
     */
    private static void fileOutputHeader(XMLTree channel,
            SimpleWriter fileOut) {
        assert channel != null : "Violation of: channel is not null";
        assert fileOut != null : "Violation of: fileOut is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert fileOut.isOpen() : "Violation of: fileOut.is_open";

        XMLTree title = channel.child(getChildElement(channel, "title"));
        XMLTree description = channel
                .child(getChildElement(channel, "description"));
        XMLTree link = channel.child(getChildElement(channel, "link"));

        // Title
        if (title.numberOfChildren() == 1) { // Maybe no child
            fileOut.println(
                    "<html> <head> <title>" + title.child(0) + "</title>");
        } else {
            fileOut.println("<html> <head> <title>No Title</title");
        }
        fileOut.println("</head> <body>");

        // Link
        if (title.numberOfChildren() == 1) {
            fileOut.println("<h1>");
            fileOut.println("<a href=\"" + link.child(0) + "\">"
                    + title.child(0) + "</a>");
            fileOut.println("</h1>");
        } else {
            fileOut.println("<h1>");
            fileOut.println("<a href=\"No Title\">" + title.child(0) + "</a>");
            fileOut.println("</h1>");
        }

        // Description
        fileOut.println("<p>");
        if (description.numberOfChildren() == 1) { // Maybe no child
            fileOut.println(description.child(0));
        } else {
            fileOut.println("no description");
        }
        fileOut.println("</p>");

        // Table
        fileOut.println("<table border=\"1\">");
        fileOut.println("<tr>");
        fileOut.println("<th>Date</th>");
        fileOut.println("<th>Source</th>");
        fileOut.println("<th>News</th>");
        fileOut.println("</tr>");
    }

    /**
     * fileOutputs the "closing" tags in the generated HTML file.
     *
     * @param fileOut
     *            the fileOutput stream
     * @updates fileOut.contents
     * @requires fileOut.is_open
     * @ensures fileOut.content = #fileOut.content * [the HTML "closing" tags]
     */
    private static void fileOutputFooter(SimpleWriter fileOut) {
        assert fileOut != null : "Violation of: fileOut is not null";
        assert fileOut.isOpen() : "Violation of: fileOut.is_open";

        fileOut.println("</table>");
        fileOut.println("</body> </html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of the {@code XMLTree} matching the
     *         given tag or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of the {@code XMLTree} matching the
     *   given tag or -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int childElement = -1;
        for (int i = xml.numberOfChildren() - 1; i >= 0; i--) {
            if (xml.child(i).label().equals(tag)) {
                childElement = i;
            }
        }
        return childElement;
    }

    /**
     * Processes one news item and fileOutputs the title, or the description if
     * the title is not present, and the link (if available) with appropriate
     * labels.
     *
     * @param item
     *            the news item
     * @param fileOut
     *            the fileOutput stream
     * @requires [the label of the root of item is an <item> tag] and
     *           fileOut.is_open
     * @ensures fileOut.content = #fileOut.content * [the title (or description)
     *          and link]
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter a file name: ");
        String fileName = in.nextLine();
        SimpleWriter fileOut = new SimpleWriter1L(fileName);

        out.print("Enter the URL of an RSS 2.0 news feed: ");
        String url = in.nextLine();

        XMLTree xml = new XMLTree1(url);
        XMLTree channel = xml.child(0);

        fileOutputHeader(channel, fileOut);

        for (int i = 0; i < channel.numberOfChildren(); i++) {
            XMLTree item = channel.child(i);

            if (item.label().equals("item")) {
                XMLTree titleDesc = item;
                XMLTree link = item;
                fileOut.println("<tr>");

                // Date
                if (getChildElement(item, "pubDate") != -1) {
                    XMLTree pubDate = item
                            .child(getChildElement(item, "pubDate"));

                    if (pubDate.numberOfChildren() == 1) {
                        fileOut.println("<td>" + pubDate.child(0) + "</td>");
                    } else {
                        fileOut.println("<td>No date available</td>");
                    }
                } else {
                    fileOut.println("<td>No date available</td>");
                }

                // Source
                if (getChildElement(item, "source") != -1) {
                    XMLTree source = item
                            .child(getChildElement(item, "source"));

                    if (source.numberOfChildren() == 1) {
                        fileOut.println("<td>");
                        String sourceLink = source.attributeValue("url");
                        fileOut.println("<a href=\"" + sourceLink + "\">"
                                + source.child(0) + "</a>");
                        fileOut.println("</td>");
                    } else {
                        fileOut.println("<td>No source available</td>");
                    }
                } else {
                    fileOut.println("<td>No source available</td>");
                }

                // Title / Description
                if (getChildElement(item, "title") != -1) {
                    titleDesc = item.child(getChildElement(item, "title"));
                } else {
                    titleDesc = item
                            .child(getChildElement(item, "description"));
                }
                if (getChildElement(item, "link") != -1) {
                    link = item.child(getChildElement(item, "link"));
                }

                if (titleDesc.numberOfChildren() == 1) {
                    fileOut.println("<td>");
                    if (getChildElement(item, "link") != -1) {
                        fileOut.println("<a href=\"" + link.child(0) + "\">"
                                + titleDesc.child(0) + "</a>");
                    }
                    fileOut.println("</td>");
                } else {
                    fileOut.println("<td>No title available</td>");
                }
                fileOut.println("</tr>");
            }
        }

        fileOutputFooter(fileOut);

        in.close();
        out.close();
        fileOut.close();
    }

}
