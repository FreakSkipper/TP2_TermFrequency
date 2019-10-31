#include <iostream>
#include <fstream>
#include <vector>

using namespace std;


void teste(vector<string> words){

}

template<typename Func>
void normalize(vector<string> words){
    for(unsigned i = 0; i < words.size(); i++){
        for(unsigned j = 0 ; j < words[i].size(); j++){
            words[i][j] = tolower(words[i][j]);
        }
    }
    
    for(string i : words)
        cout << i << "!" << endl;

    
}

void filter_chars(vector<string> words, void (*func)( vector<string> ) ){
    vector<string> strip_words;
    for(string i : words){
        int ultima = 0;
        for(unsigned j = 0; j < i.size(); j ++){
            if(i[j] == ' '){
                string sub = i.substr(ultima, j - ultima);
                strip_words.push_back(sub);
                ultima = j+1;
            }
            else if(j == i.size() - 1){
                string sub = i.substr(ultima, j - ultima + 1);
                strip_words.push_back(sub);
            }
        }
    }
    
    func(strip_words);

}

template<typename Func>
void read_file(string path_to_file, Func func){
    fstream file;
    vector<string> words;

    file.open(path_to_file, fstream::in);

    if(!file.is_open()){
        cout << "Falha ao ler o arquivo...\n";
        return;
    }

    string linha;
    while(getline(file, linha)){
        words.push_back(linha);
    }
    file.close();

    func(words, normalize);
}



int main(){
    //void  (*ptr) () = soma;

    //ptr();

    read_file("teste.txt", filter_chars);

    return 0;
}