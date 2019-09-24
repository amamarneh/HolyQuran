package com.amarnehsoft.holyquran.network.n

sealed class RemoteError {
    data class General(val throwable: Throwable) : RemoteError()
    data class Failed(val responseCode: Int) : RemoteError()

    fun asException(): Exception {
        return when (this) {
            is General -> Exception(this.throwable)
            is Failed -> Exception("Request failed with response code: ${this.responseCode}")
        }
    }

    companion object {
        fun failed(responseCode: Int): RemoteError {
            return Failed(responseCode)
        }

        fun general(throwable: Throwable): RemoteError {
            return General(throwable)
        }
    }
}