package com.dkb.urlshortner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class UrlshortnerApplication

fun main(args: Array<String>) {
	runApplication<UrlshortnerApplication>(*args)
}
