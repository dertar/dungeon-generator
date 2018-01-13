package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;
import com.t3o.gameobjects.subjects.Subject;

public class StateComponent implements Component{
    Subject.STATE current;
    String subject;

    public StateComponent(String subject){
        current = Subject.STATE.IDLE;
        this.subject = subject;
    }


    public boolean change(Subject.STATE state){
        if(!current.equals(state)){
            current = state;
            return true;
        }
        return false;
    }

    public String get(){
        return subject + "_" + current.toString();
    }

    public Subject.STATE getState(){
        return current;
    }


    public boolean is(Subject.STATE state){
        return current.equals(state);
    }
}
