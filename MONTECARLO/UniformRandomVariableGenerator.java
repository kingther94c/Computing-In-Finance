package simulation;
import java.util.Random;

public class UniformRandomVariableGenerator implements RandomVariableGenerator {

    private Random rand;
    public UniformRandomVariableGenerator(){
        rand = new Random();
    }
    public UniformRandomVariableGenerator(long seed){
        rand = new Random(seed);
    }
    
    public double getNextNumber(){
        return rand.nextDouble();
    }
}