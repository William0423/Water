package designpatterns.structure.builder;

public class Product {

    private String m_a ="";
    private String m_b ="";
    private String m_c ="";


    public void setM_a(String a) {
        this.m_a = a;
    }

    public void setM_b(String b) {
        this.m_b = b;
    }

    public void setM_c(String c) {
        this.m_c = c;
    }

    public void show () {
        System.out.println("product has " + m_a + " " + m_b + " " + m_c);
    }

}
