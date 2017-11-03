import java.util.ArrayList;

/**
 * Created by 费慧通 on 2017/10/23.
 */
public class NFAState {
    private int state_num;
    private ArrayList<String> edge;
    private ArrayList<NFAState> next;

    public NFAState(int num){
        this.state_num = num;
        edge = new ArrayList<>();
        next = new ArrayList<>();
    }

    public NFAState(int num, String Edge, NFAState state){
        this.state_num = num;
        edge = new ArrayList<>();
        next = new ArrayList<>();
        edge.add(Edge);
        next.add(state);
    }

    public NFAState(int num, NFAState state1, NFAState state2){
        this.state_num = num;
        edge = new ArrayList<>();
        next = new ArrayList<>();
        edge.add("ε");
        edge.add("ε");
        next.add(state1);
        next.add(state2);
    }

    public void AddEdge(String Edge, NFAState state){
        edge.add(Edge);
        next.add(state);
    }

    public ArrayList<String> getEdge() {
        return edge;
    }

    public ArrayList<NFAState> getNext() {
        return next;
    }

    public int getState_num() {
        return state_num;
    }


}
