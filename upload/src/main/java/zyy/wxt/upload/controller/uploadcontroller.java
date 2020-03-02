package zyy.wxt.upload.controller;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import zyy.wxt.upload.domain.salary;
import zyy.wxt.upload.service.ImportService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import static javax.xml.transform.OutputKeys.ENCODING;


@Controller
public class uploadcontroller {


    @Value("${filePath}")
    private String filepath;

    @Autowired
    private ImportService importService;

    @RequestMapping("/index")
    public String index() {
        return "upload";
    }

    @RequestMapping("/")
    public String upload() {
        return "upload";
    }

    List<salary> list = null;
    List<salary> lt = null;

    @PostMapping(value = "/uploading")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (file.isEmpty()) {
                return "文件不能为空";
            }
            String filename = file.getOriginalFilename();
            String suffixname = filename.substring(filename.lastIndexOf("."));
            System.out.println("上传的文件名为：" + filename + "后缀名" + suffixname);
            String path = filepath + filename;
            File fl = new File(path);
            //检测是否存在目录
            if (!fl.getParentFile().exists()) {
                fl.getParentFile().mkdirs();//新建文件夹
            }

            InputStream inputStream = file.getInputStream();
            //每行的数据
            list = importService.getBankListByExcel(inputStream, file.getOriginalFilename());
            System.out.println("====第2月五险一金===");
            System.out.println(list.get(2).getInsurance());
            System.out.println("====================");
            inputStream.close();
            for (int i = 0; i < list.size(); i++) {
                salary lo = list.get(i);
                //TODO 随意发挥
                System.out.println(lo);
            }
            model.addAttribute("excellent", list);
            //写入磁盘中
            //  file.transferTo(fl);

            return "success";

        } catch (IllegalStateException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "文件上传失败";

    }


    //个税计算器
    //所有坐标从零开始
    @RequestMapping("/jisuan")
    public String sum(Model model) {
        lt = new ArrayList<>();
        int SMonth = 1;      //月份
        final double Sindex = 5000;//起征点
        double SAindex = 0;//累计起征点
        double SPreIncome = 0;//预交应纳税所得
        double STaxrate = 0;  //税率
        double SQuickcal = 0;   //速算扣除数
        double SPayable = 0;   //应纳税额
        double Spaid = 0;//已缴税额
        double Sfinallypay = 0;//应补（退）税额
        double Ssal = 0;//实发工资
        double Aincome = 0;//累加累计收入
        double Ainsurance = 0;//累计五险一金
        double AAttach = 0;//累加专项附加扣除
        List sumlist = new ArrayList();//用于存放本月应该税,便于下月计算最终税。

        for (int i = 0; i < 12; i++) {
            salary s = new salary();
            SMonth = SMonth++;
            Aincome = Aincome + list.get(i).getIncome();
            Ainsurance = Ainsurance + list.get(i).getInsurance();
            AAttach = AAttach + list.get(i).getAttach();
            SAindex = Sindex * (i + 1);
            SPreIncome = Aincome - Ainsurance - AAttach - SAindex;
            if (SPreIncome <= 36000) {
                STaxrate = 0.03;
                SQuickcal = 0;
            } else if (SPreIncome <= 144000) {
                STaxrate = 0.1;
                SQuickcal = 2520;
            } else if (SPreIncome <= 300000) {
                STaxrate = 0.2;
                SQuickcal = 16920;
            } else if (SPreIncome <= 420000) {
                STaxrate = 0.25;
                SQuickcal = 31920;
            } else if (SPreIncome <= 660000) {
                STaxrate = 0.3;
                SQuickcal = 52920;
            } else if (SPreIncome <= 960000) {
                STaxrate = 0.35;
                SQuickcal = 85920;
            } else {
                STaxrate = 0.45;
                SQuickcal = 181920;
            }
            //应纳税
            if (SPreIncome <= 0) {
                SPayable = 0;
            } else {
                SPayable = SPreIncome * STaxrate - SQuickcal;
            }
            //应纳税
            if (SPreIncome <= 0) {
                SPayable = 0;
            } else {
                SPayable = SPreIncome * STaxrate - SQuickcal;
            }
            //计算个人最终纳税：应补（退）税额
            if (i == 0) {
                Spaid = 0;
                sumlist.add(SPayable);
            } else {
                sumlist.add(SPayable);
                Spaid = (double) sumlist.get((int) (i - 1));
            }
            Sfinallypay = SPayable - Spaid;
            if (i == 0) {
                Ssal = list.get(0).getIncome() - list.get(0).getInsurance();
            } else {
                Ssal = list.get((int) (i)).getIncome() - list.get((int) (i)).getInsurance() - Sfinallypay;
            }
            s.setMonth(i + 1);
            s.setIncome(list.get((int) (i)).getIncome());
            s.setInsurance(list.get((int) (i)).getInsurance());
            s.setAttach(list.get((int) (i)).getAttach());
            s.setAincome(Aincome);
            s.setAinsurance(Ainsurance);
            s.setAAttach(AAttach);
            s.setAindex(SAindex);
            s.setPreIncome(SPreIncome);
            s.setTaxrate(STaxrate);
            s.setQuickcal(SQuickcal);
            s.setPayable(SPayable);
            s.setPaid(Spaid);
            s.setFinallypay(Sfinallypay);
            s.setSal(Ssal);
            System.out.println("第" + (i + 1) + "月份" + "  " + "税前工资" + list.get((int) (i)).getIncome() + "  " + "五险一金" + list.get((int) (i)).getInsurance() + "  " + "专项附加扣除" + list.get((int) (i)).getAttach() + "  " + "总收入：" + Aincome + "  " + "累计五险一金" + Ainsurance + "  " + "累加专项附加扣除" + AAttach + "  " + "累计起征点" + SAindex + "  " + "预交应纳税所得" + SPreIncome + "  " + "税率" + STaxrate + "  " + "速算扣除数" + SQuickcal + "  " + "应纳税" + SPayable + "  " + "已交税" + Spaid + "  " + "最终个人税" + Sfinallypay + "  " + "税后工资" + Ssal);
            lt.add(s);
        }
        model.addAttribute("result", lt);

        System.out.println("===");
        for (int y = 0; y < lt.size(); y++) {
            salary l = lt.get(y);
            //TODO 随意发挥
            System.out.println(l);
        }

        return "result";
    }

    //下载
    @RequestMapping("/export")
    @ResponseBody
    public void ex(HttpServletResponse response, HttpServletRequest request) {
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("Month", "月份");
        writer.addHeaderAlias("Income", "税前工资");
        writer.addHeaderAlias("insurance", "五险一金");
        writer.addHeaderAlias("Attach", "专项附加扣除");
        writer.addHeaderAlias("Index", "起征点");
        writer.addHeaderAlias("Aincome", "总收入");
        writer.addHeaderAlias("Ainsurance", "累计五险一金");
        writer.addHeaderAlias("AAttach", "累加专项附加扣除");
        writer.addHeaderAlias("Aindex", "累计起征点");
        writer.addHeaderAlias("PreIncome", "预交应纳税所得");
        writer.addHeaderAlias("Taxrate", "税率");
        writer.addHeaderAlias("Quickcal", "速算扣除数");
        writer.addHeaderAlias("Payable", "应纳税额");
        writer.addHeaderAlias("paid", "已缴税额");
        writer.addHeaderAlias("finallypay", "应补（退）税额");
        writer.addHeaderAlias("sal", "实发工资");
        writer.merge(10, "申请人信息");
        writer.write(lt, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //下载表名

        String name="Demo";
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        IoUtil.close(out);
    }

}
