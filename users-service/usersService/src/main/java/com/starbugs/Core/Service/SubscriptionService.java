package com.starbugs.Core.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbugs.Core.Dao.ClientRepository;
import com.starbugs.Core.Dao.SubscriptionsRepository;
import com.starbugs.Core.Model.StarBugsClient;
import com.starbugs.Core.Model.StarbugsSubscription;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionsRepository subscriptionsRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	public SubscriptionService() {
	}
	
	public SubscriptionService(SubscriptionsRepository subscriptionsRepository, ClientRepository clientRepository) {
		this.subscriptionsRepository = subscriptionsRepository;
		this.clientRepository = clientRepository;
	}
	
	// Create Subscription...
	public StarbugsSubscription addSubscription(StarbugsSubscription subscription) {
		return subscriptionsRepository.save(subscription);
	}
	
	// Get Subscription By ID....
	public StarbugsSubscription getSubscription(UUID subscription_id) throws Exception {
		return subscriptionsRepository.findById(subscription_id).orElseThrow(()-> new Exception("NOT_SUPPORTED_SUBSCRIPTION"));
	}
	
	// Get List of subscriptions....
	public List<StarbugsSubscription> getAllSubscriptions(){
		return subscriptionsRepository.findAll();
	}
	
	// change Subscription for clients...
	public StarbugsSubscription changeSubscription(UUID client_id, UUID subscription_id) throws Exception{
	
		StarBugsClient client = clientRepository.findById(client_id).orElseThrow(()-> new Exception("Client Not Found"));	

		StarbugsSubscription newSubscription = subscriptionsRepository.findById(subscription_id).orElseThrow(()-> new Exception("Subscription Not Found"));
		
		if(!client.getSubscription().getId().equals(newSubscription.getId())) {
			client.setSubscription(newSubscription);
			StarBugsClient updatedClient = clientRepository.save(client);
			return updatedClient.getSubscription();
		}else {
			return client.getSubscription();
		}
		
	}
	
	// Update Subscription...
	public StarbugsSubscription updateSubscription(UUID subscription_id, StarbugsSubscription newSubscriptionDetails) throws Exception {
		StarbugsSubscription currentSubscriptionDetails = subscriptionsRepository.findById(subscription_id).orElseThrow(()-> new Exception("NOT_SUPPORTED_SUBSCRIPTION"));
		currentSubscriptionDetails.setName(newSubscriptionDetails.getName());
		currentSubscriptionDetails.setPrice(newSubscriptionDetails.getPrice());
		currentSubscriptionDetails.setSubscriptionDurationInMonths(newSubscriptionDetails.getSubscriptionDurationInMonths());
		currentSubscriptionDetails.setType(newSubscriptionDetails.getType());
		return subscriptionsRepository.save(currentSubscriptionDetails);
	}
	
	// Delete Subscription.....
	public void deleteSubscription(UUID subscription_id) {
		subscriptionsRepository.deleteById(subscription_id);
	}
	
	
}
