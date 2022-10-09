package com.yj.lab.spring.alog.dfs;

import java.util.ArrayList;
import java.util.List;

public class Dfs113 {
    /**
     * 113. 路径总和 II
     *
     * @param args
     */
    public static void main(String[] args) {

    }

    static class Solution {

        public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> chain = new ArrayList<>();
            dfs(root, targetSum, chain, result);
            return result;
        }

        private static void dfs(TreeNode t, int targetSum, List<Integer> chain, List<List<Integer>> result) {
            if (t == null) {
                return;
            }
            targetSum = targetSum - t.val;

            chain.add(t.val);

            // 截止条件
            if (t.left == null && t.right == null) {
                if (targetSum == 0) {
                    result.add(new ArrayList<>(chain));
                    return;
                }
            }

            // 候选节点
            if (t.left != null) {
                dfs(t.left, targetSum, chain, result);
                chain.remove(chain.size() - 1);
            }

            if (t.right != null) {
                dfs(t.right, targetSum, chain, result);
                chain.remove(chain.size() - 1);
            }
        }
    }

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
