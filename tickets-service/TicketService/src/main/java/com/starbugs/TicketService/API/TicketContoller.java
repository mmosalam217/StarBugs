package com.starbugs.TicketService.API;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.starbugs.TicketService.Exception.EntityNotFoundException;
import com.starbugs.TicketService.Model.Ticket;
import com.starbugs.TicketService.Model.TicketAssignment;
import com.starbugs.TicketService.Model.TicketAttachment;
import com.starbugs.TicketService.Model.TicketComment;
import com.starbugs.TicketService.Model.TicketFollow;
import com.starbugs.TicketService.Service.TicketService;



@RestController()
public class TicketContoller {
	
	@Autowired
	private TicketService ticketService;
	
	
	
	public TicketContoller() {
		
	}
	
	public TicketContoller(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	@PostMapping(value = "/add-ticket")
	public ResponseEntity<?> addTicket(@RequestBody Ticket ticket){
		
		return ResponseEntity.status(HttpStatus.OK).body(this.ticketService.addTicket(ticket));
		
	}
	
	// Get Ticket By ID..
	@GetMapping("/{id}")
	public ResponseEntity<?> getTicketById(@PathVariable("id") Long id) throws EntityNotFoundException{
		
			Ticket ticket = this.ticketService.getTicketByID(id);
			return ResponseEntity.status(HttpStatus.OK).body(ticket);
		
	}
	
	// Browse all tickets....
	@GetMapping("/clients/{client_id}/browse")
	public ResponseEntity<?> browse(@PathVariable("client_id") UUID client_id, @RequestParam Map<String, String> filter) throws Exception{
		List<Ticket> tickets = this.ticketService.browse(client_id, filter);
		return ResponseEntity.status(HttpStatus.OK).body(tickets);
	}
	
	@GetMapping("/clients/{client_id}/search")
	public ResponseEntity<?> search(@PathVariable("client_id") UUID client_id, @RequestParam("s") String keyword) throws EntityNotFoundException{
		List<Ticket> tickets = this.ticketService.getByIdOrTitle(client_id, keyword);
		return ResponseEntity.status(HttpStatus.OK).body(tickets);
	}

	
	
	
	// Update Ticket by id....
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateTicket(@PathVariable("id") Long id, @RequestBody Ticket ticketUpdates) throws EntityNotFoundException{
		
		
			Ticket ticket = this.ticketService.updateTicket(id, ticketUpdates);
			return ResponseEntity.status(HttpStatus.OK).body(ticket);
		
		
	}
	
	@PutMapping(value = "/{id}/assign")
	public ResponseEntity<?> assignTicket(@PathVariable("id") Long id, @RequestBody TicketAssignment newAssignemt) throws EntityNotFoundException{
		
		
			TicketAssignment assignment = this.ticketService.assignTo(id, newAssignemt);
			return ResponseEntity.status(HttpStatus.OK).body(assignment);
		
		
	}
	
	// Delete A Ticket By ID....
	@DeleteMapping("/{id}/delete-ticket")
	public ResponseEntity<?> deleteTicket(@PathVariable("id") Long id) throws EntityNotFoundException{
	
			this.ticketService.deleteTicketByID(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ticket Deleted Successfully");
		
	}
	
	
	
	// Add new attachments to an existing ticket...
	@PostMapping(value = "/{id}/add-attachments")
	public ResponseEntity<?> addAttachments(@PathVariable("id") Long id, @RequestPart(value = "files") MultipartFile[] files) throws EntityNotFoundException{
		
			List <TicketAttachment> attachments = this.ticketService.addAttachments(id, files);
			return ResponseEntity.status(HttpStatus.OK).body(attachments);
		
	}
	
	@DeleteMapping("/attachments/{attachment_id}")
	public ResponseEntity<?> deleteAttachment(@PathVariable("attachment_id") UUID attachment_id) throws Exception{
	
			this.ticketService.removeAttachment(attachment_id);
			return ResponseEntity.status(HttpStatus.OK).body("File Deleted Successfully");
		
	}
	
	
	// Add a comment to a ticket...
	@PostMapping(value = "/{id}/add-comment")
	public ResponseEntity<?> addComment(@PathVariable("id") Long id, @RequestBody TicketComment comment) throws EntityNotFoundException{
		
			List<TicketComment> comments = this.ticketService.addComment(id, comment);
			return ResponseEntity.status(HttpStatus.OK).body(comments);
		
	}
	
	// Update a ticket comment..
	@PutMapping(value = "/comments/{comment_id}")
	public ResponseEntity<?> updateComment(@PathVariable("comment_id") UUID comment_id, @RequestBody TicketComment comment) throws EntityNotFoundException{
	
			TicketComment updatedCommment = this.ticketService.updateComment(comment_id, comment);
			return ResponseEntity.status(HttpStatus.OK).body(updatedCommment);
		
	}
	
	@DeleteMapping(value = "/comments/{comment_id}")
	public ResponseEntity<?> deleteComment(@PathVariable("comment_id") UUID comment_id){
		
			this.ticketService.deleteComment(comment_id);
			return ResponseEntity.status(HttpStatus.OK).body("Comment Removed Successfully");
		
	}
	
	@PostMapping(value = "/{id}/follow-ticket")
	public ResponseEntity<?> followTicket(@PathVariable("id") Long id, @RequestBody TicketFollow followRequest) throws EntityNotFoundException{
	
			List<TicketFollow> follows = this.ticketService.followTicket(id, followRequest);
			return ResponseEntity.status(HttpStatus.OK).body(follows);
		
	}
	
	@DeleteMapping(value = "/{ticket_id}/unfollow-ticket")
	public ResponseEntity<?> unfollowTicket(@PathVariable("ticket_id") Long ticket_id, @RequestParam("follower_id") String follower_id){
		try {
			this.ticketService.unfollowTicket(ticket_id, follower_id);
			return ResponseEntity.status(HttpStatus.OK).body("Ticket unfollowed");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
	}
	
	
}
