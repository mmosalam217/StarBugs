package com.starbugs.Core.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starbugs.Core.Model.StarbugsSubscription;
import com.starbugs.Core.Service.SubscriptionService;

@RestController
@RequestMapping("/starbugs/admin/subscriptions")
public class StarBugsAdminController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	public StarBugsAdminController() {
	}
	
	@PostMapping("/add-subscription")
	public ResponseEntity<?> addSubscription(@RequestBody StarbugsSubscription subscriptionDetails){
		StarbugsSubscription subscription = subscriptionService.addSubscription(subscriptionDetails);
		return ResponseEntity.ok(subscription);
	}

}
