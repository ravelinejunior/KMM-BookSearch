package com.plcoding.bookpedia.core.presentation

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.error_disk_full
import cmp_bookpedia.composeapp.generated.resources.error_forbidden
import cmp_bookpedia.composeapp.generated.resources.error_no_internet
import cmp_bookpedia.composeapp.generated.resources.error_not_found
import cmp_bookpedia.composeapp.generated.resources.error_request_timeout
import cmp_bookpedia.composeapp.generated.resources.error_serialization
import cmp_bookpedia.composeapp.generated.resources.error_server_error
import cmp_bookpedia.composeapp.generated.resources.error_too_many_requests
import cmp_bookpedia.composeapp.generated.resources.error_unauthorized
import cmp_bookpedia.composeapp.generated.resources.error_unknown
import com.plcoding.bookpedia.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.Local.SERIALIZATION -> Res.string.error_serialization
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.SERVER_ERROR -> Res.string.error_server_error
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNAUTHORIZED -> Res.string.error_unauthorized
        DataError.Remote.BAD_REQUEST -> Res.string.error_request_timeout
        DataError.Remote.NOT_FOUND -> Res.string.error_not_found
        DataError.Remote.FORBIDDEN -> Res.string.error_forbidden
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
    }

    return UiText.StringResourceId(stringRes)
}