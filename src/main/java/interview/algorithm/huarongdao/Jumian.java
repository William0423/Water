package interview.algorithm.huarongdao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jumian {


    char[] qizi = new char[20];

    Jumian parent;

    public Jumian() {

    }

    public Jumian(char[] qizi) {
        this.qizi = qizi;
    }

    public List<Jumian> childrenJumian() {
        List<Jumian> result = new ArrayList<Jumian>();


        int firstZeroIndex = findFirstZero();
        int secondZeroIndex = findSecondeZero(firstZeroIndex);

        //先看第一个0上面的棋子
        checkUpSideOfFirstZero(result, firstZeroIndex, secondZeroIndex);

        //看第一个0右边的棋子
        checkRightSideOfFirstZero(result, firstZeroIndex, secondZeroIndex);

        //看第一个0下面的棋子
        checkDownSideOfFirstZero(result, firstZeroIndex, secondZeroIndex);

        //看第一个0左边的棋子
        checkLeftSideOfFirstZero(result, firstZeroIndex, secondZeroIndex);

        //看第二个0上面的棋子
        if(secondZeroIndex - 4 >= 0) {
            char[] newQizi = new char[20];
            System.arraycopy(qizi, 0, newQizi, 0, 20);
            char upQizi = newQizi[secondZeroIndex - 4];
            switch (upQizi) {
                case '1':
                    newQizi[secondZeroIndex] = '1';
                    newQizi[secondZeroIndex - 4] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case '0':
                    break;
                case 'C':
                case 'G':
                    break;
                case 'H':
                case 'Z':
                case 'M':
                case 'Y':
                    newQizi[secondZeroIndex] = upQizi;
                    newQizi[secondZeroIndex - 8] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                default:
                    throw new IllegalStateException("invalid jumian: upQizi" + upQizi + "\n"+ this.toString());

            }

        }

        //看第二个0的右边
        if(secondZeroIndex % 4 != 3) {
            char[] newQizi = new char[20];
            System.arraycopy(qizi, 0, newQizi, 0, 20);
            char rightQizi = newQizi[secondZeroIndex  + 1];
            switch (rightQizi) {
                case '1':
                    newQizi[secondZeroIndex] = '1';
                    newQizi[secondZeroIndex + 1] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case 'G':
                    newQizi[secondZeroIndex] = 'G';
                    newQizi[secondZeroIndex + 2] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case 'H':
                case 'M':
                case 'Y':
                case 'Z':
                case 'C':
                    //这几种情况，要么在第一个0的时候已经考虑过了，要么没法产生新局面
                    break;

                default:
                    throw new IllegalStateException("invalid jumian: rightQizi" + rightQizi + "\n"+ this.toString());
            }

        }
        //看第二个0的下面
        if(secondZeroIndex + 4 < 20) {
            char[] newQizi = new char[20];
            System.arraycopy(qizi, 0, newQizi, 0, 20);
            int downQiziIndex = secondZeroIndex + 4;
            char downQizi = newQizi[downQiziIndex];
            switch (downQizi) {
                case '1':
                    newQizi[secondZeroIndex] = '1';
                    newQizi[downQiziIndex] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case 'H':
                case 'Z':
                case 'M':
                case 'Y':
                    newQizi[secondZeroIndex] = downQizi;
                    newQizi[downQiziIndex + 4] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case 'G':
                case 'C':
                    //这两种情况，要么在第一个0的时候已经考虑过了，要么没法产生新局面
                    break;
                default:
                    throw new IllegalStateException("invalid jumian: rightQizi" + downQizi + "\n"+ this.toString());
            }

        }
        //看第二个0的左边的棋子
        if(secondZeroIndex % 4 != 0) {
            char[] newQizi = new char[20];
            System.arraycopy(qizi, 0, newQizi, 0, 20);
            int leftQiziIndex = secondZeroIndex - 1;
            char leftQizi = newQizi[leftQiziIndex];
            switch (leftQizi) {
                case '1':
                    newQizi[secondZeroIndex] = '1';
                    newQizi[leftQiziIndex] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case '0':
                    break;
                case 'H':
                case 'Z':
                case 'M':
                case 'Y':
                case 'C':
                    //这几种情况，要么在第一个0的时候已经考虑过了，要么没法产生新局面
                    break;
                case 'G':
                    newQizi[secondZeroIndex] = 'G';
                    newQizi[leftQiziIndex - 1] = '0';
                    result.add(new Jumian(newQizi));
                    break;

                default:
                    throw new IllegalStateException("invalid jumian: rightQizi" + leftQizi + "\n"+ this.toString());
            }

        }

        return result;


    }

    public Jumian getParent() {
        return parent;
    }

    public void setParent(Jumian parent) {
        this.parent = parent;
    }

    private void checkLeftSideOfFirstZero(List<Jumian> result, int firstZeroIndex, int secondZeroIndex) {
        if(firstZeroIndex % 4 != 0) {
            char[] newQizi = new char[20];
            System.arraycopy(qizi, 0, newQizi, 0, 20);
            char leftQizi = qizi[firstZeroIndex - 1];
            switch (leftQizi) {
                case '1':
                    newQizi[firstZeroIndex - 1] = '0';
                    newQizi[firstZeroIndex] = '1';
                    result.add(new Jumian(newQizi));
                    break;
                case 'G':
                    newQizi[firstZeroIndex] = 'G';
                    newQizi[firstZeroIndex - 2] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case 'H':
                case 'Z':
                case 'M':
                case 'Y':
                    if(firstZeroIndex + 4 == secondZeroIndex && newQizi[firstZeroIndex + 3] == leftQizi) {
                        newQizi[firstZeroIndex] = leftQizi;
                        newQizi[secondZeroIndex] = leftQizi;
                        newQizi[firstZeroIndex - 1] = '0';
                        newQizi[secondZeroIndex - 1] = '0';
                        result.add(new Jumian(newQizi));
                    }
                    break;
                case 'C':
                    if(firstZeroIndex + 4 == secondZeroIndex && newQizi[firstZeroIndex + 3] == leftQizi) {
                        newQizi[firstZeroIndex] = leftQizi;
                        newQizi[secondZeroIndex] = leftQizi;
                        newQizi[firstZeroIndex - 2] = '0';
                        newQizi[secondZeroIndex - 2] = '0';
                        result.add(new Jumian(newQizi));
                    }
                    break;
                default:
                    throw new IllegalStateException("invalid jumian: upQizi" + leftQizi + "\n"+ this.toString());
            }
        }
    }

    private void checkDownSideOfFirstZero(List<Jumian> result, int firstZeroIndex, int secondZeroIndex) {
        if(firstZeroIndex + 4 < 20) {
            char[] newQizi = new char[20];
            System.arraycopy(qizi, 0, newQizi, 0, 20);
            char downQizi = qizi[firstZeroIndex + 4];
            switch(downQizi) {
                case '1':
                    newQizi[firstZeroIndex] = '1';
                    newQizi[firstZeroIndex + 4] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case '0':
                    break;
                case 'G':
                    if(firstZeroIndex % 4 != 3 && firstZeroIndex + 1 == secondZeroIndex && newQizi[secondZeroIndex + 4] == 'G' ) {
                        newQizi[firstZeroIndex] = 'G';
                        newQizi[secondZeroIndex] = 'G';
                        newQizi[firstZeroIndex + 4] = '0';
                        newQizi[secondZeroIndex + 4] = '0';
                        result.add(new Jumian(newQizi));
                    }
                    break;
                case 'H':
                case 'Z':
                case 'M':
                case 'Y':
                    newQizi[firstZeroIndex] = downQizi;
                    newQizi[firstZeroIndex + 8] = '0';
                    result.add(new Jumian(newQizi));
                    break;
                case 'C':
                    if(firstZeroIndex + 1 == secondZeroIndex && newQizi[firstZeroIndex + 5] == 'C') {
                        newQizi[firstZeroIndex] = 'C';
                        newQizi[secondZeroIndex] = 'C';
                        newQizi[firstZeroIndex + 8] = '0';
                        newQizi[secondZeroIndex + 8] = '0';
                        result.add(new Jumian(newQizi));
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void checkRightSideOfFirstZero(List<Jumian> result, int firstZeroIndex, int secondZeroIndex) {
        if(firstZeroIndex % 4 != 3) {
            char[] newQizi = new char[20];
            System.arraycopy(qizi, 0, newQizi, 0, 20);
            char rightQizi = qizi[firstZeroIndex + 1];
            switch (rightQizi) {

                case '1':
                    newQizi[firstZeroIndex] = '1';
                    newQizi[firstZeroIndex + 1] = '0';
                    result.add(new Jumian(newQizi));
                    break;

                case '0':
                    break;
                case 'G':
                    newQizi[firstZeroIndex+2]='0';
                    newQizi[firstZeroIndex]='G';
                    result.add(new Jumian(newQizi));
                    break;
                case 'C':
                    if(secondZeroIndex == firstZeroIndex + 4 && newQizi[firstZeroIndex + 5] == 'C') {
                        newQizi[firstZeroIndex] = 'C';
                        newQizi[secondZeroIndex] = 'C';
                        newQizi[firstZeroIndex + 2] = '0';
                        newQizi[firstZeroIndex + 6] = '0';
                        result.add(new Jumian(newQizi));
                    }
                    break;

                case 'H':
                case 'Z':
                case 'M':
                case 'Y':
                    if(secondZeroIndex == firstZeroIndex + 4 && newQizi[firstZeroIndex + 5] == rightQizi) {
                        newQizi[firstZeroIndex] = rightQizi;
                        newQizi[secondZeroIndex] = rightQizi;
                        newQizi[firstZeroIndex + 1] = '0';
                        newQizi[firstZeroIndex + 5] = '0';
                        result.add(new Jumian(newQizi));
                    }
                    break;
                default:
                    throw new IllegalStateException("invalid jumian: upQizi" + rightQizi + "\n"+ this.toString());
            }

        }
    }

    private void checkUpSideOfFirstZero(List<Jumian> result, int firstZeroIndex, int secondZeroIndex) {

        if(firstZeroIndex - 4 >= 0) {
            char upQizi = qizi[firstZeroIndex - 4];
            switch (upQizi) {
                case '1':{
                    char[] newQizi = new char[20];
                    System.arraycopy(qizi, 0, newQizi, 0, 20);

                    newQizi[firstZeroIndex] = '1';
                    newQizi[firstZeroIndex - 4] = '0';
                    result.add(new Jumian(newQizi));

                    break;
                }
                case 'H':
                case 'Z':
                case 'M':
                case 'Y':{
                    char[] newQizi = new char[20];
                    System.arraycopy(qizi, 0, newQizi, 0, 20);
                    newQizi[firstZeroIndex] = upQizi;
                    newQizi[firstZeroIndex - 8] = '0';
                    result.add(new Jumian(newQizi));
                }
                break;
                case 'G':{
                    if(secondZeroIndex == firstZeroIndex + 1 && qizi[secondZeroIndex - 4] == 'G') {
                        char[] newQizi = new char[20];
                        System.arraycopy(qizi, 0, newQizi, 0, 20);
                        newQizi[firstZeroIndex] = 'G';
                        newQizi[secondZeroIndex] = 'G';
                        newQizi[firstZeroIndex - 4] = '0';
                        newQizi[secondZeroIndex - 4] = '0';
                        result.add(new Jumian(newQizi));
                    }
                }
                break;
                case 'C':{
                    if(secondZeroIndex == firstZeroIndex + 1 && qizi[secondZeroIndex - 4] == 'C') {
                        char[] newQizi = new char[20];
                        System.arraycopy(qizi, 0, newQizi, 0, 20);
                        newQizi[firstZeroIndex] = 'C';
                        newQizi[secondZeroIndex] = 'C';
                        newQizi[firstZeroIndex - 8] = '0';
                        newQizi[secondZeroIndex - 8] = '0';
                        result.add(new Jumian(newQizi));
                    }
                }
                break;
                default:
                    throw new IllegalStateException("invalid jumian: upQizi" + upQizi + "\n"+ this.toString());
            }

        }
    }

    public boolean isCaocaoCanEscape() {
        if(qizi[17] == 'C' && qizi[18] == 'C') {
            return true;
        }else {
            return false;
        }
    }
    public Jumian getShadow() {
        char[] newQizi = new char[20];
        System.arraycopy(qizi, 0, newQizi, 0, 20);
        for(int i = 0; i < newQizi.length; i++) {
            char ch = newQizi[i];
            switch (ch) {
                case 'H':
                case 'Z':
                case 'M':
                case 'Y':
                    newQizi[i] = 'S';
                    break;

                default:
                    break;
            }
        }
        return new Jumian(newQizi);

    }
    public Jumian flip() {
        char[] newQizi = new char[20];
        System.arraycopy(qizi, 0, newQizi, 0, 20);
        int cols = 4;
        int rows = 5;
        for(int i = 0; i < rows; i++) {
            for(int col = 0; col < cols/2; col++)
                swap(newQizi , i * cols + col, i * cols  + cols - col - 1);
        }
        return new Jumian(newQizi);

    }

    private void swap(char[] array, int pos1 , int pos2) {
        char tmp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = tmp;



    }


    private int findSecondeZero(int firstZeroIndex) {
        for(int i = firstZeroIndex+1; i < qizi.length; i++) {
            if(qizi[i] == '0') {
                return i;
            }
        }
        throw new IllegalStateException("Can not find sencode zero!");
    }


    private int findFirstZero() {
        for(int i = 0; i < qizi.length; i++) {
            if(qizi[i] == '0') {
                return i;
            }
        }
        throw new IllegalStateException("Can not find first zero!");
    }


    public static Jumian first() {
        Jumian result = new Jumian();
        result.qizi = "HCCZHCCZMGGYM11Y1001".toCharArray();
        return result;
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < qizi.length; i=i+4) {
            s += qizi[i];
            s += qizi[i+1];
            s += qizi[i+2];
            s += qizi[i+3];
            s += "\n";
        }

        return s;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(qizi);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Jumian other = (Jumian) obj;
        if (!Arrays.equals(qizi, other.qizi))
            return false;
        return true;
    }











}
