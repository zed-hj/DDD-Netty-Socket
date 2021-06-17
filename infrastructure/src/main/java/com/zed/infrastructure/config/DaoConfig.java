package com.zed.infrastructure.config;

import com.zed.domain.repository.NamespaceRepository;
import com.zed.infrastructure.persistence.dao.NamespaceRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * dao 层Bean 注入
 *
 * @author zed
 */
@Configuration
public class DaoConfig {

    @Bean
    public NamespaceRepository namespaceRepository() {
        return new NamespaceRepositoryImpl();
    }

}
