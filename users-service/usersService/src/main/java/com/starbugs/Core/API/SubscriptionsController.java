package com.starbugs.Core.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.Model.StarbugsSubscription;
import com.starbugs.Core.Service.SubscriptionService;

@RestController
public class SubscriptionsController {

	@Autowired
	private SubscriptionService subscriptionService;
	
	public SubscriptionsController() {
	}

	@GetMapping("/subscriptions")
	public ResponseEntity<?> getAllSubscriptions(){
		List<StarbugsSubscription> subscriptions = subscriptionService.getAllSubscriptions();
		return ResponseEntity.ok(subscriptions);
	}
}
