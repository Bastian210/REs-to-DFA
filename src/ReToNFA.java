import java.util.*;

/**
 * Created by 费慧通 on 2017/10/22.
 */
public class ReToNFA {
    private OperatorStack stack;

    /**
     * 得到正则表达式中的所有操作数
     * @param regexp
     * @return
     */
    public ArrayList<String> getOperand(String regexp){
        ArrayList<String> result = new ArrayList<>();
        List<String> operators = Arrays.asList("*","|","(",")");
        for(int i=0;i<regexp.length();i++){
            String current = regexp.substring(i,i+1);
            if(!operators.contains(current)&&!result.contains(current)){
                result.add(current);
            }
        }
        return result;
    }

    /**
     * 在正则表达式中添加省略掉的毗邻运算符(.)
     * @param regexp
     * @return
     */
    public String addNeighborOperators(String regexp){
        String new_regexp = "";
        for(int i=0;i<regexp.length()-1;i++){
            new_regexp = new_regexp+regexp.substring(i,i+1);
            boolean is_connect = IsConnect(regexp.substring(i,i+1),regexp.substring(i+1,i+2));
            if(is_connect){
                new_regexp = new_regexp+".";
            }
        }
        new_regexp = new_regexp+regexp.substring(regexp.length()-1, regexp.length());
        return new_regexp;
    }

    /**
     * 判断正则表达式相邻两个字符之间是否需要加毗邻运算符
     * @param current
     * @param next
     * @return
     */
    private boolean IsConnect(String current, String next){
        List<String> operators = Arrays.asList("*","|");
        List<String> binOperators = Arrays.asList("|","^");
        if(current.equals("(")){
            return false;
        }else if(next.equals(")")){
            return false;
        }else if(operators.contains(next)){
            return false;
        }else if(binOperators.contains(current)){
            return false;
        }
        return true;
    }

    /**
     * 将中缀表达式转化为后缀表达式
     * @param infix
     * @return
     */
    public String InToPost(String infix){
        stack = new OperatorStack(infix.length());
        String postfix = "";
        List<String> operators = Arrays.asList("*","+","|","?","^",".","(",")");
        for(int i=0;i<infix.length();i++){
            String current = infix.substring(i,i+1);
            if(operators.contains(current)){
                if(stack.isEmpty()){
                    stack.push(current);
                }else{
                    while(!stack.isEmpty()){
                        String peek = stack.peek();
                        if(current.equals(")")){
                            if(peek.equals("(")){
                                stack.pop();
                                break;
                            }else{
                                postfix = postfix+stack.pop();
                            }
                        }else if(current.equals("(")){
                            stack.push(current);
                            break;
                        } else{
                            if(IsHighPriority(peek,current)){
                                postfix = postfix+stack.pop();
                                //如果此时栈为空，则压入当前操作符
                                if(stack.isEmpty()){
                                    stack.push(current);
                                    break;
                                }
                            }else{
                                //栈顶操作符优先级低于当前操作符，则将打过前操作符压入栈中
                                stack.push(current);
                                break;
                            }
                        }
                    }
                }
            }else{
                postfix = postfix+current;
            }
        }
        while (!stack.isEmpty()){
            String pop = stack.pop();
            if(!pop.equals("(")){
                postfix = postfix+pop;
            }
        }
        return postfix;
    }

    /**
     * 判断两个操作符的优先级高低
     * @param operator1
     * @param operator2
     * @return
     */
    private boolean IsHighPriority(String operator1, String operator2){
        int priority1 = getPriority(operator1);
        int priority2 = getPriority(operator2);
        return priority1>=priority2;
    }

    /**
     * 得到操作符优先级
     * @param operator
     * @return
     */
    private int getPriority(String operator){
        switch (operator){
            case "(":
                return 1;
            case "|":
                return 2;
            case ".":
                return 3;
            case "*":
                return 4;
//            case "?":
//                return 4;
//            case "+":
//                return 5;
//            case "^":
//                return 6;
            default:
                return 0;
        }
    }

    /**
     * 将用后缀表达式表示的正则表达式转化为等价的NFA对象
     * @param postfix 表示正则表达式的后缀表达式
     * @return 转化得到的NFA对象
     */
    public NFA evaluateExpression(String postfix) {
        //创建一个操作数栈来存储操作数
        Stack<NFA> operandStack = new Stack<>();
        //状态机数目
        int num = -1;
        for(int i=0;i<postfix.length();i++){
            String character = postfix.substring(i,i+1);
            switch (character){
                case "|":
                    NFA origin2 = operandStack.pop();
                    NFA origin1 = operandStack.pop();
                    NFA result = origin1.Choose(origin1,origin2,num);
                    operandStack.push(result);
                    num = num+2;
                    break;
                case "*":
                    NFA origin3 = operandStack.pop();
                    NFA result1 = origin3.Closure(origin3,num);
                    operandStack.push(result1);
                    num = num+2;
                    break;
                case ".":
                    NFA origin5 = operandStack.pop();
                    NFA origin4 = operandStack.pop();
                    NFA result2 = origin4.Connect(origin4,origin5);
                    operandStack.push(result2);
                    break;
                default:
                    NFA operand = new NFA(character,num);
                    operandStack.push(operand);
                    num = num+2;
                    break;
            }
        }
        return operandStack.pop();
    }
}
