# JAX-RS plain vanilla server code generator
This is an add-on for the swagger-codegen project. 

The goals of this code generator are

* separation of generated code from manually added code
* implementation of REST best practices
* usage of as few as possible dependencies apart from JAX-RS

The inital code is based on the jaxrs-cfx code generator from the swagger-codegen project.

# Changes
* change of the method naming convention from "ressourceGet" to "getRessource" in case no operationId is provided in swagger specification

# Usage
``` java -cp jaxrs-vanilla-server-codegen-1.0.0.jar;swagger-codegen-cli.jar io.swagger.codegen.SwaggerCodegen  generate -i swagger.yml -l jaxrs-vanilla -o src\main
```