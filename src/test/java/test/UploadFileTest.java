package test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class UploadFileTest {
    @Test
    public void test1(){
        String fileName="safsa.jpg";
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
    @Test
    public void testRedis(){
        //1、获取连接
        Jedis jedis=new Jedis("localhost",6379);
        //2、执行具体的操作
        jedis.set("username","xiaoming");
        System.out.println(jedis.get("username"));
        jedis.hset("hash", "name1", "xiaohua");
        System.out.println(jedis.hget("hash","name1"));
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        //3、关闭连接
        jedis.close();
    }
}
