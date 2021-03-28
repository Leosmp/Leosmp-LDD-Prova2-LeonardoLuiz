/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dev Dreamm
 */
public class Q3_SAX extends DefaultHandler {

    private String album;
    private int qtdSong;
    private List<Teste> list = new ArrayList<>();

    public List<Teste> getList() {
        return list;
    }

    @Override
    public void endDocument() throws SAXException {
        Collections.sort(list);
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

        if (qName.equals("album")) {
            album = atts.getValue(0);
            qtdSong = 0;
        } else if (qName.equals("song")) {
            qtdSong++;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (qName.equals("album")) {

            Teste albumFaixa = new Teste();
            albumFaixa.album = album;
            albumFaixa.qtd = qtdSong;
            list.add(albumFaixa);
        }
    }

    public static void main(String[] args) throws IOException, XMLStreamException {
        File inputFile = new File("albums.xml");
        Q3_SAX userhandler = new Q3_SAX();

        XMLOutputFactory xmlof = XMLOutputFactory.newFactory();
        Writer writer = new FileWriter("q3_output.xml");
        XMLStreamWriter xmlsw = xmlof.createXMLStreamWriter(writer);

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputFile, userhandler);

            //Inicio do documento
            xmlsw.writeStartDocument();
            xmlsw.writeStartElement("table");
            List<Teste> list2 = userhandler.getList();
            for (Teste string : list2) {
                xmlsw.writeStartElement("tr");
                xmlsw.writeStartElement("td");
                xmlsw.writeCharacters(string.album);
                xmlsw.writeEndElement();
                xmlsw.writeStartElement("td");
                xmlsw.writeCharacters(string.qtd.toString());
                xmlsw.writeEndElement();
                xmlsw.writeEndElement();
            }
            xmlsw.writeEndElement();
            xmlsw.writeEndDocument();
            xmlsw.close();
            //Fim do documento
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println(e.getMessage());
        }

    }

    public class Teste implements Comparable<Teste> {

        private String album;
        private Integer qtd;

        @Override
        public int compareTo(Teste o) {
            int valor = qtd.compareTo(o.qtd) * -1;
            if (valor == 0) {
                return album.compareTo(o.album);
            }
            return valor;
        }
    }

}
