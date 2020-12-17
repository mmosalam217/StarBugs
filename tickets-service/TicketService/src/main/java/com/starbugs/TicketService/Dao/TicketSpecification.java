package com.starbugs.TicketService.Dao;

import java.util.List;

import com.starbugs.TicketService.DTO.TicketDetailsDto;
import com.starbugs.TicketService.DTO.TicketFilter;

public interface TicketSpecification {
	List<TicketDetailsDto> findTicketsByClientID(TicketFilter filter) throws Exception;
}
