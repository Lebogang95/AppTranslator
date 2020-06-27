package za.co.lebogangnkosi.apptranslatelib.util

import za.co.lebogangnkosi.apptranslatelib.network.BaseApiService
import za.co.lebogangnkosi.apptranslatelib.network.RetrofitClient

class UtilsApi {

    companion object {
        private const val BASE_URL_API = "https://www.googleapis.com/language/translate/v2/"

        fun getAPIService(): BaseApiService? {
            return RetrofitClient.getClient(BASE_URL_API)?.create(BaseApiService::class.java)
        }
    }

}