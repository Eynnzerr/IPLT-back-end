package com.example.relearnspring.mapper

import com.example.relearnspring.model.Running
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

interface RunningMapper {
    @Select("SELECT * FROM running limit 10")
    fun testRunList(): List<Running>

    @Select("SELECT * FROM running")
    fun getAllRun(): List<Running>

    @Select("SELECT * FROM running WHERE sample_batch = #{batch}")
    fun getRunByBatch(batch: Int): List<Running>

    @Select("SELECT DISTINCT sample_batch FROM running")
    fun getBatchNum(): List<Int>

    @Update("UPDATE running set " +
            "address = #{address}, " +
            "accx = #{accx}, " +
            "accy = #{accy}, " +
            "accz = #{accz}, " +
            "gyroscopex = #{gyroscopex}, " +
            "gyroscopey = #{gyroscopey}, " +
            "gyroscopez = #{gyroscopez}, " +
            "stay = #{stay}, " +
            "timestamp = #{timestamp}, " +
            "bs_address = #{bsAddress}, " +
            "sample_time = #{sampleTime}, " +
            "sample_batch = #{sampleBatch} " +
            "WHERE id = #{id}"
    )
    fun updateById(running: Running): Int

    @Delete("DELETE FROM running WHERE id = #{id}")
    fun deleteById(id: Int): Int
}