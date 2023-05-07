package dev.szymonchaber.weather.domain.model


sealed class RequestResult<Data, Error> {

    data class Success<Data, Error>(val data: Data): RequestResult<Data, Error>()
    data class Error<Data, Error>(val error: Error): RequestResult<Data, Error>()

    companion object {

        fun <Data, Error> success(data: Data) : RequestResult<Data, Error> {
            return Success(data)
        }
    }
}
