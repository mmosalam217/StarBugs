package com.starbugs.TicketService.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.TicketService.Model.TicketComment;

@Repository
public interface CommentRepository extends JpaRepository<TicketComment, UUID> {

}
