package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;
import com.t3o.gameobjects.subjects.Subject;

public class MovementComponent implements Component {
    public Subject subject = null;

    public int keyJump = Input.Keys.Z;
    public int keyLeft = Input.Keys.LEFT;
    public int keyRight = Input.Keys.RIGHT;

    public MovementComponent(Subject subject) {
        this.subject = subject;
    }



}
