package com.example.gmall0917;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class CodeGenerator {
    public static void main(String[] args) {
        // 需要构建一个 代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
        //配置说明参考https://baomidou.com/config/generator-config.html#%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE
        // 配置策略
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //C:\Users\jinha\IdeaProjects\gmall0904\gmall-study0915
        gc.setOutputDir(projectPath+"/gmall0917/src/main/java");//生成文件的输出目录，从工作空间的顶层目录开始
        gc.setAuthor("jin");//开发人员
        gc.setOpen(false);//是否打开资源管理器，默认值：false
        gc.setFileOverride(true); // 是否覆盖已有文件
        gc.setServiceName("%sService"); // service 命名方式，默认值：null 例如：%sBusiness 生成 UserBusiness
        gc.setIdType(IdType.ID_WORKER);//指定生成的主键的ID类型
        gc.setDateType(DateType.ONLY_DATE);//时间类型对应策略
        gc.setSwagger2(true);//开启 swagger2 模式    默认值：false
        mpg.setGlobalConfig(gc);
        //2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/gmall_study?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.example.gmall0917");//父包名
        pc.setModuleName("ums_member");//包含entiy、mapper、service、controller的文件夹名
        pc.setEntity("entity");//Entity包名
        pc.setMapper("mapper");//Mapper包名
        pc.setService("service");//Service包名
        pc.setController("controller");//Controller包名
        mpg.setPackageInfo(pc);
        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("ums_member_receive_address"); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setEntityLombokModel(true); // 自动lombok；
        strategy.setLogicDeleteFieldName("deleted");//逻辑删除属性名称，数据库的表要实现有这样的字段
        // 自动填充配置
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);//要保证数据库中有“gmt_create”这样的字段,且要添加时间处理器组件到IOC容器中
        TableFill gmtModified = new TableFill("gmt_modified",FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();//表填充字段
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);
        // 乐观锁
        strategy.setVersionFieldName("version");//乐观锁属性名称，数据库的表要实现有这样的字段
        strategy.setRestControllerStyle(true);//生成 @RestController 控制器
        strategy.setControllerMappingHyphenStyle(true);
//        localhost:8081;
        mpg.setStrategy(strategy);
        mpg.execute(); //执行
    }
}
