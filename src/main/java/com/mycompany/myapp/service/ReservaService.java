package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Reserva}.
 */
public interface ReservaService {

    /**
     * Save a reserva.
     *
     * @param reserva the entity to save.
     * @return the persisted entity.
     */
    Reserva save(Reserva reserva);

    /**
     * Get all the reservas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reserva> findAll(Pageable pageable);


    /**
     * Get the "id" reserva.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reserva> findOne(Long id);

    /**
     * Delete the "id" reserva.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
