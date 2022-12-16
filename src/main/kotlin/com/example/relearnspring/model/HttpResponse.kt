package com.example.relearnspring.model

data class HttpResponse(
    val code: Int,
    val msg: String,
    val data: Any?
) {
    companion object {
        fun success(msg: String) = HttpResponse(0, msg, null)
        fun success(msg: String, data: Any) = HttpResponse(0, msg, data)
        fun fail(code: Int, msg: String) = HttpResponse(code, msg, null)
    }
}
