package com.example.relearnspring.mapper

import com.example.relearnspring.model.GroundTruth
import com.example.relearnspring.utils.DaoProvider
import org.apache.ibatis.annotations.InsertProvider
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

interface GroundTruthMapper {
//    @Select("SELECT * FROM truth")
//    fun getAllTruth() : List<GroundTruth>
//
//    @InsertProvider(type = DaoProvider::class, method = "insertAllTruth")
//    fun insertCsv(@Param("list") positions: List<GroundTruth>): Int
}