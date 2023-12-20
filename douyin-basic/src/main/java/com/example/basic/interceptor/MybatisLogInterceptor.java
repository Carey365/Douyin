package com.example.basic.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * mybatis日志拦截器
 *
 * @author yuchengying
 * @date 2023/07/31
 */
@Slf4j
@Profile({"local", "test"})
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})

public class MybatisLogInterceptor implements Interceptor {

    private static final List<String> LIST = Arrays.asList("String", "Time", "LocalDate", "LocalTime", "LocalDateTime", "BigDecimal", "Timestamp");

    private static final Set<String> NEED_BRACKETS = new HashSet<>(LIST);


    private Configuration configuration = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        long startTime = System.currentTimeMillis();
        try {
            // 如需打印结果, 返回值即结果集
            return invocation.proceed();
        } finally {
            long sqlCost = System.currentTimeMillis() - startTime;
            String sql = this.getSql(target);
            log.info("sql语句 took {}ms", sqlCost);
        }
    }

    /**
     * 获取sql
     */
    private String getSql(Object target) {
        try {
            StatementHandler statementHandler = (StatementHandler) target;
            BoundSql boundSql = statementHandler.getBoundSql();
            if (configuration == null) {
                final ParameterHandler parameterHandler = statementHandler.getParameterHandler();
                this.configuration = (Configuration) FieldUtils.readField(parameterHandler, "configuration", true);
            }
            // 替换参数格式化Sql语句，去除换行符
            return formatSql(boundSql, configuration);
        } catch (Exception e) {
            log.warn("get sql error {}", target, e);
            return "failed to parse sql";
        }
    }

    /**
     * 获取完整的sql实体的信息
     */
    private String formatSql(BoundSql boundSql, Configuration configuration) {
        String sql = boundSql.getSql();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        // 输入sql字符串空判断
        if (StringUtils.isEmpty(sql) || Objects.isNull(configuration)) {
            return "";
        }

        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        // 替换空格容易造成本身存在空格的查询条件被替换
        sql = sql.replaceAll("[\n\r ]+", " ");

        if (parameterMappings == null) {
            return sql;
        }

        parameterMappings = parameterMappings.stream().filter(it -> it.getMode() != ParameterMode.OUT).collect(Collectors.toList());

        final StringBuilder result = new StringBuilder(sql);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 解析问号并填充
        for (int i = result.length(); i > 0; i--) {
            if (result.charAt(i - 1) != '?') {
                continue;
            }
            ParameterMapping parameterMapping = parameterMappings.get(parameterMappings.size() - 1);
            Object value;
            String propertyName = parameterMapping.getProperty();
            if (boundSql.hasAdditionalParameter(propertyName)) {
                value = boundSql.getAdditionalParameter(propertyName);
            } else if (parameterObject == null) {
                value = null;
            } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                value = parameterObject;
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                value = metaObject.getValue(propertyName);
            }
            if (value != null) {
                String type = value.getClass().getSimpleName();
                if (NEED_BRACKETS.contains(type)) {
                    result.replace(i - 1, i, "'" + value + "'");
                } else if ("Date".equals(type)) {
                    result.replace(i - 1, i, "'" + simpleDateFormat.format(value) + "'");
                } else {
                    result.replace(i - 1, i, value.toString());
                }
            } else {
                result.replace(i - 1, i, "null");
            }
            parameterMappings.remove(parameterMappings.size() - 1);
        }
        return result.toString();
    }

}
