package com.dkb.urlshortner.url.repository

import com.dkb.urlshortner.url.entity.DetailedUrlDB
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UrlRepository: JpaRepository<DetailedUrlDB, UUID>{
    fun findFirstByCodeOrCodeIsNull(email: String?): DetailedUrlDB?
}