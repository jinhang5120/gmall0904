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
        gc.setOutputDir(projectPath+"/gmall-parent/gmall-api/src/main/java");//生成文件的输出目录，从工作空间的顶层目录开始
        gc.setAuthor("jin");//开发人员
        gc.setOpen(false);//是否打开资源管理器，默认值：false
        gc.setFileOverride(false); // 是否覆盖已有文件
        gc.setServiceName("%sService"); // service 命名方式，默认值：null 例如：%sBusiness 生成 UserBusiness
        gc.setIdType(IdType.ID_WORKER);//指定生成的主键的ID类型
        gc.setDateType(DateType.ONLY_DATE);//时间类型对应策略
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
        pc.setParent("com.jh.gmall");//父包名
        pc.setModuleName(null);//命名不要有横线
        pc.setEntity("entity");//Entity包名
        pc.setService("service");//Service包名
        mpg.setPackageInfo(pc);
        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("oms_cart_item",
                "oms_company_address",
                "oms_order",
                "oms_order_item",
                "payment_info",
                "pms_base_attr_info",
                "pms_base_attr_value",
                "pms_base_catalog1",
                "pms_base_catalog2",
                "pms_base_catalog3",
                "pms_base_sale_attr",
                "pms_brand",
                "pms_comment",
                "pms_comment_replay",
                "pms_product_image",
                "pms_product_info",
                "pms_product_sale_attr",
                "pms_product_sale_attr_value",
                "pms_product_vertify_record",
                "pms_sku_attr_value",
                "pms_sku_image",
                "pms_sku_info",
                "pms_sku_sale_attr_value",
                "ums_member",
                "ums_member_level",
                "ums_member_receive_address",
                "wms_ware_info",
                "wms_ware_order_task",
                "wms_ware_order_task_detail",
                "wms_ware_sku"
        ); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setEntityLombokModel(true); // 自动lombok；
        strategy.setRestControllerStyle(true);//生成 @RestController 控制器

        mpg.setStrategy(strategy);
        mpg.execute(); //执行
    }
}
