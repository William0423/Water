package interview.algorithm.huarongdao;


import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception
    {

        Deque<Jumian> queue = new LinkedList<>();
        Set<Jumian>  seen = new HashSet<>();

        Jumian first = Jumian.first();

        queue.addLast(first);
        seen.add(first);
        int step = 0;
        while(true) {
            step++;
            if(queue.isEmpty()) {
                System.out.println("Caocao can not escaped!" + "step: "  + step);
                break;
            }

            Jumian top = queue.pop();


            if(top.isCaocaoCanEscape()) {
                Deque<Jumian> trace = new LinkedList<Jumian>();
                trace.addFirst(top);
                while(top.getParent() != null) {
                    trace.addFirst(top.parent);
                    top = top.parent;
                }
                System.out.println("Caocao escaped: \n" + top + "tried: " + step + " step: " + trace.size());
                trace.stream().forEach(System.out::println);
                break;
            }else {
                List<Jumian> children = top.childrenJumian();
                for (Jumian child : children) {
                    Jumian shadow = child.getShadow();
                    if(!seen.contains(shadow) ) {
                        child.setParent(top);
                        seen.add(shadow);
                        seen.add(shadow.flip());
                        queue.addLast(child);
                    }
                }

            }
        }

    }

    private static void deepFirst() {
        Jumian first = Jumian.first();
        Stack<Jumian> stack = new Stack<>();
        Set<Jumian>  all = new HashSet<>();
        stack.push(first);
        all.add(first);
        int step = 0;
        while(true) {
            Jumian top = stack.peek();
//        		System.out.println("top " + (++step) + ": \n" + top );

            if(top.isCaocaoCanEscape()) {
                break;
            }else {
                List<Jumian> nextJumians = top.childrenJumian();
                boolean foundNewJumian = false;
                for (int i = 0; i < nextJumians.size(); i++) {
                    Jumian next = nextJumians.get(i);
                    if(!all.contains(next)) {
                        all.add(next);
                        stack.push(next);
//        					System.out.println("push : \n" + next );
                        foundNewJumian = true;
                        break;
                    }
                }
                if(!foundNewJumian) {
                    Jumian pop = stack.pop();
//        				System.out.println("Stack pop: " + pop);
                }

//        			 System.out.println("Stack  " + stack.size() + ": ");
//        		     stack.stream().forEach(System.out::println);
            }


        }

        System.out.println("Caocao escaped!");
        System.out.println("stack: " + stack.size());
//        BufferedWriter w = Files.newBufferedWriter(new File("/Users/liujian/Downloads/huarongdao").toPath(), StandardOpenOption.CREATE_NEW);
//
//        stack.stream().forEach(j->{
//	        	try {
//	        		w.write(j.toString());
//	        		w.newLine();
//	        	}catch(Exception e) {
//
//	        	}
//        });
//        w.flush();
//        w.close();

        Map<Jumian, Integer> jumain_step = new HashMap<>();

        for(int i = 0 ; i < stack.size(); i ++) {
            jumain_step.put(stack.get(i), i);
        }

        for(int i = 0 ; i < stack.size(); i ++) {
            Jumian tmp = stack.get(i);
            List<Jumian> children = tmp.childrenJumian();
            for (Jumian child : children) {
                Integer s = jumain_step.get(child);
                if(s != null && i + 1 < s ) {
                    System.out.println("step" + i + "  has child in step " + s);
                }
            }
        }
    }
}
