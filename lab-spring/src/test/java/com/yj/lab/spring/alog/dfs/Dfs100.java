package com.yj.lab.spring.alog.dfs;

public class Dfs100 {

    /**
     * 100. 相同的树
     *
     * @param args
     */

    public static void main(String[] args) {

//        输入：p = [1,2,3], q = [1,2,3]
//        输出：true
//        TreeNode ta2 = new TreeNode();
//        ta2.val = 2;
//        ta2.left = null;
//        ta2.right = null;
//        TreeNode ta3 = new TreeNode();
//        ta3.val = 3;
//        ta3.left = null;
//        ta3.right = null;
//        TreeNode ta1 = new TreeNode();
//        ta1.val = 1;
//        ta1.left = ta2;
//        ta1.right = ta3;
//
//        TreeNode tb2 = new TreeNode();
//        tb2.val = 2;
//        tb2.left = null;
//        tb2.right = null;
//        TreeNode tb3 = new TreeNode();
//        tb3.val = 3;
//        tb3.left = null;
//        tb3.right = null;
//        TreeNode tb1 = new TreeNode();
//        tb1.val = 1;
//        tb1.left = tb2;
//        tb1.right = tb3;
//
//        boolean result = Solution.isSameTree(ta1, tb1);
//        System.out.println(result);

//        输入：p = [1,2], q = [1,null,2]
//        输出：false
        TreeNode ta2 = new TreeNode();
        ta2.val = 2;
        ta2.left = null;
        ta2.right = null;
        TreeNode ta1 = new TreeNode();
        ta1.val = 1;
        ta1.left = ta2;
        ta1.right = null;

        TreeNode tb2 = new TreeNode();
        tb2.val = 2;
        tb2.left = null;
        tb2.right = null;
        TreeNode tb1 = new TreeNode();
        tb1.val = 1;
        tb1.left = null;
        tb1.right = tb2;

        boolean result = Solution.isSameTree(ta1, tb1);
        System.out.println(result);


    }

    static class Solution {
        public static boolean isSameTree(TreeNode p, TreeNode q) {
            if (p == null && q == null) {
                return true;
            }
            if (p == null || q == null) {
                return false;
            }

            if (p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right)) {
                return true;
            }
            return false;
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





















