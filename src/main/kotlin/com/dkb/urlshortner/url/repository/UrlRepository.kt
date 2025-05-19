package com.dkb.urlshortner.url.repository

import com.dkb.urlshortner.url.entity.DetailedUrlDB
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UrlRepository: JpaRepository<DetailedUrlDB, UUID>{
    /*
     TODO : add sql index on Code column
     This function is of O(n) complexity to improve it to O(log n )
     we need to add an sql index on the code column ,
     I should create a flyway migration for this.
    */
    fun findFirstByCodeOrCodeIsNull(code: String?): DetailedUrlDB?
}