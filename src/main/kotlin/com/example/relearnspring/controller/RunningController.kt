@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.example.relearnspring.controller

import com.example.relearnspring.mapper.RunningMapper
import com.example.relearnspring.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/run")
class RunningController {

    @Autowired
    lateinit var runningMapper: RunningMapper

    //test
    @GetMapping("/test")
    fun testRunning(): HttpResponse {
        val list = runningMapper.testRunList()
        return HttpResponse.success("test is successful", list)
    }

    // get all position data
    @GetMapping("/all")
    fun getAllRun(): HttpResponse {
        val list = runningMapper.getAllRun()
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
        else HttpResponse.fail(-1, "No corresponding data.")
    }

    // get position data by batch number
    @GetMapping("/batch")
    fun getRunByBatch(@RequestParam(value = "batch", defaultValue = "27") batch: Int): HttpResponse {
        print("Receive a request for batch selecting.")
        val list = runningMapper.getRunByBatch(batch)
        return if (list.isNotEmpty()) HttpResponse.success("OK", list)
        else HttpResponse.fail(-1, "No corresponding data.")
    }
}