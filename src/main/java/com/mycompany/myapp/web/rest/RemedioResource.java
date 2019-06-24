package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Remedio;
import com.mycompany.myapp.service.RemedioService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Remedio}.
 */
@RestController
@RequestMapping("/api")
public class RemedioResource {

    private final Logger log = LoggerFactory.getLogger(RemedioResource.class);

    private static final String ENTITY_NAME = "remedio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemedioService remedioService;

    public RemedioResource(RemedioService remedioService) {
        this.remedioService = remedioService;
    }

    /**
     * {@code POST  /remedios} : Create a new remedio.
     *
     * @param remedio the remedio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remedio, or with status {@code 400 (Bad Request)} if the remedio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remedios")
    public ResponseEntity<Remedio> createRemedio(@RequestBody Remedio remedio) throws URISyntaxException {
        log.debug("REST request to save Remedio : {}", remedio);
        if (remedio.getId() != null) {
            throw new BadRequestAlertException("A new remedio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Remedio result = remedioService.save(remedio);
        return ResponseEntity.created(new URI("/api/remedios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remedios} : Updates an existing remedio.
     *
     * @param remedio the remedio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remedio,
     * or with status {@code 400 (Bad Request)} if the remedio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remedio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remedios")
    public ResponseEntity<Remedio> updateRemedio(@RequestBody Remedio remedio) throws URISyntaxException {
        log.debug("REST request to update Remedio : {}", remedio);
        if (remedio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Remedio result = remedioService.save(remedio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, remedio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /remedios} : get all the remedios.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remedios in body.
     */
    @GetMapping("/remedios")
    public ResponseEntity<List<Remedio>> getAllRemedios(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Remedios");
        Page<Remedio> page = remedioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remedios/:id} : get the "id" remedio.
     *
     * @param id the id of the remedio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remedio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remedios/{id}")
    public ResponseEntity<Remedio> getRemedio(@PathVariable Long id) {
        log.debug("REST request to get Remedio : {}", id);
        Optional<Remedio> remedio = remedioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remedio);
    }

    /**
     * {@code DELETE  /remedios/:id} : delete the "id" remedio.
     *
     * @param id the id of the remedio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remedios/{id}")
    public ResponseEntity<Void> deleteRemedio(@PathVariable Long id) {
        log.debug("REST request to delete Remedio : {}", id);
        remedioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
