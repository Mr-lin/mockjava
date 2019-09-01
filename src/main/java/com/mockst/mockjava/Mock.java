package com.mockst.mockjava;

import com.mifmif.common.regex.Generex;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @describe:
 * @author: linzhiwei
 * @date: 2018/11/9 8:56
 */
public class Mock {


    /**-------------------name-----------------------------**/

    /**
     * 随机生成一个英文常见名
     *
     * @return
     */
    public static String first() {
        String[] maleName = {
                "James", "John", "Robert", "Michael", "William",
                "David", "Richard", "Charles", "Joseph", "Thomas",
                "Christopher", "Daniel", "Paul", "Mark", "Donald",
                "George", "Kenneth", "Steven", "Edward", "Brian",
                "Ronald", "Anthony", "Kevin", "Jason", "Matthew",
                "Gary", "Timothy", "Jose", "Larry", "Jeffrey",
                "Frank", "Scott", "Eric"
        };
        String[] femaleName = {
                "Mary", "Patricia", "Linda", "Barbara", "Elizabeth",
                "Jennifer", "Maria", "Susan", "Margaret", "Dorothy",
                "Lisa", "Nancy", "Karen", "Betty", "Helen",
                "Sandra", "Donna", "Carol", "Ruth", "Sharon",
                "Michelle", "Laura", "Sarah", "Kimberly", "Deborah",
                "Jessica", "Shirley", "Cynthia", "Angela", "Melissa",
                "Brenda", "Amy", "Anna"
        };
        String[] names = new String[maleName.length + femaleName.length];
        System.arraycopy(maleName, 0, names, 0, maleName.length);
        System.arraycopy(femaleName, 0, names, maleName.length, femaleName.length);
        return pick(names);
    }

    /**
     * 随机生成一个英文姓
     * @return
     */
    public static String last(){
        String[] names = {
            "Smith", "Johnson", "Williams", "Brown", "Jones",
            "Miller", "Davis", "Garcia", "Rodriguez", "Wilson",
            "Martinez", "Anderson", "Taylor", "Thomas", "Hernandez",
            "Moore", "Martin", "Jackson", "Thompson", "White",
            "Lopez", "Lee", "Gonzalez", "Harris", "Clark",
            "Lewis", "Robinson", "Walker", "Perez", "Hall",
            "Young", "Allen"
        };
        return pick(names);
    }


    /**
     * 随机生成一个常见的中文姓
     *
     * @return
     */
    public static String cfirst() {
        String[] names ={
                "王", "李", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴",
                "徐", "孙", "胡", "朱", "高", "林", "何", "郭", "马", "罗",
                "梁", "宋", "郑", "谢", "韩", "唐", "冯", "于", "董", "萧",
                "程", "曹", "袁", "邓", "许", "傅", "沈", "曾", "彭", "吕",
                "苏", "卢", "蒋", "蔡", "贾", "丁", "魏", "薛", "叶", "阎",
                "余", "潘", "杜", "戴", "夏", "锺", "汪", "田", "任", "姜",
                "范", "方", "石", "姚", "谭", "廖", "邹", "熊", "金", "陆",
                "郝", "孔", "白", "崔", "康", "毛", "邱", "秦", "江", "史",
                "顾", "侯", "邵", "孟", "龙", "万", "段", "雷", "钱", "汤",
                "尹", "黎", "易", "常", "武", "乔", "贺", "赖", "龚", "文"
        };
        return pick(names);
    }

    /**
     * 随机生成一个常见的中文名
     *
     * @return
     */
    public static String clast() {
        String[] names ={
                "伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军",
                "洋", "勇", "艳", "杰", "娟", "涛", "明", "超", "秀兰", "霞",
                "平", "刚", "桂英"
        };
        return pick(names);
    }

    /**
     * 随机生成一个常见的英文名
     * @return
     */
    public static String name(){
        return first()+" "+last();
    }

