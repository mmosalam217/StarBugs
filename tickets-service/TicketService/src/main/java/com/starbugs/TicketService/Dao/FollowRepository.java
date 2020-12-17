package com.starbugs.TicketService.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.starbugs.TicketService.Model.TicketFollow;

@Repository
public interface FollowRepository extends JpaRepository<TicketFollow, UUID>{
	
	@Modifying
	@Transactional
	@Query(value = "Delete from ticket_follows where ticket_id = ?1 and follower_id = ?2" , nativeQuery = true)
	void deleteFollowByTicketAndFollowerIds(Long ticket_id, String follower_id);
}
