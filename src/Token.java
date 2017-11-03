/**
 * Created by 费慧通 on 2017/10/25.
 */
public class Token {
    private String code;
    private String type;
    private String error;

    public Token(String code, String type, String error){
        this.code = code;
        this.type = type;
        this.error = error;
    }

    @Override
    public String toString() {
        return "Token{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
