import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;
import static io.restassured.path.xml.XmlPath.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FilterTest {
    @BeforeClass
    public  static void beforeAll(){

        filters( (req, res, ctx)->{

            Response resOrigin= ctx.next(req, res);
            System.out.println("response origin");
            System.out.println(resOrigin.getBody().asString());
            ResponseBuilder builder=new ResponseBuilder().clone(resOrigin);

            //String raw=base64decode(resOrigin.getBody().asString())
            Response resNew=builder.setBody("{\"OC899002\":{\"symbol\":\"OC899002\",\"exchange\":\"OC\",\"code\":\"899002\",\"name\":\"四板做市\",\"current\":\"995.39\",\"percentage\":\"0.12\",\"change\":\"1.17\",\"open\":\"994.22\",\"high\":\"995.39\",\"low\":\"993.85\",\"close\":\"995.39\",\"last_close\":\"994.22\",\"high52week\":\"1195.43\",\"low52week\":\"991.46\",\"volume\":\"5.4824395E7\",\"lot_volume\":\"5.4824395E7\",\"volumeAverage\":\"0\",\"marketCapital\":\"0.0\",\"eps\":\"0.0\",\"pe_ttm\":\"\",\"pe_lyr\":\"\",\"beta\":\"0.0\",\"totalShares\":\"0\",\"time\":\"Fri Dec 08 15:36:31 +0800 2017\",\"afterHours\":\"0.0\",\"afterHoursPct\":\"0.0\",\"afterHoursChg\":\"0.0\",\"updateAt\":\"1481093330005\",\"dividend\":\"\",\"yield\":\"0.0\",\"turnover_rate\":\"\",\"instOwn\":\"0.0\",\"rise_stop\":\"0.0\",\"fall_stop\":\"0.0\",\"currency_unit\":\"CNY\",\"amount\":\"4.15807429E8\",\"net_assets\":\"0.0\",\"hasexist\":\"\",\"has_warrant\":\"0\",\"type\":\"60\",\"flag\":\"1\",\"rest_day\":\"\",\"amplitude\":\"0.16%\",\"market_status\":\"\",\"lot_size\":\"0\",\"min_order_quantity\":\"0\",\"max_order_quantity\":\"0\",\"tick_size\":\"0.01\",\"variable_tick_size\":\"\",\"kzz_stock_symbol\":\"\",\"kzz_stock_name\":\"\",\"kzz_stock_current\":\"0.0\",\"kzz_convert_price\":\"0.0\",\"kzz_covert_value\":\"0.0\",\"kzz_cpr\":\"0.0\",\"kzz_putback_price\":\"0.0\",\"kzz_convert_time\":\"\",\"kzz_redempt_price\":\"0.0\",\"kzz_straight_price\":\"0.0\",\"kzz_stock_percent\":\"\",\"pb\":\"0.0\",\"benefit_before_tax\":\"0.0\",\"benefit_after_tax\":\"0.0\",\"convert_bond_ratio\":\"\",\"totalissuescale\":\"\",\"outstandingamt\":\"\",\"maturitydate\":\"\",\"remain_year\":\"\",\"convertrate\":\"\",\"interestrtmemo\":\"\",\"release_date\":\"\",\"circulation\":\"0.0\",\"par_value\":\"0.0\",\"due_time\":\"0.0\",\"value_date\":\"\",\"due_date\":\"\",\"publisher\":\"\",\"redeem_type\":\"\",\"issue_type\":\"\",\"bond_type\":\"\",\"warrant\":\"\",\"sale_rrg\":\"\",\"rate\":\"\",\"after_hour_vol\":\"0\",\"float_shares\":\"0\",\"float_market_capital\":\"0.0\",\"disnext_pay_date\":\"\",\"convert_rate\":\"\",\"volume_ratio\":\"0.0\",\"percent5m\":\"\",\"pankou_ratio\":\"0.0%\"}}\n").build();
            return resNew;
        });

    }
    @Test
    public void testBaidu(){

        given().log().all().get("http://www.baidu.com/s?wd=mp3").then().log().all();

    }
    @Test
    public void testFilter(){
        given().log().all().filter( (req, res, ctx)->{
            Response resOrigin= ctx.next(req, res);
            System.out.println("response origin");
            System.out.println(resOrigin.getBody().asString());

            ResponseBuilder builder=new ResponseBuilder().clone(resOrigin);
            Response resNew=builder.setBody("filter").build();
            return resNew;
        }).get("http://www.baidu.com/s?wd=mp3").then().log().all();
    }

    @Test
    public void xueqiu(){
        given().log().all().header("User-Agent", "Xueqiu Android 9.23'")
                .header("Cookie", "xq_a_token=e22bfe7637d05c94d5dae24f91e800bffd22aa6c; u=7041783755")
                .param("_t", "1GENYMOTION0325b24d972b9a5d85870d95fcb5bbb4.7041783755.1512884713658.1512885933098")
                .param("code", "OC899002")
                .get("https://stock.xueqiu.com/v4/stock/quote.json")
                .then().log().all()
                .body("OC899002.name", equalTo("四板做市"))
                .body("OC899002.symbol", res-> equalTo("OC"+res.path("OC899002.code")))
                .time(lessThan(200L));
    }
}
