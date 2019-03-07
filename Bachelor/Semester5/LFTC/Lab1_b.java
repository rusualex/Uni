public class Suma{
    public int suma(){
        Scanner scan = new Scanner(System.in);
        double sum;
        if(scan.hasNextInt())
            n=scan.next();
        for(int i=0;i<=n;i++)
            if(scan.hasNextDouble())
                sum+=scan.nextDouble();
        return sum;
    }
}