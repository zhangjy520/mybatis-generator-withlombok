package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.DefaultValue;

import java.util.List;

/**
 * @author zjy
 * @qq 1211079133
 * @date 2019-10-30
 */
public class LombokPlugin extends PluginAdapter {

    private FullyQualifiedJavaType dataAnnotation;
    private FullyQualifiedJavaType idAnnotation;
    private FullyQualifiedJavaType tableAnnotation;
    private FullyQualifiedJavaType defaultAnnotation;

    public LombokPlugin() {
        dataAnnotation = new FullyQualifiedJavaType("lombok.Data");
        idAnnotation = new FullyQualifiedJavaType("javax.persistence.Id");
        tableAnnotation = new FullyQualifiedJavaType("javax.persistence.Table");
        defaultAnnotation = new FullyQualifiedJavaType("lombok.Builder");
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 拦截 普通字段
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass);
        addTableAnnotation(topLevelClass,introspectedTable.getTableConfiguration().getTableName());
        return true;
    }

    /**
     * 拦截 主键
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass);
        return true;
    }

    /**
     * 拦截 blob 类型字段
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass);
        return true;
    }

    /**
     * Prevents all getters from being generated.
     * See SimpleModelGenerator
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    /**
     * Prevents all setters from being generated
     * See SimpleModelGenerator
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (introspectedColumn.getIntrospectedTable().getPrimaryKeyColumns().contains(introspectedColumn)){
          addPrimaryAnnotation(field,topLevelClass);
       }
        for (DefaultValue defaultValue :introspectedTable.getTableConfiguration().getDefaultValues()) {
            if (defaultValue.getColumnName().equals(field.getName())){
                String name = field.getName();
                if ("String".equals(defaultValue.getDataType())){
                    name = field.getName()+" = "+"\""+defaultValue.getColumnValue()+"\"";
                }else if ("int".equals(defaultValue.getDataType())){
                    //数字不操作
                    name = field.getName()+" = "+defaultValue.getColumnValue();
                }

                addDefaultValueAnnotation(field,topLevelClass);
                field.setName(name);
            }
        }
       return true;
    }

    /**
     * Adds the @Data lombok import and annotation to the class
     */
    protected void addDataAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(dataAnnotation);
        topLevelClass.addAnnotation("@Data");
    }

    /**
     * Adds the @ID lombok import and annotation to the class
     */
    protected void addPrimaryAnnotation(Field field,TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(idAnnotation);
        field.addAnnotation("@Id");
    }

    /**
     * Adds the @ID lombok import and annotation to the class
     */
    protected void addDefaultValueAnnotation(Field field,TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(defaultAnnotation);
        field.addAnnotation("@Builder.Default");
    }

    /**
     * Adds the @Table lombok import and annotation to the class
     */
    protected void addTableAnnotation(TopLevelClass topLevelClass,String tableName) {
        topLevelClass.addImportedType(tableAnnotation);
        topLevelClass.addAnnotation("@Table(name = \""+tableName+"\")");
    }

}
