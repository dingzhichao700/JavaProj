package Chat;
import java.io.Serializable;
public class Objecting implements Serializable{
    String key;
    String value;
    public Objecting(){   }
    public Objecting(String key,String value) {
        this.key =key;
        this.value=value;}
    public void setKey(String key) {
        this.key = key; }
    public void setValue(String value) {
        this.value = value; }

    public String getKey() {
        return key;   }
    public String getValue() {
        return value;}   }
