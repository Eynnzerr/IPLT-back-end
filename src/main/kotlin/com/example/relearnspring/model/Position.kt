package com.example.relearnspring.model

/**
 * Model of positioning result by base station.
 */
data class Position(
    val id: Int,
    val address: String,
    val x: Float,
    val y: Float,
    val z: Float,  // 0.8 constantly
    val stay: Int,
    val timestamp: Long,
    val bsAddress: Int,
    val sampleTime: String,
    val sampleBatch: Int
)
