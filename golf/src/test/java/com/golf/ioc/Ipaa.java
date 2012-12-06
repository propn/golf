package com.golf.ioc;

import com.golf.ioc.anno.Named;

@Named(value = "ipaa")
public class Ipaa extends Ipa {

    @Override
    public void sayHello() {
        System.out.println("hello ipaa" + this.getClass().getName());
    }

}
