package com.example.mvc.controller.put

import com.example.mvc.controller.model.http.Result
import com.example.mvc.controller.model.http.UserRequest
import com.example.mvc.controller.model.http.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PutApiController {

    @PutMapping("/put-mapping")
    fun putMapping(): String {
        return "put-mapping"
    }

    @RequestMapping(method = [RequestMethod.PUT], path = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping - put method"
    }

    @PutMapping(path = ["/put-mapping/object"])
    fun putMappingObject(@Valid @RequestBody userRequest: UserRequest, bindingResult: BindingResult): ResponseEntity<String> {

        if (bindingResult.hasErrors()) {
            // 500 error
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                val field = it as FieldError
                val message = it.defaultMessage

                msg.append("${field.field} : $message\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }

        return ResponseEntity.ok("")

        // 0. Response
//        return UserResponse().apply {
//            // 1. result
//            this.result = Result().apply {
//                this.resultCode = "OK"
//                this.resultMessage = "성공"
//            }
//        }.apply {
//            // 2. description
//            this.description = "설명"
//        }.apply {
//            // 3. user mutable list
//            val userList = mutableListOf<UserRequest>()
//
//            userList.add(userRequest) // Param으로 들어온 request
//            userList.add(UserRequest().apply {
//                this.name = "minsuk"
//                this.age = 28
//                this.email = "sukmuzi@naver.com"
//                this.address = "a address"
//                this.phoneNumber = "010-1111-2222"
//            })
//
//            userList.add(UserRequest().apply {
//                this.name = "sukmuzi"
//                this.age = 27
//                this.email = "sukmuzi@nate.com"
//                this.address = "b address"
//                this.phoneNumber = "010-3333-4444"
//            })
//
//            this.user = userList
//        }
    }
}