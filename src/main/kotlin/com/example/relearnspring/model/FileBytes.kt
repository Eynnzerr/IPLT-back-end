package com.example.relearnspring.model

import org.springframework.web.multipart.MultipartFile

data class FileBytes(
    val name: String,
    val multipartFile: MultipartFile
)
