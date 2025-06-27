package `in`.ding.user.user.domain.model

@JvmInline
value class Email(val value: String) {
    init {
        require(
            value.contains("@") && value.split("@").let {
                it.size == 2 && it[1].contains(".")
            }
        ) { "Invalid email format" }
    }

    fun getDomain(): String = value.split("@")[1]
}
