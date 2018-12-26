package org.avenue1.target.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.target.domain.Target;
import org.avenue1.target.domain.enumeration.TargetTypeEnum;
import org.avenue1.target.service.TargetService;
import org.avenue1.target.web.rest.errors.BadRequestAlertException;
import org.avenue1.target.web.rest.util.HeaderUtil;
import org.avenue1.target.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Target.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TargetResource {

    private final Logger log = LoggerFactory.getLogger(TargetResource.class);

    private static final String ENTITY_NAME = "targetSvcTarget";

    private final TargetService targetService;

    public TargetResource(TargetService targetService) {
        this.targetService = targetService;
    }

    /**
     * POST  /targets : Create a new target.
     *
     * @param target the target to create
     * @return the ResponseEntity with status 201 (Created) and with body the new target, or with status 400 (Bad Request) if the target has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/targets")
    @Timed
    public ResponseEntity<Target> createTarget(@Valid @RequestBody Target target) throws URISyntaxException {
        log.debug("REST request to save Target : {}", target);
        if (target.getId() != null) {
            throw new BadRequestAlertException("A new target cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Target result = targetService.save(target);
        return ResponseEntity.created(new URI("/api/targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /targets : Updates an existing target.
     *
     * @param target the target to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated target,
     * or with status 400 (Bad Request) if the target is not valid,
     * or with status 500 (Internal Server Error) if the target couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/targets")
    @Timed
    public ResponseEntity<Target> updateTarget(@Valid @RequestBody Target target) throws URISyntaxException {
        log.debug("REST request to update Target : {}", target);
        if (target.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Target result = targetService.save(target);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, target.getId().toString()))
            .body(result);
    }

    /**
     * GET  /targets : get all the targets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of targets in body
     */
    @GetMapping("/targets")
    @Timed
    public ResponseEntity<List<Target>> getAllTargets(Pageable pageable) {
        log.debug("REST request to get a page of Targets");
        Page<Target> page = targetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/targets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/targetsByType/{type}")
    @Timed
    public ResponseEntity<List<Target>> getAllTargetsByType(@PathVariable("type") String type, Pageable pageable) {
        log.debug("REST request to get a page of Targets by type {}", type);
        Page<Target> page = targetService.findAllByType(TargetTypeEnum.valueOf(type.toUpperCase()),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/targets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /targets/:id : get the "id" target.
     *
     * @param id the id of the target to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the target, or with status 404 (Not Found)
     */
    @GetMapping("/targets/{id}")
    @Timed
    public ResponseEntity<Target> getTarget(@PathVariable String id) {
        log.debug("REST request to get Target : {}", id);
        Optional<Target> target = targetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(target);
    }

    /**
     * DELETE  /targets/:id : delete the "id" target.
     *
     * @param id the id of the target to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/targets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTarget(@PathVariable String id) {
        log.debug("REST request to delete Target : {}", id);
        targetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
