import java.util.ArrayList;

/**
 * Created by 费慧通 on 2017/10/23.
 */
public class NFA {
    private NFAState start;

    private NFAState end;

    private ArrayList<NFAState> list;

    public NFA(String edge, int max_num){
        NFAState state1 = new NFAState(max_num+1);
        NFAState state2 = new NFAState(max_num+2);
        state1.AddEdge(edge, state2);
        start = state1;
        end = state2;
        list = new ArrayList<>();
        list.add(state1);
        list.add(state2);
    }

    public NFA(NFAState start, NFAState end, ArrayList<NFAState> list){
        this.start = start;
        this.end = end;
        this.list = list;
    }

    public NFAState getStart() {
        return start;
    }

    public NFAState getEnd() {
        return end;
    }

    public ArrayList<NFAState> getList() {
        return list;
    }

    /**
     * 处理闭包运算
     * @param origin
     * @param max_num
     * @return
     */
    public NFA Closure(NFA origin, int max_num){
        NFAState start = origin.getStart();
        NFAState end = origin.getEnd();
        ArrayList<NFAState> list = origin.getList();
        end.AddEdge("ε",start);
        NFAState new_end = new NFAState(max_num+2);
        end.AddEdge("ε",new_end);
        NFAState new_start = new NFAState(max_num+1,start,new_end);
        list.add(new_start);
        list.add(new_end);
        return new NFA(new_start,new_end,list);
    }

    /**
     * 处理连接运算
     * @param origin1
     * @param origin2
     * @return
     */
    public NFA Connect(NFA origin1, NFA origin2){
        NFAState start1 = origin1.getStart();
        NFAState end1 = origin1.getEnd();
        ArrayList<NFAState> list1 = origin1.getList();
        NFAState start2 = origin2.getStart();
        NFAState end2 = origin2.getEnd();
        ArrayList<NFAState> list2 = origin2.getList();
        end1.AddEdge("ε",start2);
        for(int i=0;i<list2.size();i++){
            list1.add(list2.get(i));
        }
        return new NFA(start1,end2,list1);
    }

    /**
     * 处理选择运算
     * @param origin1
     * @param origin2
     * @param max_num
     * @return
     */
    public NFA Choose(NFA origin1, NFA origin2, int max_num){
        NFAState start1 = origin1.getStart();
        NFAState end1 = origin1.getEnd();
        ArrayList<NFAState> list1 = origin1.getList();
        NFAState start2 = origin2.getStart();
        NFAState end2 = origin2.getEnd();
        ArrayList<NFAState> list2 = origin2.getList();
        NFAState new_start = new NFAState(max_num+1, start1, start2);
        NFAState new_end = new NFAState(max_num+2);
        end1.AddEdge("ε",new_end);
        end2.AddEdge("ε",new_end);
        for(int i=0;i<list2.size();i++){
            list1.add(list2.get(i));
        }
        list1.add(new_start);
        list1.add(new_end);
        return new NFA(new_start, new_end, list1);
    }
}
