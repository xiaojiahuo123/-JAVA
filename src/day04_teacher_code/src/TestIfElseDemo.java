package day04_teacher_code.src;

import java.util.Scanner;

public class TestIfElseDemo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("请输入1个整数：");
        int num = input.nextInt();

        if(num % 3 == 0){
            System.out.println(num +"是3的倍数");
        }
        if(num % 5 == 0){
            System.out.println(num +"是5的倍数");
        }
        if(num % 7 == 0){
            System.out.println(num +"是7的倍数");
        }


        input.close();
    }
}
