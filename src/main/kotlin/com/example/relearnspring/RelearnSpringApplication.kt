package com.example.relearnspring

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan(basePackages = ["com.example.relearnspring.mapper"])
class RelearnSpringApplication

// This is a small project, so I don't follow the standard architecture of a backend project.
// e.g. I removed service layer, and let controllers directly hold the DAO.
fun main(args: Array<String>) {
    runApplication<RelearnSpringApplication>(*args)
}
