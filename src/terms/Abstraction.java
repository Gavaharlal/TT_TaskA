package terms;

public class Abstraction implements LambdaTerm {
    private final Variable variable;
    private final LambdaTerm lambdaTerm;

    public Abstraction(Variable variable, LambdaTerm lambdaTerm) {
        this.variable = variable;
        this.lambdaTerm = lambdaTerm;
    }

    @Override
    public String toString() {
        return "(\\" + variable.toString() + "." + lambdaTerm.toString() + ")";
    }
}
