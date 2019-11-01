#include <iostream>
#include <iomanip>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

using Func = void(*)(vector<string>, void *);

auto lambda_file = [](string path_to_file){ 
    fstream file;
    vector<string> words;

    file.open(path_to_file, fstream::in);

    if(!file.is_open()){
        cout << "Falha ao ler o arquivo..." << endl;
        return words;
    }

    string linha;
    while(getline(file, linha)){
        words.push_back(linha);
    }
    file.close();

    return words;
 };

 bool isEspecial(char a){
    string especiais = "!?°ºª@#$%¨&*()+=,.;_-:></\\{[]}~^´`|\"' ";
    if(especiais.find(a) != string::npos){
        return true;
    }
    return false;
}

bool compareSort (const pair<string,int> a, const pair<string,int> b){
  return (a.second > b.second );
}

void noOp(){
    return;
}

void print_text(vector<pair<string,int>> words, void *func){
    for(pair<string, int> i : words)
        cout << left << setw(20) << i.first << "========= " << right << setw(5) << i.second << endl;

    reinterpret_cast<void(*)()>(func)();
}

void write_file(vector<pair<string,int>> words, void *func){
    ofstream file;
    file.open("result_termFrequency.txt", ios::out);

    if(file.is_open()){
        for(pair<string, int> i : words){
            file << left << setw(20) << i.first << "=========" << right << setw(5) << i.second << '\n';
        }
        file.flush();
        file.close();
    }
    else{
        cout << "Error in Create File Result.." << endl;
    }

    reinterpret_cast<void(*)(vector<pair<string,int>>, void *)>(func)(words, (void *) noOp);
}

void sorte(vector<pair<string,int>> words, void * func){
    sort(words.begin(), words.end(), compareSort);

    reinterpret_cast<void(*)(vector<pair<string,int>>, void *)>(func)(words, (void *) print_text);
}

void frequencies(vector<string> words, void * func){
    vector<pair<string,int>> contagem;

    cout << "Aguardando";
    int it = 0;
    for(string i : words){
        it++;

        if(it == 1000){
            cout << ".";
            it = 0;
        }

        bool encontrou = false;
        for( unsigned j = 0; j < contagem.size(); j++){
            if(contagem[j].first == i){
                encontrou = true;
                contagem[j].second++;
            }
        }
        if(!encontrou){
            contagem.push_back(make_pair(i, 1));
        }
    }
    cout << '\n';
    reinterpret_cast<void(*)(vector<pair<string,int>>, void *)>(func)(contagem, (void *) write_file);
}

void remover_stopwords(vector<string> words, void * func){
    vector<string> stopwords = lambda_file("stopwords.txt");
    vector<string> words_limpas;
    for(string i : words ){
        bool encontrou = false;
        for(string j : stopwords ){
           if(i == j)
                encontrou = true;
        }
        if(!encontrou)
            words_limpas.push_back(i);
    }

    reinterpret_cast<Func>(func)(words_limpas, (void*) sorte);   
}

void normalize(vector<string> words, void * func){
    for(unsigned i = 0; i < words.size(); i++){
        for(unsigned j = 0 ; j < words[i].size(); j++){
            if(words[i][j] == ' '){
                words[i].erase(j);
                j--;
            }
            else{
                words[i][j] = tolower(words[i][j]);
            }
        }
    }

    reinterpret_cast<Func>(func)(words, (void *) frequencies);
}

void filter_chars(vector<string> words, void *func){
    vector<string> strip_words;

    cout << "Filtrando";
    for(unsigned i = 0; i < words.size(); i++){
        if( i % 1000 == 0)
            cout << ".";
        for(unsigned j = 0; j < words[i].size(); j++){
            if(isEspecial(words[i][j])){
                words[i][j] = ' ';
            }
        }
    }
    cout << "\n";

    for(string i : words){
        int ultima = 0;
        for(unsigned j = 0; j < i.size(); j ++){
            if(i[j] == ' '){
                string sub = i.substr(ultima, j - ultima);
                ultima = j+1;

                if(sub.size() > 2)
                    strip_words.push_back(sub);
            }
            else if(j == i.size() - 1){
                string sub = i.substr(ultima, j - ultima + 1);

                if(sub.size() > 2)
                    strip_words.push_back(sub);
            }
        }
    }
    
    reinterpret_cast<Func>(func)(strip_words, (void *) remover_stopwords);
}

void read_file(string path_to_file, void *func){
   vector<string> words = lambda_file(path_to_file);

   reinterpret_cast<Func>(func)(words, (void *) normalize);
}



int main(){
    /* Leitura de Path  */
    string caminho;
    cout << "\n========== Term Frequency - UnB =============\n";
    cout << "Diga qual arquivo voce deseja pesquisar: ";
    cin >> caminho;
    cout << "\n";
    /* ================== */

    read_file(caminho, (void *) filter_chars);
    cout << "\nUm Arquivo contendo todo o Resultado foi criado com o nome: result_termFrequency.txt\n\nFinalizado." << endl;

    return 0;
}
