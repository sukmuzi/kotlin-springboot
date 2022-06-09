package com.example.mvc.controller.model.http

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class UserRequest(

    @field:NotEmpty
    @field:Size(min = 2, max = 8)
    var name: String? = null,

    @field:PositiveOrZero // 양수 검증
    var age: Int? = null,

    @field:Email // email 양식 검증
    var email: String? = null,

    @field:NotBlank //공백 검증
    var address: String? = null,

    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$") // 정규식 검증
    var phoneNumber: String? = null,

    var createdAt: String? = null // yyyy-MM-dd HH:mm:ss  ex) 2022-06-09 18:00:00
) {
    @AssertTrue(message = "생성일자의 패턴은 yyyy-MM-dd HH:mm:ss 여야 합니다.")
    private fun isValidCreatedAt(): Boolean {
        return try {
            LocalDateTime.parse(this.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            true
        } catch (e: Exception) {
            false
        }
    }
}