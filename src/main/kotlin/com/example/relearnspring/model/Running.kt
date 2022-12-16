package com.example.relearnspring.model

/**
 * Model of data collected by sensor.
 *
 * Unused data is removed, e.g. power, heart rate, temperature,
 * systolicPressure, diastolicPressure, sos, elockOpenIllegalWarn.
 */
data class Running(
    val id: Int,
    val address: String,
    val accx: Int,
    val accy: Int,
    val accz: Int,
    val gyroscopex: Int,
    val gyroscopey: Int,
    val gyroscopez: Int,
    val stay: Int,
    val timeStamp: Long,
    val bs_address: Int,
    val sampleTime: String,
    val sampleBatch: Int
)
