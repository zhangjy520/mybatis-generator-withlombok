# mybatis-generator源码修改

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
