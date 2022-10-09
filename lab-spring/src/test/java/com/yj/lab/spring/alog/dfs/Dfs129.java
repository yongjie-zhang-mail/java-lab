package com.yj.lab.spring.alog.dfs;

/**
 * 129. 求根节点到叶节点数字之和
 */
public class Dfs129 {

    public static void main(String[] args) {

    }

    class Solution {
        public int sumNumbers(TreeNode root) {
            int preSum = 0;
            return dfs(root, preSum);
        }

        private int dfs(TreeNode t, int preSum) {
            // 校验
            if (t == null) {
                return 0;
            }
            int sum = t.val + preSum * 10;
            // 截止条件
            if (t.left == null && t.right == null) {
                return sum;
            } // 候选节点
            else {
                return dfs(t.left, sum) + dfs(t.right, sum);
            }
        }
    }
}
