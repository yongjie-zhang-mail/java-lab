package com.yj.lab.spring.alog.dppath;

import java.util.List;

public class DpPath120 {

    public static void main(String[] args) {
//        示例 1：
//        输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
//        输出：11
//        解释：如下面简图所示：
//        2
//        3 4
//        6 5 7
//        4 1 8 3
//        自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
//        示例 2：
//        输入：triangle = [[-10]]
//        输出：-10

//        List<Integer> a1 = new ArrayList<>(List.of(2));
//        List<Integer> a2 = new ArrayList<>(Arrays.asList(3, 4));
//        List<Integer> a3 = new ArrayList<>(Arrays.asList(6, 5, 7));
//        List<Integer> a4 = new ArrayList<>(Arrays.asList(4, 1, 8, 3));

        List<Integer> a1 = List.of(2);
        List<Integer> a2 = List.of(3, 4);
        List<Integer> a3 = List.of(6, 5, 7);
        List<Integer> a4 = List.of(4, 1, 8, 3);
        List<List<Integer>> triangle = List.of(a1, a2, a3, a4);

//        List<Integer> a1 = List.of(-10);
//        List<List<Integer>> triangle = List.of(a1);

        int i = Solution.minimumTotal(triangle);
        System.out.println(i);


    }

    static class Solution {
        public static int minimumTotal(List<List<Integer>> triangle) {
            // 行数
            int m = triangle.size();
//            // 列数
//            int n = m + 1;
            // 计算矩阵
            int[][] f = new int[m][m + 1];
            // 起点值
//            f[0][0] = triangle.get(0).get(0);
            // 动态规划：遍历计算
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < i + 1; j++) {
                    int top = Integer.MAX_VALUE;
                    if (j != i) {
                        top = f[i - 1][j];
                    }
                    int topLeft = Integer.MAX_VALUE;
                    if (j != 0) {
                        topLeft = f[i - 1][j - 1];
                    }
                    // 起点值
                    if (i == 0 && j == 0) {
                        f[i][j] = triangle.get(0).get(0);
                    } else {
                        // 状态转移方程：核心递归计算公式
                        f[i][j] = Math.min(top, topLeft) + triangle.get(i).get(j);
                    }
                }
            }

            int result = Integer.MAX_VALUE;
            // 遍历f[][]最后一行中，最小值
            for (int i = 0; i < m; i++) {
                result = Math.min(result, f[m - 1][i]);
            }
            return result;
        }
    }

}

//120. 三角形最小路径和
//        给定一个三角形 triangle ，找出自顶向下的最小路径和。
//        每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
//        也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
//        示例 1：
//        输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
//        输出：11
//        解释：如下面简图所示：
//        2
//        3 4
//        6 5 7
//        4 1 8 3
//        自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
//        示例 2：
//        输入：triangle = [[-10]]
//        输出：-10
//        提示：
//        1 <= triangle.length <= 200
//        triangle[0].length == 1
//        triangle[i].length == triangle[i - 1].length + 1
//        -104 <= triangle[i][j] <= 104
//        进阶：
//        你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题吗？

