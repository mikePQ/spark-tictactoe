package pl.edu.agh.eaiib.io.ztb.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class SparkConfig {
    private static SparkConfig INSTANCE;

    private final Properties sparkProperties;
    private final JavaSparkContext sparkContext;

    public static synchronized SparkConfig getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new SparkConfig();
        }

        return INSTANCE;
    }

    private SparkConfig() throws IOException {
        String sparkConfigFilePath = System.getProperty(SPARK_CONFIG_FILE_KEY);
        File sparkConfigFile = sparkConfigFilePath != null ? getConfigFile(sparkConfigFilePath) : getDefaultConfigFile();
        if (!sparkConfigFile.exists()) {
            throw new SparkConfigException("Spark configuration file not found");
        }

        sparkProperties = new Properties();
        try (FileReader reader = new FileReader(sparkConfigFile)) {
            sparkProperties.load(reader);
        }

        sparkContext = createSparkContext();
    }

    public JavaSparkContext getSparkContext() {
        return sparkContext;
    }

    private JavaSparkContext createSparkContext() {
        String appName = getProperty(APPNAME_KEY, DEFAULT_APP_NAME);
        String master = getProperty(MASTER_KEY, DEFAULT_MASTER_VALUE);

        SparkConf sparkConfig = new SparkConf()
                .setAppName(appName)
                .setMaster(master);

        return new JavaSparkContext(sparkConfig);
    }

    private String getProperty(String key, String defaultValue) {
        String value = sparkProperties.getProperty(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    private static File getConfigFile(String filePath) {
        return new File(filePath);
    }

    private static File getDefaultConfigFile() {
        ClassLoader classLoader = SparkConfig.class.getClassLoader();
        URL configFileUrl = classLoader.getResource(DEFAULT_CONFIG_FILE);
        if (configFileUrl == null) {
            throw new SparkConfigException("Spark configuration file not found");
        }

        return new File(configFileUrl.getFile());
    }

    private static final String DEFAULT_CONFIG_FILE = "spark.properties";
    private static final String SPARK_CONFIG_FILE_KEY = "spark.config.file";
    private static final String APPNAME_KEY = "app.name";
    private static final String MASTER_KEY = "master";
    private static final String DEFAULT_APP_NAME = "application";
    private static final String DEFAULT_MASTER_VALUE = "local[4]";
}

class SparkConfigException extends RuntimeException {
    SparkConfigException(String message) {
        super(message);
    }
}
