package com.example.relearnspring.mapper

import com.example.relearnspring.model.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

// Mybatis mapper接口方法不能重载，除非在xml方式下使用动态sql。这里使用的是注解方式。
interface UserMapper {
    @Insert("INSERT INTO users(name,password,email) VALUES(#{name},#{password},#{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun addUser(user: User): Int

    @Select("SELECT id FROM users WHERE email = #{email}")
    fun findUserByEmail(email: String): Int?

    @Select("SELECT id FROM users WHERE name = #{name}")
    fun findUserByName(name: String): Int?

    @Select("SELECT password FROM users WHERE name = #{name}")
    fun getPasswd(name: String): String?

    @Update("UPDATE users SET passwd = #{passwd} WHERE name = #{name}")
    fun updatePasswd(name: String): Int
}