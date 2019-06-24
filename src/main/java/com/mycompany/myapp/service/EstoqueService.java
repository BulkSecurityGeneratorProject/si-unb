package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Estoque;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Estoque}.
 */
public interface EstoqueService {

    /**
     * Save a estoque.
     *
     * @param estoque the entity to save.
     * @return the persisted entity.
     */
    Estoque save(Estoque estoque);

    /**
     * Get all the estoques.
     *
     * @return the list of entities.
     */
    List<Estoque> findAll();


    /**
     * Get the "id" estoque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Estoque> findOne(Long id);

    /**
     * Delete the "id" estoque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
