package com.starbugs.TicketService.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.starbugs.TicketService.Dao.AttachmentRepository;
import com.starbugs.TicketService.Dao.CommentRepository;
import com.starbugs.TicketService.Dao.FollowRepository;
import com.starbugs.TicketService.Dao.TicketRepository;
import com.starbugs.TicketService.Exception.EntityNotFoundException;
import com.starbugs.TicketService.Model.Ticket;
import com.starbugs.TicketService.Model.TicketAssignment;
import com.starbugs.TicketService.Model.TicketAttachment;
import com.starbugs.TicketService.Model.TicketComment;
import com.starbugs.TicketService.Model.TicketFollow;
import com.starbugs.TicketService.Projections.ProjectTicketsCount;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private AmazonS3Service amazonS3Service;
	
	public TicketService() {
	}
	
	public TicketService(TicketRepository ticketRepository, CommentRepository commentRepository, 
			FollowRepository followRepository, AmazonS3Service amazonS3Service, AttachmentRepository attachmentRepository) {
		this.ticketRepository = ticketRepository;
		this.commentRepository = commentRepository;
		this.followRepository = followRepository;
		this.attachmentRepository=attachmentRepository;
		this.amazonS3Service = amazonS3Service;
	}
	
	// Ticket Crud Operations.....
	public Ticket getTicketByID(Long id) throws EntityNotFoundException{
		return ticketRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Ticket with id: " + id.toString() +" Not Found!"));
	}
	public List<Ticket> browse(UUID clientID, Map<String, String> filter){
		TicketSpecification ticketSpec = new TicketSpecification(clientID, filter);
		return this.ticketRepository.findAll(ticketSpec);
	}
	
	public List<Ticket> getByIdOrTitle(UUID client_id, String keyword) throws EntityNotFoundException{
		List<Ticket> tickets;
		if(StringUtils.isNumeric(keyword)) {
			long id = Long.parseLong(keyword);
			Ticket ticket = this.ticketRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Ticket with id: " + id + " is not found."));
			tickets = new ArrayList<>();
			tickets.add(ticket);
		}else {
			if(!keyword.isEmpty() && keyword != null) {
				tickets = this.ticketRepository.findByClientIDAndTitleContaining(client_id, keyword);
			}else {
				tickets = new ArrayList<>();
			}
		}
		
		return tickets;
	}
	
	public List<Ticket> getByTitle(UUID client_id, String title){
		return this.ticketRepository.findByClientIDAndTitleContaining(client_id, title);
	}
	

	
	public List<Ticket> getTicketsByProjectID(UUID project_id){
		return ticketRepository.findByProjectID(project_id);
	}
	
	public List<Ticket> getTicketsByProjectName(UUID client_id, String project_name){
		return ticketRepository.findByClientIDAndProjectName(client_id, project_name);
	}
	
	public List<Ticket> getTicketsByIssuerID(UUID issuer_id){
		return ticketRepository.findByIssuerID(issuer_id);
	}
	
	public List<Ticket> getTicketsByIssuerName(UUID client_id, String issuer_name){
		return ticketRepository.findByClientIDAndIssuerName(client_id, issuer_name);
	}

	public List<Ticket> getTicketsByApp(UUID app_id){
		return ticketRepository.findByApp(app_id);
	}
	
	public List<Ticket> getTicketsByAppName(UUID project_id, String app_name){
		return ticketRepository.findByProjectIDAndAppNameContaining(project_id, app_name);
	}
	
	public List<Ticket> getTicketsByComponent(UUID component_id){
		return ticketRepository.findByComponent(component_id);
	}
	
	public List<Ticket> getTicketsByComponentName(UUID app_id, String component_name){
		return ticketRepository.findByAppAndComponentName(app_id, component_name);
	}
	
	public List<ProjectTicketsCount> groupTicketsByProject(UUID client_id){
		return this.ticketRepository.countTicketsByProject(client_id.toString());
	}
	
	public Ticket addTicket(Ticket ticket){
		TicketFollow follow = new TicketFollow();
		follow.setFollowerID(ticket.getAssignee().getAssigneeID());
		follow.setFollowerEmail(ticket.getAssignee().getAssigneeEmail());
		follow.setFollowerName(ticket.getAssignee().getAssigneeName());
		// Save Ticket and Follow into DB..
		ticket.addFollow(follow);
	
		ticket.getAssignee().setAssignmentDate(new Date());
		ticket.setCreatedAt(LocalDateTime.now());
		ticket.setUpdatedAt(LocalDateTime.now());
		return ticketRepository.save(ticket);
	}
	
	// Backlog implement notifyFollowers...
	public Ticket updateTicket(Long id, Ticket updatedTicket) throws EntityNotFoundException {
		
			Ticket ticket = this.getTicketByID(id);
			ticket.setUpdatedAt(LocalDateTime.now());
			ticket.setTitle(updatedTicket.getTitle());
			ticket.setDesc(updatedTicket.getDesc());
			ticket.setApp(updatedTicket.getApp());
			ticket.setAppName(updatedTicket.getAppName());
			ticket.setComponent(updatedTicket.getComponent());
			ticket.setComponentName(updatedTicket.getComponentName());
			ticket.setStatus(updatedTicket.getStatus());
			ticket.setSeverity(updatedTicket.getSeverity());
			return ticketRepository.save(ticket);

	}
	
	public TicketAssignment assignTo(Long ticket_id, TicketAssignment newAssignee) throws EntityNotFoundException {
		Ticket ticket = ticketRepository.findById(ticket_id).orElseThrow(()-> new EntityNotFoundException("Ticket Not Found!"));
		if(!newAssignee.getAssigneeEmail().equals(ticket.getAssignee().getAssigneeEmail())) {
			newAssignee.setAssignmentDate(new Date());
			ticket.setAssignee(newAssignee);
			Ticket updatedTicket = ticketRepository.save(ticket);
			return updatedTicket.getAssignee();
		}else {
			return ticket.getAssignee();
		}
		
	}
	
	public void deleteTicketByID(Long id) throws EntityNotFoundException {
		Ticket ticket = this.ticketRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Ticket Not Found"));
		
		if(ticket.getAttachments().size() > 0) {
			
			ticket.getAttachments().stream().forEach(attachment -> {
				String url = attachment.getUrl();
				String filename = url.substring(url.lastIndexOf("/") + 1);
				// Delete File from Amazon S3 Bucket
		
					try {
						this.amazonS3Service.deleteFile(filename);
					} catch (Exception e) {
						e.printStackTrace();
					}
				
			});
		}

		
		ticketRepository.delete(ticket);
	}
	
	// Attachment Crud operations....
	public List<TicketAttachment> addAttachments(Long ticket_id, MultipartFile[] files) throws EntityNotFoundException{
		Ticket ticket = ticketRepository.findById(ticket_id)
				.orElseThrow(()-> new EntityNotFoundException("Ticket with id: " + ticket_id + " Not Found!"));
		List<TicketAttachment> attachments = this.amazonS3Service.uploadFiles(files);
		attachments.stream().forEach(attachment -> ticket.addAttachment(attachment));
		Ticket updatedTicket = this.ticketRepository.save(ticket);
		return updatedTicket.getAttachments();
	}
	
	
	
	
	// Remove Attachment along with File from S3
	public void removeAttachment(UUID attachment_id) throws Exception {
		// Get Filename from the URL...
		TicketAttachment attachment = this.attachmentRepository.findById(attachment_id).orElseThrow(()-> new EntityNotFoundException("Attachment Not Found"));
		String url = attachment.getUrl();
		String filename = url.substring(url.lastIndexOf("/") + 1);
		// Delete File from Amazon S3 Bucket
		this.amazonS3Service.deleteFile(filename);
		this.attachmentRepository.delete(attachment);
	
		
	}
	
	// Comment Crud operations...
	
	// Backlog: notify followers...
	public List<TicketComment> addComment(Long tikcet_id, TicketComment comment) throws EntityNotFoundException {
		Ticket ticket = ticketRepository.findById(tikcet_id).orElseThrow(()-> new EntityNotFoundException("Ticket Not Found"));
		comment.setCommentDate(new Date());
		ticket.getComments().add(comment);
		TicketFollow follow = new TicketFollow();
		follow.setFollowerEmail(comment.getCommentatorEmail());
		follow.setFollowerID(comment.getCommentatorID());
		follow.setFollowerName(comment.getCommentatorName());
		ticket.getFollows().add(follow);
		Ticket updatedTicket =  ticketRepository.save(ticket);
		return updatedTicket.getComments();
	}
	

	
	public TicketComment updateComment(UUID id, TicketComment updatedComment) throws EntityNotFoundException {
		TicketComment comment = commentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Comment Not Found"));
		comment.setCommentContent(updatedComment.getCommentContent());
		comment.setCommentDate(new Date());
		return commentRepository.save(comment);
	}
	
	public void deleteComment(UUID cid) {
		commentRepository.deleteById(cid);
	}
	
	// Follow operations....
	public List<TicketFollow> followTicket(Long ticket_id, TicketFollow followRequest) throws EntityNotFoundException {
		Ticket ticket = ticketRepository.findById(ticket_id).orElseThrow(() -> new EntityNotFoundException("Ticket Not Found"));
		ticket.getFollows().add(followRequest);
		Ticket updatedTicket = ticketRepository.save(ticket);
		return updatedTicket.getFollows();
	}
	

	
	public void unfollowTicket(Long ticket_id, String follower_id) throws Exception {
		followRepository.deleteFollowByTicketAndFollowerIds(ticket_id, follower_id);
	}
	
	
	
	
	
}
