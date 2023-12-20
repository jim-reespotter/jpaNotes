package com.example.demo.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
// default table name == CHILD_THING
public class ChildThing implements Serializable {

    @Id
    @JsonIgnore // so doesn't need getters/setters
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    // we need laziness at one end of this relationship (or JsonIgnore it?)
    @ManyToOne(fetch = FetchType.LAZY)
    private ParentThing parent;

    protected ChildThing() {}
    public ChildThing(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ParentThing getParent() {return parent;}
    // make this callable only by parent, do both way link there
    void setParent(ParentThing parent) {this.parent = parent;}
}
