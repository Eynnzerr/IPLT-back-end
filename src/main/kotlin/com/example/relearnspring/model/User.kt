package com.example.relearnspring.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import springfox.documentation.annotations.ApiIgnore

@ApiModel(description = "注册请求参数")
data class User(
    @ApiIgnore
    val id: Int,
    @ApiModelProperty(value = "用户名", required = true)
    val name: String,
    @ApiModelProperty(value = "密码", required = true)
    val password: String,
    @ApiModelProperty(value = "注册邮箱", required = true)
    val email: String
)
