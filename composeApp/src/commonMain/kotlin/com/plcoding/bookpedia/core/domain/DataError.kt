package com.plcoding.bookpedia.core.domain

sealed interface DataError {
    enum class Remote : DataError, Error {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        UNKNOWN,
        FORBIDDEN,
        BAD_REQUEST,
        NOT_FOUND,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        TOO_MANY_REQUESTS,
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN,
        SERIALIZATION,
    }
}