#include "stdio.h"

int main(){
    int lab[] = {8,6,7,8,1,4};
    double highScore = 3.00 / 14.00;
    double lowScore = 1.00 / 14.00;
    double medie=0;
    for (int i=0;i<=5;i++){
        if(i==2 || i==3)
            medie = medie + (lab[i]*lowScore);
        else medie = medie + (lab[i]*highScore);
    }
    printf("%f",medie);
    return 0;
}