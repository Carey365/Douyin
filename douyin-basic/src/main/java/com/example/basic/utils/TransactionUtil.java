package com.example.basic.utils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionUtil {
    public static void main(String[] args) {
        // 获取事务管理器
        PlatformTransactionManager transactionManager = getTransactionManager();

        // 定义事务属性
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);

        try {
            // 执行业务逻辑
            performBusinessLogic();

            // 提交事务
            transactionManager.commit(status);
        } catch (Exception e) {
            // 发生异常时回滚事务
            transactionManager.rollback(status);
            e.printStackTrace();
        }
    }

    private static void performBusinessLogic() {
        // 在这里执行你的业务逻辑
        System.out.println("Performing business logic...");
    }

    private static PlatformTransactionManager getTransactionManager() {
        // 在实际应用中，你可能需要根据你的配置获取合适的事务管理器
        // 这里简单示例，实际中可能涉及到 Spring 配置等
        return new DataSourceTransactionManager();
    }
}

