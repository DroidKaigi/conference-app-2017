package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import android.text.TextUtils;

public class Questionnaire {

    @SerializedName("entry.45076472")
    public String ageRange;

    @SerializedName("entry.1423428529")
    public String androidExperience;

    @SerializedName("entry.1748620423")
    public String developmentExperience;

    @SerializedName("entry.595646748")
    public String jobCategory;

    @SerializedName("entry.595646748.other_option_response")
    public String jobCategoryOther;

    @SerializedName("entry.2100414566")
    public String jobPosition;

    @SerializedName("entry.2100414566.other_option_response")
    public String jobPositionOther;

    @SerializedName("entry.1035760212")
    public String foundChannel;

    @SerializedName("entry.779173573")
    public String[] attendHistory;

    @SerializedName("entry.1035760212.other_option_response")
    public String foundChannelOther;

    @SerializedName("entry.676422586")
    public String whichTicket;

    @SerializedName("entry.676422586.other_option_response")
    public String whichTicketOther;

    @SerializedName("entry.1832736685")
    public String price;

    @SerializedName("entry.455416737")
    public String afterParty;

    @SerializedName("entry.1089426034")
    public String commentForPrice;

    @SerializedName("entry.1556681290")
    public String timeTable;

    @SerializedName("entry.910403137")
    public String[] priority;

    @SerializedName("entry.221077566")
    public String commentForTimeTable;

    @SerializedName("entry.2041100455")
    public String sponsorLogo;

    @SerializedName("entry.1726476155")
    public String sponsorBooth;

    @SerializedName("entry.1655374454")
    public String feelingBooth;

    @SerializedName("entry.1947460274")
    public String commentForBooth;

    @SerializedName("entry.1647433992")
    public String foodAndDrink;

    @SerializedName("entry.1202479996")
    public String commentForFoodAndDrink;

    @SerializedName("entry.1195531622")
    public String goods;

    @SerializedName("entry.1701741632")
    public String commentForGoods;

    @SerializedName("entry.1224977534")
    public String equipment;

    @SerializedName("entry.1429181897")
    public String youtube;

    @SerializedName("entry.814658876")
    public String satisfaction;

    @SerializedName("entry.1505426067")
    public String suggestion;

    @SerializedName("entry.1147397694")
    public String wantSpeak;

    @SerializedName("entry.2047139770")
    public String nextTime;

    public Questionnaire() {
    }

    @SuppressWarnings("MalformedFormatString")
    @Override
    //TODO: delete this (for developing)
    public String toString() {
        String ah = attendHistory != null ? (String) TextUtils.concat(attendHistory) : null;
        String pri = priority != null ? (String) TextUtils.concat(priority) : null;
        return String.format("ageRange=%s,androidExperience=%s,developmentExperience=%s,jobCategory=%s,jobCategoryOther=%s,"
                        + "jobPosition=%s,jobPositionOther=%s,attendHistory=%s,foundChannel=%s,foundChannelOther=%s,whichTicket=%s,whichTicketOther=%s,"
                        + "price=%s,afterParty=%s,commentForPrice=%s,timeTable=%s,priority=%s,commentForTimeTable=%s,sponsorLogo=%s,"
                        + "sponsorBooth=%s,feelingBooth=%s,commentForBooth=%s,foodAndDrink=%s,commentForFoodAndDrink=%s,goods=%s,"
                        + "commentForGoods=%s,equipment=%s,youtube=%s,satisfaction=%s,suggestion=%s,wantSpeak=%s,nextTime=%s,"
                        , ageRange, androidExperience, developmentExperience, jobCategory, jobCategoryOther,
                jobPosition, jobPositionOther, ah, foundChannel, foundChannelOther, whichTicket, whichTicketOther, price,
                afterParty, commentForPrice, timeTable, pri, commentForTimeTable, sponsorLogo, sponsorBooth, feelingBooth,
                commentForBooth, foodAndDrink, commentForFoodAndDrink, goods, commentForGoods, equipment, youtube,
                satisfaction, suggestion, wantSpeak, nextTime);
    }

    // TODO: delete this (for developing)
    public static Questionnaire createTestData() {
        Questionnaire q = new Questionnaire();
        q.ageRange = "答えたくない";
        q.androidExperience = "なし";
        q.developmentExperience = "なし";
        q.jobCategory = "__other_option__";
        q.jobCategoryOther = "テストデータです by gen0083";
        q.jobPosition = "__other_option__";
        q.jobPositionOther = "テストデータです by gen0083";
        q.attendHistory = new String[]{"初めて参加した"};
        q.foundChannel = "__other_option__";
        q.foundChannelOther = "テストデータです by gen0083";
        q.whichTicket = "__other_option__";
        q.whichTicketOther = "テストデータです by gen0083";
        q.price = "妥当";
        q.afterParty = "良い";
        q.commentForPrice = "テストデータです by gen0083";
        q.timeTable = "満足している";
        q.priority = new String[]{"最新技術・情報（Nougatで追加された機能のような公式API）", "最新技術・情報（RxJavaのようなサードパーティAPI）"};
        q.commentForTimeTable = "テストデータです by gen0083";
        q.sponsorLogo = "気が付かなかった";
        q.sponsorBooth = "立ち寄らなかった";
        q.feelingBooth = "とても良い";
        q.commentForBooth = "テストデータ by gen0083";
        q.foodAndDrink = "良い";
        q.commentForFoodAndDrink = "テストデータです by gen0083";
        q.goods = "良い";
        q.commentForGoods = "テストデータです by gen0083";
        q.equipment = "良い";
        q.youtube = "だいたい見た";
        q.satisfaction = "5";
        q.suggestion = "テストデータです by gen0083";
        q.wantSpeak = "わからない";
        q.nextTime = "わからない";
        return q;
    }
}
