package Operate;

import Category.NFA;
import Category.NFAState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 费慧通 on 2017/10/23.
 */
public class NFAToDFA {
    private Map<Integer,ArrayList<Integer>> result;

    /**
     * 得到一列状态机的ε闭包
     * @param list
     */
    public ArrayList<NFAState> eClosure(ArrayList<NFAState> list){
        ArrayList<NFAState> result = list;
        int k=0;
        while(k==0||result.size()!=list.size()){
            k=1;
            list = result;
            for(int i=0;i<list.size();i++){
                NFAState current = list.get(i);
                ArrayList<String> edge = current.getEdge();
                ArrayList<NFAState> next = current.getNext();
                for(int j=0;j<edge.size();j++){
                    if(edge.get(j).equals("ε")&&!result.contains(next.get(j))){
                        result.add(next.get(j));
                    }
                }
            }
        }
        return result;
    }

    /**
     * 得到闭包的特定弧转换
     * @param edge
     * @param list
     * @return
     */
    public ArrayList<NFAState> getTransformSet(String edge, ArrayList<NFAState> list){
        ArrayList<NFAState> result = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            NFAState current = list.get(i);
            ArrayList<String> edge_list = current.getEdge();
            ArrayList<NFAState> next = current.getNext();
            for(int j=0;j<edge_list.size();j++){
                if(edge_list.get(j).equals(edge)){
                    result.add(next.get(j));
                }
            }
        }
        return result;
    }

    /**
     * NFA转DFA
     * @param nfa
     * @param operand
     * @return
     */
    public ArrayList<ArrayList<NFAState>> Main(NFA nfa, ArrayList<String> operand){
        result = new HashMap<>();
        ArrayList<ArrayList<NFAState>> console_list = new ArrayList<>();
        int num=0;
        ArrayList<NFAState> begin = new ArrayList<>();
        begin.add(nfa.getStart());
        console_list.add(eClosure(begin));
        while(console_list.size()>num){
//            Print(console_list.get(num));
//            System.out.print("     ");
            //表格每一行，除去第一列
            ArrayList<Integer> line = new ArrayList<>();
            for(int i=0;i<operand.size();i++){
                ArrayList<NFAState> set = getTransformSet(operand.get(i),console_list.get(num));
//                Print(set);
//                System.out.print("--");
                ArrayList<NFAState> current_closure = eClosure(set);
//                System.out.println("current"+current_closure.size());
                if(current_closure.size()!=0&&!console_list.contains(current_closure)){
                    console_list.add(current_closure);
                }
                line.add(console_list.indexOf(current_closure));
//                Print(current_closure);
//                System.out.print("     ");
            }
            result.put(num,line);
            num++;
//            System.out.println();
        }
        return console_list;
    }

    /**
     * 得到NFA表
     * @return
     */
    public Map<Integer, ArrayList<Integer>> getResult() {
        return result;
    }

    /**
     * 输出
     * @param list
     */
    public void Print(ArrayList<NFAState> list){
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getState_num());
            System.out.print(" ");
        }
//        System.out.println();
    }
}
