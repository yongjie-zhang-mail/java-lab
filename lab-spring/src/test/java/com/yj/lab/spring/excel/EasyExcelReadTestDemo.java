package com.yj.lab.spring.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.lab.spring.model.rdb.entity.medical.DemoData;
import com.yj.lab.spring.model.rdb.mapper.medical.DemoDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

/**
 * @author Zhang Yongjie
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EasyExcelReadTestDemo {

    /**
     * 指定列的下标或者列名
     *
     * <p>
     * 1. 创建excel对应的实体对象,并使用{@link ExcelProperty}注解. 参照{@link IndexOrNameData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link IndexOrNameDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();
    }

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void repeatedRead() {

        String fileName = TestFileUtil.getPath() + "excel" + File.separator + "demo.xlsx";
        /*
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).doReadAll();
        */

        // 读取部分sheet
        fileName = TestFileUtil.getPath() + "excel" + File.separator + "demo.xlsx";

        // 写法1
        try (ExcelReader excelReader = EasyExcel.read(fileName).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        }

    }

    @Autowired
    private DemoDataMapper demoDataMapper;

    /**
     * 读取多个 sheet 页，并将结果写入数据库
     */
    @Test
    public void repeatedRead2() {

        // 读取 多sheet页 excel，存储在 list 中
        String fileName = TestFileUtil.getPath() + "excel" + File.separator + "demo.xlsx";

        DataListener<DemoData> dl1 = new DataListener<>();
        DataListener<DemoData> dl2 = new DataListener<>();

        try (ExcelReader excelReader = EasyExcel.read(fileName).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(dl1).build();
            ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(dl2).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        }

        List<DemoData> sd1 = dl1.getSheetData();
        List<DemoData> sd2 = dl2.getSheetData();

        // 查询 rdb
        LambdaQueryWrapper<DemoData> where = new QueryWrapper<DemoData>().lambda().eq(DemoData::getDoubleData, 1);
        List<DemoData> demoData = demoDataMapper.selectList(where);

        // 删除 rdb
        int delete = demoDataMapper.delete(null);

        // 写入 rdb
        sd1.forEach(record -> {
            demoDataMapper.insert(record);
        });

        // 将 list 写入 数据库


    }


}



















