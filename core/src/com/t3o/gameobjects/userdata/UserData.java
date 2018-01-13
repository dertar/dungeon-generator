package com.t3o.gameobjects.userdata;

public class UserData {
    public static final float []colorWall = new float[]{
            1f,0.33f,0.25f,1f
    };

    public static final float []colorFloor = new float[]{
            1f,0.5f,0.33f,1f
    };
    public static final float []colorSubject = new float[]{
            1f,0f,0f,1f
    };

    public static final float []colorNoneFloor = new float[]{
            1f,1f,1f,1f
    };

    public enum TYPE{
        WALL,FLOOR,SUBJECT, NONE
    }

    TYPE type;

    public UserData(){
        set(TYPE.NONE);
    }

    public UserData(TYPE type){
        set(type);
    }

    public void set(TYPE type){
        this.type = type;
    }

    public TYPE get(){
        return type;
    }

    @Override
    public String toString(){
        return type.toString();
    }

    public String acronym(){
        if(type.equals(TYPE.WALL))
            return "W";
        else if(type.equals(TYPE.FLOOR))
            return "F";
        else if(type.equals(TYPE.SUBJECT))
            return "S";
        return "-";
    }

    public float[] getColor(){
        if(type.equals(TYPE.WALL))
            return colorWall;
        else if(type.equals(TYPE.FLOOR))
            return colorFloor;
        else if(type.equals(TYPE.SUBJECT))
            return colorSubject;

        return colorNoneFloor;
    }

    static public String getInfo(UserData data){
        if(data == null){
            return "info: data is null";
        }else{
            return "info: data is " + data.type.toString();
        }
    }

}
