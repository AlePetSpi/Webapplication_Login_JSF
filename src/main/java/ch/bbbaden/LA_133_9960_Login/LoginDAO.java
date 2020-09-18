/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.LA_133_9960_Login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    private List<Eintrag> data = new ArrayList<>();

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
        System.out.println("flop");
        return null;
    }

    public List<Eintrag> getData() throws JDOMException, IOException {

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

    public void setEintrag() throws IOException {
        Element root = new Element("eintrag");
        Element ele1 = new Element("ID");
        Element ele2 = new Element("Name");
        Element ele3 = new Element("DatumUhrzeit");
        Element ele4 = new Element("Nachricht");
        ele1.setText("Hello!");
        ele2.setText("World!");
        ele3.setText("asd!");
        ele4.setText("123!");
        root.addContent(ele1);
        root.addContent(ele2);
        root.addContent(ele3);
        root.addContent(ele4);
        root.setAttribute("date", "5.9.02");
        Document doc = new Document(root);
        //Schreiben eines formatierten XML-Dokuments
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, new FileWriter(xmlFile1));

    }

}
