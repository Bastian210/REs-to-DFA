package Category;

import java.util.ArrayList;

/**
 * Created by 费慧通 on 2017/10/23.
 */
public class DFAO {
    private DFAState start;
    private ArrayList<DFAState> end;
    private ArrayList<DFAState> content;

    public DFAO(DFAState start, ArrayList<DFAState> end, ArrayList<DFAState> content){
        this.start = start;
        this.end = end;
        this.content = content;
    }

    public DFAState getStart() {
        return start;
    }

    public ArrayList<DFAState> getEnd() {
        return end;
    }

    public ArrayList<DFAState> getContent() {
        return content;
    }
}
