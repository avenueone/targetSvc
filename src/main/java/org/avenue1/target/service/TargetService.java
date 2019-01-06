package org.avenue1.target.service;

import org.avenue1.target.domain.Target;
import org.avenue1.target.domain.enumeration.InstrumentTypeEnum;
import org.avenue1.target.domain.enumeration.TargetTypeEnum;
import org.avenue1.target.repository.TargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.sound.midi.Instrument;
import java.util.Optional;

/**
 * Service Implementation for managing Target.
 */
@Service
public class TargetService {

    private final Logger log = LoggerFactory.getLogger(TargetService.class);

    private final TargetRepository targetRepository;

    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    /**
     * Save a target.
     *
     * @param target the entity to save
     * @return the persisted entity
     */
    public Target save(Target target) {
        log.debug("Request to save Target : {}", target);
        return targetRepository.save(target);
    }

    /**
     * Get all the targets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Target> findAll(Pageable pageable) {
        log.debug("Request to get all Targets");
        return targetRepository.findAll(pageable);
    }

    public Page<Target> findAllByInstrumentType(InstrumentTypeEnum type, Pageable pageable) {
        log.debug("Request to get all Targets by instrument type {}", type);
        return targetRepository.findAllByInstrumentType(type, pageable);
    }

    public Page<Target> findAllByType(TargetTypeEnum type, Pageable pageable) {
        log.debug("Request to get all Targets by type {}", type.name());
        return targetRepository.findAllByTargetType(type, pageable);
    }

    public Page<Target> findAllByTypeAndInstrument(TargetTypeEnum type, InstrumentTypeEnum instrumentType, Pageable pageable) {
        log.debug("Request to get all Targets by type {} and instrument {}", type.name(), instrumentType.name());
        return targetRepository.findAllByTargetTypeAndInstrumentType(type, instrumentType, pageable);
    }

    /**
     * Get one target by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Target> findOne(String id) {
        log.debug("Request to get Target : {}", id);
        return targetRepository.findById(id);
    }

    /**
     * Delete the target by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Target : {}", id);
        targetRepository.deleteById(id);
    }
}
