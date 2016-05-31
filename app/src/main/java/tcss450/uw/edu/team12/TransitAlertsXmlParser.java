package tcss450.uw.edu.team12;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses XML in order to read a web feed.
 *
 * Created by Lachezar Dimov on 5/17/2016.
 * Base code used from https://developer.android.com/training/basics/network-ops/xml.html#parse
 */
public class TransitAlertsXmlParser {

    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    /**
     * Reads an feed by parsing XML.
     */
    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the item tag
            if (name.equals("item")) {
                entries.add(readItem(parser));
            } else if (name.equals("channel")) {
                continue;
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    /**
     * Creats an item containing the title of the article, publication date and
     * web link.
     */
    public static class Item {
        public final String title;
        public final String pubDate;
        public final String link;

        private Item(String title, String pubDate, String link) {
            this.title = title;
            this.pubDate = pubDate;
            this.link = link;
        }
    }

    /**
     * Parses the contents of an item. If it encounters a title, publication date, or link tag,
     * hands them off to their respective "read" methods for processing. Otherwise, skips the tag.
     */
    private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String pubDate = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("pubDate")) {
                pubDate = readPubDate(parser);

            } else if (name.equals("title")) {
                title = readTitle(parser);

            } else if (name.equals("link")) {

                link = readLink(parser);

            } else {
                skip(parser);
            }
        }
        return new Item(title, pubDate, link);
    }

    /** Processes title tags in the feed. */
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    /** Processes pubDate tags in the feed. */
    private String readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        String date = "";
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String tag = parser.getName();
        date = readText(parser);

        // Format date, since it is in the format of Sun, 08 May 2016 17:18:25 -0500
        date = date.substring(0, date.length() - 6);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return date;
    }

    /** Processes link tags in the feed. */
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();

        link = readText(parser);

        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }



    /** Extracts text values from title and pubDate tags. */
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
