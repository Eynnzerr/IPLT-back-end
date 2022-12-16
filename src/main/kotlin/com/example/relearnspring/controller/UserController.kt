@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.example.relearnspring.controller

import com.example.relearnspring.mapper.UserMapper
import com.example.relearnspring.model.HttpResponse
import com.example.relearnspring.model.User
import com.example.relearnspring.utils.EmailUtils
import com.example.relearnspring.utils.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<Any, Any>

    @PostMapping("/register/info")
    fun addUser(@RequestBody user: User): HttpResponse {
        var id = userMapper.findUserByName(user.name)
        if (id != null) return HttpResponse.fail(-1, "Username has been used.")
        id = userMapper.addUser(user)
        return if (id > 0) HttpResponse.success("Register successful.", id)
        else HttpResponse.fail(-2, "Register failed for some other reason.")
    }

    @PostMapping("/register/auth")
    fun authenticate(email: String, auth: String): HttpResponse = when {
        !EmailUtils.isEmailValid(email) -> HttpResponse.fail(-1, "incorrect form of email address.")
        userMapper.findUserByEmail(email) != null -> HttpResponse.fail(-2, "This email is already used.")
        redisTemplate.getExpire(email) < 0 -> HttpResponse.fail(-3, "The authentication code has expired.")
        auth == redisTemplate.opsForValue().get(email) -> HttpResponse.success("Authentication successfully.")
        else -> HttpResponse.fail(-4, "Authentication failed for some other reason.")
    }

    @PostMapping("/register/send")
    fun sendEmail(email: String): HttpResponse = when {
        !EmailUtils.isEmailValid(email) -> HttpResponse.fail(-1, "incorrect form of email address.")
        userMapper.findUserByEmail(email) != null -> HttpResponse.fail(-2, "This email is already used.")
        else -> {
            val authCode = ((Math.random() * 9 + 1) * 100000) as Int
            redisTemplate.opsForValue().set(email, authCode.toString(), Duration.ofMinutes(5L))

            if (EmailUtils.sendEmail(email, authCode)) HttpResponse.success("Auth code has been sent.")
            else HttpResponse.fail(-3, "Failed to send auth code.")
        }
    }

    @PostMapping("/login/pwd")
    fun loginByPasswd(name: String, password: String): HttpResponse {
        val realPwd = userMapper.getPasswd(name)
        realPwd?.let {
            if (password == realPwd) {
                val token = JwtUtils.generate(name, "Eynnzerr-security", null)
                return HttpResponse.success("login successfully.", token)
            }
            else {
                return HttpResponse.fail(-1, "Incorrect password.")
            }
        }
        return HttpResponse.fail(-2, "User does not exist.")
    }

}