    /**
     * 生成一个常见的中文姓名
     *
     * @return
     */
    public static String cname() {
        return cfirst() + clast();
    }

    /**
     * 获取随机数据
     *
     * @param data
     * @return
     */
    private static <T> T pick(T[] data) {
        Random random = new Random();
        return data[random.nextInt(data.length)];
    }

    /**
     * 获取随机uuid
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString();
    }


    /**----------------------date------------------------------------**/

    /**
     * 随机生成一个日期字符串 1970-01-01 至今
     * @return
     */
    public static String date(String format){
        Calendar calendar = Calendar.getInstance();
        long end = calendar.getTimeInMillis();
        calendar.set(1970,0,1);
        long begin = calendar.getTimeInMillis();
        long rnt = ThreadLocalRandom.current().nextLong(begin, end);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(rnt));
    }

    /**-----------------------basic-----------------------------------**/

    /**
     * 生成随机字符串
     * @param type string number
     * @param len 长度
     * @return
     */
    public static String string(String type,int len){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<len;i++){
            sb.append(character(type));
        }
        return sb.toString();
    }

    /**
     * 返回一个随机字符
     * @param type
     * @return
     */
    public static char character(String type){
        Map<String,String> pools = new HashMap<>();
        pools.put("lower","abcdefghijklmnopqrstuvwxyz");
        pools.put("upper","ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        pools.put("number","0123456789");
        pools.put("symbol","!@#$%^&*()[]");
        pools.put("alpha","abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        type = pools.get(type)!=null?pools.get(type):type;
        return type.charAt(natural(0,type.length()-1));
    }

    /**
     * 返回一个随机的自然数（大于等于0的整数）
     * @param min
     * @param max
     * @return
     */
    public static int natural(int min ,int max){
        min = min >= 0 ? min : 0;
        max = max >= 0 && max >= min ? max : Integer.MAX_VALUE;
        Random random = new Random();
        return min + random.nextInt(max);
    }

    /**
     * 返回一个 min max 之间的浮点数 默认到四位小数点
     * @param min
     * @param max
     * @return
     */
    public static float Float(int min,int max){
        return Float(min,max,4);
    }

    /**
     *
     * @param min 最小值
     * @param max 最大值
     * @param dcout 小数点后几位
     * @return
     */
    public static float Float(int min,int max ,int dcout){
        if (min > max){
            max = Integer.MAX_VALUE;
        }
        String retStr = String.valueOf(natural(min,max))+".";
        for (int i = 0; i < dcout; i++) {
            retStr += character("number");
        }
        return Float.parseFloat(retStr);
    }

    /**
     * 生成一个18位的身份证
     * 地址码 6 + 出生日期码 8 + 顺序码 3 + 校验码 1
     * @return
     */
    public static String id(){
        int[] rank = {
                7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
        };
        String[] last ={
                "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"
        };
        int sum = 0;
        //身份证 区号+出生年月+顺序码+校验码
        //随机生成一个城市的区码
        AddressCode province = pick(AddressDict.addressCodes);
        AddressCode[] cities = new AddressCode[province.getChildren().size()];
        province.getChildren().toArray(cities);
        AddressCode city = pick(cities);
        //组合身份证号
        String id = city.getId()
                + date("yyyyMMdd")
                + string("number", 3);
        char[] c = id.toCharArray();
        for (int i = 0; i < c.length; i++) {
            sum += (c[i] - '0') * rank[i];
        }
        int lastIndex = sum%11;
        id = id + last[lastIndex];
        return id;
    }


    /**----------------------------------address------------------------**/
    /**
     * 随机生成一个大区域
     *
     *     国标 省（市）级行政区划码表
     华北   北京市 天津市 河北省 山西省 内蒙古自治区
     东北   辽宁省 吉林省 黑龙江省
     华东   上海市 江苏省 浙江省 安徽省 福建省 江西省 山东省
     华南   广东省 广西壮族自治区 海南省
     华中   河南省 湖北省 湖南省
     西南   重庆市 四川省 贵州省 云南省 西藏自治区
     西北   陕西省 甘肃省 青海省 宁夏回族自治区 新疆维吾尔自治区
     港澳台 香港特别行政区 澳门特别行政区 台湾省
     * @return
     */
    public static String region(){
        String[] REGION = {"东北", "华北", "华东", "华中", "华南", "西南", "西北"};
        return pick(REGION);
    }

    /**
     * 随机生成一个省
     * @return
     */
    public static String province(){
        return pick(AddressDict.addressCodes).getName();
    }

    /**
     * 随机生成一个城市
     * @param prefix 是否显示前缀省份
     * @return
     */
    public static String city(boolean prefix){
        AddressCode province = pick(AddressDict.addressCodes);
        AddressCode[] cities = new AddressCode[province.getChildren().size()];
        province.getChildren().toArray(cities);
        AddressCode city = pick(cities);
        return prefix?
                String.join( " ",new String[]{province.getName(),city.getName()}):
                city.getName();
    }

    /**
     * 随机生成一个县
     * @param prefix 是否显示前缀省份
     * @return
     */
    public static String county(boolean prefix){
        AddressCode province = pick(AddressDict.addressCodes);
        AddressCode[] cities = new AddressCode[province.getChildren().size()];
        province.getChildren().toArray(cities);
        AddressCode city = pick(cities);
        if (city==null||city.getChildren()==null||city.getChildren().isEmpty()){
            return "-";
        }
        AddressCode[] counties = new AddressCode[city.getChildren().size()];
        city.getChildren().toArray(counties);
        AddressCode county = pick(counties);
        String countyName = county!=null?county.getName():"-";
        return prefix?
                String.join(" ",new String[]{province.getName(),city.getName(),countyName}):
                countyName;
    }

    /**
     * 生成一个单词
     * @return
     */
    public static String word(){
        int len = natural(3,10);
        char[] result = new char[len];
        for (int i = 0; i < len; i++) {
            result[i] = character("lower");
        }
        return new String(result);
    }

    /**
     * 生成顶级域名
     * @return
     */
    public static String tld(){
        String tldStr =
                // 域名后缀
                "com net org edu gov int mil cn " +
                        // 国内域名
                        "com.cn net.cn gov.cn org.cn " +
                        // 中文国内域名
                        "中国 中国互联.公司 中国互联.网络 " +
                        // 新国际域名
                        "tel biz cc tv info name hk mobi asia cd travel pro museum coop aero " +
                        // 世界各国域名后缀
                        "ad ae af ag ai al am an ao aq ar as at au aw az ba bb bd be bf bg bh bi bj bm bn bo br bs bt bv bw by bz ca cc cf cg ch ci ck cl cm cn co cq cr cu cv cx cy cz de dj dk dm do dz ec ee eg eh es et ev fi fj fk fm fo fr ga gb gd ge gf gh gi gl gm gn gp gr gt gu gw gy hk hm hn hr ht hu id ie il in io iq ir is it jm jo jp ke kg kh ki km kn kp kr kw ky kz la lb lc li lk lr ls lt lu lv ly ma mc md mg mh ml mm mn mo mp mq mr ms mt mv mw mx my mz na nc ne nf ng ni nl no np nr nt nu nz om qa pa pe pf pg ph pk pl pm pn pr pt pw py re ro ru rw sa sb sc sd se sg sh si sj sk sl sm sn so sr st su sy sz tc td tf tg th tj tk tm tn to tp tr tt tv tw tz ua ug uk us uy va vc ve vg vn vu wf ws ye yu za zm zr zw";
        String[] tlds = tldStr.split(" ");
        return pick(tlds);
    }

    /**
     * 生成email
     * @return
     */
    public static String email(){
        return word()+"@"+word()+"."+tld();
    }

    /**
     * 通过正则生成属性值
     * @return
     */
    public static String regExp(String rex){
        Generex generex = new Generex(rex);
        return generex.random();
    }
}
