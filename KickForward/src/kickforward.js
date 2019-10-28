function noOp(){
    return;
}

function print_text(words, func){
    if (words.length > 0){
        var maior = words[0][1];

        for ( var i = 0 ; i < words.length; i++){
            var porcento = words[i][1]*100/maior;
            document.getElementById('termFrequency').innerHTML = document.getElementById('termFrequency').innerHTML + 
            // "<span class=\"word-left\">"+ words[i][0] + "</span><div class=\"progressBar\" style=\"height:14px;width:"+ porcento +"%\"></div> " + "<span class=\"word-right\">" + words[i][1] + "</span><br>";
            "<div class=\"Word\">" +
            "<p>"+ words[i][0] + "</p>" +
            "<div class=\"progressBar\" style=\"width:"+ porcento +"%; height:10px\"><div class=\"barFill\"></div></div>" +
            "<p class=\"word-right\">"+ words[i][1] + "</p>" +
            "</div>";
        }
    }

    func();
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
                                
        var reader = new FileReader();

        reader.readAsText(file);
        reader.onload = function(e){
            stopwords = reader.result;
            var split_quebra = stopwords.split('\n');
            var words_limpas = [];
            
            for(var i = 0; i < words.length; i++){
                var encontrou = false;
                for(var j = 0; j < split_quebra.length; j++){
                    
                    if(split_quebra[j].trim() == words[i]){
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
    document.getElementById('termFrequency').innerHTML = "";
    read_file(filter_chars);
    document.getElementById('finish').innerHTML = "Finalizado!";
}

function alterou(id, objeto){
    document.getElementById(id).innerHTML = "Selecionado: " + objeto.files[0].name;
}