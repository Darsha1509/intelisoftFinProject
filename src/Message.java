/**
 * Created by Dasha on 30.06.2017.
 */
public class Message {
    private String text;
    private static int id;
    private int fromWhomId;
    private int forWhomId;


    {
        id =0;
    }

    Message(){}

    Message(String text, int id, int fromWhomId, int forWhomId){
        this.text = text;
        Message.id=id;
        this.fromWhomId = fromWhomId;
        this.forWhomId =forWhomId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Message.id = id;
    }

    public int getFromWhomId() {
        return fromWhomId;
    }

    public void setFromWhomId(int fromWhomId) {
        this.fromWhomId = fromWhomId;
    }

    public int getForWhomId() {
        return forWhomId;
    }

    public void setForWhomId(int forWhomId) {
        this.forWhomId = forWhomId;
    }

    public void sendMessage(){

    }
}
