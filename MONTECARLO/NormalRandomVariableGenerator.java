package simulation;

import java.util.Random;

public class NormalRandomVariableGenerator {
    private Random rand;
    /**
     * Construct a NormalRandomVariableGenerator
     * @param seed : Seed for random variable generator
     */
    public NormalRandomVariableGenerator(long seed){
        rand = new Random(seed);
    }
    /**
     * Construct a NormalRandomVariableGenerator
     */
    public NormalRandomVariableGenerator(){
        rand = new Random();
    }
    /**
     * Return a standard normal random variable
     * @return : a standard normal random variable
     */
    public double getNextNumber() {
        return StandardNormalDistributionStat.inverseCumulativeProbability(rand.nextDouble());
        
    }
}
