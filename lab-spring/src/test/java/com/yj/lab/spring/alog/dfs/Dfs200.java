package com.yj.lab.spring.alog.dfs;

/**
 * 200. 岛屿数量
 */
public class Dfs200 {

    public static void main(String[] args) {

        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        int i = Solution.numIslands(grid);
        System.out.println(i);

//        char[][] grid = new char[][]{
//                {'1', '1', '1', '1', '0'},
//                {'1', '1', '0', '1', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '0', '0', '0'}
//        };
//
//        int i = Solution.numIslands(grid);
//        System.out.println(i);

    }

    static class Solution {
        public static int numIslands(char[][] grid) {
            int result = 0;
            if (grid.length == 0 || grid[0].length == 0) {
                return 0;
            }

            int x = grid.length;
            int y = grid[0].length;

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (grid[i][j] == '1') {
                        result = result + 1;
                        dfs(grid, i, j);
                    }
                }
            }

            return result;
        }

        private static void dfs(char[][] grid, int i, int j) {
            int x = grid.length;
            int y = grid[0].length;

            // 截止条件
            if (grid[i][j] == '0') {
                return;
            }
            // 更新状态
            grid[i][j] = '0';
            // 候选节点
            if (i - 1 >= 0) dfs(grid, i - 1, j);
            if (i + 1 <= x - 1) dfs(grid, i + 1, j);
            if (j - 1 >= 0) dfs(grid, i, j - 1);
            if (j + 1 <= y - 1) dfs(grid, i, j + 1);
        }
    }

}
