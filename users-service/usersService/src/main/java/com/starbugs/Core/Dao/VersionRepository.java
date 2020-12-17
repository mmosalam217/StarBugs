package com.starbugs.Core.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.Core.Model.Version;

@Repository
public interface VersionRepository extends JpaRepository<Version, UUID> {

}
