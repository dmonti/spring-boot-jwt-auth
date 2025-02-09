package com.dmonti.infra.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO

@Configuration
@EntityScan("com.dmonti.domain.entity")
@EnableJpaRepositories("com.dmonti.infra.jpa.repository")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
class JpaConfiguration