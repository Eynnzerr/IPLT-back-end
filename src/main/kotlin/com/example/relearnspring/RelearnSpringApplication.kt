package com.example.relearnspring

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan(basePackages = ["com.example.relearnspring.mapper"])
class RelearnSpringApplication

fun main(args: Array<String>) {
    runApplication<RelearnSpringApplication>(*args)
}
