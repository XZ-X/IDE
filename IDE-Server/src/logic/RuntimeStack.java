package logic;

import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class RuntimeStack {
    private ArrayList<Integer> stack=new ArrayList<>();

    public int pop(){
        int tmp=stack.get(stack.size());
        stack.remove(stack.size());
        return tmp;
    }

    public int get(int n){
        while(stack.size()-1<n){
            stack.add(0);
        }
        return stack.get(n);
    }

    public int get(){
        return stack.get(stack.size());
    }
    public void add(int a){
        stack.add(a);
    }
    public void add(int n,int a){
        if(stack.size()<=n){
            stack.add(n,a);
        }else {
            stack.set(n,a);
        }
    }
    public void replace(int n,int a){
        stack.remove(n);
        stack.add(n,a);
    }
    public void remove(int n){
        stack.remove(n);
    }
    public void clear(){
        stack=new ArrayList<Integer>();
    }

    @Override
    public String toString(){
        return stack.toString();
    }
}
