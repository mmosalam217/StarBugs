package com.starbugs.workspace.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbugs.workspace.Model.AppVersion;


@Repository
public interface VersionRepository extends JpaRepository<AppVersion, UUID> {

}
