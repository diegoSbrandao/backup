package com.apprest.repositories;

import com.apprest.entities.Devotional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevotionalRepository extends JpaRepository<Devotional, Long> {

}
