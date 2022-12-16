package com.example.relearnspring.utils

import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object EmailUtils {
    const val SENDER = "809204291@qq.com"
    const val AUTH_PASSWD = "qxaezpljdowzbbgb"
    const val HOST_EMAIL = "smtp.qq.com"

    fun isEmailValid(email: String) = Regex("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$") matches email

    fun sendEmail(receiver: String, authCode: Int): Boolean {
        val properties = System.getProperties().apply {
            setProperty("mail.smtp.host", HOST_EMAIL);
            put("mail.smtp.auth", "true");
        }
        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication() = PasswordAuthentication(SENDER, AUTH_PASSWD)
        })

        kotlin.runCatching {
            val msg = MimeMessage(session).apply {
                subject = "IPLT系统 注册邮箱验证"
                setFrom(InternetAddress(SENDER))
                addRecipient(Message.RecipientType.TO, InternetAddress(receiver))
                setText(with(StringBuilder()) {
                    append("【IPLT System】\n您的验证码是：")
                    append(authCode)
                    append("，五分钟内有效。\n欢迎使用IPLT室内行人定位轨迹系统！")
                    toString()
                })
            }
            Transport.send(msg)
        }.onFailure {
            it.printStackTrace()
            return false
        }

        println("Send email to $receiver successfully.")
        return true
    }
}