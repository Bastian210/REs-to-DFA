package Operate;

import Category.DFAO;
import Category.DFAState;
import Category.NFA;
import Category.NFAState;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 费慧通 on 2017/10/23.
 */
public class MinimizeDFA {
    /**
     * 得到终态（用数字代号表示）
     * @param nfa
     * @param list
     * @return
     */
    public ArrayList<Integer> getFinalState(NFA nfa, ArrayList<ArrayList<NFAState>> list){
        ArrayList<Integer> result = new ArrayList<>();
        NFAState end = nfa.getEnd();
        for(int i=0;i<list.size();i++){
            ArrayList<NFAState> current = list.get(i);
            if(current.contains(end)){
                result.add(i);
            }
        }
        return result;
    }

    /**
     * 得到初始状态（用数字代号表示）
     * @param nfa
     * @param list
     * @return
     */
    public ArrayList<Integer> getInitialState(NFA nfa,ArrayList<ArrayList<NFAState>> list){
        ArrayList<Integer> result = new ArrayList<>();
        NFAState end = nfa.getEnd();
        for(int i=0;i<list.size();i++){
            if(!list.get(i).contains(end)){
                result.add(i);
            }
        }
        return result;
    }

    /**
     * 分割
     * @return
     */
    public ArrayList<ArrayList<Integer>> Division(ArrayList<ArrayList<Integer>> state_division,ArrayList<Integer> current,Map<Integer, ArrayList<Integer>> map,int operand){
        //得到的转换状态在state_division中的位置
        ArrayList<Integer> position = new ArrayList<>();
        ArrayList<ArrayList<Integer>> current_division = new ArrayList<>();
        //处理没有指向下一节点的情况
        ArrayList<Integer> empty = new ArrayList<>();
        for(int i=0;i<current.size();i++){
            int next = map.get(current.get(i)).get(operand);
//            System.out.print(current.get(i)+"  "+next+"    ");
            //没有指向下一个节点的边
            if(next==-1){
                empty.add(current.get(i));
                continue;
            }
            for(int m=0;m<state_division.size();m++){
                ArrayList<Integer> temp = state_division.get(m);
                if(temp.contains(next)){
                    if(position.contains(m)){
                        current_division.get(position.indexOf(m)).add(current.get(i));
                    }else{
                        position.add(m);
                        ArrayList<Integer> new_list = new ArrayList<>();
                        new_list.add(current.get(i));
                        current_division.add(new_list);
                    }
                    break;
                }
            }
        }
        //用current_division的内容取代state_division中的current
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for(int i=0;i<state_division.size();i++){
            result.add(state_division.get(i));
        }
        result.remove(current);
        if(empty.size()!=0){
            result.add(empty);
        }
        for(int i=0;i<current_division.size();i++){
            result.add(current_division.get(i));
        }
//        System.out.println("sout"+operand+"   "+result);
        return result;
    }

