package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ChildThing;

@Repository
public interface ChildThingRepo extends JpaRepository<ChildThing, Long>{
    
}
