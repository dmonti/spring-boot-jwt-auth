package com.dmonti.domain.entity

import com.dmonti.domain.RoleName
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

@Entity
class Role(
    @Id
    val id: Long,
    @Enumerated(STRING)
    val name: RoleName,
)