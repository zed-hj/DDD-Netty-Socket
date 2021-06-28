package com.zed.infrastructure.config;

import com.zed.domain.repository.ClientBoxRepository;
import com.zed.domain.repository.NamespaceRepository;
import com.zed.infrastructure.persistence.dao.ClientBoxRepositoryImpl;
import com.zed.infrastructure.persistence.dao.NamespaceRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dao 层Bean 注入
 *
 * @author zed
 */
@Configuration
public class DaoConfig {


    @Bean
    @ConditionalOnMissingBean
    public NamespaceRepository namespaceRepository() {
        return new NamespaceRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientBoxRepository clientBoxRepository() {
        return new ClientBoxRepositoryImpl();
    }

}
