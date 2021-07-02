import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class myProg {
    public static TreeSet<String> setOfNonFullNameTag = new TreeSet<>();
    public static TreeSet<String> setOfFullNameTag = new TreeSet<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        File cars = new File("src/" + args[0]);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(cars, handler);
        for(String string : setOfFullNameTag) {
            System.out.println(string);
        }

        for(String string : setOfNonFullNameTag) {
            System.out.println(string);
        }
        System.out.println(setOfFullNameTag.size());
        System.out.println(setOfNonFullNameTag.size());
    }

    private static class XMLHandler extends DefaultHandler {

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("modification")) {
                if (attributes.getValue("name") == null) return;
                Pattern pattern = Pattern.compile("\\d\\.\\d\\w? \\w{2,4} \\(.+\\)");
                Matcher matcher = pattern.matcher(attributes.getValue("name"));
                StringBuilder collectingShortName = new StringBuilder();
                if(matcher.find()) {
                    collectingShortName.append(attributes.getValue("name").substring(matcher.start(), matcher.end()));
                }


                if(!setOfNonFullNameTag.contains(collectingShortName.toString())) {
                    setOfNonFullNameTag.add(collectingShortName.toString());
                    setOfFullNameTag.add(attributes.getValue("name"));
                }
            }
        }

    }


}
