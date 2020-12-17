package com.starbugs.TicketService.Dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.starbugs.TicketService.Model.Ticket;
import com.starbugs.TicketService.Model.TicketAssignment;
import com.starbugs.TicketService.Projections.ProjectTicketsCount;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
	
	List<Ticket> findByIssuerID(UUID issuerID);
	List<Ticket> findByClientIDAndIssuerName(UUID client_id, String issuerName);
	List<Ticket> findByAssignee(TicketAssignment assignee);
	List<Ticket> findByClientID(UUID client_id);
	List<Ticket> findByClientName(String name);
	List<Ticket> findByApp(UUID app);
	List<Ticket> findByProjectIDAndAppNameContaining(UUID project_id, String app_name);
	List<Ticket> findByComponent(UUID component);
	List<Ticket> findByAppAndComponentName(UUID app_id, String name);
	List<Ticket> findByProjectID(UUID project);
	List<Ticket> findByClientIDAndProjectName(UUID client_id, String project_name);
	List<Ticket> findByClientIDAndTitleContaining(UUID client_id, String title);
	List<Ticket> findByClientIDAndIdOrTitleContaining(UUID client_id, String id, String title);

	@Query(value = "SELECT project_id AS projectId, project_name AS projectName,"
			+ " COUNT(*) AS total FROM tickets WHERE client_id=?1 GROUP BY project_id",nativeQuery = true)
	List<ProjectTicketsCount> countTicketsByProject(String client_id);

}
