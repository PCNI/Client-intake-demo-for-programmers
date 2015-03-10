/* Copyright (c) 2014 Pathways Community Network Institute
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.openhmis.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.openhmis.domain.Client;
import org.openhmis.exception.client.ClientAlreadyExistException;
import org.openhmis.exception.client.ClientNotFoundException;
import org.openhmis.exception.client.InValidClientException;
import org.openhmis.exception.client.UnableToAddClientException;
import org.openhmis.exception.client.UnableToUpdateClientException;
import org.openhmis.service.AuthenticateManager;
import org.openhmis.service.ClientManager;
import org.openhmis.service.impl.AuthenticateManagerImpl;
import org.openhmis.service.impl.ClientManagerImpl;
import org.openhmis.vo.ClientDetailVO;
import org.openhmis.vo.ClientVO;




@Path("/clients")
public class ClientService 
{	
	private static final Logger log = Logger.getLogger(ClientService.class);
	private ClientManager clientManager;
	private AuthenticateManager authenticateManager;
	Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
	public ClientService()
	{
		clientManager = new ClientManagerImpl();
		authenticateManager = new AuthenticateManagerImpl();
	}

	@GET
	@Path("/client/{clientKey}/{username}/{password}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@RolesAllowed({"ADMIN","CUSTOMER"})
	public ClientVO getClient(@PathParam("clientKey") Long clientKey, @PathParam("username") String username, @PathParam("password") String password) throws ClientNotFoundException
	{
		log.debug("getClient");
		ClientVO clientVO = new ClientVO();
		try
		{
			boolean isAuthenticate;//= authenticateManager.authenticateUser(username, password);
			isAuthenticate=true;
			if (isAuthenticate)
			{
				clientVO = clientManager.getClientById(clientKey);
				if (clientVO == null)
				{
					throw new ClientNotFoundException(clientKey + " doesn't exist");
				}
			}
			else
			{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e)
		{
			log.error("Couldn't get the client " + e.getMessage());
			throw new ClientNotFoundException(e.getMessage());
		}
		return clientVO;
	}
	
	@GET
	@Path("/clientdetail/{clientKey}/{username}/{password}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ClientDetailVO getClientDetail(@PathParam("clientKey") Long clientKey, @PathParam("username") String username, @PathParam("password") String password)
	{
		log.debug("get Client Detail");
		ClientDetailVO clientDetailVO = new ClientDetailVO();
		try
		{
			boolean isAuthenticate;// = authenticateManager.authenticateUser(username, password);
			isAuthenticate=true;
			if (isAuthenticate)
			{
				clientDetailVO = clientManager.getClientDetailById(clientKey);
				if (clientDetailVO == null)
				{
					throw new ClientNotFoundException(clientKey + " doesn't exist");
				}
			}
			else
			{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e)
		{
			log.error("Couldn't get the client " + e.getMessage());
			throw new ClientNotFoundException(e.getMessage());
		}
		return clientDetailVO;
	}
	
	
	@GET
	@Path("lastName/{lastName}/{username}/{password}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<ClientVO> getClientByLastName(@PathParam("lastName") String lastName, @PathParam("username") String username, @PathParam("password") String password) throws ClientNotFoundException 
	{
		log.debug("getClientByLastName");
		List<ClientVO> clientVOList = null;
		try
		{
			boolean isAuthenticate;// = authenticateManager.authenticateUser(username, password);
			isAuthenticate=true;
			if (isAuthenticate)
			{
				System.out.println("hai");
				clientVOList = new ArrayList<ClientVO>();			
				List<Client> clientList = clientManager.getClientByLastName(lastName);
				System.out.println("Size : "+clientList.size());
				if (( clientList == null) || (clientList.isEmpty()))
				{
					System.out.println(" if");
					throw new ClientNotFoundException(" No Client exist with last name " + lastName);
				}
				for(Client c: clientList)
				{
					System.out.println("for");
					ClientVO clientVO = mapper.map(c, ClientVO.class);
					clientVOList.add(clientVO);
				}
			}
			else
			{
				System.out.println("Forbideen");
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e)
		{
			System.out.println("Log");
			log.error("Couldn't get the client By Last Name " + e.getMessage());
			throw new ClientNotFoundException(e.getMessage());
		}
		
		return clientVOList;
	}

	@POST
	@Path("/addClient/{username}/{password}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ClientDetailVO addClient(JAXBElement<ClientDetailVO> client, @PathParam("username") String username, @PathParam("password") String password) throws ClientAlreadyExistException, InValidClientException, UnableToAddClientException
	{
		log.debug("addClient");
		ClientDetailVO clientDetailVO = null;
		try
		{
			boolean isAuthenticate = authenticateManager.authenticateUser(username, password);
			if (isAuthenticate)
			{
				clientDetailVO = client.getValue();
				Client newClient = mapper.map(clientDetailVO, Client.class);
				// first check if this client already exist in the database
				if (newClient.getSocSecNumber() == null){
					throw new InValidClientException("Client doesn't have all the mandatory data.");
				}
				Client existingClient = clientManager.getClientBySSN(newClient.getSocSecNumber());
				if (existingClient != null)
				{
					throw new ClientAlreadyExistException("Client already exist in the system.");
				}
				clientManager.addClient(newClient);
			}
			else
			{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e)
		{
			log.error("Couldn't add the client " + e.getMessage());
			throw new UnableToAddClientException(e.getMessage());
		}
		return clientDetailVO;
	}

	@PUT
	@Path("/updateClient/{clientKey}/{username}/{password}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ClientDetailVO updateClient(JAXBElement<ClientDetailVO> client,@PathParam("clientKey") Long clientKey, @PathParam("username") String username, @PathParam("password") String password) throws UnableToUpdateClientException
	{
		log.debug("updateClient");
		ClientDetailVO updatedClientVO = null;
		try
		{
			boolean isAuthenticate = authenticateManager.authenticateUser(username, password);
			if (isAuthenticate)
			{
				updatedClientVO = client.getValue();
				Client updatedClient = mapper.map(updatedClientVO, Client.class);
				clientManager.updateClient(updatedClient);
			}
			else
			{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e)
		{
			log.error("Couldn't update the client " + e.getMessage());
			throw new UnableToUpdateClientException(e.getMessage());
		}		
		return updatedClientVO;
	}
	
	public ClientManager getClientManager() {
		return clientManager;
	}

	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}
}
