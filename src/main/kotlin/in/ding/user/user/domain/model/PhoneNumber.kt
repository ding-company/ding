package `in`.ding.user.user.domain.model

@JvmInline
value class PhoneNumber(val value: String) {
    init {
        require(value.matches(Regex("^\\+[0-9]{1,3}[0-9]{4,14}$"))) {
            "Invalid international phone format"
        }
    }
}