    public boolean CanDivisible(ArrayList<ArrayList<Integer>> state_division,Map<Integer, ArrayList<Integer>> map,ArrayList<String> operand){
        for(int i=0;i<state_division.size();i++){
            ArrayList<Integer> temp = state_division.get(i);
            if(temp.size()>1){
                int next = -2;
                for(int j=0;j<operand.size();j++){
                    for(int k=0;k<temp.size();k++){
                        int s = map.get(temp.get(k)).get(j);
                        int n = getLocation(state_division,s);
                        if(next==-2){
                            next = n;
                        }else if(next!=n){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public int getLocation(ArrayList<ArrayList<Integer>> state_division, int next){
        int location = -1;
        for(int i=0;i<state_division.size();i++){
            if(state_division.get(i).contains(next)){
                location = i;
            }
        }
        return location;
    }

    /**
     * 判断一个节点是否可以被替代，是则返回-1，否则返回替代其的节点值
     * @param node
     * @param state_division
     * @return
     */
    public int IsReplaceable(int node,ArrayList<ArrayList<Integer>> state_division){
        for(int i=0;i<state_division.size();i++){
            ArrayList<Integer> current = state_division.get(i);
            if(current.contains(node)){
                //选择列表第一个代替该节点
                if(current.get(0)==node){
                    return -1;
                }else{
                    return current.get(0);
                }
            }
        }
        return -2;
    }

    /**
     * @param states
     * @param num
     * @return
     */
    public DFAState getDFAState(ArrayList<DFAState> states, int num){
        for(int i=0;i<states.size();i++){
            if(states.get(i).getNum()==num){
                return states.get(i);
            }
        }
        return null;
    }

    /**
     * 得到总的分割结果
     * @param nfa
     * @param list
     * @param map
     * @param operand
     * @param loop
     * @return
     */
    public DFAO Main(NFA nfa, ArrayList<ArrayList<NFAState>> list, Map<Integer, ArrayList<Integer>> map, ArrayList<String> operand, boolean loop){
        ArrayList<Integer> final_state = getFinalState(nfa,list);
        ArrayList<Integer> initial_state = getInitialState(nfa,list);
        ArrayList<ArrayList<Integer>> state_division = new ArrayList<>();
        state_division.add(initial_state);
        state_division.add(final_state);
        ArrayList<ArrayList<Integer>> temp = state_division;
        if(loop){
            while(!CanDivisible(state_division,map,operand)){
                state_division = temp;
                for(int j=0;j<operand.size();j++){
                    state_division = temp;
//                    System.out.println(temp+" "+state_division);
                    for(int i=0;i<state_division.size();i++){
                        ArrayList<Integer> current = state_division.get(i);
                        temp = Division(temp,current,map,j);
                    }
                }
            }
        }else{
            for(int j=0;j<operand.size();j++){
                state_division = temp;
                for(int i=0;i<state_division.size();i++){
                    ArrayList<Integer> current = state_division.get(i);
                    temp = Division(temp,current,map,j);
                }
            }
        }
//        System.out.println(state_division);
        //使用集合中某一个DFA状态代替等价的其他状态
        ArrayList<DFAState> states = new ArrayList<>();
        for(int i=0;i<state_division.size();i++){
            int num = state_division.get(i).get(0);
            states.add(new DFAState(num,list.get(num)));
        }

        for(int i=0;i<state_division.size();i++){
            int num = state_division.get(i).get(0);
            DFAState dfaState = getDFAState(states, num);
            ArrayList<String> edge = new ArrayList<>();
            ArrayList<DFAState> dfaStates = new ArrayList<>();
            for(int n=0;n<operand.size();n++){
                //num在某个操作下指向的下一个节点
                int node = map.get(num).get(n);
                //判断是不是不存在只想下一个节点的边
                if(node!=-1){
                    //判断这个节点有没有被替代
                    if(IsReplaceable(node,state_division)==-1){
                        DFAState temp_state = getDFAState(states,node);
                        edge.add(operand.get(n));
                        dfaStates.add(temp_state);
                    }else{
                        DFAState temp_state = getDFAState(states,IsReplaceable(node,state_division));
                        edge.add(operand.get(n));
                        dfaStates.add(temp_state);
                    }
                }
            }
            dfaState.setEdge(edge);
            dfaState.setNext(dfaStates);
        }
        DFAState start = getDFAState(states,0);
        ArrayList<DFAState> end = new ArrayList<>();
        for(int i=0;i<final_state.size();i++){
            if(IsReplaceable(final_state.get(i),state_division)==-1){
                end.add(getDFAState(states,final_state.get(i)));
            }
        }
        return new DFAO(start,end,states);
    }

    public void Print(DFAState state){
        System.out.print(state.getNum());
        ArrayList<String> edge = state.getEdge();
        ArrayList<DFAState> next = state.getNext();
        for(int i=0;i<edge.size();i++){
            System.out.print(edge.get(i));
            System.out.print(next.get(i).getNum());
            System.out.print("    ");
        }
        System.out.println();
    }
}
