package com.datastax.prodcat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLReader {

   public static void main(String[] args) {

      try {
         File inputFile = new File("src/main/data/RelationsList.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList rGrpList = doc.getElementsByTagName("RelationGroup");
         System.out.println("----------------------------");
         
         for (int temp = 0; temp < rGrpList.getLength(); temp++) {
            Node rGrpNode = rGrpList.item(temp);
            System.out.println("\nCurrent Element :" + rGrpNode.getNodeName());
            
            if (rGrpNode.getNodeType() == Node.ELEMENT_NODE) {
               Element rGrpElement = (Element) rGrpNode;
               System.out.println("RelationGroupID : "
                       + rGrpElement.getAttribute("ID"));
               System.out.println("name : "
                       + rGrpElement.getAttribute("name"));
               System.out.println("Description : "
                       + rGrpElement.getAttribute("description"));
               NodeList relList = rGrpElement.getElementsByTagName("Relation");
               for (int temp2 = 0; temp2 < relList.getLength(); temp2++) {
                   Node relNode = relList.item(temp2);
                   System.out.println("\nCurrent Element :" + relNode.getNodeName());
                   if (relNode.getNodeType() == Node.ELEMENT_NODE) {
                       Element relElement = (Element) relNode;
                       System.out.println("Relation ID: "
                               + relElement.getAttribute("ID"));
                       System.out.println("name : "
                               + relElement.getAttribute("name"));
                       NodeList srcList = relElement.getElementsByTagName("SourceIncludeRules");
                       for (int temp3 = 0; temp3 < srcList.getLength(); temp3++) {
                           Node srcNode = srcList.item(temp3);
                           System.out.println("\nCurrent Element :" + srcNode.getNodeName());
                           if (srcNode.getNodeType() == Node.ELEMENT_NODE) {
                               Element srcElement = (Element) srcNode;
                               processRules(srcElement);
                           }
                       }
                       NodeList destList = relElement.getElementsByTagName("DestinationIncludeRules");
                       for (int temp7 = 0; temp7 < destList.getLength(); temp7++) {
                           Node destNode = destList.item(temp7);
                           System.out.println("\nCurrent Element :" + destNode.getNodeName());
                           if (destNode.getNodeType() == Node.ELEMENT_NODE) {
                               Element destElement = (Element) destNode;
                               processRules(destElement);
                           }
                       }
                       NodeList destExList = relElement.getElementsByTagName("DestinationExcludeRules");
                       for (int tempB = 0; tempB < destExList.getLength(); tempB++) {
                           Node destExNode = destExList.item(tempB);
                           System.out.println("\nCurrent Element :" + destExNode.getNodeName());
                           if (destExNode.getNodeType() == Node.ELEMENT_NODE) {
                               Element destExElement = (Element) destExNode;
                               processRules(destExElement);
                           }
                       }
                   }
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
    private static void processRules(Element inElement){
        NodeList inRuleList = inElement.getElementsByTagName("Rule");
        for (int temp = 0; temp < inRuleList.getLength(); temp++) {
            Node inRuleNode = inRuleList.item(temp);
            System.out.println("\nCurrent Element :" + inRuleNode.getNodeName());
            if (inRuleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element inRuleElement = (Element) inRuleNode;
                System.out.println("Rule ID: "
                        + inRuleElement.getAttribute("ID"));
                NodeList supplierList = inRuleElement.getElementsByTagName("Supplier");
                for (int temp5 = 0; temp5 < supplierList.getLength(); temp5++) {
                    Node supplierNode = supplierList.item(temp5);
                    System.out.println("\nCurrent Element :" + supplierNode.getNodeName());
                    if (supplierNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element supplierElement = (Element) supplierNode;
                        System.out.println("Supplier ID: "
                                + supplierElement.getAttribute("ID"));
                    }
                }
                NodeList prodList = inRuleElement.getElementsByTagName("Prod_id");
                for (int temp6 = 0; temp6 < prodList.getLength(); temp6++) {
                    Node prodNode = prodList.item(temp6);
                    System.out.println("\nCurrent Element :" + prodNode.getNodeName());
                    if (prodNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element prodElement = (Element) prodNode;
                        System.out.println("Prod ID: "
                                + prodElement.getAttribute("value"));
                    }
                }
                NodeList categoryList = inRuleElement.getElementsByTagName("Category");
                for (int temp9 = 0; temp9 < categoryList.getLength(); temp9++) {
                    Node categoryNode = categoryList.item(temp9);
                    System.out.println("\nCurrent Element :" + categoryNode.getNodeName());
                    if (categoryNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element categoryElement = (Element) categoryNode;
                        System.out.println("Category ID: "
                                + categoryElement.getAttribute("ID"));
                    }
                }
                NodeList featureList = inRuleElement.getElementsByTagName("Feature");
                for (int tempA = 0; tempA < featureList.getLength(); tempA++) {
                    Node featureNode = featureList.item(tempA);
                    System.out.println("\nCurrent Element :" + featureNode.getNodeName());
                    if (featureNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element prodElement = (Element) featureNode;
                        System.out.println("Feature ID: "
                                + prodElement.getAttribute("ID"));
                        System.out.println("Feature exact: "
                                + prodElement.getAttribute("exact"));
                    }
                }
            }
        }
    }
}
