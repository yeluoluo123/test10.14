package linux7;

import java.net.URLEncoder;
import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;

public class Test {

	public static void main(String[] args) throws Exception{
		Long timestamp = System.currentTimeMillis();
        String secret = "SECe52308a848e1f5d084d53675a6f4d48f59a3d1b72513c251922e846a71c75a82";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign);
        
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=77d43379c6106fefc7732b0b45c8a2392d5972a9e80c7f86d7fc6190e1c29dac"
				+"&timestamp="+timestamp+"&sign="+sign);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("text");
		OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
		text.setContent("Hello World!");
		request.setText(text);
		OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
		at.setIsAtAll(true);
		request.setAt(at);
		OapiRobotSendResponse response = client.execute(request);

}
}