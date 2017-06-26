package logic;

import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class RuntimeStack {
    private ArrayList<Integer> stack=new ArrayList<>();

    public int pop(){
        int tmp=stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        return tmp;
    }

    public int get(int n){
        while(stack.size()-1<n){
            stack.add(0);
        }
        return stack.get(n);
    }

    public int get(){
        return stack.get(stack.size()-1);
    }
    public void push(int a){
        stack.add(a);
    }
    public void add(int n,int a){
        if(stack.size()<=n){
            stack.add(n,a);
        }else {
            stack.set(n,a);
        }
    }
    public Integer replace(int n,int a){
        while(stack.size()-1<n){
            stack.add(0);
        }
        int temp=stack.remove(n);
        stack.add(n,a);
        return temp;
    }
    public Integer remove(int n){
        return stack.remove(n);
    }
    public void clear(){
        stack.clear();
    }

    @Override
    public String toString(){
        return stack.toString();
    }
}
