package com.example.demo.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ParentThing implements Serializable {
    
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    /**
     * With no params (other than oneotmany/manytoone), creates an intersection table PARENT_THING_CHILDREN here (and 2 FKs?)
     * with otm(mappedBy=field) creates a foreign key on the many table
     * adding cascade.PERSIST will add any child things when saving parent, but not set their parent value (not good!)
     */
    @OneToMany(mappedBy = "parent")
    private Set<ChildThing> children = new HashSet<ChildThing>() {
        @Override
        public boolean add(ChildThing child) {
            child.setParent(ParentThing.this);
            return super.add(child);
        }
    };

    public ParentThing() {}
    public ParentThing(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<ChildThing> getChildren() {return children;}
    // called only by ParentThing
    void setChildren(Set<ChildThing> childs) {this.children = childs;}
}
