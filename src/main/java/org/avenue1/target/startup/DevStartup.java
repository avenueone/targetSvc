package org.avenue1.target.startup;

import org.avenue1.target.domain.Target;
import org.avenue1.target.domain.enumeration.InstrumentTypeEnum;
import org.avenue1.target.domain.enumeration.TargetTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Profile("dev")
@Controller
@Configuration
public class DevStartup extends BaseStartup {

    private static final Logger log = LoggerFactory.getLogger(DevStartup.class);
    private static final String[] EMAIL_LIST = {"lee@avenue1.org","phani@avenue1.org","raghu@avenue1.org","peter@avenue1.org","rene@avenue1.org"};

    @PostConstruct
    private void startup() {
        if ( targetRepository.findAll().isEmpty()) {
            log.debug("Creating some sample targets...");
            Set<Target> targets = new HashSet<>();
            int sgIdx = 1;
            // we'll create some storegroups, stores and email targets
            for ( int storeIdx = 1 ; storeIdx <= 5000; storeIdx++) {

                String name = "Store-" + storeIdx;
                Target target = new Target().name(name).targetType(TargetTypeEnum.STORE).active(true).description("Sample " + storeIdx).instrumentType(InstrumentTypeEnum.FLYER);
                Target saved = targetRepository.save(target);
                targets.add(saved);
                log.debug("Create store {} {}", name, saved.getId());
                if ( (storeIdx % 50) == 0 ) {
                    name = "SG-" + sgIdx;
                    Target storeGroup = new Target().name(name).targetType(TargetTypeEnum.STOREGROUP).active(true).description("SG " + sgIdx).instrumentType(InstrumentTypeEnum.FLYER);
                    storeGroup.setChildren(targets);
                    Target sgSaved = targetRepository.save(storeGroup);
                    log.debug("Create store group {} {}", name, sgSaved.getId());

                    sgIdx++;
                    targets.clear();
                }

            }
        } else {
            log.info("Some targets already exist...keeping existing");
        }

        // see if we have any email targets
        if ( targetRepository.findAllByTargetType(TargetTypeEnum.EMAIL, null ).getSize() == 0 ) {
            log.debug("Adding sample email targets...");
            for ( String em : EMAIL_LIST) {
                Target target = new Target().name(em).targetType(TargetTypeEnum.EMAIL).active(true).description(em).instrumentType(InstrumentTypeEnum.EMAIL);
                Target sgSaved = targetRepository.save(target);
                log.debug("Added {}", sgSaved);
            }
        }

        // see if we have any email targets
        if ( targetRepository.findAllByTargetType(TargetTypeEnum.EMAILGROUP, null ).getSize() == 0 ) {
            log.debug("Adding sample email group target...");
                Target target = new Target().name("dev@avenue1.org").targetType(TargetTypeEnum.EMAILGROUP).active(true).description("dev@avenue1.org").instrumentType(InstrumentTypeEnum.EMAIL);
                Target sgSaved = targetRepository.save(target);
                log.debug("Added {}", sgSaved);

        }
    }

}
