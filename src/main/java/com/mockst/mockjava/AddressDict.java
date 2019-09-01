package com.mockst.mockjava;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @describe: 区域字典
 * @author: linzhiwei
 * @date: 2018/11/9 11:24
 */
public class AddressDict {

    public static LinkedHashMap<String, String> dict;
    public static AddressCode[] addressCodes;

    static {
        InputStream in = AddressDict.class.getClassLoader().getResourceAsStream("dict.json");
        try {
            dict = JSON.parseObject(in, LinkedHashMap.class);
            Iterator<Map.Entry<String, String>> iterator = dict.entrySet().iterator();
            //用于排序
            Map<String, AddressCode> codeMap = new HashMap<>();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String id = entry.getKey();
                String pid = "0000".equals(id.substring(2, 6)) ? null :
                        "00".equals(id.substring(4, 6)) ? id.substring(0, 2) + "0000" : id.substring(0, 4) + "00";
                AddressCode addressCode = new AddressCode();
                addressCode.setId(id);
                addressCode.setPid(pid);
                addressCode.setName(entry.getValue());
                codeMap.put(id, addressCode);
            }
            //排序操作
            Iterator<Map.Entry<String, AddressCode>> dictIt = codeMap.entrySet().iterator();
            List<AddressCode> addressCodeList = new ArrayList<>();
            while (dictIt.hasNext()) {
                AddressCode addressCode = dictIt.next().getValue();
                if (addressCode.getPid() == null) {
                    addressCodeList.add(addressCode);
                    continue;
                }
                AddressCode parent = codeMap.get(addressCode.getPid());
                if (parent == null) {
                    continue;
                }
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(addressCode);
            }
            addressCodes = new AddressCode[addressCodeList.size()];
            addressCodeList.toArray(addressCodes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
