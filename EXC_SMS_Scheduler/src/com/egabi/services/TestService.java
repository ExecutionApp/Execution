package com.egabi.services;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "TestServiceSMS")
public class TestService {
    @WebMethod
    @Oneway
    public void makeSum(){
        System.out.println("hello");
    }
}
