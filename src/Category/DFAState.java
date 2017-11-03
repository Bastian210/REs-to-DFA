package Category;

import java.util.ArrayList;

/**
 * Created by 费慧通 on 2017/10/23.
 */

//DFA中的一个状态点
public class DFAState {
    private int num;
    private ArrayList<NFAState> content;
    private ArrayList<String> edge;
    private ArrayList<DFAState> next;

    public DFAState(int num, ArrayList<NFAState> content){
        this.num = num;
        this.content = content;
        edge = new ArrayList<>();
        next = new ArrayList<>();
    }

    public int getNum() {
        return num;
    }

    public ArrayList<NFAState> getContent() {
        return content;
    }

    public ArrayList<String> getEdge() {
        return edge;
    }

    public ArrayList<DFAState> getNext() {
        return next;
    }

    public void setEdge(ArrayList<String> edge) {
        this.edge = edge;
    }

    public void setNext(ArrayList<DFAState> next) {
        this.next = next;
    }
}
