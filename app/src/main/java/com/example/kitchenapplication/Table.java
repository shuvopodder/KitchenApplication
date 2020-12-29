package com.example.kitchenapplication;

import java.util.ArrayList;
import java.util.List;

public class Table {

    static List <String> list = new ArrayList<>();
    public Table(){

    }

    public void setList(String item) {
        this.list.add(item);
    }

    public List<String> getList() {
        return list;
    }
}
