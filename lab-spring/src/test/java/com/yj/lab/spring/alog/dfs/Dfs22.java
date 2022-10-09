package com.yj.lab.spring.alog.dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dfs22 {

    public static void main(String[] args) {

//        List<String> result = Solution.generateParenthesis(3);
//        System.out.println(result);
//
//        List<String> result = Solution.generateParenthesis(5);
//        System.out.println(result);

//        List<String> result = Solution.generateParenthesis(1);
//        System.out.println(result);

        List<String> result = Solution2.generateParenthesis(3);
        System.out.println(result);

    }

    static class Solution2 {

        private static final Character LEFT = '(';
        private static final Character RIGHT = ')';

        public static List<String> generateParenthesis(int n) {
            List<String> result = new ArrayList<>();

            Map<Character, Integer> map = new HashMap<>();
            map.put(LEFT, n);
            map.put(RIGHT, n);

            String temp = "";
            dfs(n, map, temp, result);
            return result;
        }

        private static void dfs(int n, Map<Character, Integer> map, String temp, List<String> result) {
            // 截止条件
            if (temp.length() == n * 2) {
                result.add(temp);
            }

            // 候选节点
            Integer leftCount = map.get(LEFT);
            Integer rightCount = map.get(RIGHT);

            if (leftCount > 0) {
                map.put(LEFT, leftCount - 1);
                dfs(n, map, temp + LEFT, result);
                map.put(LEFT, leftCount);
            }

            if (rightCount > 0 && rightCount > leftCount) {
                map.put(RIGHT, rightCount - 1);
                dfs(n, map, temp + RIGHT, result);
                map.put(RIGHT, rightCount);
            }
        }
    }

    static class Solution {
        public static List<String> generateParenthesis(int n) {
            List<String> result = new ArrayList<>();
            char[] p = new char[]{'(', ')'};
            int[] pb = new int[]{n, n};
            StringBuilder sb = new StringBuilder();
            dfs(n, p, pb, sb, result);
            return result;
        }

        private static void dfs(int n, char[] p, int[] pb, StringBuilder sb, List<String> result) {

            // 截止条件
            if (sb.length() == n * 2) {
                result.add(sb.toString());
                return;
            }

            // 候选节点
            if (pb[0] > 0) {
                pb[0]--;
                dfs(n, p, pb, sb.append(p[0]), result);
                sb.deleteCharAt(sb.length() - 1);
                pb[0]++;
            }

            if (pb[1] > 0 && pb[1] > pb[0]) {
                pb[1]--;
                dfs(n, p, pb, sb.append(p[1]), result);
                sb.deleteCharAt(sb.length() - 1);
                pb[1]++;
            }


        }


    }


}
