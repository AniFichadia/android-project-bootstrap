package com.anifichadia.sdknetworking.service

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

    class Failure<DataT, ErrorT>(
        val failureType: FailureType<ErrorT>
    ) : ApiResult<DataT, ErrorT>()


    sealed class FailureType<ErrorT> {

        open class ResponseWithStatus<ErrorT>(
            val httpStatusCode: Int
        ) : FailureType<ErrorT>()

        class ResponseWithStatusAndError<ErrorT>(
            httpStatusCode: Int,
            val error: ErrorT
        ) : ResponseWithStatus<ErrorT>(httpStatusCode)

        class ResponseMapping<ErrorT>(
            httpStatusCode: Int,
            val throwable: Throwable,
            val failedOnSuccessBody: Boolean
        ) : ResponseWithStatus<ErrorT>(httpStatusCode)

        class Ssl<ErrorT>(
            val sslException: SSLException
        ) : FailureType<ErrorT>()

        class UnclassifiedException<ErrorT>(
            val throwable: Throwable
        ) : FailureType<ErrorT>()
    }
}
