package jvm.referencetest.demo2;

import jvm.referencetest.entity.Employee;
import jvm.referencetest.entity.EmployeeVal;

import java.util.WeakHashMap;

public class WeakRef {

    public static void main(String args[]) {
        WeakHashMap<Employee, EmployeeVal> aMap =
                new WeakHashMap<Employee, EmployeeVal>();

        Employee emp = new Employee("Vinoth");
        EmployeeVal val = new EmployeeVal("Programmer");

        aMap.put(emp, val);

//        emp = null;

        System.gc();
        int count = 0;
        while (0 != aMap.size()) {
            System.out.println(count);
            ++count;
            System.gc();
        }
        System.out.println("Took " + count
                + " calls to System.gc() to result in weakHashMap size of : "
                + aMap.size());
    }

}
