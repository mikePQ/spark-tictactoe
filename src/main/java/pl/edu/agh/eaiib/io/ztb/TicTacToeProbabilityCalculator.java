package pl.edu.agh.eaiib.io.ztb;

import org.apache.spark.api.java.JavaRDD;
import pl.edu.agh.eaiib.io.ztb.config.SparkConfig;

import java.io.IOException;

public class TicTacToeProbabilityCalculator {

    public Results calculate(BoardState boardState) throws IOException {
        SparkConfig config = SparkConfig.getInstance();
        Tree tree = new Tree(boardState, config);

        JavaRDD<Node> leafs = tree.collectAllNodes()
                .filter(Node::isLeaf);

        long all = leafs.count();
        long circle = leafs.filter(node -> node.getResult() == Result.CircleWon).count();
        long cross = leafs.filter(node -> node.getResult() == Result.CrossWon).count();
        long draws = all - cross - circle;

        return new Results(all, cross, circle, draws);
    }
}
