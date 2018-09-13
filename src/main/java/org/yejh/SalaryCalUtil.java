package org.yejh;

import java.math.BigDecimal;
import java.util.Scanner;

public class SalaryCalUtil {
    private SalaryCalUtil() {
        throw new AssertionError();
    }

    public static double afterTaxSalary(double wages, double bonus, int isMinInsurance, double subMoney, double percentage) {
        BigDecimal b_wages = new BigDecimal(wages);
        BigDecimal b_bonus = new BigDecimal(bonus);
        BigDecimal b_subMoney = new BigDecimal(subMoney);
        BigDecimal b_percentage = new BigDecimal(percentage);

        BigDecimal insurance = isMinInsurance != 1 ? b_wages.multiply(new BigDecimal(0.102)) : new BigDecimal(380.34);// 社保
        BigDecimal accumulation = b_subMoney.multiply(b_percentage);// 公积金

        double beforeTax = b_wages.add(b_bonus).subtract(insurance).subtract(accumulation).doubleValue();
        if (beforeTax > 85000) {
            beforeTax = beforeTax - ((beforeTax - 5000) * 0.45 - 15160);
        } else if (beforeTax > 60000) {
            beforeTax = beforeTax - ((beforeTax - 5000) * 0.35 - 7160);
        } else if (beforeTax > 40000) {
            beforeTax = beforeTax - ((beforeTax - 5000) * 0.30 - 4410);
        } else if (beforeTax > 30000) {
            beforeTax = beforeTax - ((beforeTax - 5000) * 0.25 - 2660);
        } else if (beforeTax > 17000) {
            beforeTax = beforeTax - ((beforeTax - 5000) * 0.20 - 1410);
        } else if (beforeTax > 8000) {
            beforeTax = beforeTax - ((beforeTax - 5000) * 0.10 - 210);
        } else if (beforeTax > 5000) {
            beforeTax = beforeTax - ((beforeTax - 5000) * 0.03);
        }
        return beforeTax;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入税前工资：  ");
        double wages = scanner.nextDouble();
        System.out.print("输入加班奖金：  ");
        double bonus = scanner.nextDouble();
        System.out.print("输入最低社保（1是 2否）：  ");
        int isMinInsurance = scanner.nextInt();
        System.out.print("输入公积金基数：  ");
        double subMoney = scanner.nextDouble();
        System.out.print("输入公积金比例%：  ");
        double percentage = scanner.nextDouble();
        System.out.println("税后工资：   " + afterTaxSalary(wages, bonus, isMinInsurance, subMoney, percentage / 100));
        scanner.close();
    }
}
