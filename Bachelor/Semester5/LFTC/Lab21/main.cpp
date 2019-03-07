#include <iostream>
#include <fstream>
#include <vector>
#include <string>
using namespace std;
vector<int>  alf;

vector<int> tokenize(string str) {
    vector<int> tokens;
    string token = "";
    for (int i = 0; i < str.length(); i++) {
        if (str[i] != ' ')
            tokens.push_back((int)str[i]);
    }
    return tokens;
}

void printArray(vector<int> vect){
    for(int i = 0; i < vect.size(); i++){
        cout<<char(vect[i])<<" ";
    }
    cout<<endl;
}

int poz(int x){
    for(int i = 0; i < alf.size(); i++)
        if(alf[i] == x)
            return i;
}

int main() {
    cout<<"bla";
    ifstream file("const.txt");
    cout<<"bla1";
    if (!file.is_open()) {
        cout << "error while opening the file\n";
        exit(0);
    } 
    cout<<"bla2";
    string str;
    std::getline(file, str);
    vector<int>  q = tokenize(str);
    std::getline(file, str);
    alf = tokenize(str);
    std::getline(file, str);
    vector<int>  init = tokenize(str);
    std::getline(file, str);
    vector<int>  fin = tokenize(str);
    vector<vector<int>> tranzitii;
    cout<<"bla3"<<endl;
    while (std::getline(file, str)) {
        vector<int>  tranz = tokenize(str);
        printArray(tranz);
        tranzitii.push_back(tranz);
    }
    cout<<"bla4"<<endl;
    int l = tranzitii.size();
    int automat[q.size()][alf.size()];
    cout<<q.size()<<endl;
    for(int i = 0; i < q.size(); i++)
        for(int j = 0; j <alf.size(); j++)
            automat[i][j] = -1;
    for(int i = 0; i < tranzitii.size(); i++)
    {
        automat[tranzitii[i][0]-48][poz(tranzitii[i][1])] = tranzitii[i][2]-48;
    }
     for(int i = 0; i < q.size(); i++){
        for(int j = 0; j <alf.size(); j++)
            cout<<automat[i][j]<<" ";
        cout<<endl;
     }
    int opt = 1 ;
    //cout<<"bla5";
    while(true)
    {
        cout<<"Alegeti o optiune: "<<endl;
        cout<<"1 - Afiseaza multimea starilor"<<endl;
        cout<<"2 - Afiseaza alfabetul"<<endl;
        cout<<"3 - Afiseaza tranzitiile"<<endl;
        cout<<"4 - Afiseaza multimea starilor finale"<<endl;
        cout<<"5 - Verifica daca o secventa este acceptata de automatul finit"<<endl;
        cout<<"6 - Determina cel mai lung prefix dintr-o secventa data care este o secventa acceptata de automat"<<endl;
        cin>>opt;
        if(opt<0 || opt >6)
            return 0;
        if(opt == 1)
            printArray(q);
        if(opt == 2)
            printArray(alf);
        if(opt == 3){
            for(int i = 0; i < tranzitii.size(); i++){
                cout<<"(" ;
                cout<<(char)tranzitii[i][0]; 
                cout<<" , ";
                cout<<(char)tranzitii[i][1]; 
                cout<<") - >"; 
                cout<<(char)tranzitii[i][2]<<endl;
            }
            cout<<endl;
        }
         if(opt == 4)
            printArray(fin);
        if(opt == 5)
        {
            cout<<"Introduceti secventa: ";
            string secv;
            cin>>secv;
            int stare = init[0]-48;
            int i=0;
            while(i<secv.size()){
                cout<< stare << " ";
                stare = automat[stare][poz((int)secv[i])];
                cout<<secv[i]<<" "<<stare<<" "<<endl;
                if(stare == -1)
                {
                   goto neacceptat;
                }
                if( i == secv.size()-1){
                    for(int j = 0; j < fin.size(); j++)
                        if(stare == fin[j]-48)
                            goto acceptat;
                    goto neacceptat;
                }
                i++;
            }
       
            acceptat:
            cout<<"Secventa acceptata"<<endl;
            goto next;
            neacceptat:
            cout<<"Secventa neacceptata"<<endl;
            next:
            cout<<endl;
        }
        if(opt == 6)
        {
            cout<<"Introduceti secventa: ";
            string secv;
            cin>>secv;
            int stare = init[0] - 48;
            int i=0;
            string prefix = "";
            while(i<secv.size()){
                stare = automat[stare][poz((int)secv[i])];
                if(stare == -1)
                {
                   goto neacc;
                }
                for(int j = 0; j < fin.size(); j++)
                    if(stare == fin[j]-48)
                    {
                        prefix = secv.substr(0,i+1);
                    }
                i++;
            }
           
            neacc:
             if(prefix.size() != 0)
            {
                cout<<"Prefix: "<<prefix<<endl;
            }else
                cout<<"Nu exista niciun prefix care sa fie acceptat"<<endl;
        } 
    }
   
    return 0;
}

