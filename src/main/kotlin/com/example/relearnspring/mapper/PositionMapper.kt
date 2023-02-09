package com.example.relearnspring.mapper

import com.example.relearnspring.model.Position
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

interface PositionMapper {
    @Select("SELECT * FROM position limit 10")
    fun testPosList(): List<Position>

    @Select("SELECT * FROM position")
    fun getAllPos(): List<Position>

    @Select("SELECT * FROM position WHERE sample_batch = #{batch}")
    fun getPosByBatch(batch: Int): List<Position>

    @Select("SELECT DISTINCT sample_batch FROM position")
    fun getBatchNum(): List<Int>

    @Update("UPDATE position set " +
            "address = #{address}, " +
            "x = #{x}, " +
            "y = #{y}, " +
            "z = #{z}, " +
            "stay = #{stay}, " +
            "timestamp = #{timestamp}, " +
            "bs_address = #{bsAddress}, " +
            "sample_time = #{sampleTime}, " +
            "sample_batch = #{sampleBatch} " +
            "WHERE id = #{id}"
    )
    fun updateById(pos: Position): Int

    @Delete("DELETE FROM position WHERE id = #{id}")
    fun deleteById(id: Int): Int
}