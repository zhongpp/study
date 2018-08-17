import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author pingpingZhong
 * Date: 2017/12/27
 * Time: 18:00
 */
public class XMLUtil {


    static String xml = "<?xml version='1.0' encoding='UTF-8'?>" +
            "<data>" +
            "  <message>" +
            "    <status>0</status>" +
            "    <value>处理成功</value>" +
            "  </message>" +
            "  <badInfoDs>" +
            "    <badInfoD inputXm='衷平平' inputZjhm='362428198810124135' inputZt='' inputWfxw='' inputSd='' inputXd=''>" +
            "      <wybs>ff8080814a7ae1c7014a7afc15de001f</wybs>" +
            "      <inputZjhm18>362428198810124135</inputZjhm18>" +
            "      <code>0</code>" +
            "      <message>查询成功_无数据</message>" +
            "      <checkCount2>0</checkCount2>" +
            "      <inputZt />" +
            "      <inputWfxw />" +
            "      <inputSd />" +
            "      <inputXd />" +
            "      <ztCheckresult />" +
            "      <wfxwCheckresult />" +
            "      <sdCheckresult />" +
            "      <xdCheckresult />" +
            "      <item />" +
            "    </badInfoD>" +
            "  </badInfoDs>" +
            "</data>";

    static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

    //Load and parse XML file into DOM
    public static Document parse(String xml) {
        Document document = null;
        try {
            //DOM parser instance
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            //parse an XML file into a DOM tree
            document = builder.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void main(String[] args) throws Exception {
        Document document = parse(xml);
        //get root element
        Element rootElement = document.getDocumentElement();

        //traverse child elements
        NodeList nodes = rootElement.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element child = (Element) node;
                NodeList nodeList = child.getChildNodes();
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node n = nodeList.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println(n.getNodeName());
                    }
                }
                System.out.println(nodeList.item(0).getNodeValue());
                //process child element
            }
        }

        NodeList messageNodeList = rootElement.getElementsByTagName("message");
        if (messageNodeList != null) {
            Element element = (Element) messageNodeList.item(0);
            NodeList message = element.getElementsByTagName("status");
            System.out.println(message.item(0).getTextContent());
            NodeList value = element.getElementsByTagName("value");
            System.out.println(value.item(0).getTextContent());
        }

        NodeList badInfoDNodeList = rootElement.getElementsByTagName("badInfoD");
        if (badInfoDNodeList != null) {
            for (int i = 0; i < badInfoDNodeList.getLength(); i++) {
                Element element = (Element) badInfoDNodeList.item(i);
                NodeList message = element.getElementsByTagName("message");
                System.out.println(message.item(0).getTextContent());
            }
        }

        System.out.println(rootElement.getElementsByTagName("inputWfxw").item(0).getTextContent());

    }


}
