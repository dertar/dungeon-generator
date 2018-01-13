package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;

public class StringDataComponent implements Component{
    public String data;

    public StringDataComponent(String data) {
        this.data = data;
    }
}
