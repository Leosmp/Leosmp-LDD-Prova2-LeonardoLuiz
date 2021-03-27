/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Dev Dreamm
 */
public class Q2_DOM {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("albums.xml"));
        Document q2_output = db.newDocument();
        
        Element ol = q2_output.createElement("ol");
        q2_output.appendChild(ol);

        Map<String, String> map = new HashMap<>();

        NodeList songs = doc.getElementsByTagName("song");
        for (int i = 0; i < songs.getLength(); i++) {
            Element ele = (Element) songs.item(i);
            map.put(ele.getAttribute("title"), ele.getAttribute("length"));

        }
        
        Object[] orderedMap
                = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).toArray();
        for

        //System.out.println(map);
    }

}
