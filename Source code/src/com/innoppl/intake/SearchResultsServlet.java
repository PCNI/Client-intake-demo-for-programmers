package com.innoppl.intake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.innoppl.intake.model.ClientVO;
import com.innoppl.utility.ClientHelper;
import com.innoppl.utility.PropertyValues;


public class SearchResultsServlet extends HttpServlet {
	
	final static Logger logger = Logger.getLogger(SearchResultsServlet.class);	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
    	
    	String search = "";
    	ClientHelper clientHelper = new ClientHelper();
    	search = request.getParameter("firstName");
    	String url = clientHelper.BuildServerUrlforSearch(search);
    	String clientSearchTag = PropertyValues.getPropValue("clientSearchTag");
    	
    	if(logger.isDebugEnabled())
    	    logger.debug("URL -- > " + url);
    	
    	try { 
    		
    	String xml = null;
		xml = clientHelper.getHTML(url);
		if(xml==null || xml.isEmpty()){
			request.setAttribute("message", 1000);
			getServletContext().getRequestDispatcher("/search.jsp").forward
	        (request, response); 
		}
		else{
			NodeList nList = clientHelper.getNodeList(xml, clientSearchTag);
			List<ClientVO> cl = getClientList(nList);	

	    	if(logger.isDebugEnabled())
	    		logger.info("Searched by " + search);
			
			request.setAttribute("clientList", cl);
			getServletContext().getRequestDispatcher("/result.jsp").forward
	        (request, response); 
		}
		
    	} catch (NullPointerException null_ex) {
			logger.error("response xml could not found ..."	+ null_ex.getMessage());
		} catch (Exception other_ex) {
			logger.error("Web Service Server down :Please try later sometime..."+ other_ex.getMessage());
		}
    	

	}
	
    private List<ClientVO> getClientList(NodeList nList){   	
    	List<ClientVO> cl = new ArrayList<ClientVO>();		
		for (int temp = 0; temp < nList.getLength(); temp++) {	 
			Node nNode = nList.item(temp);	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {	 
				Element eElement = (Element) nNode;	 
				cl.add(new ClientVO(Long.valueOf(getElementInfo(eElement,"clientKey")), "", 
												 getElementInfo(eElement,"nameLast"),
												 getElementInfo(eElement,"nameFirst"),
												 getElementInfo(eElement,"socSecNumber"),
												 getElementInfo(eElement,"dateOfBirth")));								
	 
			}
		}
    	
    	return cl;
    }
    
	private String getElementInfo(Element eElement, String tagName){
		NodeList name = eElement.getElementsByTagName(tagName);
        Element el = (Element) name.item(0);
        if(el!=null){
        	return el.getTextContent();
        }
        else return "";
	}
    
}

