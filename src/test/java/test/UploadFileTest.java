package test;

import org.junit.Test;

public class UploadFileTest {
    @Test
    public void test1(){
        String fileName="safsa.jpg";
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}
