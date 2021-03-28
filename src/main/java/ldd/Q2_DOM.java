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
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Dev Dreamm
 */
public class Q2_DOM {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("albums.xml"));
        Document q2_output = db.newDocument();

        Element ol = q2_output.createElement("ol");
        q2_output.appendChild(ol);

        List<Ordenar> list = new ArrayList<>();
        

        NodeList songs = doc.getElementsByTagName("song");
        for (int i = 0; i < songs.getLength(); i++) {
            Element ele = (Element) songs.item(i);
            Ordenar text = new Ordenar();
            text.length = ele.getAttribute("length");
            text.album = ele.getAttribute("title");
            list.add(text);
        }

        Collections.sort(list);

        for (Ordenar text : list) {
            String item = text.album + " (" + text.length + ")";
            Element li = q2_output.createElement("li");
            li.setTextContent(item);
            ol.appendChild(li);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(q2_output);
        StreamResult result = new StreamResult(new File("q2_output.xml"));
        transformer.transform(source, result);
    }
    
    public static class Ordenar implements Comparable<Ordenar> {

        private String album;
        private String length;

        @Override
        public int compareTo(Ordenar o) {
            int valor = length.compareTo(o.length) * -1;
            if (valor == 0) {
                return album.compareTo(o.album);
            }
            return valor;
        }
    }

}
