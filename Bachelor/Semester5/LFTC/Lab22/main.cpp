#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <unordered_map>
using namespace std;

string cuvRez[] = {"void", "double", "cout", "endl", "int", "while", "if",
    "else", "return", "cin"};
string oper[] = {"+", "-", "*", "<<", ">>", "!=", ">", "=", "=="};
string sep[] = {";", ",", "{", "}", " ", "(", ")"};
unordered_map<string, int> TS = { };
vector<string> tokens;
vector<string> identifConst;
vector<string> opSepKeyw;
vector<string> cuvinteRezervate(cuvRez, cuvRez + sizeof (cuvRez) / sizeof (string));
;
vector<string> operatori(oper, oper + sizeof (oper) / sizeof (string));
;
vector<string> separatori(sep, sep + sizeof (sep) / sizeof (string));
;
vector<string> FIP;
vector<int>  alfIdent;
vector<int>  initIdent;
vector<int>  finIdent;
vector<vector<int>> automatIdent;
vector<int>  alfConst;
vector<int>  initConst;
vector<int>  finConst;
vector<vector<int>> automatConst;
//string tokens = [];

/*
 * 
 */

void tokenizare(string str) {
    string token = "";
    int ct = 0;
    for (int i = 0; i < str.length(); i++) {
        if (str[i] != ' ')
            token = token + str[i];
        else {
            tokens.push_back(token);
            token = "";
        }
    }
    tokens.push_back(token);
}

vector<int> tokenizareAutomat(string str) {
    vector<int> tokens;
    string token = "";
    for (int i = 0; i < str.length(); i++) {
        if (str[i] != ' ')
            tokens.push_back((int)str[i]);
    }
    return tokens;
}

int pozIdent(int x){
    for(int i = 0; i < alfIdent.size(); i++)
        if(alfIdent[i] == x)
            return i;
    return -1;
}
int pozConst(int x){
    for(int i = 0; i < alfConst.size(); i++)
        if(alfConst[i] == x)
            return i;
    return -1;
}


bool isOpSepOrKeyword(string car) {
    for (int i = 0; i < operatori.size(); i++)
        if (car == operatori[i])
            return true;
    for (int i = 0; i < separatori.size(); i++)
        if (car == separatori[i])
            return true;
    for (int i = 0; i < cuvinteRezervate.size(); i++)
        if (car == cuvinteRezervate[i])
            return true;
    return false;
}

int find(string token, vector<string> list) {
    for (int i = 0; i < list.size(); i++)
        if (token == list[i])
            return i;
    return -1;
}

void insertFIP(int cod, int pozTS) {
    string inregistrare = to_string(cod) + " , " + to_string(pozTS);
    //cout << "IN FIP: " << inregistrare << endl;
    FIP.push_back(inregistrare);
}

bool isIdent(string token) {
    if (token.length() > 8)
        return false;
//    for (int i = 0; i < token.length(); i++)
//        if (isalpha(token[i]) == false)
//            return false;
//    return true;
     int stare = initIdent[0]-48;
            int i=0;
            while(i<token.size()){
                if(pozIdent((int)token[i]) == -1)
                    return false;
                //cout<<"cauta "<< (int)token[i] << " poz:"<<pozIdent((int)token[i])<<" "<< stare << " ";
                
                
                stare = automatIdent[stare][pozIdent((int)token[i])];
                //cout<<token[i]<<" "<<stare<<" "<<endl;
                if(stare == -1)
                {
                   //goto neacceptat;
                    return false;
                }
                if( i == token.size()-1){
                    for(int j = 0; j < finIdent.size(); j++)
                        if(stare == finIdent[j]-48)
                            return true; //goto acceptat;
                    return false;//goto neacceptat;
                }
                i++;
            }
//            acceptat:
//            return true;
//            goto next;
//            neacceptat:
//            return false;
//            next:
//            cout<<endl;
//       
            return false;
}

