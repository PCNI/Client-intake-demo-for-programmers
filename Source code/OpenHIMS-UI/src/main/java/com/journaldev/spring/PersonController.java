package com.journaldev.spring;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.ClientDetailVO;
import com.journaldev.spring.model.ClientVO;
import com.journaldev.spring.model.EthnicityVO;
import com.journaldev.spring.model.GenderVO;
import com.journaldev.spring.model.Person;
import com.journaldev.spring.model.UserInfo;
import com.journaldev.spring.service.PersonService;
import com.journaldev.spring.service.UserService;

@Controller
public class PersonController {

	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		System.out.println("User Servie : " + userService);
		this.userService = userService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {

		return "login";

	}

	@RequestMapping(value = "/temp_login", method = RequestMethod.GET)
	public String ClientSearch(Model model, HttpServletRequest request) {

		model.addAttribute("login", this.userService.LoginUser(

		request.getParameter("j_username"), request.getParameter("j_password")));
		if (this.userService.LoginUser(request.getParameter("j_username"),
				request.getParameter("j_password")).size() != 0) {
			request.getSession().setAttribute(
					"username",
					this.userService
							.LoginUser(request.getParameter("j_username"),
									request.getParameter("j_password")).get(0)
							.getUserId());
			request.getSession().setAttribute(
					"password",
					this.userService
							.LoginUser(request.getParameter("j_username"),
									request.getParameter("j_password")).get(0)
							.getPasswordEnc());
			System.out.println("User Availble");
			return "search";
		} else {
			request.getSession().removeAttribute("username");
			System.out.println("User not  Availble");
			// return "redirect:/login";
			return "redirect:/login";
		}

	}

