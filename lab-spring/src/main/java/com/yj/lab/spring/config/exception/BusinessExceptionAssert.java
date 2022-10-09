package com.yj.lab.spring.config.exception;

import com.yj.lab.spring.model.enums.ReturnCode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @author zhangyj21
 * 业务异常 BusinessException 的 断言抛出接口；
 * 创建Enum继承该接口，即可使用该接口中的断言方法；参考ReturnCode
 */
public interface BusinessExceptionAssert {
    /**
     * 接口方法，获取 Code
     *
     * @return Code
     */
    String getCode();

    /**
     * 接口方法：获取 Message
     *
     * @return Message
     */
    String getMsg();

    /**
     * 断言为 【True】;
     * 若为 【False】，抛出 Exception
     *
     * @param expression 表达式
     * @param extraMsg   传入的额外信息，可不传
     */
    default void assertTrue(boolean expression, String... extraMsg) {
        if (!expression) {
            throwBizException(extraMsg);
        }
    }

    /**
     * 断言为 【False】;
     * 若为 【True】，抛出 Exception
     *
     * @param expression 表达式
     * @param extraMsg   传入的额外信息，可不传
     */
    default void assertFalse(boolean expression, String... extraMsg) {
        if (expression) {
            throwBizException(extraMsg);
        }
    }

    /**
     * 断言为 【对象非空】;
     * 若不为 【对象非空】，抛出 Exception
     *
     * @param o        对象
     * @param extraMsg 传入的额外信息，可不传
     */
    default void assertNotNull(Object o, String... extraMsg) {
        if (Objects.isNull(o)) {
            throwBizException(extraMsg);
        }
    }

    /**
     * 断言为 【集合非空】;
     * 若不为 【集合非空】，抛出 Exception
     *
     * @param collection 集合
     * @param extraMsg   传入的额外信息，可不传
     */
    default void assertNotEmpty(Collection<?> collection, String... extraMsg) {
        if (CollectionUtils.isEmpty(collection)) {
            throwBizException(extraMsg);
        }
    }

    private void throwBizException(String[] extraMsg) {
        throw new BusinessException(this.getCode(), this.getMsg().concat(String.join(", ", extraMsg)));
    }

    /**
     * 测试：断言类写法，抛出异常，并有Enum定义好的Return Code 和 Message
     *
     * @param args 入参
     */
    static void main(String[] args) {
        Object o = null;
        ReturnCode.NULL.assertNotNull(o, "测试断言1", "测试断言2", "测试断言3");
        ReturnCode.NULL.assertNotNull(o);

        ReturnCode.FAIL.assertTrue(false, "aa", "bb", "cc");
        ReturnCode.FAIL.assertTrue(false);
    }

}
















