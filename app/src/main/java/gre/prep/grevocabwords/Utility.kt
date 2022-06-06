package gre.prep.grevocabwords

import gre.prep.grevocabwords.Resource.Error
import retrofit2.Response


/**
 * function to handle api request errors & parse the response to a Resource object
 */
suspend fun <T: Any> handleRequest(requestFunc: suspend () -> Response<T>): Resource<T> {

    try {
        val response = requestFunc.invoke()
        response.body()?.let { res ->
            if(response.isSuccessful) {
                return Resource.Success(res)
            }
        }
        return Error(Throwable(response.message()))
    } catch (exception: Exception) {
        return Error(exception)
    }
}


/**
 * Base Class for all network response
 * error = null for Success & data = null for Error
 * Throwable is the base class for exception so network exceptions can be casted & handled
 */
sealed class Resource<T>(val data: T? = null, val error: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(error: Throwable) : Resource<T>(error = error)
    class Loading<T>: Resource<T>()
    class Uninitialised<T>: Resource<T>()
}