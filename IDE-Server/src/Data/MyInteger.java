package Data;

/**
 * A terrible practice to pass the reference through different methods.
 */
public class MyInteger  {
    public Integer value;
    public MyInteger(int a){
        value=a;
    }
    @Override
    public String toString(){
        return value.toString();
    }
}
