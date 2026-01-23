package `in`.ding.user.auth.application.rest.response

enum class PostAuthStatus {
    AUTHENTICATED,
    NEED_TERMS_NOT_REGISTERED,
    NEED_TERMS_EXPIRED
}
