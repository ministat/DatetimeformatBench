package net.local.test;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Evaluator {

    @Option(name="-i", aliases="--iterations", usage="Specify the iterations. Default is 99999999")
    private long iterations = 99999999;

    @Option(name="-t", aliases="--threads", usage="Specify the thread number. Default is 4")
    private int thread = 4;

    @Option(name="-c", aliases="--check", usage="Verify results")
    private boolean check = false;

    @Option(name="-j", aliases="--jdk", usage="Only use jdk datetime")
    private boolean onlyUseJdk = false;

    @Option(name="-d", aliases="--joda", usage="Only use Joda datetime")
    private boolean onlyUseJoda = false;

    private boolean parseArgs(final String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);

        boolean ret = true;
        try {
            parser.parseArgument(args);
        } catch (CmdLineException clEx) {
            System.out.println("Error: failed to parse command-line opts: " + clEx);
            ret = false;
        }
        return ret;
    }

    public static void main(String args[]) {
        Evaluator eval = new Evaluator();
        if (!eval.parseArgs(args)) {
            return;
        }

        JdkDatetime jdt = new JdkDatetime(eval.iterations);
        JodaDatetime jodt = new JodaDatetime(eval.iterations);
        if (eval.check) {
            long now = System.currentTimeMillis();

            String ref = jdt.format(now);
            String dt = jodt.format(now);
            if (ref.equals(dt)) {
                System.out.println("Equal!");
            } else {
                System.out.println("Not equal: ref=" + ref + " gen=" + dt);
            }
        } else if (eval.onlyUseJdk) {
            MultipleThreadingEvaluator mt = new MultipleThreadingEvaluator(eval.thread, jdt);
            mt.run();
        } else if (eval.onlyUseJoda) {
            MultipleThreadingEvaluator mt = new MultipleThreadingEvaluator(eval.thread, jodt);
            mt.run();
        } else {
            MultipleThreadingEvaluator mtJdt = new MultipleThreadingEvaluator(eval.thread, jdt);
            mtJdt.run();
            MultipleThreadingEvaluator mtJodt = new MultipleThreadingEvaluator(eval.thread, jodt);
            mtJodt.run();
        }
    }
}
