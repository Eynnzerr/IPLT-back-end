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
}