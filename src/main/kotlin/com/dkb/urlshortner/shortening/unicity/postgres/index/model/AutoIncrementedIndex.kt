package com.dkb.urlshortner.shortening.unicity.postgres.index.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "auto_incremented_index")
data class AutoIncrementedIndex(
    @Id
    @Column(name = "id")
    val id: Long = 1,

    @Column(name = "index")
    var currentIndex: Long = 0
)

