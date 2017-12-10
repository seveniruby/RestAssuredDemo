import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class ProxyTest {
    @Test
    public void testProxy(){
        given().log().all().proxy("127.0.0.1", 8080).get("http://www.baidu.com/s?wd=mp3").then().log().all();
    }

    @Test
    public void testJson(){
        //curl -i -s -k  -X 'GET' \
        //    -H 'User-Agent: Xueqiu Android 9.23' \
        //    -b 'xq_a_token=e22bfe7637d05c94d5dae24f91e800bffd22aa6c; u=7041783755' \
        //    'https://stock.xueqiu.com/v4/stock/quote.json?_t=1GENYMOTION0325b24d972b9a5d85870d95fcb5bbb4.7041783755.1512884713658.1512885933098&_s=368bc1&return_hasexist=1&isdelay=0&x=0.574&code=OC899002'
        given().log().all()
                .header("User-Agent", "Xueqiu Android 9.23'")
                .header("Cookie", "xq_a_token=e22bfe7637d05c94d5dae24f91e800bffd22aa6c; u=7041783755")
                .param("_t", "1GENYMOTION0325b24d972b9a5d85870d95fcb5bbb4.7041783755.1512884713658.1512885933098")
                .param("code", "OC899002")
                .get("https://stock.xueqiu.com/v4/stock/quote.json")
                .then().log().all()
                .body("OC899002.name", equalTo("三板做市"))
                .body("OC899002.symbol", res-> equalTo("OC"+res.path("OC899002.code")));
    }
}
