package zyy.wxt.upload.domain;


public class salary {
    private double Month;      //月份
    private double Income;         //税前工资
    private double insurance;//五险一金
    private double Attach;//专项附加扣除
    private double Index=5000;//起征点
    //累计
    private double Aincome;//累加累计收入
    private double Ainsurance;//累计五险一金
    private double AAttach;//累加专项附加扣除
    private double Aindex;//累计起征点

    private double PreIncome;//预交应纳税所得
    private double Taxrate;  //税率
    private double Quickcal;   //速算扣除数
    private double Payable;   //应纳税额
    private double paid;//已缴税额
    private double finallypay;//应补（退）税额
    private double sal;//实发工资

    @Override
    public String toString() {
        return "salary{" +
                "Month=" + Month +
                ", Income=" + Income +
                ", insurance=" + insurance +
                ", Attach=" + Attach +
                ", Index=" + Index +
                ", Aincome=" + Aincome +
                ", Ainsurance=" + Ainsurance +
                ", AAttach=" + AAttach +
                ", Aindex=" + Aindex +
                ", PreIncome=" + PreIncome +
                ", Taxrate=" + Taxrate +
                ", Quickcal=" + Quickcal +
                ", Payable=" + Payable +
                ", paid=" + paid +
                ", finallypay=" + finallypay +
                ", sal=" + sal +
                '}';
    }

    public double getMonth() {
        return Month;
    }

    public void setMonth(double month) {
        Month = month;
    }

    public double getIncome() {
        return Income;
    }

    public void setIncome(double income) {
        Income = income;
    }

    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    public double getAttach() {
        return Attach;
    }

    public void setAttach(double attach) {
        Attach = attach;
    }

    public double getIndex() {
        return Index;
    }

    public void setIndex(double index) {
        Index = index;
    }

    public double getAincome() {
        return Aincome;
    }

    public void setAincome(double aincome) {
        Aincome = aincome;
    }

    public double getAinsurance() {
        return Ainsurance;
    }

    public void setAinsurance(double ainsurance) {
        Ainsurance = ainsurance;
    }

    public double getAAttach() {
        return AAttach;
    }

    public void setAAttach(double AAttach) {
        this.AAttach = AAttach;
    }

    public double getAindex() {
        return Aindex;
    }

    public void setAindex(double aindex) {
        Aindex = aindex;
    }

    public double getPreIncome() {
        return PreIncome;
    }

    public void setPreIncome(double preIncome) {
        PreIncome = preIncome;
    }

    public double getTaxrate() {
        return Taxrate;
    }

    public void setTaxrate(double taxrate) {
        Taxrate = taxrate;
    }

    public double getQuickcal() {
        return Quickcal;
    }

    public void setQuickcal(double quickcal) {
        Quickcal = quickcal;
    }

    public double getPayable() {
        return Payable;
    }

    public void setPayable(double payable) {
        Payable = payable;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getFinallypay() {
        return finallypay;
    }

    public void setFinallypay(double finallypay) {
        this.finallypay = finallypay;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public salary() {
    }

    public salary(double month, double income, double insurance, double attach, double index, double aincome, double ainsurance, double AAttach, double aindex, double preIncome, double taxrate, double quickcal, double payable, double paid, double finallypay, double sal) {
        Month = month;
        Income = income;
        this.insurance = insurance;
        Attach = attach;
        Index = index;
        Aincome = aincome;
        Ainsurance = ainsurance;
        this.AAttach = AAttach;
        Aindex = aindex;
        PreIncome = preIncome;
        Taxrate = taxrate;
        Quickcal = quickcal;
        Payable = payable;
        this.paid = paid;
        this.finallypay = finallypay;
        this.sal = sal;
    }
}
