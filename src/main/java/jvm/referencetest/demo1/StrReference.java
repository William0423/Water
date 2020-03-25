package jvm.referencetest.demo1;

import jvm.referencetest.entity.Employee;
import jvm.referencetest.entity.EmployeeVal;

import java.util.HashMap;

public class StrReference {

    public static void main(String[] args) {

        HashMap<Employee, EmployeeVal> aMap = new
                HashMap<Employee, EmployeeVal>();

        Employee emp = new Employee("Vinoth");
        EmployeeVal val = new EmployeeVal("Programmer");

        aMap.put(emp, val);

        emp = null;

        System.gc();
        System.out.println("Size of Map" + aMap.size());

    }
}
