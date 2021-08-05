package com.toy.matcherloper.core.user.repository;

import com.toy.matcherloper.core.user.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
