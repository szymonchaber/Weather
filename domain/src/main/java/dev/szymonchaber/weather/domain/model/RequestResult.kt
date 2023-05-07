package dev.szymonchaber.weather.domain.model


sealed class RequestResult<Data, Error> {

    data class Success<Data, Error>(val data: Data): RequestResult<Data, Error>()
    data class Error<Data, Error>(val error: Error): RequestResult<Data, Error>()

    companion object {

        fun <Data, Error> success(data: Data) : RequestResult<Data, Error> {
            return Success(data)
        }
        fun <Data, Error> error(error: Error) : RequestResult<Data, Error> {
            return Error(error)
        }
    }
}
fun <Data, Error> RequestResult<Data, Error>.tapSuccess(onSuccess: (Data) -> Unit) {
    if (this is RequestResult.Success) {
        onSuccess(this.data)
    }
}

fun <T,Data, Error> RequestResult<Data, Error>.mapSuccess(function: (Data) -> T): RequestResult<T, Error> {
    return when (this) {
        is RequestResult.Success -> {
            RequestResult.success(function(this.data))
        }
        is RequestResult.Error -> {
            RequestResult.error(this.error)
        }
    }
}
