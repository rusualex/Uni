#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <unordered_map>
using namespace std;
unordered_map<string, int> TS = { };
vector<string> FIP;
vector<string> manual;

void addFip(int cod, int pozTS) {
    string inregistrare = to_string(cod) + " , " + to_string(pozTS);
    FIP.push_back(inregistrare);
}

int hashfunction(string word){
    int value = 1;
    for(int i = 0; i < word.length(); i++)
        value+=word[i];
    return  value % 100;
}

void insertTSandFIP(string simbol, int cod){
	int pozitie = TS[simbol];
    if (pozitie == 0)
        TS[simbol] = hashfunction(simbol);
    addFip(cod, TS[simbol]);
}


void showFIP(){
	cout<<"FIP: "<<endl;
	for (int i = 0; i < FIP.size(); i++)
        cout << FIP[i] << endl;
}

void showTS(){
    cout<<"TS: "<<endl;
    for( const auto& n : TS ) {
        cout << "Key:[" << n.first << "] Value:[" << n.second << "]\n";
    }
}


void show(){
    showFIP();
	showTS();
}