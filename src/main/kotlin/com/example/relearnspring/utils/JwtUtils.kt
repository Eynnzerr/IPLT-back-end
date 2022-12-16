package com.example.relearnspring.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

object JwtUtils {
    private const val SECRET_KEY = "Eynnzerr2022"
    private const val KEEP_TIME = 1800000L  // ms
    private val parser: JwtParser by lazy {
        Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
    }

    fun generate(id: String, issuer: String?, subject: String?): String {
        val sa = SignatureAlgorithm.HS256
        val nowMillis = System.currentTimeMillis()
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY)
        val signingKey = SecretKeySpec(apiKeySecretBytes, sa.jcaName)
        return with(Jwts.builder()) {
            setId(id)
            setIssuedAt(Date(nowMillis))
            setExpiration(Date(nowMillis + KEEP_TIME))
            issuer?.let {
                setIssuer(it)
            }
            subject?.let {
                setSubject(it)
            }
            signWith(sa, signingKey)
            compact()
        }
    }

    private fun verify(token: String): Claims = parser
        .parseClaimsJwt(token)
        .body

    fun checkIfExpired(token: String): Boolean = verify(token).expiration < Date(System.currentTimeMillis())

    fun update(token: String): String = with(verify(token)) {
        generate(id, issuer, subject)
    }

    fun decode(token: String): String = verify(token).id
}