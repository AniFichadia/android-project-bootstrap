package com.anifichadia.bootstrap.service

import javax.net.ssl.SSLException

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
sealed class ApiResult<DataT, ErrorT> {

    class Success<DataT, ErrorT>(
        val httpStatusCode: Int,
        val data: DataT
    ) : ApiResult<DataT, ErrorT>()

    sealed class Failure<DataT, ErrorT> : ApiResult<DataT, ErrorT>() {

        open class ResponseWithStatus<DataT, ErrorT>(
            val httpStatusCode: Int
        ) : Failure<DataT, ErrorT>()

        class ResponseWithStatusAndError<DataT, ErrorT>(
            httpStatusCode: Int,
            val error: ErrorT
        ) : ResponseWithStatus<DataT, ErrorT>(httpStatusCode)

        class ResponseMapping<DataT, ErrorT>(
            httpStatusCode: Int,
            val throwable: Throwable,
            val failedOnSuccessBody: Boolean
        ) : ResponseWithStatus<DataT, ErrorT>(httpStatusCode)

        class Ssl<DataT, ErrorT>(
            val sslException: SSLException
        ) : Failure<DataT, ErrorT>()

        class UnclassifiedException<DataT, ErrorT>(
            val throwable: Throwable
        ) : Failure<DataT, ErrorT>()
    }
}
