import java.util.*;

/**
 * Created by 费慧通 on 2017/10/25.
 */
public class Match {
    private List<String> operators = Arrays.asList("+","-","*","/",">","<","|", "&","!" ,"==", "=",">=","<=","+=","-=","*=","/=","&&","||","!=");
    private List<String> notes = Arrays.asList("/*","*/");
    private List<String> punctuation = Arrays.asList("{","}",";",".","(",")","[","]",":","\"",",");
    private String[] reservedWords={"void","class","public",
            "private","for","if","else","while","do",
            "int","double","char","boolean","String","new","try",
            "catch","static","return","this","main"};
    private String[] number = {"(1|2|3|4|5|6|7|8|9|0)*","-(1|2|3|4|5|6|7|8|9|0)*",
            "(1|2|3|4|5|6|7|8|9|0)*·(1|2|3|4|5|6|7|8|9|0)*","-(1|2|3|4|5|6|7|8|9|0)*·(1|2|3|4|5|6|7|8|9|0)*"};
    private String[] id = {"(q|w|e|r|t|y|u|i|o|p|a|s|d|f|g|h|j|k|l|z|x|c|v|b|n|m)*(1|2|3|4|5|6|7|8|9|0)*"};

    private ArrayList<String> type;
    private ArrayList<DFAO> list;

    public Match(){
        type = new ArrayList<>();
        list = new ArrayList<>();
        for(int i=0;i<reservedWords.length;i++){
            type.add("reservedWord");
            list.add(getDFAO(reservedWords[i],true));
        }
        for(int i=0;i<number.length;i++){
            type.add("number");
            list.add(getDFAO(number[i],false));
        }
        for(int i=0;i<id.length;i++){
            type.add("id");
            list.add(getDFAO(id[i],false));
        }
    }

    /**
     * 得到Token序列
     * @param code
     * @return
     */
    public Token getToken(String code){
        if(operators.contains(code)){
            return new Token(code,"operator",null);
        }
        if(notes.contains(code)){
            return new Token(code,"note",null);
        }
        if(punctuation.contains(code)){
            return new Token(code,"punctuation",null);
        }
        for(int i=0;i<type.size();i++){
            if(IsMatch(code,list.get(i))){
                return new Token(code,type.get(i),null);
            }
        }
        return new Token(code,"Unknow","Not Find");
    }

    /**
     * 判断单词的类型
     * @param code
     * @param dfa
     * @return
     */
    public boolean IsMatch(String code,DFAO dfa){
        DFAState start = dfa.getStart();
        for(int i=0;i<code.length();i++){
            ArrayList<String> edge = start.getEdge();
            ArrayList<DFAState> next = start.getNext();
            boolean find = false;
            for(int j=0;j<edge.size();j++){
                if(edge.get(j).equals(code.substring(i,i+1))){
                    start = next.get(j);
                    find = true;
                }
            }
            if(!find){
                return false;
            }
        }
        ArrayList<DFAState> end = dfa.getEnd();
        if(end.contains(start)){
            return true;
        }
        return false;
    }

    /**
     * 根据正则表达式得到最小化的DFA
     * @param regexp
     * @return
     */
    private DFAO getDFAO(String regexp,boolean loop){
        ReToNFA inToPost = new ReToNFA();
        String postfix = inToPost.InToPost(inToPost.addNeighborOperators(regexp));
        NFA result = inToPost.evaluateExpression(postfix);

        NFAToDFA nfaToDFA = new NFAToDFA();
        ArrayList<ArrayList<NFAState>> result1 = nfaToDFA.Main(result,inToPost.getOperand(regexp));
        MinimizeDFA minimizeDFA = new MinimizeDFA();
        DFAO all = minimizeDFA.Main(result,result1,nfaToDFA.getResult(),inToPost.getOperand(regexp),loop);
        return all;
    }
}
