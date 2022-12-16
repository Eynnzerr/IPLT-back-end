@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.example.relearnspring.controller

import com.example.relearnspring.mapper.PositionMapper
import com.example.relearnspring.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pos")
class PositionController {

    @Autowired
    private lateinit var positionMapper: PositionMapper

    // test
    @GetMapping("/test")
    fun testPosition(): HttpResponse {
        val list = positionMapper.testPosList()
        return HttpResponse.success("test is successful", list)
    }

    // get all position data
    @GetMapping("/all")
    fun getAllPos(): HttpResponse {
        val list = positionMapper.getAllPos()
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
        else HttpResponse.fail(-1, "No corresponding data.")
    }

    // get position data by batch number
    @GetMapping("/batch")
    fun getPosByBatch(@RequestParam(value = "batch", defaultValue = "27") batch: Int): HttpResponse {
        print("Receive a request for batch selecting.")
        val list = positionMapper.getPosByBatch(batch)
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
            else HttpResponse.fail(-1, "No corresponding data.")
    }
}