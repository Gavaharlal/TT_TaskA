package terms;

public class Application implements LambdaTerm {
    private final LambdaTerm left;
    private final LambdaTerm right;

    public Application(LambdaTerm left, LambdaTerm right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + right.toString() + ")";
    }
}
