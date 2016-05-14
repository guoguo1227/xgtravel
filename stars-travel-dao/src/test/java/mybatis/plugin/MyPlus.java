package mybatis.plugin;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MyPlus extends PluginAdapter implements Plugin {
    static Logger logger = LoggerFactory.getLogger(MyPlus.class);

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        //initDatabaseTools(introspectedTable);
        //moveXml(introspectedTable);
        super.initialized(introspectedTable);
    }


    /**
     * 移动、备份xml配置文件
     * @param introspectedTable
     */
//    private void moveXml(IntrospectedTable introspectedTable) {
//        SqlMapGeneratorConfiguration sqlmapConfig = context.getSqlMapGeneratorConfiguration();
//        String dir = sqlmapConfig.getTargetProject() + "/"
//                     + sqlmapConfig.getTargetPackage().replaceAll("\\.", "/");
//        final String fileName = StringUtil.splitLast(introspectedTable.getExampleType(), "\\.")
//            .replaceAll("Example", "Mapper") + ".xml";
//        File file = new File(dir + "/" + fileName);
//        if (file.isFile()) {
//            File dest = new File(dir + "_" + fileName + "."
//                                 + new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date())
//                                 + ".bak");
//            file.renameTo(dest);
//        }
//    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType ptype = new FullyQualifiedJavaType("com.stars.travel.dao.base.BaseMapper");
        interfaze.addImportedType(ptype);
        interfaze.addSuperInterface(ptype);

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    
    private void addSerialVersionUID(InnerClass clazz) {
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setStatic(true);
        field.setFinal(true);
        field.setName("serialVersionUID");
        field.setType(new FullyQualifiedJavaType("long"));
        field.setInitializationString("1L");
        List<Field> fields = clazz.getFields();
        for (int i = 0; i < fields.size(); i++) { //将序列化id放第一个属性上
            Field currField = fields.get(i);
            fields.set(i, field);
            field = currField;
        }
        fields.add(field);
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
                                                      IntrospectedTable introspectedTable) {
        addSerialVersionUID(topLevelClass);
        return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        // 实体类添加统一父接口
        FullyQualifiedJavaType ptype = new FullyQualifiedJavaType("com.lagou.common.base.bean.BaseBean");
        topLevelClass.addImportedType(ptype);
        topLevelClass.addSuperInterface(ptype);

        addSerialVersionUID(topLevelClass);

        Method methodCopy = new Method();
        methodCopy.setVisibility(JavaVisibility.PUBLIC);
        methodCopy.addJavaDocLine("/** ");
        methodCopy.addJavaDocLine(" * 拷贝，将对象中的字段全部拷贝到子对象中");
        methodCopy.addJavaDocLine(" * @param bean 接收对象的子类");
        methodCopy.addJavaDocLine(" * @return 拷贝完成后的子类");
        methodCopy.addJavaDocLine(" */ ");
        methodCopy.setName("<T extends " + topLevelClass.getType().getShortName() + "> T copy");
        methodCopy.setReturnType(new FullyQualifiedJavaType(""));
        methodCopy.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "bean"));

        for (Field column : topLevelClass.getFields()) {
            if (column.isStatic()) {
                continue;
            }
            String columnName = column.getName();
            String methodName = Character.toUpperCase(columnName.charAt(0))
                                + columnName.substring(1);
            methodCopy.addBodyLine("bean.set" + methodName + "(get" + methodName + "());");
        }
        methodCopy.addBodyLine("return bean;");
        topLevelClass.addMethod(methodCopy);

        Method methodToString = new Method();
        methodToString.setVisibility(JavaVisibility.PUBLIC);
        methodToString.addJavaDocLine("/** ");
        methodToString.addJavaDocLine(" * 格式化显示");
        methodToString.addJavaDocLine(" */ ");
        methodToString.addAnnotation("@Override");
        methodToString.setName("toString");
        methodToString.setReturnType(FullyQualifiedJavaType.getStringInstance());
        methodToString.addBodyLine("return \"{\" + ");
        boolean first = true;
        for (Field column : topLevelClass.getFields()) {
            if (column.isStatic()) {
                continue;
            }
            String columnName = column.getName();
            String methodName = Character.toUpperCase(columnName.charAt(0))
                                + columnName.substring(1);
            if (first) {
                first = false;
                methodToString.addBodyLine("	\"" + columnName + ":\" + get" + methodName + "() + ");
            } else {
                methodToString.addBodyLine("	\", " + columnName + ":\" + get" + methodName
                                           + "() + ");
            }
        }
        methodToString.addBodyLine("\"}\";");
        topLevelClass.addMethod(methodToString);

        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 字段修改，添加注释
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn,
            introspectedTable, modelClassType);
    }

    /**
     * 修改 查询结果对象映射，支持自定义结果对象
     */
    @Override
    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element,
                                                               IntrospectedTable introspectedTable) {
        String resultClass = introspectedTable.getTableConfiguration().getProperty("resultClass");
        if (null != resultClass && !"".equals(resultClass)) {
            final List<Attribute> list = element.getAttributes();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equals("type")) {
                    list.set(i, new Attribute("type", resultClass));
                    break;
                }
            }
        }
        return true;
    }

    /**
     * 修改client接口，支持自定义结果对象
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        String resultClass = introspectedTable.getTableConfiguration().getProperty("resultClass");
        if (resultClass != null && !"".equals(resultClass.trim())) {
            interfaze.addImportedType(new FullyQualifiedJavaType(resultClass));
            method.setReturnType(new FullyQualifiedJavaType("List<" + resultClass + ">"));
        }
        return true;
    }

    /**
     * 修改client接口，支持自定义结果对象
     */
    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
                                                           IntrospectedTable introspectedTable) {
        String resultClass = introspectedTable.getTableConfiguration().getProperty("resultClass");
        if (resultClass != null && !"".equals(resultClass.trim())) {
            interfaze.addImportedType(new FullyQualifiedJavaType(resultClass));
            method.setReturnType(new FullyQualifiedJavaType(resultClass));
        }
        return true;
    }

    /**
     * 修改 select 查询xml 对象， 添加自动分页功能
     */
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        XmlElement choose = new XmlElement("choose");
        XmlElement rangeLimitWhen = new XmlElement("when");
        rangeLimitWhen.addAttribute(new Attribute("test", "limitStart != -1 and limitEnd != -1"));
        rangeLimitWhen.addElement(new TextElement("limit ${limitStart} , ${limitEnd}"));
        XmlElement limitStartWhen = new XmlElement("when");
        limitStartWhen.addAttribute(new Attribute("test", "limitStart != -1"));
        limitStartWhen.addElement(new TextElement("limit ${limitStart}"));
        choose.addElement(rangeLimitWhen);
        choose.addElement(limitStartWhen);
        element.addElement(choose);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    /** example 中添加分页信息 */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        // 所有类条件类添加统一父接口
        FullyQualifiedJavaType ptype = new FullyQualifiedJavaType("com.lagou.common.base.bean.BaseCriteria");
        topLevelClass.addImportedType(ptype);
        topLevelClass.addSuperInterface(ptype);
        //cobra add
        //添加实体的序列化号
        addSerialVersionUID(topLevelClass);
        //添加内部类
        
        //GeneratedCriteria, Criterion
        andGeneratedCriteriaImplSerialable(topLevelClass, "GeneratedCriteria");
        andGeneratedCriteriaImplSerialable(topLevelClass, "Criterion"); 
        andGeneratedCriteriaImplSerialable(topLevelClass, "Criteria");
        
        addLimit(topLevelClass, introspectedTable, "limitStart");
        addLimit(topLevelClass, introspectedTable, "limitEnd");

        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    private void andGeneratedCriteriaImplSerialable(TopLevelClass topLevelClass, String innerClassName) {
    	if(innerClassName == null){
    		return;
    	}
    	InnerClass generatedCriteria = null;
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            if (innerClassName.equals(innerClass.getType().getShortName())) {
            	generatedCriteria = innerClass;
                break;
            }
        }
        if(generatedCriteria == null){
        	return;
        }
        FullyQualifiedJavaType ptype = new FullyQualifiedJavaType("java.io.Serializable");
        topLevelClass.addImportedType(ptype);
        generatedCriteria.addSuperInterface(ptype);
        addSerialVersionUID(generatedCriteria);
	}

	private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,
                          String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getIntInstance());
        field.setName(name);
        field.setInitializationString("-1");
        commentGenerator.addFieldComment(field, introspectedTable);

        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);

        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

}
