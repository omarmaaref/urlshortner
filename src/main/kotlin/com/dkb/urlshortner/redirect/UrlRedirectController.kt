package com.dkb.urlshortner.redirect

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping
class UrlRedirectController(
    private var urlRedirectService: UrlRedirectService
) {
    // Redirects GET /{code} to the real URL
    // with no caching, because with caching this endpoint is ignored.
    @GetMapping("/{code}")
    fun redirect(
        @PathVariable code: String,
        response: HttpServletResponse)
    : RedirectView{
        // No caching
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Expires", 0)

        val longUrl = urlRedirectService.handleAccessAndReturnFullUrlOrThrow(code)
        return RedirectView(longUrl, true)
    }
}