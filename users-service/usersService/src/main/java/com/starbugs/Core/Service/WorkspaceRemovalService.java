package com.starbugs.Core.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.starbugs.Core.Exception.WorkspaceRemovalException;

@Service
public class WorkspaceRemovalService {

	@Autowired
	private WorkspaceClient workspaceClient;
	
	@HystrixCommand(fallbackMethod = "deleteWorkspaceFallback",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="3000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
				})
	public void deleteWorkspace(UUID client_id) {
		this.workspaceClient.deleteWorkspace(client_id);
	}
	
	public void deleteWorkspaceFallback(UUID client_id) throws WorkspaceRemovalException{
		throw new WorkspaceRemovalException("Workspace Service is currently unavailable.");
	}
}
