package pl.edu.agh.eaiib.io.ztb;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import pl.edu.agh.eaiib.io.ztb.config.SparkConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class SparkExample {
    public static void main(String[] args) throws IOException {
        SparkConfig sparkConfig = SparkConfig.getInstance();
        JavaSparkContext sparkContext = sparkConfig.createSparkContext();

        JavaRDD<String> lines = sparkContext.textFile("src/main/resources/word_count.txt");
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        Map<String, Long> wordCounts = words.countByValue();

        for (Map.Entry<String, Long> entry : wordCounts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
