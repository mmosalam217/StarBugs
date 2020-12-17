package com.starbugs.Core.Model;


import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "subscriptions")
public class StarbugsSubscription {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name ="id", updatable = false, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name = "subscription_name")
	private String name;
	
	@Column(name = "subscription_type")
	private String type;
	
	
	@Column(name = "subscription_price")
	private long price;
	
	
	@Column(name = "subscription_duration")
	private int subscriptionDurationInMonths;
	
	public StarbugsSubscription() {
	}

	public StarbugsSubscription(UUID id, String name, String type, long price,
			int subscriptionDurationInMonths) {
		
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.subscriptionDurationInMonths = subscriptionDurationInMonths;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}


	public int getSubscriptionDurationInMonths() {
		return subscriptionDurationInMonths;
	}

	public void setSubscriptionDurationInMonths(int subscriptionDurationInMonths) {
		this.subscriptionDurationInMonths = subscriptionDurationInMonths;
	}

	
	
}
