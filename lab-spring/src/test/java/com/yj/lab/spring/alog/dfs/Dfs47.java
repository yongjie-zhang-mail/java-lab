package com.yj.lab.spring.alog.dfs;

import java.util.ArrayList;
import java.util.List;

public class Dfs47 {

    public static void main(String[] args) {

        int[] nums = new int[]{
                1, 1, 2
        };
        List<List<Integer>> result = Solution.permuteUnique(nums);
        System.out.println(result);

//        int[] nums = new int[]{
//                1,2,3
//        };
//        List<List<Integer>> result = Solution.permuteUnique(nums);
//        System.out.println(result);


    }

    static class Solution {
        public static List<List<Integer>> permuteUnique(int[] nums) {

            List<List<Integer>> result = new ArrayList<>();

            boolean[] pbs = new boolean[nums.length];
            List<Integer> temp = new ArrayList<>();
            dfs(nums, pbs, temp, result);

            return result;
        }

        private static void dfs(int[] nums, boolean[] pbs, List<Integer> temp, List<List<Integer>> result) {
            // 截止条件
            if (temp.size() == nums.length) {
                List<Integer> tempSame = new ArrayList<>(temp);
//                Collections.sort(tempSame);
                if (!result.contains(tempSame)) {
                    result.add(tempSame);
                }
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
