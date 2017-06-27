import java.io.UnsupportedEncodingException;

public class Test {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String aliPayPreURL = "alipays://platformapi/startApp?appId=10000011&&app_pay=Y&&actionType=WebView&&url=";
		String aliPayURL = "http://smartpay-server.91link.com/smartpay-api/api/scanpay/authRequest"
		+"?merchId=8168002750&amount=1&merchOrderId=70641499164&merchName=上海奥派数据科技有限公司";
		String realAliPayURL = aliPayPreURL + java.net.URLEncoder.encode(aliPayURL, "utf-8");
		System.out.println(realAliPayURL);
	}
	
}
