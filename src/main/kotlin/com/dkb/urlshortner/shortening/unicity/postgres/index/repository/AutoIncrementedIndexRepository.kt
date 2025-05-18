package com.dkb.urlshortner.shortening.unicity.postgres.index.repository

import com.dkb.urlshortner.shortening.unicity.postgres.index.model.AutoIncrementedIndex
import org.springframework.data.jpa.repository.JpaRepository

interface AutoIncrementedIndexRepository: JpaRepository<AutoIncrementedIndex, Long>