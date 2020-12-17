package com.starbugs.Core.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.Core.Model.StarbugsSubscription;

@Repository
public interface SubscriptionsRepository extends JpaRepository<StarbugsSubscription, UUID>{

}
