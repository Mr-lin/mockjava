package com.mockst.mockjava;

/**
 * @Auther: zhiwei
 * @Date: 2018/11/13 20:18
 * @Description: 模板实体
 */
public class MockTemplate {

    //id
    private String id;
    //父级id
    private String parentId;
    //名称
    private String name;
    //类型 String Number Object Array Boolean
    private String type;
    //规则
    private String rule;
    //值
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
