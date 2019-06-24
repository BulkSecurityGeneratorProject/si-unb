package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Farmacia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Farmacia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {

}
