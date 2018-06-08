package com.tstdct.testkotlin.b_network.api

import com.tstdct.testkotlin.b_network.reponse.W_AnnouncementList
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Dechert on 2018-02-27.
 * Company: www.chisalsoft.co
 */
interface AnnouncementApi {
    @FormUrlEncoded
    @POST("tAnnouncementsList.thtml")
    fun getRegularBusDetail(@Field("pageNumber") pageNumber: Int,
                            @Field("countPerPage") countPerPage: Int): Observable<W_AnnouncementList>

}