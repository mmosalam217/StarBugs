package com.starbugs.TicketService.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.starbugs.TicketService.Model.Ticket;
import com.starbugs.TicketService.Model.TicketAssignment;

public class TicketSpecification implements Specification<Ticket>{

	private static final long serialVersionUID = 1L;
	
	private UUID clientID;
	private Map<String, String> filterMap;
	
	public TicketSpecification(UUID clientID, Map<String, String> filterMap) {
		this.clientID = clientID;
		this.filterMap = filterMap;
		
	}
	


	@Override
	public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> filters = new ArrayList<>();
		// Client ID is mandatory to get only tickets for a specific client..
		 filters.add(criteriaBuilder.equal(root.get("clientID"), clientID));

		 // Iterate through query and filter by equal for ids and LIKE for text..
		for(final Entry<String, String> e: filterMap.entrySet()) {
			final String col = e.getKey();
			final String val = e.getValue();
			
			if(col !=null && val !=null) {
					if(val.contains("%")) {
						filters.add(criteriaBuilder.like(root.get(col), "%"+val+"%"));
					}else {
						// In case the query contains id of a nested entity such as the assignee id, get the path and filter by entity id..
						if(col.equals("assignee")) {
							Path<TicketAssignment> assignee = root.get("assignee");
							 filters.add(criteriaBuilder.equal(assignee.get("assigneeID"), UUID.fromString(val)));

						}else {
							 filters.add(criteriaBuilder.equal(root.get(col), UUID.fromString(val)));

						}

					}
				
			}
		}
		
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));

	}
	

}
