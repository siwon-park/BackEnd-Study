package com.example.kotlinsecurity.domain

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
class User(
    var name: String,

    @Column(nullable = false)
    var phone: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var role: String,


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?
) {

    fun updateName(name: String) {
        this.name = name;
    }

}