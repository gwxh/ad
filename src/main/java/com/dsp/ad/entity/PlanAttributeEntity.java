package com.dsp.ad.entity;

import javax.persistence.*;

/**
 * @author wanghh
 * @date 2019/1/3 20:53
 */
@Entity
@Table(name="plan_attribute")
public class PlanAttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int sid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int level;
    @Column(nullable = false)
    private int parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
