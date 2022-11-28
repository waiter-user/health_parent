package com.java.mobile;

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;
import com.java.util.ValidateCodeUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MobileTest {
    public static String ACCESS_KEY_ID = "RkFo7wGjVkRBM36XA5DFpzHFkLJ5g8mVPqHqgUTHgoVCGCaY1";
    private static String ACCESS_KEY_SECRET = "your access key secret";
    @Test
    public void test1() {
        // 初始化
        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET); // 若使用简易验签模式仅传入第一个参数即可

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        Integer i = ValidateCodeUtil.generateValidateCode(6);
        templateData.put("code", String.valueOf(i));
        templateData.put("ttl","3");

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo("17282382081")
                .setSignature("软帝信息")
                .setTemplateId("pub_verif_ttl3")
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
    }
}
