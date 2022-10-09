package com.yj.lab.spring.alog.dp;

public class Dp121 {

    public static void main(String[] args) {
        int[] prices = new int[]{
                7, 1, 5, 3, 6, 4
        };

//        int[] prices = new int[]{
//                1, 2, 3, 4, 5
//        };

//        int[] prices = new int[]{
//                7, 6, 4, 3, 1
//        };
        int i = Solution.maxProfit(prices);
        System.out.println(i);
    }

    static class Solution {
        public static int maxProfit(int[] prices) {
            // 校验
            if (prices == null || prices.length < 2) {
                return 0;
            }

            int length = prices.length;

            // i 表示第几天， j：0 不持股 1 持股
            // dp 表示 现有现金；持股减现金
            int[][] dp = new int[length][2];
            dp[0][0] = 0;
            dp[0][1] = -prices[0];

            // 遍历，起点 > 0
            for (int i = 1; i < length; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                dp[i][1] = Math.max(-prices[i], dp[i - 1][1]);
            }

            return dp[length - 1][0];
        }
    }

}
