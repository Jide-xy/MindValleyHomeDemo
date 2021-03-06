package com.example.mindvalleytest.core.repository

import com.example.mindvalleytest.core.network.models.DataResponse
import com.example.mindvalleytest.core.util.DispatcherProvider
import com.example.mindvalleytest.core.util.NetworkConstants
import com.example.mindvalleytest.core.util.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

abstract class BaseRepository(
    val dispatcherProvider: DispatcherProvider
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend inline fun <reified ResponseType> processNetworkResponse(
        crossinline block: suspend () -> Response<DataResponse<ResponseType>>
    ): NetworkStatus<ResponseType> {
        try {
            val response = block()
            return when {
                response.isSuccessful -> {
                    NetworkStatus.Success(response.body()!!.data)
                }
                else -> {
                    throw HttpException(response)
                }
            }
        } catch (e: Exception) {
            return when (e) {
                is ConnectException -> {
                    NetworkStatus.Error(NetworkConstants.CONNECT_EXCEPTION)
                }
                is UnknownHostException -> {
                    NetworkStatus.Error(NetworkConstants.UNKNOWN_HOST_EXCEPTION)
                }
                is SocketTimeoutException -> {
                    NetworkStatus.Error(NetworkConstants.SOCKET_TIME_OUT_EXCEPTION)
                }
                is HttpException -> {
                    return withContext(dispatcherProvider.io()) {
                        return@withContext NetworkStatus.Error<ResponseType>(
                            e.response()?.errorBody()?.string()
                                ?: NetworkConstants.UNKNOWN_NETWORK_EXCEPTION
                        )
                    }
                }
                is SSLException -> {
                    NetworkStatus.Error(NetworkConstants.SSL_EXCEPTION)
                }
                else -> NetworkStatus.Error(NetworkConstants.UNKNOWN_NETWORK_EXCEPTION)
            }
        }
    }

    protected inline fun <NetworkResponse, CachedResponse> bindLocalAndRemoteCalls(
        crossinline networkCall: suspend () -> NetworkStatus<NetworkResponse>,
        crossinline localDbCall: suspend () -> CachedResponse,
        crossinline localDbObservableCall: () -> Flow<CachedResponse>,
        crossinline saveNetworkResponse: suspend (NetworkResponse) -> Unit
    ): Flow<NetworkStatus<CachedResponse>> {
        return flow {
            val cachedResponse = localDbCall()
            emit(NetworkStatus.Loading(cachedResponse))
            when (val response = networkCall()) {
                is NetworkStatus.Success -> {
                    withContext(dispatcherProvider.io()) { saveNetworkResponse(response.data) }
                    emitAll(localDbObservableCall().map { NetworkStatus.Success(it) })
                }
                is NetworkStatus.Error -> {
                    emit(NetworkStatus.Error(response.message, cachedResponse))
                }
            }
        }
    }
}