bool isConst(string token) {
    
    int stare = initConst[0]-48;
            int i=0;
            while(i<token.size()){
                if(pozConst((int)token[i]) == -1)
                    return false;
                
                stare = automatConst[stare][pozConst((int)token[i])];
              
                if(stare == -1)
                {
                   return false;
                }
                if( i == token.size()-1){
                    for(int j = 0; j < finConst.size(); j++)
                        if(stare == finConst[j]-48)
                            return true;
                }
                i++;
            }
       
            return false;
//    if (token.length() > 1 && token[0] == '0' && token[1] != '.')
//        return false;
//    for (int i = 0; i < token.length(); i++) {
//        if (token[i] != '.' && isdigit(token[i]) == false)
//            return false;
//        if (token[i] == '.' && i == 0)
//            return false;
//        if (token[i] == '.' && i == token.length() - 1)
//            return false;
//        if (token[i] == '.' && isdigit(token[i - 1]) == false)
//            return false;
//        if (token[i] == '.' && isdigit(token[i + 1]) == false)
//            return false;
//    }
//    return true;
}
int hashfunction(string word){
    int value = 1;
    for(int i = 0; i < word.length(); i++)
        value+=word[i];
    return  value % 100;
}
string verificare(int poz) {
    int pozitie;
    string mesaj = "";
    for (int i = poz; i < tokens.size(); i++) {
        //cout << "token: " << tokens[i] << endl;
        mesaj = "";
        if (isOpSepOrKeyword(tokens[i]) == true) {
            pozitie = find(tokens[i], opSepKeyw);
            //cout << "POZ op, sep, sau cuv rez " << pozitie << endl;
            if (pozitie == -1) {
                opSepKeyw.push_back(tokens[i]);
                pozitie = opSepKeyw.size() - 1;
                //cout << "POZ op, sep, sau cuv rez " << pozitie << endl;
            }
            //cout<< "ins in fip" << pozitie;
            insertFIP(pozitie, -1);
            mesaj = "ok";
        } else if (isIdent(tokens[i]) == true) {
            //pozitie = find(tokens[i], identifConst);
            pozitie = TS[tokens[i]];
            // cout << "POZ ident " << pozitie << endl;
             if (pozitie == 0) {
            //if (pozitie == -1) {
                 TS[tokens[i]] = hashfunction(tokens[i]);
                 //identifConst.push_back(tokens[i]);
                //pozitie = identifConst.size() - 1;
            //    cout << "POZ ident " << pozitie << endl;
            }
            //insertFIP(0, pozitie);
            insertFIP(0, TS[tokens[i]]);
            mesaj = "ok";
            //cout<<"ident "<< tokens[i] << endl;
        } else if (isConst(tokens[i]) == true) {
            //pozitie = find(tokens[i], identifConst);
            pozitie = TS[tokens[i]];
            //cout << "POZ const " << pozitie << endl;
            if (pozitie == 0) {
                //identifConst.push_back(tokens[i]);
                TS[tokens[i]] = hashfunction(tokens[i]);
                //pozitie = identifConst.size() - 1;
            //    cout << "POZ const " << pozitie << endl;
            }
            insertFIP(1, TS[tokens[i]]);
            mesaj = "ok";
        }
        if (mesaj != "ok") {
            mesaj = "Eroare lexicala, cuvantul " + tokens[i];
            //cout << "cuv gresit" << tokens[i];
            return mesaj;
        }
    }
    return mesaj;
}
void afiseazaVect(vector<int> vect){
    for(int i = 0; i < vect.size(); i++){
        cout<<char(vect[i])<<" ";
    }
    cout<<endl;
}


