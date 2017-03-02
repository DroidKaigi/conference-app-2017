package io.github.droidkaigi.confsched2017.api.service;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GoogleFormService {

    // TODO: change test url and add form item
    @POST("1SNBvJernnyBwglNentXxpdSUkWI9U6umWdDs4Na8OIU/formResponse")
    @FormUrlEncoded
    Single<Response<Void>> submitSessionFeedback(@Field("entry.684428626") int id);

    @POST("1SNBvJernnyBwglNentXxpdSUkWI9U6umWdDs4Na8OIU/formResponse")
    @FormUrlEncoded
    Single<Response<Void>> submitQuestionnaire(
            @Field("entry.45076472") String ageRange,
            @Field("entry.1423428529") String androidExperience,
            @Field("entry.1748620423") String developmentExperience,
            @Field("entry.595646748") String jobCategory,
            @Field("entry.595646748.other_option_response") String jobCategoryOther,
            @Field("entry.2100414566") String jobPosition,
            @Field("entry.2100414566.other_option_response") String jobPositionOther,
            @Field("entry.1035760212") String foundChannel,
            @Field("entry.1035760212.other_option_response") String foundChannelOther,
            @Field("entry.676422586") String howGotTicket,
            @Field("entry.676422586.other_option_response") String howGotTicketOther,
            @Field("entry.1832736685") String price,
            @Field("entry.455416737") String afterParty,
            @Field("entry.1089426034") String commentForPrice,
            @Field("entry.1556681290") String timeTable,
            @Field("entry.910403137") String priority,
            @Field("entry.221077566") String commentForTimeTable,
            @Field("entry.2041100455") String sponsorLogo,
            @Field("entry.1726476155") String sponsorBooth,
            @Field("entry.1655374454") String feelingBooth,
            @Field("entry.1947460274") String commentForBooth,
            @Field("entry.1647433992") String foodAndDrink,
            @Field("entry.1202479996") String commentForFoodAndDrink,
            @Field("entry.1195531622") String goods,
            @Field("entry.1701741632") String commentForGoods,
            @Field("entry.1224977534") String equipment,
            @Field("entry.1429181897") String youtube,
            @Field("entry.1147397694") String wantSpeak,
            @Field("entry.2047139770") String nextTime
    );
}
