package com.dkb.urlshortner.shortening

import com.dkb.urlshortner.shortening.dtos.UrlGenerateRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/shortner")
class UrlShorteningController (
    private val shortner: UrlShorteningService
) {
    data class GenerateResponse(val shortUrl: String)

    @PostMapping("/generate")
    //PS: I should add some validation to the req body
    fun generate(@RequestBody req: UrlGenerateRequest): GenerateResponse {
        val short = shortner.shorten(req)
        return GenerateResponse(short)
    }
}
