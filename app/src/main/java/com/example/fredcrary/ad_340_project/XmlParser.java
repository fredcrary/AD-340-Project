package com.example.fredcrary.ad_340_project;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fred Crary on 5/9/2017.
 * Modified from https://developer.androic.com/training/basics/network-ops/xml.html
 */

public class XmlParser {
    private static final String ns = null;  //We're not using name spaces

    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
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

    private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        //List<Entry> entries = new ArrayList<Entry>;
        List<Entry> entries = new ArrayList<Entry>();

        parser.require(XmlPullParser.START_TAG, ns, "booklist");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // starts by looking for the book tag
            if( name.equals("book")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    // This class represents a single book in the XML feed
    // It includes the data members "title", "author", "isbn", "price", "cover"
    public static class Entry {
        public final String title;
        public final String author;
        public final String isbn;
        public final String price;
        public final String cover;

        private Entry(String title, String author, String isbn, String price, String cover) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.price = price;
            this.cover = cover;
        }
    }

    // Parses the content of an entry. If it encounters one of the target tags, hands them
    // off to their respective "read" methods fro processing. Otherwise it skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "book");
        String title = null;
        String author = null;
        String isbn = null;
        String price = null;
        String cover = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            //String name = parser.getName();
            //if      (name.equals("title"))  {title  = readString(parser, "title");}
            //else if (name.equals("author")) {author = readString(parser, "author");}
            //else if (name.equals("isbn"))   {isbn   = readString(parser, "isbn");}
            //else if (name.equals("price"))  {price  = readString(parser, "price");}
            //else if (name.equals("cover"))  {cover  = readString(parser, "cover");}
            //else                            {skip(parser);};
            switch (parser.getName()) {
                case "title":
                    title = readString(parser, "title");
                    break;
                case "author":
                    author = readString(parser, "author");
                    break;
                case "isbn":
                    isbn = readString(parser, "isbn");
                    break;
                case "price":
                    price = readString(parser, "price");
                    break;
                case "cover":
                    cover = readString(parser, "cover");
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new Entry(title, author, isbn, price, cover);
    }

    // Reads the target string tags in the feed
    private String readString(XmlPullParser parser, String target) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, target);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, target);
        return value;
    }

    private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in.
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.nextTag()) {
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
