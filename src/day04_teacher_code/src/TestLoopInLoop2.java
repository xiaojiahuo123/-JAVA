package day04_teacher_code.src;

public class TestLoopInLoop2 {
    public static void main(String[] args) {
        for(int hour = 0; hour<24; hour++){
            for(int minute = 0; minute<60; minute++){
                System.out.println(hour +":" + minute);
            }
        }
    }
}
