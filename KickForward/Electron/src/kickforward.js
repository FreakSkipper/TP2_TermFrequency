function noOp(){
    return;
}

function print_text(words, func){
    for ( var i = 0 ; i < words.length; i++){
        document.getElementById('termFrequency').innerHTML = document.getElementById('termFrequency').innerHTML + words[i][0] + " : " + words[i][1] + "<br>";
    }

    func(null);
}

function sort(words, func){
    var words_sorted = [];

    words_sorted = words.sort(function(a,b){
        return b[1] - a[1];
    });

    func(words_sorted, noOp);
}

function frequencies(words, func){
    var contagem = [];

    for (var i = 0 ; i < words.length; i++){
        var encontrou = false;
        for(var j = 0; j < contagem.length; j++){
            if(contagem[j][0] == words[i]){
                encontrou = true;
                contagem[j][1] = contagem[j][1] + 1;
            }
        }
        if(!encontrou){
            contagem.push([words[i], 1]);
        }
        
    }

    func(contagem, print_text);
}

function remove_stop_words(words, func){
    var arquivo = document.getElementById('stopWords');
    
    if(arquivo.files){
        var stopwords;
        var file = arquivo.files[0];
        document.getElementById('stopWords_selected').innerHTML =
                                '  nome do arquivo: '+file.name +
                                ';  tipo do arquivo: '+file.type +
                                ';  tamanho do arquivo: '+file.size + ' bytes';
                                
        var reader = new FileReader();

        reader.readAsText(file);
        reader.onload = function(e){
            stopwords = reader.result;
            var split_quebra = stopwords.split('\n');
            var words_limpas = [];
            for(var i = 0; i < words.length; i++){
                var encontrou = false;
                for(var j = 0; j < split_quebra.length; j++){
                    
                    if(split_quebra[j] == words[i]){
                        encontrou = true;
                    }
                }

                if(!encontrou){
                    words_limpas.push(words[i]);
                }
            }

            func(words_limpas, sort);
        }
    }    
}

function normalize(words, func){
    var words_lower = [];
    for(var i = 0; i < words.length; i++){
        words_lower.push(words[i].toLowerCase().trim());
    }

    func(words_lower, frequencies);
}

function filter_chars(words, func){
    var split_quebra = words.split('\n');
    var split_total = [];
    
    for(var i = 0; i < split_quebra.length; i++){
        var split_space = split_quebra[i].split(' ');
        split_total = split_total.concat(split_space);
    }

    func(split_total, remove_stop_words);
}

function read_file(func){
    var arquivo = document.getElementById('arquivo');
    
    if(arquivo.files){
        var words;
        var file = arquivo.files[0];
        document.getElementById('arquivo_selected').innerHTML =
                                '  nome do arquivo: '+file.name +
                                ';  tipo do arquivo: '+file.type +
                                ';  tamanho do arquivo: '+file.size + ' bytes'

        var reader = new FileReader();

        reader.readAsText(file);
        reader.onload = function(e){
            
            words = reader.result;
            func(words, normalize);
            // document.getElementById('finish').innerHTML = "Finalizado!";
        }
    }
}

function main(){
    read_file(filter_chars);
    document.getElementById('finish').innerHTML = "Finalizado!";
}