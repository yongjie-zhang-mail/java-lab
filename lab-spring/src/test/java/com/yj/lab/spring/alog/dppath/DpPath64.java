package com.yj.lab.spring.alog.dppath;

public class DpPath64 {

    public static void main(String[] args) {
//        示例 1：
//        输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
//        输出：7
//        解释：因为路径 1→3→1→1→1 的总和最小。
//        示例 2：
//        输入：grid = [[1,2,3],[4,5,6]]
//        输出：12
        int[][] grid = new int[][]{
                {1, 3, 1}, {1, 5, 1}, {4, 2, 1}
        };
//        int[][] grid = new int[][]{
//                {1, 2, 3}, {4, 5, 6}
//        };
        int i = Solution.minPathSum(grid);
        System.out.println(i);

    }

    static class Solution {
        public static int minPathSum(int[][] grid) {
            // m 行 n 列
            int m = grid.length;
            int n = grid[0].length;
            // f[i][j] 代表 g[0][0]点 -> g[i][j]点，最低权重点的[]；
            // f[i][j] = min(左点f[i-1][j], 上点f[i][j-1]) + g[i][j]当前点权重；
            int[][] f = new int[m][n];
            // 起点值
//            f[0][0] = grid[0][0];
            // 找到终点，倒推；转换公式；
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // 上点，若i==0，则不存在上点；设置为MAX值，后面取最小值时，会被计算为不存在；
                    int top = Integer.MAX_VALUE;
                    if (i != 0) {
                        top = f[i - 1][j];
                    }
                    // 左点，同上逻辑
                    int left = Integer.MAX_VALUE;
                    if (j != 0) {
                        left = f[i][j - 1];
                    }
                    // 当前点值=上点 和 左点 中，最小值，再加上当前点的值；
                    if (i == 0 && j == 0) {
                        // 起点值
                        f[i][j] = grid[0][0];
                    } else {
                        f[i][j] = Math.min(top, left) + grid[i][j];
                    }
                }
            }
            // 结果=最后一个元素的值
            return f[m - 1][n - 1];
        }
    }

}

//64. 最小路径和
//        给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//        说明：每次只能向下或者向右移动一步。
//        示例 1：
//        输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
//        输出：7
//        解释：因为路径 1→3→1→1→1 的总和最小。
//        示例 2：
//        输入：grid = [[1,2,3],[4,5,6]]
//        输出：12
//        提示：
//        m == grid.length
//        n == grid[i].length
//        1 <= m, n <= 200
//        0 <= grid[i][j] <= 100

