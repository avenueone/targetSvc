package org.avenue1.target.repository;

import org.avenue1.target.domain.Target;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Target entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetRepository extends MongoRepository<Target, String> {

}
