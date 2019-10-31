# mybatis-generator-core-fix
mybatis generator 生成数据库备注为注释 修整部分代码的样式

本工程为maven项目，可以直接打包使用

1. master 主要是原生样式的代码生成，不包含任何的第三方。

2. branches-swagger 主要是整合了 swagger来生成带有swagger 注释的model， 用于在线文档。

3. branches-swagger-tkmapper 主要整合了 swagger 和 tk 通用mapper ，以及hibernate-validator的数据校验

生成后的样式：
```

package com.ty.adapter.entity;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Table(name = "testtable")
public class TestTable implements Serializable {

    @Id
    private Long id;

    @Builder.Default
    private String name = "dadasda";

    @Builder.Default
    private String password = 123;

    private byte[] content;

    private static final long serialVersionUID = 1L;
}
```
# 使用步骤：1 下载项目后自己去找 generatorConfigMyBatis3.xml 改里面你觉得需要改的配置
# 2 然后运行 MyBatisGeneratorTest.java main方法
