package com.exam.rest.webservices.restfulwebservice.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
    
    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionPerson(){

        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionPerson(){

        return new PersonV2(new Name("Bob", "saget"));
    }

    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstVersionReqParam(){

        return new PersonV1("Bob Charlie");
    }

    //localhsot:8080/person?version=2
    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecVersionReqParam(){

        return new PersonV2(new Name("Bob", "saget"));
    }

    
    //IN postman, headers section. Name is X-API-VERSION and value is 1
    @GetMapping(path = "/person/header", headers= "X-API-VERSION=1")
    public PersonV1 getFirstVersionReqHeader(){

        return new PersonV1("Bob Charlie");
    }

    //in postman, headers. key is Accept and value is that whole produces line
    @GetMapping(path = "/person/accept", produces="application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionContentNegotiation(){

        return new PersonV1("Bob Charlie");
    }

//first two methods of versioning create URI pollution
    //changing headers and media type preserves URI
//howver, Misuse of HTTP headers occurs from headers and media type
        //since never designed to be used for versioning
//Also need to consider Caching if using header changes
//Add'lly, header and media type versioning CANNOT be 
        //executed from browser
        //need a command line or rest api client
//Consider API documentation since not auto-generated when changing headers
}
