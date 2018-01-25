package pl.edu.agh.eaiib.io.ztb;

import java.text.MessageFormat;

public class Results {
    private final long all;
    private final long cross;
    private final long circle;
    private final long draw;

    public Results(long all, long cross, long circle, long draw) {
        this.all = all;
        this.cross = cross;
        this.circle = circle;
        this.draw = draw;
    }

    public double getCrossProbability() {
        return (100.0 * cross) / all;
    }

    public double getCircleProbability() {
        return (100.0 * circle) / all;
    }

    public double getDrawProbability() {
        return (100.0 * draw) / all;
    }

    public String prettyPrint() {
        return MessageFormat.format("Wygrana gracza 1 (krzyżyk) : {0}%\nWygrana gracza 2 (kółko) : {1}%\nRemis : {2}%", getCrossProbability(), getCircleProbability(), getDrawProbability());
    }

}
