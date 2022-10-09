package com.yj.lab.spring.alog.dfs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Dfs39 {


    public static void main(String[] args) {

        int[] candidates = new int[]{
                2, 3, 6, 7
        };
        int target = 7;
        List<List<Integer>> result = Solution.combinationSum(candidates, target);
        log.info(JSON.toJSONString(result));

//        int[] candidates = new int[]{
//                2, 3, 5
//        };
//        int target = 8;
//        List<List<Integer>> result = Solution.combinationSum(candidates, target);
//        log.info(JSON.toJSONString(result));

//        int[] candidates = new int[]{
//                2
//        };
//        int target = 1;
//        List<List<Integer>> result = Solution.combinationSum(candidates, target);
//        log.info(JSON.toJSONString(result));


    }

    static class Solution {
        public static List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> temp = new ArrayList<>();
            dfs(candidates, target, temp, result);
            return result;
        }

        static void dfs(int[] candidates, int target, List<Integer> temp, List<List<Integer>> result) {
            // 截止条件
            int sumTemp = sum(temp);
            if (sumTemp >= target) {
                if (sumTemp == target) {
                    // 去重
//                    List<Integer> sortTemp = temp.stream()
//                            .sorted(Comparator.comparing(Function.identity()))
//                            .collect(Collectors.toList());
                    List<Integer> sortTemp = new ArrayList<>(temp);
                    Collections.sort(sortTemp);
                    if (!result.contains(sortTemp)) {
                        result.add(sortTemp);
                    }
//                    result.add(new ArrayList<>(temp));
                }
                return;
            }

            // 遍历节点
            for (int i = 0; i < candidates.length; i++) {

                int c = candidates[i];
                temp.add(c);
                dfs(candidates, target, temp, result);
                temp.remove(temp.size() - 1);

            }

        }

        static int sum(List<Integer> temp) {

            int sum = 0;
            sum = temp.stream().mapToInt(t -> t).sum();
//            for (Integer i : temp) {
//                sum += i;
//            }
            return sum;

        }


    }

}











































