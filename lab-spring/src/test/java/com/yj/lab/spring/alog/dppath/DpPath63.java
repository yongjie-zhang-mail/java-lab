package com.yj.lab.spring.alog.dppath;

public class DpPath63 {

    public static void main(String[] args) {
        int[][] obstacleGrid = new int[][]{
                {0, 0, 0}, {0, 1, 0}, {0, 0, 0}
        };
//        int[][] obstacleGrid = new int[][]{
//                {0, 1}, {0, 0}
//        };

        int i = Solution.uniquePathsWithObstacles(obstacleGrid);
        System.out.println(i);
    }

    static class Solution {
        public static int uniquePathsWithObstacles(int[][] grid) {

            int m = grid.length;
            int n = grid[0].length;
            int[][] f = new int[m][n];

            f[0][0] = grid[0][0] == 1 ? 0 : 1;

            // 若有障碍物 grid[i][j] = 1，则 f[i][j] = 0
            // 计算结果值时，过滤掉 grid[i][j] = 1的部分
            // 状态装换方程，同62
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] != 1) {
                        if (i > 0 && j > 0) {
                            f[i][j] = f[i][j - 1] + f[i - 1][j];
                        } else if (i > 0) {
                            f[i][j] = f[i - 1][j];
                        } else if (j > 0) {
                            f[i][j] = f[i][j - 1];
                        }
                    }
                }
            }

            return f[m - 1][n - 1];

        }
    }

}

//63. 不同路径 II
//        一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
//        机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
//        现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
//        网格中的障碍物和空位置分别用 1 和 0 来表示。
//        示例 1：
//        输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
//        输出：2
//        解释：
//        3x3 网格的正中间有一个障碍物。
//        从左上角到右下角一共有 2 条不同的路径：
//        1. 向右 -> 向右 -> 向下 -> 向下
//        2. 向下 -> 向下 -> 向右 -> 向右
//        示例 2：
//        输入：obstacleGrid = [[0,1],[0,0]]
//        输出：1
//        提示：
//        m == obstacleGrid.length
//        n == obstacleGrid[i].length
//        1 <= m, n <= 100
//        obstacleGrid[i][j] 为 0 或 1
