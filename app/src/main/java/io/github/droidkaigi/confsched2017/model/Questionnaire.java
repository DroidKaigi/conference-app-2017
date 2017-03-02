package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

public class Questionnaire {
    // https://github.com/DroidKaigi/conference-app-2017/issues/77#issuecomment-282930517
    // フォーム: https://docs.google.com/forms/d/1SNBvJernnyBwglNentXxpdSUkWI9U6umWdDs4Na8OIU/viewform?edit_requested=true
    // このEntityはDroidKaigiClientが使う。
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
    @SerializedName("entry.1035760212.other_option_response")
    public String foundChannelOther;
    @SerializedName("entry.676422586")
    public String howGotTicket;
    @SerializedName("entry.676422586.other_option_response")
    public String howGotTicketOther;
    @SerializedName("entry.1832736685")
    public String price;
    @SerializedName("entry.455416737")
    public String afterParty;
    @SerializedName("entry.1089426034")
    public String commentForPrice;
    @SerializedName("entry.1556681290")
    public String timeTable;
    @SerializedName("entry.910403137")
    public String priority;
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
    @SerializedName("entry.1147397694")
    public String wantSpeak;
    @SerializedName("entry.2047139770")
    public String nextTime;

    public Questionnaire() {
    }

    public boolean isValid() {
        return true;
    }

    // TODO: delete this (for developing)
    public static Questionnaire createTestData() {
        Questionnaire q = new Questionnaire();
        q.ageRange = "10代";
        q.androidExperience = "3年";
        q.developmentExperience = "3年";
        q.jobCategory = "その他";
        q.jobCategoryOther = "テストデータです by gen0083";
        q.jobPosition = "その他";
        q.jobPositionOther = "テストデータです by gen0083";
        q.foundChannel = "その他";
        q.foundChannelOther = "テストデータです by gen0083";
        q.howGotTicket = "その他";
        q.howGotTicketOther = "テストデータです by gen0083";
        q.price = "妥当";
        q.afterParty = "普通・なにも感じない";
        q.commentForPrice = "テストデータです by gen0083";
        q.timeTable = "普通・何も感じない";
        q.priority = "セキュリティ, 登壇者 複数項目選択時にはどうなる？";
        q.commentForTimeTable = "テストデータです by gen0083";
        q.sponsorLogo = "気が付かなかった";
        q.sponsorBooth = "立ち寄らなかった";
        q.feelingBooth = "普通・何も感じない";
        q.commentForBooth = "テストデータ by gen0083";
        q.foodAndDrink = "良い";
        q.commentForFoodAndDrink = "テストデータです by gen0083";
        q.goods = "良い";
        q.commentForGoods = "テストデータです by gen0083";
        q.equipment = "良い";
        q.youtube = "だいたい見た";
        q.wantSpeak = "わからない";
        q.nextTime = "わからない";
        return q;
    }
}
