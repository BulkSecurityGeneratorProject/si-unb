package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Estoque;
import com.mycompany.myapp.service.EstoqueService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Estoque}.
 */
@RestController
@RequestMapping("/api")
public class EstoqueResource {

    private final Logger log = LoggerFactory.getLogger(EstoqueResource.class);

    private static final String ENTITY_NAME = "estoque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstoqueService estoqueService;

    public EstoqueResource(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    /**
     * {@code POST  /estoques} : Create a new estoque.
     *
     * @param estoque the estoque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estoque, or with status {@code 400 (Bad Request)} if the estoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estoques")
    public ResponseEntity<Estoque> createEstoque(@RequestBody Estoque estoque) throws URISyntaxException {
        log.debug("REST request to save Estoque : {}", estoque);
        if (estoque.getId() != null) {
            throw new BadRequestAlertException("A new estoque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estoque result = estoqueService.save(estoque);
        return ResponseEntity.created(new URI("/api/estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estoques} : Updates an existing estoque.
     *
     * @param estoque the estoque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estoque,
     * or with status {@code 400 (Bad Request)} if the estoque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estoque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estoques")
    public ResponseEntity<Estoque> updateEstoque(@RequestBody Estoque estoque) throws URISyntaxException {
        log.debug("REST request to update Estoque : {}", estoque);
        if (estoque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Estoque result = estoqueService.save(estoque);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estoque.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estoques} : get all the estoques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estoques in body.
     */
    @GetMapping("/estoques")
    public List<Estoque> getAllEstoques() {
        log.debug("REST request to get all Estoques");
        return estoqueService.findAll();
    }

    /**
     * {@code GET  /estoques/:id} : get the "id" estoque.
     *
     * @param id the id of the estoque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estoque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estoques/{id}")
    public ResponseEntity<Estoque> getEstoque(@PathVariable Long id) {
        log.debug("REST request to get Estoque : {}", id);
        Optional<Estoque> estoque = estoqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estoque);
    }

    /**
     * {@code DELETE  /estoques/:id} : delete the "id" estoque.
     *
     * @param id the id of the estoque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estoques/{id}")
    public ResponseEntity<Void> deleteEstoque(@PathVariable Long id) {
        log.debug("REST request to delete Estoque : {}", id);
        estoqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
