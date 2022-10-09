package com.yj.lab.spring.alog.dp;

public class Dp122 {

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

            if (prices == null) {
                return 0;
            }

            int length = prices.length;
            if (length < 2) {
                return 0;
            }

            int[][] dp = new int[length][2];
            dp[0][0] = 0;
            dp[0][1] = -prices[0];

            for (int i = 1; i < length; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
            }

            return dp[length - 1][0];
        }
    }

}

//示例 1:
//
//        输入: prices = [7,1,5,3,6,4]
//        输出: 7
//        解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//        随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
//
//        示例 2:
//
//        输入: prices = [1,2,3,4,5]
//        输出: 4
//        解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//        注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
//
//        示例 3:
//
//        输入: prices = [7,6,4,3,1]
//        输出: 0
//        解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
