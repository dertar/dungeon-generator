package com.t3o.map.generators;

import org.junit.Test;

import static org.junit.Assert.*;

public class TemplateGeneratorTest {

    @Test
    public void generateTest1(){

    }

    @Test
    public void generateRooms(){
        TemplateGenerator.ROOM[][] rooms = TemplateGenerator.generate(8,4);

        for(int i = 0; i < rooms[0].length; i++){
            for(int j = 0; j < rooms.length; j++){
                System.out.print(rooms[j][i] + " ");
            }
            System.out.println();
        }
    }
}