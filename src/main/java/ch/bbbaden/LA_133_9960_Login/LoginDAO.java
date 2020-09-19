/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.LA_133_9960_Login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.jdom2.Document;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Alexander
 */
public class LoginDAO {

    private SAXBuilder builder;
    private File xmlFile;
    private File xmlFile1;
    private int aufzaehlungid;
    private String DatumUhrzeit;
    private final List<Eintrag> data = new ArrayList<>();

    public LoginDAO() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String path01 = path + "WEB-INF\\DataXML.xml";
        String path02 = path + "WEB-INF\\Gaestebuch.xml";
        xmlFile = new File(path01);
        xmlFile1 = new File(path02);
        builder = new SAXBuilder();
    }

    public User check(String user, String password) {
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("Benuetzer");
            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                if (node.getChildText("Name").equals(user) && node.getChildText("Passwort").equals(password)) {
                    return new User(user, Integer.parseInt(node.getChildText("ID")));
                }
            }
        } catch (JDOMException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("lol");
        return null;
    }

    public List getData() throws JDOMException, IOException {
        Document document = (Document) builder.build(xmlFile1);
        Element rootNode = document.getRootElement();
        List list = rootNode.getChildren("eintrag");
        for (int i = 0; i < list.size(); i++) {
            Element node = (Element) list.get(i);
            data.add(new Eintrag(Integer.parseInt(node.getChildText("ID")), node.getChildText("Name"), node.getChildText("DatumUhrzeit"), node.getChildText("Nachricht")));
        }
        System.out.println("flop");
        return data;
    }

    private String getDatumUhrzeit() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String datum = formatter.format(today);
        formatter = new SimpleDateFormat("HH:mm:ss");
        String zeit = formatter.format(today);
        return datum + " " + zeit;
    }

    public int getAufzaehlungid(List list, Document documen, Element rootNode) {
        //Vergleichswert setzen
        int aufzaehlungid = Integer.MAX_VALUE;
        Element node = (Element) list.get(0);
        aufzaehlungid = Integer.parseInt(node.getChildText("ID"));
        for (int i = 1; i < list.size(); i++) {
            node = (Element) list.get(i);
            if (aufzaehlungid < Integer.parseInt(node.getChildText("ID"))) {
                aufzaehlungid = Integer.parseInt(node.getChildText("ID"));
            }
        }
        return aufzaehlungid + 1;
    }

    public boolean setEintrag(String e, String u) throws IOException {
        try {
            Document document = (Document) builder.build(xmlFile1);
            Element rootNode = document.getRootElement();
            Element eintrag = new Element("eintrag");
            List list = rootNode.getChildren("eintrag");
            eintrag.addContent(new Element("ID").setText("" + getAufzaehlungid(list, document, rootNode)));
            eintrag.addContent(new Element("Name").setText(u));
            eintrag.addContent(new Element("DatumUhrzeit").setText(getDatumUhrzeit()));
            eintrag.addContent(new Element("Nachricht").setText(e));
            rootNode.addContent(eintrag);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(xmlFile1));

            return true;
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
