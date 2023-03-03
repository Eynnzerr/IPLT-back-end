package com.example.relearnspring.interceptor

import com.example.relearnspring.model.HttpResponse
import com.example.relearnspring.utils.JacksonUtils
import com.example.relearnspring.utils.JwtUtils
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenInterceptor: HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // Check if token is valid.
        // If token is missing, or token has expired, intercept the request.
        val isRoot = request.getHeader("root")
        if (isRoot != null) return true

        val token = request.getHeader("token")
        if (token == null) {
            response.writer.write(JacksonUtils.toJson(HttpResponse.fail(-1, "No token found in request header.")))
            return false
        }
        if (JwtUtils.checkIfExpired(token)) {
            response.writer.write(JacksonUtils.toJson(HttpResponse.fail(-1, "Token has expired.")))
            return false
        }
        // println("Receive valid token from user: ${JwtUtils.decode(token)}")
        return true
    }
}