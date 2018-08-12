package org.yejh.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class Dom4JTest {
    public static void main(String[] args) throws Exception {
        File xmlFile = new File("src/main/resources/dom4j.xml");
        System.out.println("absolutePath: " + xmlFile.getAbsolutePath());

        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlFile);
        Element root = document.getRootElement();
        Iterator<Element> iterator = root.elementIterator("Province");
        while (iterator.hasNext()) {
            Element provinceEle = iterator.next();
            System.out.println(provinceEle.attribute(0).getValue());
            Iterator<Element> cityIterator = provinceEle.elementIterator("City");
            while (cityIterator.hasNext()) {
                Element cityEle = cityIterator.next();
//                String city = cityEle.elementText("City");
                System.out.println("city: " + cityEle.getStringValue());
            }
        }
    }
}
