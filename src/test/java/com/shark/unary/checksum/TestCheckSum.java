package com.shark.unary.checksum;

import org.junit.Test;

import java.util.zip.Adler32;

public class TestCheckSum {
    @Test
    public void test1 (){
        byte[] data = {1,21,32,40,5,61,7,8,1,3,4,23,14};

        Adler32 adler32 = new Adler32();
        adler32.update(data, 0, 4);
        System.out.println(adler32.getValue());
        adler32.update(data[4]);
        System.out.println(adler32.getValue());

        adler32.reset();
        adler32.update(data, 0, 5);
        System.out.println(adler32.getValue());

        adler32.reset();
        adler32.update(data, 1, 4);
        System.out.println(adler32.getValue());

        adler32.reset();
        adler32.update(data, 0, 4);
        adler32.update(data, 1, 4);
        System.out.println(adler32.getValue());


    }
}
