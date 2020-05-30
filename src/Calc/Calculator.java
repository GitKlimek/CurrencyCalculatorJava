package Calc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DecimalFormat;

public class Calculator {

    public static void main(String[] args) {
        try {
            float amount = Float.parseFloat(args[0]); //amount of money in EUR
            if(amount < 0){ //check if amount is below 0
                System.out.println("Value can't be below 0!");
                return; //if it is, end the program
            }
            String currency = args[1].toUpperCase(); //currency you want to convert to

            boolean isCurrencyFound = false;    //variable which is used to determine if we've found
                                                // the right currency in the xml file

            float rate = 0f;    //rate of the currency we are looking for

            //get document builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //build document
            Document document = builder.parse("currency.xml");
            //normalization of xml structure
            document.getDocumentElement().normalize();
            //get all Cube nodes
            NodeList cubeList = document.getElementsByTagName("Cube");
            //search through all Cube nodes to find the targeted currency
            for(int i=0;i<cubeList.getLength();i++){
                Node node = cubeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    //check if we've found the currency
                    if(element.getAttribute("currency").equals(currency)) {
                        isCurrencyFound = true; //the currency is found
                        rate = Float.parseFloat(element.getAttribute("rate")); //store the rate of the currency
                        break;
                    }
                }
            }
            //if the currency is found in the xml file
            if(isCurrencyFound){
                float convertedValue = amount * rate;   //calculate the value
                System.out.println(new DecimalFormat("##.##").format(convertedValue));  //print converted value
            }
            //if the currency is not found
            else {
                System.out.println("Currency not found!");  //print the info
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
