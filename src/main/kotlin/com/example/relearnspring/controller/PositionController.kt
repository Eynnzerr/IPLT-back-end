@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.example.relearnspring.controller

import com.example.relearnspring.mapper.PositionMapper
import com.example.relearnspring.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PositionController {

    @Autowired
    private lateinit var positionMapper: PositionMapper

    // test for connectivity
    @GetMapping("/test")
    fun greeting() = "hello, browser!"

    // get all position data
    @GetMapping("/pos/all")
    fun testPosition(): HttpResponse {
        print("Receive a request for testing.")
        val list = positionMapper.testPositionList()
        return HttpResponse.success("test is successful", list)
    }

    // get position data by batch number
    @GetMapping("/pos/batch")
    fun getPosByBatch(@RequestParam(value = "batch", defaultValue = "27") batch: Int): HttpResponse {
        print("Receive a request for batch selecting.")
        val list = positionMapper.getPosByBatch(batch)
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
            else HttpResponse.fail(-1, "No corresponding data.")
    }
}