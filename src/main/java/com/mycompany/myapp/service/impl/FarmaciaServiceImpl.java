package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.FarmaciaService;
import com.mycompany.myapp.domain.Farmacia;
import com.mycompany.myapp.repository.FarmaciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Farmacia}.
 */
@Service
@Transactional
public class FarmaciaServiceImpl implements FarmaciaService {

    private final Logger log = LoggerFactory.getLogger(FarmaciaServiceImpl.class);

    private final FarmaciaRepository farmaciaRepository;

    public FarmaciaServiceImpl(FarmaciaRepository farmaciaRepository) {
        this.farmaciaRepository = farmaciaRepository;
    }

    /**
     * Save a farmacia.
     *
     * @param farmacia the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Farmacia save(Farmacia farmacia) {
        log.debug("Request to save Farmacia : {}", farmacia);
        return farmaciaRepository.save(farmacia);
    }

    /**
     * Get all the farmacias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Farmacia> findAll(Pageable pageable) {
        log.debug("Request to get all Farmacias");
        return farmaciaRepository.findAll(pageable);
    }


    /**
     * Get one farmacia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Farmacia> findOne(Long id) {
        log.debug("Request to get Farmacia : {}", id);
        return farmaciaRepository.findById(id);
    }

    /**
     * Delete the farmacia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Farmacia : {}", id);
        farmaciaRepository.deleteById(id);
    }
}
