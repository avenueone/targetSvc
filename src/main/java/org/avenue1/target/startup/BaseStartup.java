package org.avenue1.target.startup;

import com.netflix.discovery.converters.Auto;
import org.avenue1.target.repository.TargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseStartup {

    @Autowired
    protected TargetRepository targetRepository;

    private static final Logger log = LoggerFactory.getLogger(BaseStartup.class);



}
