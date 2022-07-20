package xmlHandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import javax.xml.parsers.*;


public class XmlHandling {
	public static List<String> datas;
	public static char initialParameter;
	public static void main(String[] args) throws ParserConfigurationException, Exception, IOException {
		 Document doc = readXmlFile();
         readRecord(doc);                                      
        Collections.sort(datas, new Comparator<String>() {
        
        @Override
        public int compare(String firstString, String secondString) {
            return firstString.compareToIgnoreCase(secondString);                               
        }
    });
        
        menu();
	}
	
	//Read XML file
	private static Document readXmlFile() throws ParserConfigurationException, SAXException, IOException {
		File inputFile = new File("./src/XmlHandling/resources/test-data.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		return doc;
	}

	// Read record tag
	private static void readRecord(Document doc) {
		NodeList nList = doc.getElementsByTagName("record");
         datas = new ArrayList<>();
         for (int i = 0; i < nList.getLength();i++) {
            Node nNode = nList.item(i);
            getNamesAndCountries( nNode);
         }         
        datas.removeIf(Objects::isNull);
	}
	
	// Get names and countries from element node
	private static void getNamesAndCountries( Node nNode) {
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		   Element eElement = (Element) nNode;            
		         datas.add(eElement.getElementsByTagName("name").item(0).getTextContent()+", "+eElement.getElementsByTagName("country").item(0).getTextContent());                  
		}
	}
	
	//Sort names by alphabet
	private static void sortByAlphabet() throws IOException {
		int numberOfElements;
		for (int elementNumber=1; elementNumber<datas.size();elementNumber++)
        {           
            numberOfElements=Collections.frequency(datas, datas.get(elementNumber));
            if(numberOfElements!=1)
            {
                if (!datas.get(elementNumber).equals(datas.get(elementNumber+1)))
                {
                    System.out.println(datas.get(elementNumber));
                }           
            }else
                {
                	System.out.println(datas.get(elementNumber));                       
                }
        
		
	}
		menu();
	}
	
	//Sort names by initial letter
	private static void sortByInitial(char initialParameter) throws IOException {
		int numberOfElements;
		for (int elementNumber=1; elementNumber<datas.size();elementNumber++)
        {
            numberOfElements=Collections.frequency(datas, datas.get(elementNumber));          
            if(numberOfElements!=1)
            {
                if (!datas.get(elementNumber).equals(datas.get(elementNumber+1)))
                {
                	printNames(initialParameter, numberOfElements, elementNumber);
                }           
            }else
                { 
            		printNames(initialParameter, numberOfElements, elementNumber);
                }		
	}
		menu();
	}

	// Print names in an organized form
	private static void printNames(char initialParameter, int numberOfElements, int elementNumber) {
		if (String.valueOf(datas.get(elementNumber).substring(0,1).toLowerCase()).equals(String.valueOf(initialParameter).toLowerCase())) {
			System.out.println(datas.get(elementNumber));
		}
	}
	
	//Ordering menu
	private static void menu(){
		int orederingChoise=0;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nOrder by");
        System.out.println("1. Alphabet");
        System.out.println("2. Initial");
        System.out.println("3. Exit");
        try {	
        orederingChoise = scanner.nextInt();
        }
		catch (InputMismatchException e){
			System.out.println("Is not a number");
		}
        switch (orederingChoise) {
		case 1: {			
			try {
				sortByAlphabet();
			} catch (IOException e) {
				System.out.println("Input error: "+e);
				e.printStackTrace();
			}
			break;
		}
		case 2: {			
			try {
				@SuppressWarnings("resource")
				Scanner scannerParameter = new Scanner(System.in);	
				System.out.println("Initial parameter: ");
				 String str=scannerParameter.next();
				initialParameter= str.charAt(0);
				sortByInitial(initialParameter);
			} catch ( IOException e ) {
				System.out.println("Input error: "+e);
				e.printStackTrace();
			}
			
			break;
		}
		case 3: {	
			System.out.println("Bye");
			break;
		}
		default:
			System.out.println("Invalid input");
			System.out.println("Bye");
			break;
		}
	}
	
	
	}
