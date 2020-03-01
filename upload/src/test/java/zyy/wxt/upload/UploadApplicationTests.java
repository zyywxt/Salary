package zyy.wxt.upload;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UploadApplicationTests {
    @Test
    void demo() {
        List list=new ArrayList();
        list.add(112121);
        list.add(121);
        System.out.println(list.get(0));
    }


}
