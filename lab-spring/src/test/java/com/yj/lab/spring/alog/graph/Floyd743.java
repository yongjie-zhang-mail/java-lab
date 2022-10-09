package com.yj.lab.spring.alog.graph;

public class Floyd743 {

    public static void main(String[] args) {
//        int[][] times = new int[][]{
//                {2, 1, 1},
//                {2, 3, 1},
//                {3, 4, 1}
//        };
//        int n = 4, k = 2;

//        int[][] times = new int[][]{
//                {1, 2, 1}
//        };
//        int n = 2, k = 1;

//        int[][] times = new int[][]{
//                {1, 2, 1}
//        };
//        int n = 2, k = 2;

        int[][] times = new int[][]{
                {2, 4, 10}, {5, 2, 38}, {3, 4, 33}, {4, 2, 76}, {3, 2, 64}, {1, 5, 54}, {1, 4, 98},
                {2, 3, 61}, {2, 1, 0}, {3, 5, 77}, {5, 1, 34}, {3, 1, 79}, {5, 3, 2},
                {1, 2, 59}, {4, 3, 46}, {5, 4, 44}, {2, 5, 89}, {4, 5, 21}, {1, 3, 86}, {4, 1, 95}
        };
        int n = 5, k = 1;
        int i = Solution.networkDelayTime(times, n, k);
        System.out.println(i);

    }

    static class Solution {

        static int N = 110;
        static int INF = 1000000000;
        static int[][] w = new int[N][N];
        static int n;
        static int k;

        public static int networkDelayTime(int[][] times, int _n, int _k) {

            n = _n;
            k = _k;

            // 初始化邻接矩阵
            // 自己到自己为0，其余暂时设置为无穷
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    w[i][j] = INF;
                    if (i == j) {
                        w[i][j] = 0;
                    }
                }
            }
            // 存图，将图的信息，保存如邻接矩阵
            for (int[] path : times) {
                int start = path[0], end = path[1], len = path[2];
                w[start][end] = len;
            }
            floyd();

            int result = 0;
            for (int i = 1; i <= n; i++) {
                result = Math.max(w[k][i], result);
            }
            if (result > INF / 2) {
                result = -1;
            }

            return result;
        }

        private static void floyd() {
            // floyd 算法：遍历中间节点p，起点i，终点j，松弛方法；
            for (int p = 1; p <= n; p++) {
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= n; j++) {
                        // 看看直达距离短，还是中转距离短
                        w[i][j] = Math.min(w[i][j], w[i][p] + w[p][j]);
                    }
                }
            }
        }
    }

}

//
//
//743. 网络延迟时间
//
//        有 n 个网络节点，标记为 1 到 n。
//
//        给你一个列表 times，表示信号经过 有向 边的传递时间。 times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间。
//
//        现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。
//
//
//
//        示例 1：
//
//        输入：times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
//        输出：2
//
//        示例 2：
//
//        输入：times = [[1,2,1]], n = 2, k = 1
//        输出：1
//
//        示例 3：
//
//        输入：times = [[1,2,1]], n = 2, k = 2
//        输出：-1
//
//
//
//        提示：
//
//        1 <= k <= n <= 100
//        1 <= times.length <= 6000
//        times[i].length == 3
//        1 <= ui, vi <= n
//        ui != vi
//        0 <= wi <= 100
//        所有 (ui, vi) 对都 互不相同（即，不含重复边）

