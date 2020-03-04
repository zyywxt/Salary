package zyy.wxt.upload;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UploadApplicationTests {
    private double N;

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }

    @Test
    void demo() {

        UploadApplicationTests s = new UploadApplicationTests();


        BigDecimal bg = new BigDecimal(124.58999999999999);
        double str = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        s.setN(str);
        System.out.println(s.getN());

    }


}
