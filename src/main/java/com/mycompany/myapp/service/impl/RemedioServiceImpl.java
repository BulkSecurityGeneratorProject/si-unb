package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.RemedioService;
import com.mycompany.myapp.domain.Remedio;
import com.mycompany.myapp.repository.RemedioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Remedio}.
 */
@Service
@Transactional
public class RemedioServiceImpl implements RemedioService {

    private final Logger log = LoggerFactory.getLogger(RemedioServiceImpl.class);

    private final RemedioRepository remedioRepository;

    public RemedioServiceImpl(RemedioRepository remedioRepository) {
        this.remedioRepository = remedioRepository;
    }

    /**
     * Save a remedio.
     *
     * @param remedio the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Remedio save(Remedio remedio) {
        log.debug("Request to save Remedio : {}", remedio);
        return remedioRepository.save(remedio);
    }

    /**
     * Get all the remedios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Remedio> findAll(Pageable pageable) {
        log.debug("Request to get all Remedios");
        return remedioRepository.findAll(pageable);
    }


    /**
     * Get one remedio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Remedio> findOne(Long id) {
        log.debug("Request to get Remedio : {}", id);
        return remedioRepository.findById(id);
    }

    /**
     * Delete the remedio by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Remedio : {}", id);
        remedioRepository.deleteById(id);
    }
}
