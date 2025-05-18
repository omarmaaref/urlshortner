package com.dkb.urlshortner.url.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "urls")
data class DetailedUrlDB(

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    var code: String = "",

    @Column(name = "full_url", nullable = false)
    var fullUrl: String = "",

    @Column(name = "view_count", nullable = false)
    var viewCount: Long = 0
)
