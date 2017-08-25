

————————————————————————————————————————————————————————————————————————
WALLET PROJECT WITH SPRING MVC, APACHE CXF (JAX-RS), HIBERNATE AND MYSQL 
————————————————————————————————————————————————————————————————————————



jaxrs-tutorials
===============

RESTful Web Services with JAX-RS

## cURL requests


curl http://localhost:8080/api/users
curl http://localhost:8080/api/users/1
curl -v http://localhost:8080/api/exception
curl http://localhost:8080/api/users -X POST -H "Content-Type: application/json" -d '{"name":"John"}'



So structure is as follows. Config files, data.def, data.impl,entitirs, services.def, services.impl and services.utils