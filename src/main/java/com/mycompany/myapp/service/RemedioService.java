package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Remedio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Remedio}.
 */
public interface RemedioService {

    /**
     * Save a remedio.
     *
     * @param remedio the entity to save.
     * @return the persisted entity.
     */
    Remedio save(Remedio remedio);

    /**
     * Get all the remedios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Remedio> findAll(Pageable pageable);


    /**
     * Get the "id" remedio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Remedio> findOne(Long id);

    /**
     * Delete the "id" remedio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
