package com.mockst.mockjava;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @describe:
 * @author: linzhiwei
 * @date: 2018/11/15 13:38
 */
public class MockHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(MockHelper.class);

    //整型
    public static final Pattern INT_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");
    //浮点型
    public static final Pattern DOUBLE_PATTERN = Pattern.compile("^[-\\+]?[.\\d]*$");
    //函数
    public static final Pattern RE_PLACEHOLDER_PATTERN  = Pattern.compile(MockRegex.RE_PLACEHOLDER);
    //键值
    public static final Pattern RE_KE_PATTERN  = Pattern.compile(MockRegex.RE_KEY);
    //区间
    public static final Pattern RE_RANGE_PATTERN = Pattern.compile(MockRegex.RE_RANGE);
    //参数解析
    public static final Pattern RE_METHOD_ARGS_PATTERN = Pattern.compile(MockRegex.RE_METHOD_ARGS);

    /**
     * 除去字符串头尾特定的字符
     * @param str
     * @param c
     * @return
     */
    public static String trimChar(String str,char c){
        char[] chars = str.toCharArray();
        int len = chars.length;
        int st = 0;
        while ( (st < len) && (chars[st] == c) ){
            st ++;
        }
        while ( (st < len) && (chars[len-1] == c) ){
            len --;
        }
        return (st >0) && (len<chars.length)? str.substring(st, len): str;
    }

    /**
     * 根据模板生成json对象
     * @param templates
     * @return
     */
    public static JSONObject buildJson(List<MockTemplate> templates){
        JSONObject jsonObject = new JSONObject();
        //获取顶级节点
        List<MockTemplate> root =
                templates.stream().filter(m->StringUtils.isBlank(m.getParentId())).collect(Collectors.toList());
        for (MockTemplate template:root){
            buildJson(jsonObject,template,templates);
        }
        return jsonObject;
    }

    /**
     * 代理函数执行
     * @param value
     * @return
     */
    private static Object invoke(String value){

        Object result = null;

        if (StringUtils.isEmpty(value)){
            return result;
        }

        Matcher m = RE_PLACEHOLDER_PATTERN.matcher(value);
        if (m.find()){
            //获取方法名
            String methodName = m.group(1);
            Method method ;
            String methodArgs = m.group(2);
            if (methodArgs!=null){
                //获取参数
                Matcher matcher = RE_METHOD_ARGS_PATTERN.matcher(methodArgs);
                List<Class> argTypes = new ArrayList<>();
                List<Object> argObjects = new ArrayList<>();
                while (matcher.find()){
                    String arg = matcher.group(1);
                    if (!arg.equals("")){
                        //判断是否为数字
                        if (INT_PATTERN.matcher(arg).matches()||DOUBLE_PATTERN.matcher(arg).matches()){
                            argTypes.add(int.class);
                            argObjects.add(new BigDecimal(arg).intValue());
                        }else {
                            argTypes.add(String.class);
                            //两个长度以上
                            if (arg.length()>=2
                                    && (arg.charAt(0)==arg.charAt(arg.length()-1))
                                    && (arg.charAt(0)=='"'||arg.charAt(0)=='\'')){
                                char trimChar = arg.charAt(0);
                                //去除参数字符串头尾的"",''
                                argObjects.add(trimChar(arg,trimChar));
                            }else {
                                argObjects.add("");
                            }
                        }
                    }
                }
                Class[] argTypeArray = new Class[argTypes.size()];
                argTypes.toArray(argTypeArray);
                Object[] argObjectArray = new Object[argObjects.size()];
                argObjects.toArray(argObjectArray);
                LOGGER.debug("methodName:{} args:{}",methodName,Arrays.toString(argObjectArray));
                try {
                    method = Mock.class.getMethod(methodName,argTypeArray);
                    result = method.invoke(null,argObjectArray);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }else {
                try {
                    method = Mock.class.getMethod(methodName);
                    result = method.invoke(null);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 构建模拟数据
     * @param parent 父级数据引用
     * @param mockTemplate 当前目标模板
     * @param templates
     */
    private static void buildJson(JSONObject parent, MockTemplate mockTemplate, List<MockTemplate> templates){
        String name = mockTemplate.getName();
        String type = mockTemplate.getType();
        String rule = mockTemplate.getRule();
        String value = mockTemplate.getValue();
        if ("String".equals(type)){
            Object result = null;
            if (StringUtils.isNotEmpty(value)){
                result = invoke(value);
            }
            if (result==null){
                result = value;
            }
            //转换成字符串型
            parent.put(name,String.valueOf(result));
        }else if ("Number".equals(type)){
            Object tmp = invoke(value);
            if (tmp != null) {
                parent.put(name, tmp);
            } else if (INT_PATTERN.matcher(value).matches()) {
                parent.put(name, Integer.parseInt(value));
            } else if (DOUBLE_PATTERN.matcher(value).matches()) {
                parent.put(name, Double.parseDouble(value));
            } else {
                parent.put(name, value);
            }
        }else if ("Object".equals(type)){
            List<MockTemplate> tem = templates.stream().filter(
                    m-> m.getParentId()!=null&&m.getParentId().equals(mockTemplate.getId()))
                    .collect(Collectors.toList());
            JSONObject object = new JSONObject();
            for (MockTemplate m:tem){
                buildJson(object,m,templates);
            }
            parent.put(name,object);
        }else if ("Array".equals(type)){
            List<MockTemplate> tem =
                    templates.stream().filter(m-> m.getParentId()!=null&&m.getParentId().equals(mockTemplate.getId()))
                            .collect(Collectors.toList());
            int min = 1;
            int max = 1;
            if (rule!=null&&!rule.isEmpty()){
                Matcher m = RE_KE_PATTERN.matcher(rule);
                if(m.find()) {
                    Matcher mm = RE_RANGE_PATTERN.matcher(m.group());
                    if (mm.find()){
                        //获取最小值
                        min = Integer.parseInt(mm.group(1));
                        //获取最大值
                        max = Integer.parseInt(mm.group(2));
                    }
                }
            }else if(StringUtils.isNotBlank(value)) {
                if(INT_PATTERN.matcher(value).matches()){
                    min = Integer.parseInt(value);
                    max = Integer.parseInt(value);
                }
            }
            int count = Mock.natural(min,max);
            JSONArray jsonArray = new JSONArray(count);
            for (int i=0;i <count;i++){
                JSONObject object = new JSONObject();
                for (MockTemplate m:tem){
                    buildJson(object,m,templates);
                }
                jsonArray.add(object);
            }
            parent.put(name,jsonArray);
        }else {
            parent.put(name,value);
        }
    }
}
