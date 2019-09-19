package com.company.bugbusters;

import java.util.Hashtable;

public class Scope {
    private int level;
    private Hashtable<String,Integer> map;

    public Scope(int level){
        this.level = level;
        map = new Hashtable<>();
    }
    public Scope(){
        level = 0;
    }

    public void define(String name,Integer value){
        map.put(name,value);
    }

    public boolean hasVarDefn(String name){
        return map.containsKey(name);
    }

    public Integer getValue(String name){
        return  map.get(name);
    }
    public int getLevel(){
        return level;
    }
}
