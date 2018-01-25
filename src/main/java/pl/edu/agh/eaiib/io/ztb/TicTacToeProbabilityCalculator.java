package pl.edu.agh.eaiib.io.ztb;

import org.apache.spark.api.java.JavaRDD;
import pl.edu.agh.eaiib.io.ztb.config.SparkConfig;

import java.io.IOException;

public class TicTacToeProbabilityCalculator {

    public void calculate() throws IOException {
        SparkConfig config = SparkConfig.getInstance();
        Tree tree = new Tree(3, config);

        JavaRDD<Node> leafs = tree.collectAllNodes()
                .filter(Node::isLeaf);

        long all = leafs.count();
        long circle = leafs.filter(node -> node.getResult() == Result.CircleWon).count();
        long cross = leafs.filter(node -> node.getResult() == Result.CrossWon).count();
        long draws = leafs.count() - cross - circle;

        System.out.println(all);
        System.out.println(circle);
        System.out.println(cross);
        System.out.println(draws);
    }

    public static void main(String[] args) throws IOException {
        TicTacToeProbabilityCalculator calculator = new TicTacToeProbabilityCalculator();
        calculator.calculate();
    }
}
