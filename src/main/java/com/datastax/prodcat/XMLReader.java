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
                               NodeList srcRuleList = srcElement.getElementsByTagName("Rule");
                               for (int temp4 = 0; temp4 < srcRuleList.getLength(); temp4++) {
                                   Node srcRuleNode = srcRuleList.item(temp4);
                                   System.out.println("\nCurrent Element :" + srcRuleNode.getNodeName());
                                   if (srcRuleNode.getNodeType() == Node.ELEMENT_NODE) {
                                       Element srcRuleElement = (Element) srcRuleNode;
                                       System.out.println("Rule ID: "
                                               + srcRuleElement.getAttribute("ID"));
                                   }
                               }
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
}
