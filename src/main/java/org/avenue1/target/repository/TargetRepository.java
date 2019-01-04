package org.avenue1.target.repository;

import org.avenue1.target.domain.Target;
import org.avenue1.target.domain.enumeration.InstrumentTypeEnum;
import org.avenue1.target.domain.enumeration.TargetTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data MongoDB repository for the Target entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetRepository extends MongoRepository<Target, String> {
    Page<Target> findAllByTargetType(TargetTypeEnum typeEnum, Pageable pageable);
    Page<Target> findAllByInstrumentType(InstrumentTypeEnum typeEnum, Pageable pageable);

}
