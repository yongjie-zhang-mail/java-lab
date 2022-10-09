package com.yj.lab.spring.alog.dp;

public class Dp1 {
    // 1 1 2 3 5 8 13 21
    public static void main(String[] args) {
        // 13
        int i = fbCalc(7);
        System.out.println(i);
        // 5
        int j = fbCalc(5);
        System.out.println(j);
    }

    static int fbCalc(int x) {
        int[] fb = new int[x + 1];
        fb[0] = 0;
        fb[1] = 1;
        fb[2] = 1;

        if (x > 2) {
            for (int i = 3; i <= x; i++) {
                fb[i] = fb[i - 1] + fb[i - 2];
            }
        }

        return fb[x];
    }

}
