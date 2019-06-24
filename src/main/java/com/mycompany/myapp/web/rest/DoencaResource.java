package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Doenca;
import com.mycompany.myapp.service.DoencaService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Doenca}.
 */
@RestController
@RequestMapping("/api")
public class DoencaResource {

    private final Logger log = LoggerFactory.getLogger(DoencaResource.class);

    private static final String ENTITY_NAME = "doenca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoencaService doencaService;

    public DoencaResource(DoencaService doencaService) {
        this.doencaService = doencaService;
    }

    /**
     * {@code POST  /doencas} : Create a new doenca.
     *
     * @param doenca the doenca to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doenca, or with status {@code 400 (Bad Request)} if the doenca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doencas")
    public ResponseEntity<Doenca> createDoenca(@RequestBody Doenca doenca) throws URISyntaxException {
        log.debug("REST request to save Doenca : {}", doenca);
        if (doenca.getId() != null) {
            throw new BadRequestAlertException("A new doenca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Doenca result = doencaService.save(doenca);
        return ResponseEntity.created(new URI("/api/doencas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doencas} : Updates an existing doenca.
     *
     * @param doenca the doenca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doenca,
     * or with status {@code 400 (Bad Request)} if the doenca is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doenca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doencas")
    public ResponseEntity<Doenca> updateDoenca(@RequestBody Doenca doenca) throws URISyntaxException {
        log.debug("REST request to update Doenca : {}", doenca);
        if (doenca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Doenca result = doencaService.save(doenca);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doenca.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doencas} : get all the doencas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doencas in body.
     */
    @GetMapping("/doencas")
    public ResponseEntity<List<Doenca>> getAllDoencas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Doencas");
        Page<Doenca> page;
        if (eagerload) {
            page = doencaService.findAllWithEagerRelationships(pageable);
        } else {
            page = doencaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doencas/:id} : get the "id" doenca.
     *
     * @param id the id of the doenca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doenca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doencas/{id}")
    public ResponseEntity<Doenca> getDoenca(@PathVariable Long id) {
        log.debug("REST request to get Doenca : {}", id);
        Optional<Doenca> doenca = doencaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doenca);
    }

    /**
     * {@code DELETE  /doencas/:id} : delete the "id" doenca.
     *
     * @param id the id of the doenca to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doencas/{id}")
    public ResponseEntity<Void> deleteDoenca(@PathVariable Long id) {
        log.debug("REST request to delete Doenca : {}", id);
        doencaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
