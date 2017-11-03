/**
 * Created by 费慧通 on 2017/10/22.
 */
public class OperatorStack {
    private int maxSize;

    private String[] stackArray;

    private int top;

    public OperatorStack(int max) {
        maxSize = max;
        stackArray = new String[maxSize];
        top = -1;
    }

    public void push(String j) {
        stackArray[++top] = j;
    }

    public String pop() {
        return stackArray[top--];
    }

    public String peek() {
        return stackArray[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public String Print(){
        String x = "";
        for(int i=0;i<=top;i++){
            x = x +stackArray[i];
        }
        return x;
    }
}