int main() {
    
    ifstream faut("identificatori.txt");
 
    if (!faut.is_open()) {
        cout << "error while opening the file\n";
        exit(0);
    } 
    
    string str;
    std::getline(faut, str);
    vector<int>  qIdent = tokenizareAutomat(str);
    
    std::getline(faut, str);
    alfIdent = tokenizareAutomat(str);
    for(int i = 0; i <alfIdent.size(); i++)
        cout<<alfIdent[i]<<" ";
    cout<<endl;
    std::getline(faut, str);
    initIdent = tokenizareAutomat(str);
    std::getline(faut, str);
    finIdent = tokenizareAutomat(str);
    vector<vector<int>> tranzitiiIdent;
 
    while (std::getline(faut, str)) {
        vector<int>  tranz = tokenizareAutomat(str);
        afiseazaVect(tranz);
        tranzitiiIdent.push_back(tranz);
    }
 
   // int l = tranzitiiIdent.size();
    //int automatIdent[qIdent.size()][alfIdent.size()];
    //cout<<qIdent.size()<<endl;
    vector<int> autIdent;
    for(int i = 0; i < qIdent.size(); i++){
        for(int j = 0; j < alfIdent.size(); j++){
            autIdent.push_back(-1);
        }
        automatIdent.push_back(autIdent);
        autIdent.clear();
     
    }
 
     for(int i = 0; i < tranzitiiIdent.size(); i++)
    {
        automatIdent[tranzitiiIdent[i][0]-48][pozIdent(tranzitiiIdent[i][1])] = tranzitiiIdent[i][2]-48;
    }
     for(int i = 0; i < qIdent.size(); i++){
       for(int j = 0; j <alfIdent.size(); j++)
            cout<<automatIdent[i][j]<<" ";
        cout<<endl;
     }
    ifstream fconst("constante.txt");
   
    if (!fconst.is_open()) {
        cout << "error while opening the file\n";
        exit(0);
    } 
  
    std::getline(fconst, str);
    vector<int>  qConst = tokenizareAutomat(str);
    std::getline(fconst, str);
    alfConst = tokenizareAutomat(str);
    std::getline(fconst, str);
    initConst = tokenizareAutomat(str);
    std::getline(fconst, str);
    finConst = tokenizareAutomat(str);
    vector<vector<int>> tranzitiiConst;
    //cout<<"bla3"<<endl;
    while (std::getline(fconst, str)) {
        vector<int>  tranz = tokenizareAutomat(str);
        afiseazaVect(tranz);
        tranzitiiConst.push_back(tranz);
    }
    //cout<<"bla4"<<endl;
    //int l = tranzitiiConst.size();
    //int automatIdent[qIdent.size()][alfIdent.size()];
    //cout<<qIdent.size()<<endl;
    vector<int> autConst;
    for(int i = 0; i < qConst.size(); i++){
        for(int j = 0; j <alfConst.size(); j++)
            autConst.push_back(-1);
        automatConst.push_back(autConst);
        autConst.clear();
    }
    for(int i = 0; i < tranzitiiConst.size(); i++)
    {
        automatConst[tranzitiiConst[i][0]-48][pozConst(tranzitiiConst[i][1])] = tranzitiiConst[i][2]-48;
    }
     for(int i = 0; i < qConst.size(); i++){
       for(int j = 0; j <alfConst.size(); j++)
            cout<<automatConst[i][j]<<" ";
        cout<<endl;
     }
    
    
    string numeFisier;
    cin>>numeFisier;
    ifstream file(numeFisier);
    //string str;
    int linie = 1;
    //int pozitie = 0;
    int inceputLinie = 0;

    if (!file.is_open()) {
        cout << "error while opening the file\n";
        exit(0);
    } 
    opSepKeyw.push_back("ident & const");
    while (std::getline(file, str)) {
        //cout << str << endl;
        inceputLinie = tokens.size();
        tokenizare(str);
        string mesaj = verificare(inceputLinie);
        if (mesaj != "ok") {
            cout << mesaj << ", linia " << linie;
            return -1;
        }
        linie++;
    }
    for(int i = 0; i < tokens.size(); i++)
              cout<<"Tokens: "<<tokens[i]<<endl;
    for (int i = 0; i < opSepKeyw.size(); i++)
        cout << "Op || Sep || Cuv Rez: " << opSepKeyw[i] << " cod manual: " << i << endl;
    //for (int i = 0; i < identifConst.size(); i++)
    //    cout << "Identif sau COnst: " << identifConst[i] << endl;
    cout<<"FIP: "<<endl;
    for (int i = 0; i < FIP.size(); i++)
        cout << FIP[i] << endl;
    cout<<"TS: "<<endl;
    for( const auto& n : TS ) {
        cout << "Key:[" << n.first << "] Value:[" << n.second << "]\n";
    }

    return 0;
}