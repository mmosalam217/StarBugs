package com.starbugs.TicketService.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.TicketService.Model.TicketAttachment;

@Repository
public interface AttachmentRepository extends JpaRepository<TicketAttachment, UUID>{
	
}
