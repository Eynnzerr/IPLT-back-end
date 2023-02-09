@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.example.relearnspring.controller

import com.example.relearnspring.mapper.UserMapper
import com.example.relearnspring.model.HttpResponse
import com.example.relearnspring.model.User
import com.example.relearnspring.utils.EmailUtils
import com.example.relearnspring.utils.JwtUtils
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@Api(
    tags = ["身份验证请求"],
    description = "用户注册、验证、登录"
)
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<Any, Any>

    @ApiOperation("提交用户注册信息")
    @ApiResponses(
        ApiResponse(code = 200, message = "注册成功"),
        ApiResponse(code = -1, message = "该用户名已被使用"),
        ApiResponse(code = -2, message = "注册失败"),
    )
    @PostMapping("/register/info")
    fun addUser(@RequestBody user: User): HttpResponse {
        var id = userMapper.findUserByName(user.name)
        if (id != null) return HttpResponse.fail(-1, "Username has been used.")
        id = userMapper.addUser(user)
        return if (id > 0) HttpResponse.success("Register successful.", id)
        else HttpResponse.fail(-2, "Register failed for some other reason.")
    }

    @ApiOperation("提交验证码")
    @ApiImplicitParams(
        ApiImplicitParam(name = "email", value = "验证邮箱", required = true, paramType = "form"),
        ApiImplicitParam(name = "auth", value = "验证码", required = true, paramType = "form"),
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "验证成功"),
        ApiResponse(code = -1, message = "邮箱格式不正确"),
        ApiResponse(code = -2, message = "邮箱已被使用"),
        ApiResponse(code = -3, message = "验证码已过期"),
        ApiResponse(code = -4, message = "验证失败")
    )
    @PostMapping("/register/auth")
    fun authenticate(email: String, auth: String): HttpResponse = when {
        !EmailUtils.isEmailValid(email) -> HttpResponse.fail(-1, "incorrect form of email address.")
        userMapper.findUserByEmail(email) != null -> HttpResponse.fail(-2, "This email is already used.")
        redisTemplate.getExpire(email) < 0 -> HttpResponse.fail(-3, "The authentication code has expired.")
        auth == redisTemplate.opsForValue().get(email) -> HttpResponse.success("Authentication successfully.")
        else -> HttpResponse.fail(-4, "Authentication failed for some other reason.")
    }

    @ApiOperation("发送验证邮件")
    @ApiImplicitParam(name = "email", value = "验证邮箱", required = true, paramType = "form")
    @ApiResponses(
        ApiResponse(code = 200, message = "邮件已发送成功"),
        ApiResponse(code = -1, message = "邮箱格式不正确"),
        ApiResponse(code = -2, message = "邮箱已被使用"),
        ApiResponse(code = -3, message = "邮件发送失败"),
    )
    @PostMapping("/register/send")
    fun sendEmail(email: String): HttpResponse = when {
        !EmailUtils.isEmailValid(email) -> HttpResponse.fail(-1, "incorrect form of email address.")
        userMapper.findUserByEmail(email) != null -> HttpResponse.fail(-2, "This email is already used.")
        else -> {
            val authCode = ((Math.random() * 9 + 1) * 100000).toInt()
            redisTemplate.opsForValue().set(email, authCode.toString(), Duration.ofMinutes(5L))

            if (EmailUtils.sendEmail(email, authCode)) HttpResponse.success("Auth code has been sent.")
            else HttpResponse.fail(-3, "Failed to send auth code.")
        }
    }

    @ApiOperation("密码登录")
    @ApiImplicitParams(
        ApiImplicitParam(name = "name", value = "用户名", required = true, paramType = "form"),
        ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form"),
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "登录成功"),
        ApiResponse(code = -1, message = "密码不正确"),
        ApiResponse(code = -2, message = "用户不存在"),
    )
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