package com.starbugs.workspace.API;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.workspace.DTO.ClientDTO;
import com.starbugs.workspace.DTO.WorkspaceDTO;
import com.starbugs.workspace.Exception.EntityExistsException;
import com.starbugs.workspace.Exception.EntityNotFoundException;
import com.starbugs.workspace.Service.WorkspaceService;

@RestController()
public class WorkspaceController {
	
	@Autowired
	private WorkspaceService workspaceService;
	
	// Create new workspace for newly added clients...
	@PostMapping(value="/")
	public ResponseEntity<?> createWorkspace(@RequestBody ClientDTO client) throws EntityExistsException{
		this.workspaceService.create(client);
		return new ResponseEntity<>("Client Added Successfully", HttpStatus.ACCEPTED);
	}
	
	// Load user workspace based on client...
	@GetMapping(value = "/")
	public ResponseEntity<?> loadWorkspace(@RequestParam("client_id") UUID client_id) throws EntityNotFoundException{
		WorkspaceDTO workspace = this.workspaceService.loadWorkspace(client_id);
		return new ResponseEntity<>(workspace, HttpStatus.ACCEPTED);
	}
	
	// Delete Workspace for contract cancellation...
	@DeleteMapping(value = "/")
	public ResponseEntity<?> deleteWorkspace(@RequestParam("client_id") UUID client_id) throws EntityNotFoundException{
		this.workspaceService.deleteByClient(client_id);
		return ResponseEntity.noContent().build();
	}

}
