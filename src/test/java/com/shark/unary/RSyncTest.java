package com.shark.unary;

import com.shark.unary.mock.SimpleDiffDataHandler;
import com.shark.unary.mock.SimpleFileProvider;
import org.junit.Test;

import java.io.IOException;

public class RSyncTest {
    @Test
    public void test1() throws IOException {
        RSync rSync = new RSync(new SimpleFileProvider());
        RSyncServer rs = rSync.buildServer("");
        RSyncClient rc = rSync.buildClient(new SimpleDiffDataHandler());

        rc.setCheckSumData(rs.getFileValidation(rc.getFileValidation()));

    }
}
