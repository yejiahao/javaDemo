package org.yejh;

import java.math.BigDecimal;
import java.util.Scanner;

public class SalaryCalUtil {
    private static BigDecimal INSURANCE = new BigDecimal(317.43);
    private static final BigDecimal MIN_ACCUMULATION = new BigDecimal(95);

    private SalaryCalUtil() {
        throw new AssertionError();
    }

    public static double afterTaxSalary(double money, int isMinInsurance, double subMoney, double percentage) throws Exception {
        BigDecimal b_money = new BigDecimal(money);
        BigDecimal b_subMoney = new BigDecimal(subMoney);
        BigDecimal b_percentage = new BigDecimal(percentage);
        if (isMinInsurance != 1) {
            INSURANCE = b_money.multiply(new BigDecimal(0.102));
        }

        double beforeTax = b_money.subtract(INSURANCE).subtract(b_subMoney.multiply(b_percentage)).doubleValue();
        if (beforeTax > 12500) {
            throw new Exception("金额数值过大");
        } else if (beforeTax > 8000) {
            beforeTax = beforeTax - ((beforeTax - 3500) * 0.2 - 555);
        } else if (beforeTax > 5000) {
            beforeTax = beforeTax - ((beforeTax - 3500) * 0.1 - 105);
        } else if (beforeTax > 3500) {
            beforeTax = beforeTax - ((beforeTax - 3500) * 0.03);
        }
        return beforeTax;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入税前工资：  ");
        double money = scanner.nextDouble();
        System.out.print("输入最低社保（1是 2否）：  ");
        int isMinInsurance = scanner.nextInt();
        System.out.print("输入公积金基数：  ");
        double subMoney = scanner.nextDouble();
        System.out.print("输入公积金比例：  ");
        double percentage = scanner.nextDouble();
        System.out.println("税后工资：   " + afterTaxSalary(money, isMinInsurance, subMoney, percentage / 100));
        scanner.close();
    }
}
