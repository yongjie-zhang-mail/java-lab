package com.yj.lab.spring.alog.dfs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Dfs17 {

    static char[][] m = new char[][]{
            {},
            {},
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'},
            {'j', 'k', 'l'},
            {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'},
            {'t', 'u', 'v'},
            {'w', 'x', 'y', 'z'}
    };

    /**
     * 17. 电话号码的字母组合
     *
     * @param args
     */
    public static void main(String[] args) {

        log.info(JSON.toJSONString(letterCombinations("23")));
        log.info(JSON.toJSONString(letterCombinations("239")));

    }

    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.length() == 0) {
            return result;
        }
        dfs(digits, 0, new StringBuilder(), result);
        return result;
    }

    static void dfs(String digits, int index, StringBuilder sb, List<String> result) {

        // 1 截止条件
        if (index == digits.length()) {
            result.add(sb.toString());
            return;
        }

        // 2 候选节点
        int mIndex = digits.charAt(index) - '0';
        for (char c : m[mIndex]) {
            sb.append(c);
            dfs(digits, index + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }


    }

}
