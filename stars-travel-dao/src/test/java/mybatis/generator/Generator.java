package mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gim on 15/10/7.
 */
public class Generator {

    public static void main(String[] args) throws Exception{

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new File("H:/stars-travel/stars-travel-dao/src/test/resources/generatorConfig.xml");
        System.out.println(configFile.getAbsoluteFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for(String s : warnings) {
            System.out.println(s);
        }

    }
}
