package com.t3o.gameworld.components;

import com.badlogic.ashley.core.Component;
import com.t3o.gameobjects.subjects.Subject;

public class AttachComponent implements Component {
    public Subject subject = null;

    public AttachComponent(Subject subject) {
        this.subject = subject;
    }
}
