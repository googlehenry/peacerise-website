package com.peacerise.identity.myaccount.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.servlet.ModelAndView


@Controller
class AuthorizedController {
    @Autowired
    @Qualifier("webclient_clean")
    lateinit var webClient: WebClient

    @RequestMapping("/peacerise/authorized")
    fun authorized(request: HttpServletRequest, @RequestParam("code") code: String):ModelAndView {

        val bodyValues: MultiValueMap<String, String> = LinkedMultiValueMap()
        bodyValues.put("code", listOf(code))
        bodyValues.put("grant_type", listOf("authorization_code"))
        bodyValues.put("redirect_uri", listOf("http://127.0.0.1:8080/peacerise/authorized"))
        val token = webClient
            .post()
            .uri("http://127.0.0.1:9000/oauth2/token")
            .header("Authorization", "Basic bG9naW4tY2xpZW50OmxvZ2luLXNlY3JldA==")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromFormData(bodyValues))
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        println(token)
        val view = ModelAndView("authorized")
        view.addObject("token",token)
        return view
    }
    @RequestMapping("/peacerise/sitelogin/authorized2")
    fun authorized2(
        request: HttpServletRequest, @RequestParam("code") code: String
    ):String {

        val bodyValues: MultiValueMap<String, String> = LinkedMultiValueMap()
        bodyValues.put("code", listOf(code))
        bodyValues.put("grant_type", listOf("authorization_code"))
        bodyValues.put("redirect_uri", listOf("http://127.0.0.1:8080/peacerise/sitelogin/authorized"))
        val messages = webClient
            .post()
            .uri("http://127.0.0.1:9000/oauth2/token")
            .header("Authorization", "Basic bG9naW4tY2xpZW50OmxvZ2luLXNlY3JldA==")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromFormData(bodyValues))
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        println(messages)
        return "authorized"
    }
}