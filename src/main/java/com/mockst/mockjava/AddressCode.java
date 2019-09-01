package com.mockst.mockjava;

import java.io.Serializable;
import java.util.List;

/**
 * @describe:
 * @author: linzhiwei
 * @date: 2018/11/9 13:35
 */
public class AddressCode implements Serializable{

    private String id;
    private String name;
    private String pid;

    private List<AddressCode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<AddressCode> getChildren() {
        return children;
    }

    public void setChildren(List<AddressCode> children) {
        this.children = children;
    }
}
