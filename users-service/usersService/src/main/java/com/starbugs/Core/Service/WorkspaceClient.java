package com.starbugs.Core.Service;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.starbugs.Core.DTO.ClientDTO;

import feign.FeignException;

@FeignClient("starbugs-workspaces")
public interface WorkspaceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/")
	void createWorkspace(ClientDTO clientDTO) throws FeignException;
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/")
	void deleteWorkspace(UUID client_id) throws FeignException;
}
