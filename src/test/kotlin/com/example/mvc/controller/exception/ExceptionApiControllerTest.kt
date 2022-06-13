package com.example.mvc.controller.exception

import com.example.mvc.controller.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest // Application Context 를 완전하게 구동하지 않고 테스트
@AutoConfigureMockMvc // 배포하지않고 서버의 MVC 동작을 테스트
class ExceptionApiControllerTest {

    // perform() - 요청을 전송하는 역할. ResultActions 객체를 받음
    // andExpect() - ResultAction 에서 리턴 값(응답)을 검증할 수 있는 메소드
    // andDo() - 요청/응답 전체 메세지 확인

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(get("/api/exception/hello"))
            .andExpect(status().isBadRequest)
            .andExpect(content().string("hello"))
            .andDo(print())
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "steve")
        queryParams.add("age", "20")

        mockMvc.perform(get("/api/exception").queryParams(queryParams))
            .andExpect(status().isOk)
            .andExpect(content().string("steve 20"))
            .andDo(print())
    }

    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "steve")
        queryParams.add("age", "8")

        mockMvc.perform(get("/api/exception").queryParams(queryParams))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("\$.result_code").value("FAIL"))
            .andExpect(jsonPath("\$.errors[0].field").value("age"))
            .andExpect(jsonPath("\$.errors[0].value").value("8"))
            .andDo(print())
    }

    @Test
    fun postTest() {
        val userRequest = UserRequest().apply {
            this.name = "steve"
            this.age = 10
            this.phoneNumber = "010-1111-2222"
            this.address = "a address"
            this.email = "sukmuzi@naver.com"
            this.createdAt = "2022-06-13 13:18:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
            post("/api/exception")
            .content(json)
            .contentType("application/json")
            .accept("application/json")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.name").value("steve"))
            .andExpect(jsonPath("\$.age").value(10))
            .andDo(print())
    }
}