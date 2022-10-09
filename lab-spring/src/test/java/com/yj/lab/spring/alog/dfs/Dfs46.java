package com.yj.lab.spring.alog.dfs;

import java.util.ArrayList;
import java.util.List;

public class Dfs46 {

    public static void main(String[] args) {
//        int[] nums = new int[]{
//                1, 2, 3
//        };
//        List<List<Integer>> permute = Solution.permute(nums);
//        System.out.println(permute);

        int[] nums = new int[]{
                1, 1, 2
        };
        List<List<Integer>> permute = Solution.permute(nums);
        System.out.println(permute);
    }

    static class Solution {
        public static List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            boolean[] pbs = new boolean[nums.length];
            List<Integer> temp = new ArrayList<>();
            dfs(nums, pbs, temp, result);
            return result;
        }

        static void dfs(int[] nums, boolean[] pbs, List<Integer> temp, List<List<Integer>> result) {
            // 截止条件
            if (temp.size() == nums.length) {
                result.add(new ArrayList<>(temp));
            }

            // 候选节点
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                boolean pb = pbs[i];
                if (!pb) {
                    temp.add(num);
                    pbs[i] = true;
                    dfs(nums, pbs, temp, result);
                    temp.remove(temp.size() - 1);
                    pbs[i] = false;
                }
            }
        }
    }

}
