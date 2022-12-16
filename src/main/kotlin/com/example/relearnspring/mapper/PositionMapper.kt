package com.example.relearnspring.mapper

import com.example.relearnspring.model.Position
import org.apache.ibatis.annotations.Select

interface PositionMapper {
    @Select("SELECT * FROM position limit 10")
    fun testPositionList(): List<Position>

    @Select("SELECT * FROM position WHERE sample_batch = #{batch}")
    fun getPosByBatch(batch: Int): List<Position>
}