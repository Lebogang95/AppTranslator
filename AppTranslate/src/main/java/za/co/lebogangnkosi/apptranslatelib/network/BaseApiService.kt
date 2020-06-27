package za.co.lebogangnkosi.apptranslatelib.network

import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query
import za.co.lebogangnkosi.apptranslatelib.model.FrontFacing

interface BaseApiService {

    @POST(".")
    fun translate(
        @Query("key") key: String,
        @Query("source") source: String,
        @Query("target") target: String,
        @Query("q") q: ArrayList<String>
    ): Observable<FrontFacing>

}