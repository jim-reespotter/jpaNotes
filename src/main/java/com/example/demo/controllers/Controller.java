package com.example.demo.controllers;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ChildThing;
import com.example.demo.models.ParentThing;
import com.example.demo.repos.ChildThingRepo;
import com.example.demo.repos.ParentThingRepo;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(path="/")
public class Controller {
    
    @Autowired
    private ParentThingRepo pRepo;

    @Autowired
    private ChildThingRepo cRepo;

    @Autowired
    private HttpSession session;

    @GetMapping(path="getParent")
    public Optional<ParentThing> getParent(Long id) {
        return pRepo.findById(id);
    }

    // Make sure sessioned enetity is in DB (and so has id)
    @PostMapping(path="postParent")
    public ParentThing postParent(@RequestBody ParentThing parent) {
        parent = pRepo.save(parent);
        session.setAttribute("PARENT", parent);
        return parent;
    }

    @GetMapping(path="getChild")
    public Optional<ChildThing> getChild(Long id) {
        return cRepo.findById(id);
    }

    @PostMapping(path="postChild")
    public ChildThing postChild(@RequestBody ChildThing child) {
        return cRepo.save(child);
    }

    @PostMapping(path="add")
    public ParentThing pair(@RequestBody PCDAO pairing) {
        pairing.parent = pRepo.save(pairing.parent);
        pairing.child = cRepo.save(pairing.child);

        pairing.parent.getChildren().add(pairing.child);
//        pRepo.save(pairing.parent); //this doesn't actually commit anything as data stored in child

//        pairing.child.setParent(pairing.parent);
        cRepo.save(pairing.child);

        return pairing.parent;
    }

    @PostMapping(path="addChild")
    public ParentThing addChild(@RequestBody ChildThing child) {
        ParentThing parent = (ParentThing) session.getAttribute("PARENT");
        // doesn't help as intersection is a property of child... (even with CascadeType.PERSIST set, this adds child but no parentId)
        // as a result, even though returned value is correct, persisted one isn't.
        // we need this so method returns the correct value:
        // also, havin this and cascadeType.PERSISTN is bad as it adds a child (but it doesn't have relation set)
        parent.getChildren().add(child);
        // don't need this for reason above...
//        parent = pRepo.save(parent);

        // we need this for persistence
        child = cRepo.save(child);

        return parent;
    }

    public static class PCDAO implements Serializable {
        public ParentThing parent;
        public ChildThing child;
    }
}
