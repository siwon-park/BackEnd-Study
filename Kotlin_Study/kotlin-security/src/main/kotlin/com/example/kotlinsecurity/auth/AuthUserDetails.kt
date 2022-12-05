package com.example.kotlinsecurity.auth

import com.example.kotlinsecurity.domain.User
import lombok.Getter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Getter
class AuthUserDetails(
    var user: User,
//    var accountNonExpired: Boolean,
//    var accountNonLocked: Boolean,
//    var credentialNonExpired: Boolean,
//    var enabled: Boolean = false,
//    var roles: List<GrantedAuthority>,
) : UserDetails {

    var accountNonExpired: Boolean = true
    var accountNonLocked: Boolean = true
    var credentialNonExpired: Boolean = true
    var enabled: Boolean = true
    var roles: List<GrantedAuthority> = arrayListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = arrayListOf() // new ArrayList<>()
        val userRole = this.user.role
        authorities.add(SimpleGrantedAuthority(userRole))
        return authorities
    }

    fun setAuthorities(roles: List<GrantedAuthority>) {
        this.roles = roles
    }

    override fun getPassword(): String {
        return this.user.password
    }

    override fun getUsername(): String {
        return this.user.name
    }

    override fun isAccountNonExpired(): Boolean {
        return this.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return this.accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return this.credentialNonExpired
    }

    override fun isEnabled(): Boolean {
        return this.enabled
    }
}