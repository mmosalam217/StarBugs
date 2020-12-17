package com.starbugs.TicketService.Projections;

import java.util.UUID;

public interface ProjectTicketsCount {

	UUID getProjectId();
	String getProjectName();
	Long getTotal();
}
