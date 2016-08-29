package mybatis.generator;

import org.springframework.cglib.proxy.Enhancer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Description :
 * Author : guo
 * Date : 2016/8/15 23:14
 */
public class Test {

   /* public static void main(String[] args){
        while(true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
        }
    }*/

    static class OOMObject{
        public byte[] placeholder = new byte[64*1024];
    }

    public static void fillHeap(int num) throws InterruptedException{
        List<OOMObject> list = new ArrayList<>();
        for(int i = 0 ; i<num ;i++){
            System.out.println("test");

            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args)throws Exception{
        fillHeap(6000);
    }





}
