package cz.fencl.minicrm.db.configurations;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("cz.fencl.minicrm.db.persistence.repositories")
@EntityScan("cz.fencl.minicrm.db.persistence.model")
@Configuration
public class DbConfiguration {
}
