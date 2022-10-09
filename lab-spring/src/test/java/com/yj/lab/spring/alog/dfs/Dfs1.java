package com.yj.lab.spring.alog.dfs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Dfs1 {

    /**
     * 排列组合
     *
     * @param args
     */
    public static void main(String[] args) {

        char[] input = new char[]{
                'A', 'B', 'C'
        };

        boolean[] available = new boolean[]{
                true, true, true
        };
        List<String> result = new ArrayList<>();

        dfs(input, available, 0, new StringBuilder(), result);

        log.info(JSON.toJSONString(result));
    }

    static void dfs(char[] input, boolean[] available, int index, StringBuilder temp, List<String> result) {
        // 截止条件
        if (index == input.length) {
            result.add(temp.toString());
            return;
        }

        // 遍历节点
        for (int i = 0; i < input.length; i++) {

            char c = input[i];
            if (available[i]) {
                temp.append(c);
                available[i] = false;
                dfs(input, available, index + 1, temp, result);
                temp.deleteCharAt(temp.length() - 1);
                available[i] = true;
            }
        }


    }


}
