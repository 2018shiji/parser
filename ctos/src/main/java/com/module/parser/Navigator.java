package com.module.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.io.Files;
import com.module.parser.asmxclient.Asmx8HttpClient;
import com.module.parser.asmxclient.ShipmentDownClient;
import com.module.parser.asmxclient.ShipmentUpClient;
import com.module.parser.entity.launch.*;
import com.module.parser.entity.result.*;
import com.module.parser.reflect.FieldReflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class Navigator {
    @Autowired FieldReflect reflect;
    Asmx8HttpClient httpClient = new Asmx8HttpClient();

    /** 船上箱位信息 OTS001008 */
    @ResponseBody
    @RequestMapping("getBoxPositionResultFormat")
    public List<FieldReflect.Format> getBoxResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(BoxResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getBoxPositionResult")
    public String getBoxResult(@RequestBody Box box1){
        Box box = new Box("111", "1", "ticket_id");
        String result1 = JSON.toJSONString(box1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        BoxResult returnResult = null;
        String returnStr0 = httpClient.accessBox(result3);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr.length());
//        String returnStr = CTOSRESULT.OTS001008;
        ResultFile.saveToFile(returnStr);
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(BoxResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (BoxResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String result = JSON.toJSONString(returnResult);
        System.out.println("##############final json result##############" + result);

        return result;
    }

    /** 按箱号检索装船箱指令信息 OP007032 */
    @ResponseBody
    @RequestMapping("getFindBoxOrderResultFormat")
    public List<FieldReflect.Format> getFindBoxOrderResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(FindBoxOrderResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getFindBoxOrderResult")
    public String getFindBoxOrderResult(@RequestBody FindBoxOrder findBoxOrder1){
        FindBoxOrder findBoxOrder = new FindBoxOrder("111", "1", "1", "ticket_id");
        String result1 = JSON.toJSONString(findBoxOrder1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        FindBoxOrderResult returnResult = null;
        String returnStr0 = httpClient.accessFindBoxOrder(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.OP007032;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(FindBoxOrderResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (FindBoxOrderResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 根据箱号查找箱 CM005001 */
    @ResponseBody
    @RequestMapping("getBoxByNumResultFormat")
    public Map<String, List<FieldReflect.Format>> getFindBoxResultFormat(){
        Map<String, List<FieldReflect.Format>> fieldReflect = reflect.getFieldReflect2(FindBoxResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getBoxByNumResult")
    public String getFindBoxResult(@RequestBody FindBox findBox1){
//        FindBox findBox = new FindBox("111","ticket_id");
        System.out.println(findBox1);
        String result1 = JSON.toJSONString(findBox1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        FindBoxResult returnResult = null;
        String returnStr0 = httpClient.accessFindBox(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);
//        String returnStr = CTOSRESULT.CM005001;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(FindBoxResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (FindBoxResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 获取泊位信息 SM003007*/
    @ResponseBody
    @RequestMapping("getBerthMsgResultFormat")
    public List<FieldReflect.Format> getBerthMsgResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(GetBerthMsgResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getBerthMsgResult")
    public String getBerthMsgResult(@RequestBody GetBerthMsg getBerthMsg1){
//        GetBerthMsg getBerthMsg = new GetBerthMsg("ticket_id");
        System.out.println(getBerthMsg1);
        String result1 = JSON.toJSONString(getBerthMsg1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        GetBerthMsgResult returnResult = null;
        String returnStr0 = httpClient.accessGetBerthMsg(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.SM003007;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(GetBerthMsgResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (GetBerthMsgResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 获取承包商信息 SM003006*/
    @ResponseBody
    @RequestMapping("getContractorResultFormat")
    public List<FieldReflect.Format> getContractorResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(GetContractorResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getContractorResult")
    public String getContractorResult(@RequestBody GetContractor getContractor1){
//        GetContractor getContractor = new GetContractor("ticket_id");
        System.out.println(getContractor1);
        String result1 = JSON.toJSONString(getContractor1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        GetContractorResult returnResult = null;
//        String returnStr0 = httpClient.accessGetContractor(result3);
//        System.out.println("-------------+++++++++++++--------------" + returnStr0);
//        String returnStr = getCTOSResultString(returnStr0);
//        System.out.println("------------final output string----------" + returnStr);

        String returnStr = CTOSRESULT.SM003006;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(GetContractorResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (GetContractorResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 获取指定角色下的用户 SM001010*/
    @ResponseBody
    @RequestMapping("getRoleUserResultFormat")
    public List<FieldReflect.Format> getRoleUserResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(GetRoleUserResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getRoleUserResult")
    public String getRoleUserResult(@RequestBody GetRoleUser getRoleUser1){
//        GetRoleUser getRoleUser = new GetRoleUser("userName", "ticket_id");
        String result1 = JSON.toJSONString(getRoleUser1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        GetRoleUserResult returnResult = null;
        String returnStr0 = httpClient.accessGetRoleUser(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.SM001010;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(GetRoleUserResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (GetRoleUserResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 获取指定岸桥下的作业指令 OP004039*/
    @ResponseBody
    @RequestMapping("getWorkOrderListResultFormat")
    public List<FieldReflect.Format> getWorkOrderListResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(GetWorkOrderListResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getWorkOrderListResult")
    public String getWorkOrderListResult(@RequestBody GetWorkOrderList getWorkOrderList1){
//        GetWorkOrderList getRoleUser = new GetWorkOrderList("number", "queryvalue", "ticket_id");
        String result1 = JSON.toJSONString(getWorkOrderList1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        GetWorkOrderListResult returnResult = null;
        String returnStr0 = httpClient.accessGetWorkOrderList(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.OP004039;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(GetWorkOrderListResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (GetWorkOrderListResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /**无线终端登录 */
    @ResponseBody
    @RequestMapping("getLoginResultFormat")
    public List<FieldReflect.Format> getLoginResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(LoginResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getLoginResult")
    public String getLoginResult(@RequestBody Login login1){
//        Login login = new Login("userName", "password", 123, "clientIp");
        String result1 = JSON.toJSONString(login1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        LoginResult returnResult = null;
//        String returnStr0 = httpClient.accessLogin(result3);
//        System.out.println("-------------+++++++++++++--------------" + returnStr0);
//        String returnStr = getCTOSResultString(returnStr0);
//        System.out.println("------------final output string----------" + returnStr);

        String returnStr = CTOSRESULT.OP007001;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(LoginResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (LoginResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 无线终端注销 */
    @ResponseBody
    @RequestMapping("getLogoutResultFormat")
    public List<FieldReflect.Format> getLogoutResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(LogoutResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getLogoutResult")
    public String getLogoutResult(@RequestBody Logout logout1){
//        Logout logout = new Logout("userId",123, "clientIp");
        String result1 = JSON.toJSONString(logout1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        LogoutResult returnResult = null;
        String returnStr0 = httpClient.accessLogout(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.OP007002;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(LogoutResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (LogoutResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 根据子箱查询主箱信息 */
    @ResponseBody
    @RequestMapping("getQueryMainBoxResultFormat")
    public List<FieldReflect.Format> getQueryMainBoxResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(QueryMainBoxResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getQueryMainBoxResult")
    public String getQueryMainBoxResult(@RequestBody QueryMainBox queryMainBox1){
//        QueryMainBox queryMainBox = new QueryMainBox("containerNo", "ticket_id");
        System.out.println(queryMainBox1);
        String result1 = JSON.toJSONString(queryMainBox1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        QueryMainBoxResult returnResult = null;
        String returnStr0 = httpClient.accessQueryMainBox(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.CM005009;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(QueryMainBoxResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (QueryMainBoxResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }


    @ResponseBody
    @RequestMapping("getQueryShipUpResultFormat")
    public List<FieldReflect.Format> getQueryShipUpResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(QueryShipUpResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getQueryShipUpResult")
    public String getQueryShipUpResult(@RequestBody QueryShipUp queryShipUp1){
//        QueryShipUp queryShipUp = new QueryShipUp("1", "1", "ticketId");
        String result1 = JSON.toJSONString(queryShipUp1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        QueryShipUpResult returnResult = null;
        String returnStr0 = httpClient.accessQueryShipUp(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.OP004042;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(QueryShipUpResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (QueryShipUpResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 注册 */
    @ResponseBody
    @RequestMapping("getRegisterResultFormat")
    public List<FieldReflect.Format> getRegisterResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(RegisterResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getRegisterResult")
    public String getRegisterResult(@RequestBody Register register1){
        System.out.println("+++++++++" + register1);
//        Register register = new Register("DYW", "123456", "1");
        String result1 = JSON.toJSONString(register1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        RegisterResult returnResult = null;
//        String returnStr0 = httpClient.accessRegister(result3);
//        System.out.println("-------------+++++++++++++" + returnStr0);
//        String returnStr = getCTOSResultString(returnStr0);
//        System.out.println(returnStr);
        String returnStr = CTOSRESULT.SM001001;
        try {
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(RegisterResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (RegisterResult) unmarshaller.unmarshal(reader);

        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 注销 */
    @ResponseBody
    @RequestMapping("getRegisterOutResultFormat")
    public List<FieldReflect.Format> getRegisterOutResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(RegisterOutResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getRegisterOutResult")
    public String getRegisterOutResult(@RequestBody RegisterOut registerOut1){
//        RegisterOut registerOut = new RegisterOut("DYW", "ticketId");
        String result1 = JSON.toJSONString(registerOut1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        RegisterOutResult returnResult = null;
        String returnStr0 = httpClient.accessRegisterOut(result3);
        System.out.println("-------------+++++++++++++" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println(returnStr);

//        String returnStr = CTOSRESULT.SM001002;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(RegisterOutResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (RegisterOutResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(returnResult);
    }

    /** 确认卸船 */
    @ResponseBody
    @RequestMapping("getShipmentDownRegResultFormat")
    public List<FieldReflect.Format> getShipmentDownRegResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(ShipmentDownRegResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getShipmentDownRegResult")
    public String getShipmentDownRegResult(@RequestBody ShipmentDownReg shipmentDownReg1){
        ShipmentDownReg shipmentDownReg = new ShipmentDownReg();
        String result1 = JSON.toJSONString(shipmentDownReg1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");

        ShipmentDownRegResult returnResult = null;
//        String returnStr = httpClient.accessShipmentDownReg(result3);
        String returnStr = CTOSRESULT.OP007037;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(ShipmentDownRegResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (ShipmentDownRegResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(returnResult);
    }

    /**装卸船作业初始化 OP007030*/
    @ResponseBody
    @RequestMapping("getShipmentTaskInitResultFormat")
    public List<FieldReflect.Format> getShipmentTaskInitResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(ShipmentTaskInitResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getShipmentTaskInitResult")
    public String getShipmentTaskInitResult(@RequestBody ShipmentTaskInit shipmentTaskInit1){
        ShipmentTaskInit shipmentTaskInit = new ShipmentTaskInit();
        String result1 = JSON.toJSONString(shipmentTaskInit1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");

        ShipmentTaskInitResult returnResult = null;
//        String returnStr = httpClient.accessShipmentTaskInit(result3);
        String returnStr = CTOSRESULT.OP007030;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(ShipmentTaskInitResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (ShipmentTaskInitResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(returnResult);
    }

    /**装卸船作业退出 */
    @ResponseBody
    @RequestMapping("getShipmentTaskQuitResultFormat")
    public List<FieldReflect.Format> getShipmentTaskQuitResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(ShipmentTaskQuitResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getShipmentTaskQuitResult")
    public String getShipmentTaskQuitResult(@RequestBody ShipmentTaskQuit shipmentTaskQuit1){
        ShipmentTaskQuit shipmentTaskQuit = new ShipmentTaskQuit();
        String result1 = JSON.toJSONString(shipmentTaskQuit1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");

        ShipmentTaskQuitResult returnResult = null;
//        String returnStr = httpClient.accessShipmentTaskQuit(result3);
        String returnStr = CTOSRESULT.OP007006;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(ShipmentTaskQuitResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (ShipmentTaskQuitResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(returnResult);
    }

    @ResponseBody
    @RequestMapping("getShipmentUpRegResultFormat")
    public List<FieldReflect.Format> getShipmentUpRegResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(ShipmentUpRegResult.class);
        return fieldReflect;
    }

    /** 确认卸船 */
    @ResponseBody
    @RequestMapping("getShipmentUpRegResult")
    public String getShipmentUpRegResult(@RequestBody ShipmentUpReg shipmentUpReg1){
//        ShipmentUpReg shipmentUpReg = new ShipmentUpReg("1", "1", "1", "1", "1", "1", "1", "DYW", "1", "1", "1");
        String result1 = JSON.toJSONString(shipmentUpReg1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");

        ShipmentUpClient shipmentUpClient = new ShipmentUpClient();
        ShipmentUpClient.ShipmentUpRegClient shipmentUpRegClient = shipmentUpClient.new ShipmentUpRegClient();

        ShipmentUpRegResult returnResult = null;
//        String returnStr = shipmentUpRegClient.accessShipmentUpReg(result3);
        String returnStr = CTOSRESULT.OP007036;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(ShipmentUpRegResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (ShipmentUpRegResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(returnResult);

    }

    /** 指定船期岸桥下的作业量统计 OP004040 */
    @ResponseBody
    @RequestMapping("getShipWorkLoadResultFormat")
    public List<FieldReflect.Format> getShipWorkLoadResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(ShipWorkLoadResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getShipWorkLoadResult")
    public String getShipWorkLoadResult(@RequestBody ShipWorkLoad shipWorkLoad1){
//        ShipWorkLoad shipmentUpReg = new ShipWorkLoad("velAliase", "ticket_id");
        String result1 = JSON.toJSONString(shipWorkLoad1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        ShipWorkLoadResult returnResult = null;
        String returnStr0 = httpClient.accessShipWorkLoad(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.OP004040;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(ShipWorkLoadResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (ShipWorkLoadResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return JSON.toJSONString(returnResult);
    }

    /** 卸船指令清单*/
    @ResponseBody
    @RequestMapping("getUnloadOrderListResultFormat")
    public List<FieldReflect.Format> getUnloadOrderListResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(UnloadOrderListResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getUnloadOrderListResult")
    public String getUnloadOrderListResult(@RequestBody UnloadOrderList unloadOrderList1){
//        UnloadOrderList unloadOrderList = new UnloadOrderList("velAliase", "number", "ticket_id");
        String result1 = JSON.toJSONString(unloadOrderList1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        UnloadOrderListResult returnResult = null;
        String returnStr0 = httpClient.accessUnloadOrderList(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);

//        String returnStr = CTOSRESULT.OP004041;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(UnloadOrderListResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (UnloadOrderListResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 根据箱号索卸船箱指令信息 */
    @ResponseBody
    @RequestMapping("getUnloadOrderResultFormat")
    public List<FieldReflect.Format> getUnloadOrderResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(UnloadOrderResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getUnloadOrderResult")
    public String getUnloadOrderResult(@RequestBody UnloadOrder unloadOrder1){
//        UnloadOrder unloadOrder = new UnloadOrder("velAliase", "number", "ticket_id");
        String result1 = JSON.toJSONString(unloadOrder1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);

        UnloadOrderResult returnResult = null;
        String returnStr0 = httpClient.accessUnloadOrder(result3);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr.length());
//        String returnStr = CTOSRESULT.OP007031;
        ResultFile.saveToFile(returnStr);
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(UnloadOrderResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (UnloadOrderResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println("##############final json result##############" + jsonString);
        return jsonString;

    }

    /** 更新船上箱残损信息 */
    @ResponseBody
    @RequestMapping("getUpdateDamageBoxResultFormat")
    public List<FieldReflect.Format> getUpdateDamageBoxResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(UpdateDamageBoxResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getUpdateDamageBoxResult")
    public String getUpdateDamageBoxResult(@RequestBody UpdateDamageBox updateDamageBox1){
        UpdateDamageBox updateDamageBox = new UpdateDamageBox("containerId", "tableParams", "ticket_id", "damageCode", "damageEmemo");
        String result1 = JSON.toJSONString(updateDamageBox, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");

        UpdateDamageBoxResult returnResult = null;
//        String returnStr = httpClient.accessUpdateDamageBox(result3);
        String returnStr = CTOSRESULT.OP007095;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(UpdateDamageBoxResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (UpdateDamageBoxResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(returnResult);

    }

    /** 查询激活的船期 */
    @ResponseBody
    @RequestMapping("getVesselScheduleResultFormat")
    public List<FieldReflect.Format> getVesselScheduleResultFormat(){
        List<FieldReflect.Format> fieldReflect = reflect.getFieldReflect(VesselScheduleResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getVesselScheduleResult")
    public String getVesselScheduleResult(@RequestBody VesselSchedule vesselSchedule1){
//        VesselSchedule vesselSchedule = new VesselSchedule("ticketId");
        System.out.println(vesselSchedule1);
        String result1 = JSON.toJSONString(vesselSchedule1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);
        
        VesselScheduleResult returnResult = null;
        String returnStr0 = httpClient.accessVesselSchedule(result3);
        System.out.println("-------------+++++++++++++--------------" + returnStr0);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr);
//        String returnStr = CTOSRESULT.VM002001;
        try{
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(VesselScheduleResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (VesselScheduleResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = JSON.toJSONString(returnResult);
        System.out.println(jsonString);
        return jsonString;
    }

    /** 船结构 OTS001006 */
    @ResponseBody
    @RequestMapping("getVesselStructResultFormat")
    public Map<String, List<FieldReflect.Format>> getVesselStructResultFormat(){
        Map<String, List<FieldReflect.Format>> fieldReflect = reflect.getFieldReflect2(VesselStructResult.class);
        return fieldReflect;
    }

    @ResponseBody
    @RequestMapping("getVesselStructResult")
    public String getVesselStructResult(@RequestBody VesselStruct vesselStruct1){
//        VesselStruct vesselStruct = new VesselStruct("velAliase","ticketId");
        String result1 = JSON.toJSONString(vesselStruct1, SerializerFeature.PrettyFormat, SerializerFeature.UseSingleQuotes);
        String result2 = result1.replaceAll("\'(\\w+)\'(\\s*:\\s*)", "$1$2");
        String result3 = result2.substring(1, result2.lastIndexOf("}")).replaceAll("\\s*", "");
        System.out.println("------------final input string----------" + result3);


        VesselStructResult returnResult = null;
        String returnStr0 = httpClient.accessVesselStruct(result3);
        String returnStr = getCTOSResultString(returnStr0);
        System.out.println("------------final output string----------" + returnStr.length());
//        ResultFile.saveToFile(returnStr);
        try{
//            File file = new File("E:\\result\\vesselStruct.txt");
//            String returnStr = Files.asCharSource(file, StandardCharsets.UTF_8).read();
            StringReader reader = new StringReader(returnStr);
            JAXBContext context = JAXBContext.newInstance(VesselStructResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            returnResult = (VesselStructResult) unmarshaller.unmarshal(reader);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("##############final json result##############" + JSON.toJSONString(returnResult));
        return JSON.toJSONString(returnResult);
    }

    private String getCTOSResultString(String xmlString){
        String regex = "<CTOSRESULT>(.*?)</CTOSRESULT>";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(xmlString);
        String result = null;
        if(matcher.find()){
            result = matcher.group(1);
        }
        result = "<CTOSRESULT>" + result + "</CTOSRESULT>";
        return result;
    }

}
