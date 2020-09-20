package com.teko.hoangviet.androidtest.utils

object Define {
    const val PREF_NAME = "app_share_preference"

    object ResponseStatus {
        const val LOADING = 0
        const val SUCCESS = 1
        const val ERROR = 2
    }

    object ApiService {
        object RelativeUrl {
            const val LIST_PRODUCT = "7af6f34b-b206-4bed-b447-559fda148ca5"
        }

        object Parameter {
            const val PAGE = "page"
            const val TOKEN = "token"
            const val ID = "id"
        }

        object Constant {
            const val LIMIT = 20
        }

        object Header {
            const val AUTHORIZATION_HEADER = "Authorization"
        }
    }

    object Fragment {
        object Argument {
            const val DETAIL_PRODUCT = "detailProduct"
        }
    }

    object Database {
        const val NAME = "Teko"
    }
}