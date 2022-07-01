package br.com.rorizinfo.cardFidelize.domain.model

abstract class User(
    var id: String = "",
    val email: String = "",
    val password: String = "",
) {
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as User
        
        if (id != other.id) return false
        if (email != other.email) return false
        if (password != other.password) return false
        
        return true
    }
}