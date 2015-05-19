package com.innoppl.intake;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.innoppl.intake.model.ClientDetailVO;
import com.innoppl.intake.model.EthnicityVO;
import com.innoppl.intake.model.GenderVO;
import com.innoppl.utility.ClientHelper;
import com.innoppl.utility.PropertyValues;

public class SearchDetailsServlet extends HttpServlet {
	
	final static Logger logger = Logger.getLogger(SearchDetailsServlet.class);	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
	
    	String pathInfo = request.getPathInfo();
    	ClientHelper clientHelper = new ClientHelper();
    	String clientDetailTag = PropertyValues.getPropValue("clientDetailTag");

    	
    	String url = clientHelper.BuildServerUrlforSearchResults(pathInfo);
    	if(logger.isDebugEnabled())
    	    logger.debug("URL -- > " + url);
    	
    	try { 
    	String xml = clientHelper.getHTML(url);
		if(xml==null || xml.isEmpty())
			throw new NullPointerException();
		else{
			NodeList nList = clientHelper.getNodeList(xml, clientDetailTag);
			ClientDetailVO cdvo = getClientDetails(nList);
			
			request.setAttribute("clientList", cdvo);
	    	getServletContext().getRequestDispatcher("/clientDetails.jsp").forward
	        (request, response);
		}
    	}
    	catch (NullPointerException null_ex) {
			logger.error("response xml could not found ..."	+ null_ex.getMessage());
		} catch (Exception other_ex) {
			logger.error("Web Service Server down :Please try later sometime..."+ other_ex.getMessage());
		}
    
}
    
    private ClientDetailVO getClientDetails(NodeList nList){
    	ClientDetailVO cdvo = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {	 
			Node nNode = nList.item(temp);	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				cdvo = new ClientDetailVO();
				cdvo.setClientKey(Long.valueOf(eElement
						.getElementsByTagName("clientKey").item(0).getTextContent()));
				cdvo.setNameLast(eElement
						.getElementsByTagName("nameLast").item(0)
						.getTextContent());
				cdvo.setNameFirst(eElement
						.getElementsByTagName("nameFirst").item(0)
						.getTextContent());
				cdvo.setNameMiddle("");
				cdvo.setDateOfBirth(eElement
						.getElementsByTagName("dateOfBirth").item(0)
						.getTextContent());
				cdvo.setSocSecNumber(eElement
						.getElementsByTagName("socSecNumber").item(0)
						.getTextContent());
				NodeList innernList = eElement
						.getElementsByTagName("ethnicityVO");
				Element innerElement = (Element) innernList.item(0);

				cdvo.setSocSecTypeCode(Integer.parseInt(eElement
						.getElementsByTagName("socSecTypeCode").item(0)
						.getTextContent()));
				cdvo.setSocSecTypeCode(Integer.parseInt(eElement
						.getElementsByTagName("socSecTypeCode").item(0)
						.getTextContent()));

				cdvo.setVeteranStatusGct(Integer.parseInt(eElement
						.getElementsByTagName("recActiveGct").item(0)
						.getTextContent()));
				cdvo.setRecActiveGct(Integer.parseInt(eElement
						.getElementsByTagName("recActiveGct").item(0)
						.getTextContent()));
				cdvo.setEntryDateTime(eElement
						.getElementsByTagName("entryDateTime").item(0)
						.getTextContent());
				cdvo.setLogDateTime(eElement
						.getElementsByTagName("logDateTime").item(0)
						.getTextContent());
				cdvo.setLogUserKey(Long.valueOf(eElement
						.getElementsByTagName("logUserKey").item(0)
						.getTextContent()));
				cdvo.setEntryUserKey(Long.valueOf("123"));
				try {
					EthnicityVO ev = new EthnicityVO(
							Integer.parseInt(innerElement
									.getElementsByTagName("codeKey")
									.item(0).getTextContent()),
							innerElement
									.getElementsByTagName("description")
									.item(0).getTextContent(), "", "",
							Integer.parseInt(eElement
									.getElementsByTagName(
											"recActiveGct").item(0)
									.getTextContent()), "",
							Long.valueOf(eElement
									.getElementsByTagName("logUserKey")
									.item(0).getTextContent()));
					cdvo.setEthnicityVO(ev);
				} catch (NullPointerException null_ex) {
					System.out.println(" Null Pointer Exception : "
							+ null_ex.getMessage());
				}
				try {
					NodeList myinnerList = eElement
							.getElementsByTagName("genderVO");
					Element myinnerElement = (Element) myinnerList
							.item(0);

					GenderVO genderVO = new GenderVO(
							Integer.parseInt(myinnerElement
									.getElementsByTagName("codeKey")
									.item(0).getTextContent()), "", "",
							"", Integer.parseInt(myinnerElement
									.getElementsByTagName(
											"recActiveGct").item(0)
									.getTextContent()), "",
							Long.valueOf(eElement
									.getElementsByTagName("logUserKey")
									.item(0).getTextContent()));
					cdvo.setGenderVO(genderVO);
				} catch (NullPointerException null_ex) {

					System.out.println(" Null Pointer Exception : "
							+ null_ex.getMessage());
				}

				
			}
		}
		return cdvo;
    	
    }
}
