package com.farmer.config;
/* Auditing 기능 사용을 위한 config 파일 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing//JPA의 Auditing 기능을 활성화 합니다.

public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider() { //등록자와 수정자를 처리해주는 AuditorAware을 빈으로 등록합니다.
        return new AuditorAwareImpl();
    }
}
