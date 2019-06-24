package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DoencaService;
import com.mycompany.myapp.domain.Doenca;
import com.mycompany.myapp.repository.DoencaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Doenca}.
 */
@Service
@Transactional
public class DoencaServiceImpl implements DoencaService {

    private final Logger log = LoggerFactory.getLogger(DoencaServiceImpl.class);

    private final DoencaRepository doencaRepository;

    public DoencaServiceImpl(DoencaRepository doencaRepository) {
        this.doencaRepository = doencaRepository;
    }

    /**
     * Save a doenca.
     *
     * @param doenca the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Doenca save(Doenca doenca) {
        log.debug("Request to save Doenca : {}", doenca);
        return doencaRepository.save(doenca);
    }

    /**
     * Get all the doencas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Doenca> findAll(Pageable pageable) {
        log.debug("Request to get all Doencas");
        return doencaRepository.findAll(pageable);
    }

    /**
     * Get all the doencas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Doenca> findAllWithEagerRelationships(Pageable pageable) {
        return doencaRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one doenca by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Doenca> findOne(Long id) {
        log.debug("Request to get Doenca : {}", id);
        return doencaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the doenca by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Doenca : {}", id);
        doencaRepository.deleteById(id);
    }
}
