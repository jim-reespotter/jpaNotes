# Notes on using Springboot JPA

### Steps:

1 - add a Spring Intializr with Jpa and sb-starter-web* 8c0d95a
2 - add h2 driver										0301018
3 - added entities, repos, controller and variables		fcfcc54

### Now runs...
- H2 console available at http://localhost:8080/h2-console/ (pw == password)

### Do stuff
Send reqs (eg via POSTMan)
- POST http://localhost:8080/postParent ( body == {"name": "parenty"} )
- POST http://localhost:8080/postChild ( body == {"name": "childy"} )

check stuff:
- GET http://localhost:8080/getParent?id=1
- GET http://localhost:8080/getChild?id=1

Add a child to last added parent:
- POST http://localhost:8080/addChild ( body == {"name": "child A"} )

Key points/tips:
- 1 to many relationships are recorded by a single attribute at the many end
  - when you persist the entities, you just need to persist the entity with this attribute I think
  - when you return/use entities elsewhere you may need both ends of the relationship done. Approaches:
    - force setting of relationship at one end (hide the other end)
	- set the many end, re-pull many end from persistence (should have children added)  


(* should have done this, did in later step)


