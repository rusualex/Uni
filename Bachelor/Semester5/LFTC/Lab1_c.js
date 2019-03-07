public class CMMDC{

public int cmmdc(a,b){
        int t;
        while (b != 0)
        {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}