/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author Leonardo Souto Maior
 */
public class Q1_StAX {

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, IOException {
        XMLInputFactory xmlif = XMLInputFactory.newFactory();
        Reader reader = new FileReader("albums.xml");
        XMLStreamReader xmlsr = xmlif.createXMLStreamReader(reader);

        XMLOutputFactory xmlof = XMLOutputFactory.newFactory();
        Writer writer = new FileWriter("q1_output.xml");
        XMLStreamWriter xmlsw = xmlof.createXMLStreamWriter(writer);

        String album = null;
        String song = null;
        String length = null;
        Map<String, List<String>> mapAlbumSong = new HashMap<>();
        Map<String, String> mapSongLength = new HashMap<>();

        xmlsw.writeStartDocument();
        xmlsw.writeStartElement("html");
        while (xmlsr.hasNext()) {
            switch (xmlsr.next()) {
                case XMLStreamReader.START_ELEMENT:

                    String qAlbum = xmlsr.getLocalName();
                    if (qAlbum.equals("album")) {
                        album = xmlsr.getAttributeValue(0);
                        mapAlbumSong.put(album, new ArrayList<>());
                    }

                    if (qAlbum.equals("song")) {
                        song = xmlsr.getAttributeValue(0);
                        length = xmlsr.getAttributeValue(1);
                        mapAlbumSong.get(album).add(song);
                        mapSongLength.put(song, length);
                    }

                    break;

                case XMLStreamReader.END_DOCUMENT:
                    for (String s : mapAlbumSong.keySet()) {
                        List<String> listAlbum = mapAlbumSong.get(s);
                        xmlsw.writeStartElement("table");
                        xmlsw.writeStartElement("caption");
                        xmlsw.writeCharacters(s);
                        xmlsw.writeEndElement();
                        for (String x : listAlbum) {
                            xmlsw.writeStartElement("tr");
                            xmlsw.writeStartElement("td");
                            xmlsw.writeCharacters(x);
                            xmlsw.writeEndElement();
                            xmlsw.writeStartElement("td");
                            xmlsw.writeCharacters(mapSongLength.get(x));
                            xmlsw.writeEndElement();
                            xmlsw.writeEndElement();
                        }
                        xmlsw.writeEndElement();
                    }
                    break;
            }
        }
        xmlsw.writeEndElement();
        xmlsw.writeEndDocument();
        xmlsw.close();
    }

}
