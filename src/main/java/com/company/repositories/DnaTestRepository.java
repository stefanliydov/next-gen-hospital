package com.company.repositories;

import com.company.data.entities.DnaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DnaTestRepository extends JpaRepository<DnaTest, Long> {
    List<DnaTest> findAllByUserUsername(String username);

}
