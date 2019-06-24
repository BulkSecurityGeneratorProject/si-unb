package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Farmacia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Farmacia}.
 */
public interface FarmaciaService {

    /**
     * Save a farmacia.
     *
     * @param farmacia the entity to save.
     * @return the persisted entity.
     */
    Farmacia save(Farmacia farmacia);

    /**
     * Get all the farmacias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Farmacia> findAll(Pageable pageable);


    /**
     * Get the "id" farmacia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Farmacia> findOne(Long id);

    /**
     * Delete the "id" farmacia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
