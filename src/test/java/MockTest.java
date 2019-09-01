import com.alibaba.fastjson.JSONObject;
import com.mockst.mockjava.Mock;
import com.mockst.mockjava.MockHelper;
import com.mockst.mockjava.MockTemplate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockTest {

    @Test
    public void name() {
        System.out.println(Mock.clast());
        //姓
        System.out.println(Mock.cfirst());
        //名
        System.out.println(Mock.cname());
        System.out.println(Mock.last());
        System.out.println(Mock.first());
        System.out.println(Mock.name());
        //uuid
        System.out.println(Mock.uuid());
        //生成日期
        System.out.println(Mock.date("yyyy-MM-dd"));
        //生成身份证号
        System.out.println(Mock.id());
        //随机字符串
        System.out.println(Mock.string("alpha",10));
        //随机大写符串
        System.out.println(Mock.string("upper",10));
        //随机省会
        System.out.println(Mock.province());
        //随机城市
        System.out.println(Mock.city(true));
        //随机县城
        System.out.println(Mock.county(true));
        //随机邮箱
        System.out.println(Mock.email());
        //随机正则
        System.out.println(Mock.regExp("\\d{6,10}"));
    }

    @Test
    public void templatesTest(){
        MockTemplate code = new MockTemplate();
        code.setId("1");
        code.setName("code");
        code.setValue("200");
        code.setType("String");

        MockTemplate data = new MockTemplate();
        data.setId("2");
        data.setName("data");
        data.setValue("");
        data.setType("Object");

        MockTemplate list = new MockTemplate();
        list.setId("3");
        list.setParentId("2");
        list.setName("list");
        //list.setRule(list.getName()+"|"+"1-10");
        list.setValue("5");
        list.setType("Array");


        MockTemplate agreementCode = new MockTemplate();
        agreementCode.setId("5");
        agreementCode.setParentId("3");
        agreementCode.setName("agreementCode");
        agreementCode.setValue("HT2134");
        agreementCode.setType("String");

        MockTemplate agreementName = new MockTemplate();
        agreementName.setId("6");
        agreementName.setParentId("3");
        agreementName.setName("agreementName");
        agreementName.setValue("@string(\"alpha\",5)");
        agreementName.setType("String");

        MockTemplate id = new MockTemplate();
        id.setId("8");
        id.setParentId("3");
        id.setName("id");
        id.setValue("@id");
        id.setType("String");

        MockTemplate cname = new MockTemplate();
        cname.setId("11");
        cname.setParentId("3");
        cname.setName("name");
        cname.setValue("@cname");
        cname.setType("String");

        MockTemplate totalCount = new MockTemplate();
        totalCount.setId("7");
        totalCount.setParentId("2");
        totalCount.setName("totalCount");
        totalCount.setValue("100");
        totalCount.setType("Number");

        MockTemplate objectMock = new MockTemplate();
        objectMock.setId("9");
        objectMock.setParentId("3");
        objectMock.setName("agreement");
        objectMock.setType("Object");

        MockTemplate objectDate = new MockTemplate();
        objectDate.setId("10");
        objectDate.setParentId("9");
        objectDate.setType("String");
        objectDate.setName("createdTime");
        objectDate.setValue("@date(\"yyyy-MM-dd\")");

        MockTemplate age = new MockTemplate();
        age.setId("11");
        age.setParentId("9");
        age.setType("Number");
        age.setName("age");
        age.setValue("@natural(2,25)");

        MockTemplate money = new MockTemplate();
        money.setId("12");
        money.setParentId("9");
        money.setType("Number");
        money.setName("money");
        money.setValue("@Float(1,2)");

        MockTemplate rex = new MockTemplate();
        rex.setId("13");
        rex.setParentId("9");
        rex.setType("String");
        rex.setName("rex");
        rex.setValue("@regExp(\"\\d{5,10}\")");

        List<MockTemplate> templates = new ArrayList<>();
        templates.add(code);
        templates.add(data);
        templates.add(list);
        templates.add(agreementCode);
        templates.add(agreementName);
        templates.add(totalCount);
        templates.add(id);
        templates.add(objectMock);
        templates.add(objectDate);
        templates.add(cname);
        templates.add(age);
        templates.add(money);
        templates.add(rex);
        JSONObject object = MockHelper.buildJson(templates);
        System.out.println("json:"+object.toJSONString());
    }

    @Test
    public void testRexp(){
        String content = "\"asasd\",1123";
        String regex = "(((\"[^\"]*\")|[^\",])*)(,|$)";
        Matcher matcher = Pattern.compile(regex).matcher(content);
        int count = matcher.groupCount();
        System.out.println("count："+count);
        while(matcher.find()){
            System.out.println("a:"+matcher.group(1));
        }
    }

    @Test
    public void test(){
        String.valueOf(null);
    }

}