package com.starbugs.Core.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.starbugs.Core.DTO.ClientDTO;
import com.starbugs.Core.Exception.TechnicalException;

import feign.FeignException;

@Service
public class WorkspaceCreatorService {
	
	@Autowired
	private WorkspaceClient workspaceClient;
	
	@HystrixCommand(fallbackMethod = "createWorkspaceFallback",
			commandProperties = {
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="6000"),
				@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
				@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
			})
	public void createWorkspace(ClientDTO clientDTO) throws FeignException{
		this.workspaceClient.createWorkspace(clientDTO);
	}
	
	public void createWorkspaceFallback(ClientDTO clientDTO) throws TechnicalException {
		throw new TechnicalException("Workspace is not available");
	}

}
