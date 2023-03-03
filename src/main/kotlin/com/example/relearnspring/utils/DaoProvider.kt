package com.example.relearnspring.utils

class DaoProvider {
    fun insertAllPos(map: Map<String, Any>): String {
        val positions = map["list"] as List<*>
        return with(StringBuilder()) {
            append("INSERT INTO position ")
            append("(id, address, x, y, z, stay, timestamp, bs_address, sample_time, sample_batch) VALUES ")
            for (i in positions.indices) {
                append("(")
                append("#{list[$i].id}")
                append(", #{list[$i].address}")
                append(", #{list[$i].x}")
                append(", #{list[$i].y}")
                append(", #{list[$i].z}")
                append(", #{list[$i].stay}")
                append(", #{list[$i].timestamp}")
                append(", #{list[$i].bsAddress}")
                append(", #{list[$i].sampleTime}")
                append(", #{list[$i].sampleBatch}")
                append(")")
                if (i < positions.size - 1) {
                    append(", ")
                }
            }
            toString()
        }
    }

    fun insertAllRun(map: Map<String, Any>): String {
        val runnings = map["list"] as List<*>
        return with(StringBuilder()) {
            append("INSERT INTO running ")
            append("(id, address, accx, accy, accz, gyroscopex, gyroscopey, gyroscopez, stay, timestamp, bs_address, sample_time, sample_batch) VALUES ")
            for (i in runnings.indices) {
                append("(")
                append("#{list[$i].id}")
                append(", #{list[$i].address}")
                append(", #{list[$i].accx}")
                append(", #{list[$i].accy}")
                append(", #{list[$i].accz}")
                append(", #{list[$i].gyroscopex}")
                append(", #{list[$i].gyroscopey}")
                append(", #{list[$i].gyroscopez}")
                append(", #{list[$i].stay}")
                append(", #{list[$i].timestamp}")
                append(", #{list[$i].bsAddress}")
                append(", #{list[$i].sampleTime}")
                append(", #{list[$i].sampleBatch}")
                append(")")
                if (i < runnings.size - 1) {
                    append(", ")
                }
            }
            toString()
        }
    }

    fun insertAllTruth(map: Map<String, Any>): String {
        val truths = map["list"] as List<*>
        return with(StringBuilder()) {
            append("INSERT INTO truth ")
            append("(step, x, y) VALUES ")
            for (i in truths.indices) {
                append("(")
                append(", #{list[$i].step}")
                append(", #{list[$i].x}")
                append(", #{list[$i].y}")
                if (i < truths.size - 1) {
                    append(", ")
                }
            }
            toString()
        }
    }
}