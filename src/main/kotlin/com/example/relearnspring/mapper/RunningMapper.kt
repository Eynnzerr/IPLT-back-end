package com.example.relearnspring.mapper

import com.example.relearnspring.model.Running
import org.apache.ibatis.annotations.Select

interface RunningMapper {
    @Select("SELECT * FROM running limit 10")
    fun testRunList(): List<Running>

    @Select("SELECT * FROM running")
    fun getAllRun(): List<Running>

    @Select("SELECT * FROM running WHERE sample_batch = #{batch}")
    fun getRunByBatch(batch: Int): List<Running>
}