	@RequestMapping(value = "clients/lastName", method = RequestMethod.GET)
	public String ClientList(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("username") == null) {

			return "redirect:/login";
		} else {
			System.out.println(request.getSession().getAttribute("username"));
			System.out.println(request.getRequestURI() + ""
					+ request.getQueryString());
			String mystr = request.getQueryString();
			String[] parts = mystr.split("=");
			String part2 = parts[1];
			List<ClientVO> cl = new ArrayList<ClientVO>();
			try {
				/*
				 * String xml = getHTML(
				 * "http://192.168.6.7:8081/OpenHMIS-master/services/clients/lastName/"
				 * + part2 + "/" + request.getSession().getAttribute("username")
				 * + "/" + request.getSession().getAttribute("password"));
				 */
				String xml = null;
				try {
					xml = getHTML("http://" + request.getServerName() + ":"
							+ request.getServerPort()
							+ "/OpenHMIS-master/services/clients/lastName/"
							+ part2 + "/"
							+ request.getSession().getAttribute("username")
							+ "/"
							+ request.getSession().getAttribute("password"));
					
					/* xml = getHTML(
					  "http://192.168.6.115:8082/OpenHMIS-master/services/clients/lastName/"
					  + part2 + "/" + request.getSession().getAttribute("username")
					   + "/" + request.getSession().getAttribute("password"));*/
					 
					Document doc = DocumentBuilderFactory.newInstance()
							.newDocumentBuilder()
							.parse(new InputSource(new StringReader(xml)));
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("clientVO");
					for (int temp = 0; temp < nList.getLength(); temp++) {

						Node nNode = nList.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;

							cl.add(new ClientVO(Long.valueOf(eElement
									.getElementsByTagName("clientKey").item(0)
									.getTextContent()), "", eElement
									.getElementsByTagName("nameLast").item(0)
									.getTextContent(), eElement
									.getElementsByTagName("nameFirst").item(0)
									.getTextContent(), eElement
									.getElementsByTagName("socSecNumber")
									.item(0).getTextContent(), eElement
									.getElementsByTagName("dateOfBirth")
									.item(0).getTextContent()));

						}
					}
				} catch (FileNotFoundException file_not_found_ex) {
					System.out.println("response xml could not found ..."
							+ file_not_found_ex.getMessage());
				} catch (Exception other_ex) {
					System.out
							.println("Web Service Server down :Please try later sometime..."
									+ other_ex.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("List SIze : " + cl.size());
			model.addAttribute("clientList", cl);
		}
		return "result";

	}

	@RequestMapping("clients/clientdetail")
	public String ClientDetailsInfo(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("username") == null) {
			return "redirect:/login";
		} else {
			System.out.println(request.getSession().getAttribute("username"));
			String mystr = request.getQueryString();
			String[] parts = mystr.split("=");
			String part2 = parts[1]; // 034556
			List<ClientDetailVO> cl = new ArrayList<ClientDetailVO>();
			try {
				/*
				 * Test Service String xml = getHTML(
				 * "http://192.168.6.7:8081/OpenHMIS-master/services/clients/clientdetail/"
				 * + part2 + "/" + request.getSession().getAttribute("username")
				 * + "/" + request.getSession().getAttribute("password"));
				 */
				String xml = null;
				try {
					xml = getHTML("http://" + request.getServerName() + ":"
							+ request.getServerPort()
							+ "/OpenHMIS-master/services/clients/clientdetail/"
							+ part2 + "/"
							+ request.getSession().getAttribute("username")
							+ "/"
							+ request.getSession().getAttribute("password"));
					 /*xml = getHTML(
							  "http://192.168.6.115:8082/OpenHMIS-master/services/clients/clientdetail/"
							  + part2 + "/" + request.getSession().getAttribute("username")
							   + "/" + request.getSession().getAttribute("password"));*/
					System.out.println(xml);
					Document doc = DocumentBuilderFactory.newInstance()
							.newDocumentBuilder()
							.parse(new InputSource(new StringReader(xml)));
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("clientDetailVO");
					for (int temp = 0; temp < nList.getLength(); temp++) {
						Node nNode = nList.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
							ClientDetailVO cdvo = new ClientDetailVO();
							cdvo.setClientKey(Long.valueOf(eElement
									.getElementsByTagName("clientKey").item(0)
									.getTextContent()));
							cdvo.setNameLast(eElement
									.getElementsByTagName("nameLast").item(0)
									.getTextContent());
							cdvo.setNameFirst(eElement
									.getElementsByTagName("nameFirst").item(0)
									.getTextContent());
							cdvo.setNameMiddle("");
							cdvo.setDateOfBirth(eElement
									.getElementsByTagName("dateOfBirth")
									.item(0).getTextContent());
							cdvo.setSocSecNumber(eElement
									.getElementsByTagName("socSecNumber")
									.item(0).getTextContent());
							NodeList innernList = eElement
									.getElementsByTagName("ethnicityVO");
							Element innerElement = (Element) innernList.item(0);

							cdvo.setSocSecTypeCode(Integer.parseInt(eElement
									.getElementsByTagName("socSecTypeCode")
									.item(0).getTextContent()));
							cdvo.setSocSecTypeCode(Integer.parseInt(eElement
									.getElementsByTagName("socSecTypeCode")
									.item(0).getTextContent()));

							cdvo.setVeteranStatusGct(Integer.parseInt(eElement
									.getElementsByTagName("recActiveGct")
									.item(0).getTextContent()));
							cdvo.setRecActiveGct(Integer.parseInt(eElement
									.getElementsByTagName("recActiveGct")
									.item(0).getTextContent()));
							cdvo.setEntryDateTime(eElement
									.getElementsByTagName("entryDateTime")
									.item(0).getTextContent());
							cdvo.setLogDateTime(eElement
									.getElementsByTagName("logDateTime")
									.item(0).getTextContent());
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
												.getElementsByTagName(
														"description").item(0)
												.getTextContent(), "", "",
										Integer.parseInt(eElement
												.getElementsByTagName(
														"recActiveGct").item(0)
												.getTextContent()), "",
										Long.valueOf(eElement
												.getElementsByTagName(
														"logUserKey").item(0)
												.getTextContent()));
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
												.item(0).getTextContent()), "",
										"", "", Integer.parseInt(myinnerElement
												.getElementsByTagName(
														"recActiveGct").item(0)
												.getTextContent()), "",
										Long.valueOf(eElement
												.getElementsByTagName(
														"logUserKey").item(0)
												.getTextContent()));
								cdvo.setGenderVO(genderVO);
							} catch (NullPointerException null_ex) {

								System.out.println(" Null Pointer Exception : "
										+ null_ex.getMessage());
							}

							cl.add(cdvo);

						}

					}
				} catch (FileNotFoundException file_not_found_ex) {
					System.out.println("response xml could not found ..."
							+ file_not_found_ex.getMessage());
				} catch (Exception other_ex) {
					System.out
							.println("Web Service Server down :Please try later sometime..."
									+ other_ex.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("List SIze : " + cl.size());
			model.addAttribute("clientList", cl);
		}
		return "client_details";
	}

	public String getHTML(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String Logout(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("password");
		return "redirect:/login";
	}

}
