package com.module.parser.wsdlclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.module.parser.entity.request.Login;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class Wsdl8CXF {
    private static String USER_NAME = "admin";
    private static String PASS_WORD = "123456";

    public static void main(String[] args){
        client2();
    }

    public static void client2(){
        //创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl");
        Client client = dcf.createClient("http://127.0.0.1:8083/CTOS/CTOSResult?wsdl");
        //需要密码的情况下，应该添加以下代码
//        client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));

        testDoLogin(client);
//        testDoLoginResultReal(client);
    }

    private static void testDoLogin(Client client){
        try{
            Login login = new Login("DYW", "222", "123", "333", "666");
            String loginJsonStr = JSON.toJSONString(login);
            Object[] objects = client.invoke("doLogin", loginJsonStr);
            System.out.println("返回数据：" + objects[0]);
            Login login1 = JSONObject.parseObject((String) objects[0], Login.class);
            System.out.println(login1);
        } catch (Exception e){e.printStackTrace();}
    }

    private static void testDoLoginResultReal(Client client){
        try{
            Login login = new Login("1","2","3","4","5");
            String loginJsonStr = JSON.toJSONString(login);
            Object[] objects = client.invoke("doLoginResultReal", login);
            System.out.println("返回数据：" + objects[0]);

        } catch (Exception e){e.printStackTrace();}
    }


}
