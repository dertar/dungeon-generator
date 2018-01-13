package com.t3o.utils;

import com.badlogic.gdx.utils.Array;

public class FloorGraphData {
    public int ID;
    public Array<Coordinates> floor;

    public FloorGraphData(int ID, Array<Coordinates> floor) {
        init(ID,floor);
    }

    public void init(int ID, Array<Coordinates> floor) {
        this.ID = ID;
        this.floor = floor;
    }
}
