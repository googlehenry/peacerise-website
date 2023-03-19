package com.peacerise.identity.myaccount.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MyProfileController {

    @RequestMapping("/peacerise/myprofile")
    fun getMyInfo(){
        println("ok")
    }
}