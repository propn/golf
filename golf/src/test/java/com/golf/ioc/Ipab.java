package com.golf.ioc;

import com.golf.ioc.anno.Named;

@Named(value = "ipab")
public class Ipab extends Ipa {

    @Override
    public void sayHello() {
        System.out.println("hello ipab" + this.getClass().getName());
    }

}
