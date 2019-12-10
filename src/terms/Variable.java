package terms;

public class Variable implements LambdaTerm {
    private final String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return variableName;
    }
}
