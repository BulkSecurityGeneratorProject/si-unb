package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.EstoqueService;
import com.mycompany.myapp.domain.Estoque;
import com.mycompany.myapp.repository.EstoqueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Estoque}.
 */
@Service
@Transactional
public class EstoqueServiceImpl implements EstoqueService {

    private final Logger log = LoggerFactory.getLogger(EstoqueServiceImpl.class);

    private final EstoqueRepository estoqueRepository;

    public EstoqueServiceImpl(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    /**
     * Save a estoque.
     *
     * @param estoque the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Estoque save(Estoque estoque) {
        log.debug("Request to save Estoque : {}", estoque);
        return estoqueRepository.save(estoque);
    }

    /**
     * Get all the estoques.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Estoque> findAll() {
        log.debug("Request to get all Estoques");
        return estoqueRepository.findAll();
    }


    /**
     * Get one estoque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Estoque> findOne(Long id) {
        log.debug("Request to get Estoque : {}", id);
        return estoqueRepository.findById(id);
    }

    /**
     * Delete the estoque by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estoque : {}", id);
        estoqueRepository.deleteById(id);
    }
}
