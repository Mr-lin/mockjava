package com.mockst.mockjava;

/**
 * @Auther: zhiwei
 * @Date: 2018/11/11 23:57
 * @Description: 正则常量集合
 */
public class MockRegex {

    /*
        RE_KEY
            'name|min-max': value
            'name|count': value
            'name|min-max.dmin-dmax': value
            'name|min-max.dcount': value
            'name|count.dmin-dmax': value
            'name|count.dcount': value
            'name|+step': value

        RE_RANGE    1 name, 2 step, 3 range [ min, max ], 4 drange [ dmin, dmax ]

        RE_PLACEHOLDER
            placeholder(*)

        [正则查看工具](http://www.regexper.com/)

        #26 生成规则 支持 负数，例如 number|-100-100
    */
    public static final String RE_KEY = "(.+)\\|(?:\\+(\\d+)|([\\+\\-]?\\d+-?[\\+\\-]?\\d*)?(?:\\.(\\d+-?\\d*))?)";
    public static final String RE_RANGE = "([\\+\\-]?\\d+)-?([\\+\\-]?\\d+)?";
    public static final String RE_PLACEHOLDER = "\\\\*@([^@#%&()\\?\\s]+)(?:\\((.*?)\\))?";
    //正则，分割获取参数
    public static final String RE_METHOD_ARGS = "(((\"[^\"]*\")|[^\",])*)(,|$)";
}
