package com.houxy.days.common;

import com.houxy.days.modules.welfare.bean.MeiZhi;
import com.houxy.days.modules.welfare.bean.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Houxy on 2016/9/22.
 */

public interface ApiInterface {

    String HOST = "http://gank.io/api/";

    @GET("data/福利/10/{page}")
    Observable<Result<List<MeiZhi>>> getMeiZhi(@Path("page") int page);

    //获取妹子图片的总条数
    @GET("day/history")
    Observable<Result<List<String>>> getWelfareCount();

}
