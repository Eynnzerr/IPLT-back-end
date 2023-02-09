package com.example.relearnspring.interceptor

import org.springframework.web.servlet.HandlerInterceptor
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogInterceptor: HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("${LocalDate.now()} Receive new request: ${request.method} uri: ${request.requestURI} from ${request.remoteUser}")
//        response.apply {
//            setHeader("Access-Control-Allow-Origin", request.getHeader("origin"))
//            setHeader("Access-Control-Allow-Credentials", "true")
//            setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
//            setHeader("Access-Control-Allow-Headers", "*")
//        }
        return true
    }

}