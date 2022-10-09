package com.yj.lab.spring.medical;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.lab.spring.excel.DataListener;
import com.yj.lab.spring.excel.TestFileUtil;
import com.yj.lab.spring.model.excel.ExcelTumorAssessmentTargetLesionsBak;
import com.yj.lab.spring.model.rdb.entity.medical.TumorAssessmentTargetLesions;
import com.yj.lab.spring.model.rdb.mapper.medical.TumorAssessmentTargetLesionsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author Zhang Yongjie
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicalAppBak {

    @Autowired
    private TumorAssessmentTargetLesionsMapper tatlMapper;

    /**
     * 总流程
     */
    @Test
    public void totalProcess() {
        extractMedicalData();
        inputValidation();
        calculateEfficacyCR();
        calculateEfficacyPRPDSD();
    }

    /**
     * 抽取数据，写入数据库
     */
    @Test
    public void extractMedicalData() {
        // 读取 多sheet页 excel，存储在 list 中
        String fileName = TestFileUtil.getPath() + "excel" + File.separator + "Data listing for medical review_FN1502_dc_01NOV21.xlsx";

        DataListener<ExcelTumorAssessmentTargetLesionsBak> dl1 = new DataListener<>();
//        DataListener<DemoData> dl2 = new DataListener<>();

        try (ExcelReader excelReader = EasyExcel.read(fileName).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 = EasyExcel.readSheet("肿瘤评估-靶病灶(筛选+后续评估)")
                    .head(ExcelTumorAssessmentTargetLesionsBak.class).registerReadListener(dl1).build();
//            ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(dl2).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1);
        }

        List<ExcelTumorAssessmentTargetLesionsBak> sd1 = dl1.getSheetData();
//        List<DemoData> sd2 = dl2.getSheetData();

        // 查询 rdb
        LambdaQueryWrapper<TumorAssessmentTargetLesions> where = new QueryWrapper<TumorAssessmentTargetLesions>().lambda()
                .eq(TumorAssessmentTargetLesions::getScreenNumber, "S01010");
        List<TumorAssessmentTargetLesions> tatlData = tatlMapper.selectList(where);

        // 删除 rdb
        int delete = tatlMapper.delete(null);

        // 写入 rdb
        sd1.forEach(source -> {
            TumorAssessmentTargetLesions target = new TumorAssessmentTargetLesions();
            BeanUtils.copyProperties(source, target);
            tatlMapper.insert(target);
        });

        // 数据清洗
        dataClean();

    }

    /**
     * 数据清洗
     */
    private void dataClean() {
        // 数据清洗
        // 去掉 is_assess = '否' 的数据
        LambdaQueryWrapper<TumorAssessmentTargetLesions> deleteWhere1 = new QueryWrapper<TumorAssessmentTargetLesions>().lambda()
                .eq(TumorAssessmentTargetLesions::getIsAssess, "否");

        int delete1 = tatlMapper.delete(deleteWhere1);

        // 数据清洗
        // 将 assessment_cycles 为空的数据，更新 assessment_cycles = 0
        LambdaQueryWrapper<TumorAssessmentTargetLesions> updateWhere = new QueryWrapper<TumorAssessmentTargetLesions>().lambda()
                .eq(TumorAssessmentTargetLesions::getEcrf, "肿瘤评估-靶病灶(筛选期)")
                .isNull(TumorAssessmentTargetLesions::getAssessmentCycles);

        int update = tatlMapper.update(
                new TumorAssessmentTargetLesions().setAssessmentCycles(0),
                updateWhere
        );

        // 数据清洗
        // 若经过上面的处理，还是有 周期为空的，则删除
        LambdaQueryWrapper<TumorAssessmentTargetLesions> deleteWhere2 = new QueryWrapper<TumorAssessmentTargetLesions>().lambda()
                .isNull(TumorAssessmentTargetLesions::getAssessmentCycles);

        int delete2 = tatlMapper.delete(deleteWhere2);

        // 数据清洗
        // 若 靶病灶最大直径总和 为空，则 设置为 0
        LambdaQueryWrapper<TumorAssessmentTargetLesions> updateWhere2 = new QueryWrapper<TumorAssessmentTargetLesions>().lambda()
                .isNull(TumorAssessmentTargetLesions::getTargetLesionsTotalMaximumDiameters);

        int update2 = tatlMapper.update(
                new TumorAssessmentTargetLesions().setTargetLesionsTotalMaximumDiameters(BigDecimal.valueOf(0)),
                updateWhere2
        );

    }

    /**
     * 数据校验
     */
    @Test
    public void inputValidation() {

        //# 校验：
        //# 评估周期为0时，类型为淋巴结的，病灶最大直径 < 15，为错误数据；
        LambdaQueryWrapper<TumorAssessmentTargetLesions> selectWhere1 =
                new QueryWrapper<TumorAssessmentTargetLesions>().lambda()
                        .eq(TumorAssessmentTargetLesions::getAssessmentCycles, 0)
                        .eq(TumorAssessmentTargetLesions::getLesionsTypes, "淋巴结")
                        .lt(TumorAssessmentTargetLesions::getLesionsMaximumDiameter, BigDecimal.valueOf(15));

        List<TumorAssessmentTargetLesions> wrongDataLinba = tatlMapper.selectList(selectWhere1);
        wrongDataLinba.forEach(r -> {
            r.setResult("0");
            tatlMapper.updateById(r);
        });

        //# 校验：
        //# 评估周期为0时，类型为非淋巴结的，病灶最大直径 < 10，为错误数据；
        LambdaQueryWrapper<TumorAssessmentTargetLesions> selectWhere2 =
                new QueryWrapper<TumorAssessmentTargetLesions>().lambda()
                        .eq(TumorAssessmentTargetLesions::getAssessmentCycles, 0)
                        .eq(TumorAssessmentTargetLesions::getLesionsTypes, "非淋巴结")
                        .lt(TumorAssessmentTargetLesions::getLesionsMaximumDiameter, BigDecimal.valueOf(10));

        List<TumorAssessmentTargetLesions> wrongDataNonLinba = tatlMapper.selectList(selectWhere1);
        wrongDataNonLinba.forEach(r -> {
            r.setResult("0");
            tatlMapper.updateById(r);
        });

        //# 校验：
        //# 同一个人，同一个评估周期，SUM(病灶最大直径) <> 靶病灶最大直径总和，为错误数据；
        List<TumorAssessmentTargetLesions> tatlList = tatlMapper.selectList(null);
        // 根据 人 分组，再根据 周期 分组
        Map<String, Map<Integer, List<TumorAssessmentTargetLesions>>> group1 = tatlList.stream()
                .collect(Collectors.groupingBy(TumorAssessmentTargetLesions::getScreenNumber,
                        Collectors.groupingBy(TumorAssessmentTargetLesions::getAssessmentCycles)));

        group1.forEach((screenNumber, map1) -> {
            // screenNumber
            map1.forEach((cycle, records) -> {
                // assessmentCycles
                BigDecimal sum = records.stream().map(TumorAssessmentTargetLesions::getLesionsMaximumDiameter)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                records.forEach(r -> {
                    if (r.getTargetLesionsTotalMaximumDiameters().compareTo(sum) != 0) {
                        r.setResult("0");
                        tatlMapper.updateById(r);
                    }
                });
            });
        });
    }

    /**
     * 计算疗效 CR 部分
     */
    @Test
    public void calculateEfficacyCR() {

        List<TumorAssessmentTargetLesions> tatlList = tatlMapper.selectList(null);
        // 根据 人 分组，再根据 周期 分组
        Map<String, Map<Integer, List<TumorAssessmentTargetLesions>>> group1 = tatlList.stream()
                .collect(Collectors.groupingBy(TumorAssessmentTargetLesions::getScreenNumber,
                        Collectors.groupingBy(TumorAssessmentTargetLesions::getAssessmentCycles)));

        group1.forEach((screenNumber, map1) -> {
            // screenNumber
            map1.forEach((cycle, records) -> {
                // assessmentCycles
                List<TumorAssessmentTargetLesions> nonLinba = records.stream().filter(record -> {
                    return Objects.equals(record.getLesionsTypes(), "非淋巴结");
                }).collect(Collectors.toList());

                List<TumorAssessmentTargetLesions> linba = records.stream().filter(record -> {
                    return Objects.equals(record.getLesionsTypes(), "淋巴结");
                }).collect(Collectors.toList());

                // 所有 非淋巴 都 = 0; 所有 淋巴 都 < 10
                boolean bNonLinba = false;
                boolean bLinba = false;

                if (CollectionUtils.isEmpty(nonLinba)) {
                    bNonLinba = true;
                } else {
                    bNonLinba = nonLinba.stream().allMatch(r ->
                            r.getLesionsMaximumDiameter().compareTo(BigDecimal.valueOf(0)) == 0);
                }

                if (CollectionUtils.isEmpty(linba)) {
                    bLinba = true;
                } else {
                    bLinba = linba.stream().allMatch(r ->
                            r.getLesionsMaximumDiameter().compareTo(BigDecimal.valueOf(10)) < 0);
                }

                if (bNonLinba && bLinba) {
                    records.forEach(r -> {
                        r.setEfficacy("CR");
                        tatlMapper.updateById(r);
                    });
                }

            });
        });


    }

    /**
     * 计算疗效 PR PD SD 部分
     *
     * <p>
     * fsss
     */
    @Test
    public void calculateEfficacyPRPDSD() {

        List<TumorAssessmentTargetLesions> tatlList = tatlMapper.selectList(null);
        // 根据 人 分组
        Map<String, List<TumorAssessmentTargetLesions>> group1 = tatlList.stream()
                .collect(Collectors.groupingBy(TumorAssessmentTargetLesions::getScreenNumber, Collectors.toList()));

        group1.forEach((screenNumber, list) -> {
            // screenNumber 受试者编号

            // 根据 周期 和 病灶序号 排序
            List<TumorAssessmentTargetLesions> sortedList = list.stream()
                    .sorted(Comparator.comparing(TumorAssessmentTargetLesions::getAssessmentCycles)
                            .thenComparing(TumorAssessmentTargetLesions::getLesionsNumber))
                    .collect(Collectors.toList());

            // 若 该 受试者 不存在 周期0，则认为数据错误，跳过 该受试者，不做计算
            TumorAssessmentTargetLesions first = sortedList.stream().findFirst().orElse(null);
            if (!Objects.isNull(first)) {
                if (!Objects.equals(first.getAssessmentCycles(), 0)) {
                    return;
                }
            }

            BigDecimal cycle0Value = BigDecimal.ZERO;

            for (TumorAssessmentTargetLesions r : sortedList) {

                // 获取 当前周期 和 当前周期的值
                Integer currentCycle = r.getAssessmentCycles();
                BigDecimal currentCycleValue = r.getTargetLesionsTotalMaximumDiameters();

                // 获取 周期0的值
                if (Objects.equals(currentCycle, 0)) {
                    cycle0Value = currentCycleValue;
                }
                // 若为周期0，不做计算
                if (Objects.equals(currentCycle, 0)) {
                    continue;
                }

                // 计算截止到目前的周期，之前历史周期中的最小值
                BigDecimal historyMinValue = sortedList.stream().filter(r2 -> r2.getAssessmentCycles() < currentCycle)
                        .map(TumorAssessmentTargetLesions::getTargetLesionsTotalMaximumDiameters)
                        .min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO);

                // 评估a = （【当前周期-靶病灶最大直径总和】 - 【历史最小-靶病灶最大直径总和】) / 【历史最小-靶病灶最大直径总和】
                // 评估a >= 0.20 && （【当前周期-靶病灶最大直径总和】 - 【历史最小-靶病灶最大直径总和】) >= 5
                // PD（疾病进展）Progressive Disease

                // 若 【历史最小-靶病灶最大直径总和】 为0， 近似设置为 0.01
                if (BigDecimal.ZERO.compareTo(historyMinValue) == 0) {
//                    log.info("错误，除数为0");
//                    log.info(JSON.toJSONString(r));
//                    continue;
                    historyMinValue = BigDecimal.valueOf(0.01);
                }

                boolean bPD = false;
                BigDecimal result1 = currentCycleValue.subtract(historyMinValue)
                        .divide(historyMinValue, 2, RoundingMode.FLOOR);

                if (result1.compareTo(BigDecimal.valueOf(0.20)) >= 0) {
                    bPD = true;
                }

                // 评估b = （【当前周期-靶病灶最大直径总和】 - 【周期0-靶病灶最大直径总和】) / 【周期0-靶病灶最大直径总和】
                // 评估b <= -0.30
                // PR（部分缓解）Partial Response

                // 若 【周期0-靶病灶最大直径总和】 为0， 近似设置为 0.01
                if (BigDecimal.ZERO.compareTo(cycle0Value) == 0) {
//                    log.info("错误，除数为0");
//                    log.info(JSON.toJSONString(r));
//                    continue;
                    cycle0Value = BigDecimal.valueOf(0.01);
                }

                boolean bPR = false;
                BigDecimal result2 = currentCycleValue.subtract(cycle0Value)
                        .divide(cycle0Value, 2, RoundingMode.FLOOR);

                if (result2.compareTo(BigDecimal.valueOf(-0.30)) <= 0) {
                    bPR = true;
                }

                // 判断逻辑
                // 若 PD 存在
                //      为PD；
                // 若 PD 不存在
                //      若 PR 存在，为 PR；
                //      若 PR 不存在，为 SD（疾病稳定）Stable Disease
                String finalResult = null;
                if (bPD) {
                    finalResult = "PD";
                } else {
                    if (bPR) {
                        finalResult = "PR";
                    } else {
                        finalResult = "SD";
                    }
                }

                // 若 疗效还没有值 (CR)，并且 finalResult 有计算的结果，则更新 汇总计算的 结果
                if (StringUtils.isBlank(r.getEfficacy()) && StringUtils.isNotBlank(finalResult)) {
                    r.setEfficacy(finalResult);
                    tatlMapper.updateById(r);
                }


            }


        });

    }


    public static void main(String[] args) {

        BigDecimal result = BigDecimal.TEN.subtract(BigDecimal.ONE)
                .divide(BigDecimal.valueOf(3), 2, RoundingMode.FLOOR);

        System.out.println(result);


    }
}



















