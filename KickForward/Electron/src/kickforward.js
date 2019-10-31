function noOp(){
    return;
}

function print_text(words, func){
    if (words.length > 0){
        var maior = words[0][1];
        var palavras = "";

        for ( var i = 0 ; i < words.length; i++){
            var porcento = words[i][1]*100/maior;
            palavras = palavras + 
            // "<span class=\"word-left\">"+ words[i][0] + "</span><div class=\"progressBar\" style=\"height:14px;width:"+ porcento +"%\"></div> " + "<span class=\"word-right\">" + words[i][1] + "</span><br>";
            "<div class=\"Word\">" +
            "<p>"+ words[i][0].toString() + "</p>" +
            "<div class=\"progressBar\" style=\"width:"+ porcento.toString() + "%; height:10px\"><div class=\"barFill\"></div></div>" +
            "<p class=\"word-right\">"+ words[i][1].toString() + "</p>" +
            "</div>";
        }

        // alert("chegou1");
        
        // for(var i = 0 ; i < words_sorted.length; i++){
        //     palavras = palavras + words_sorted[i][0].toString() + " : " + words_sorted[i][1].toString() + "<br>";
        // }
        // alert("chegou2");
        // alert(palavras.length);
        document.getElementById('termFrequency').innerHTML = document.getElementById('termFrequency').innerHTML + palavras;


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

    document.getElementById('termFrequency').innerHTML = "";

    // document.getElementById('termFrequency').innerHTML = document.getElementById('termFrequency').innerHTML + palavras;
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
                    
                    if(split_quebra[j].toString().trim() == words[i].toString()){
                        encontrou = true;
                    }
                }

                if(!encontrou){
                    words_limpas.push(words[i].toString());
                }
            }
               
            func(words_limpas, sort);
        }
    }    
}

function normalize(words, func){
    var words_lower = [];
    for(var i = 0; i < words.length; i++){
        words_lower.push(words[i].toString().toLowerCase().trim());
    }
    // document.getElementById('termFrequency').innerHTML = document.getElementById('termFrequency').innerHTML + words_lower;   

    func(words_lower, frequencies);
}

function filter_chars(words, func){
    var split_quebra = words.split('\n');
    var split_total = [];

    var term = document.getElementById("termFrequency");
    term.innerHTML = "<div class=\"circle\"></div>";

    for(var i = 0; i < split_quebra.length; i++){
        var intermediario = split_quebra[i].split(' ');
        for(var j = 0 ; j < intermediario.length; j++){
			var relaced = intermediario[j].trim().replace(/([\u0300-\u036f]|[^0-9a-zA-Z--])/g, '');
            if(relaced.length > 2)
                split_total.push(relaced);
        }        
    }

    // document.getElementById('termFrequency').innerHTML = document.getElementById('termFrequency').innerHTML + split_total;   
    // }
    
    // for(var i = 0; i < split_quebra.length; i++){
    //     var split_space = split_quebra[i].split(' ');
    //     split_total = split_total.concat(split_space);
    //     document.getElementById('termFrequency').innerHTML = document.getElementById('termFrequency').innerHTML + ".";
    // }
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
            document.getElementById('finish').innerHTML = "Finalizado!";
        }
    }
}

function main(){
    document.getElementById('termFrequency').innerHTML = "";
    read_file(filter_chars);
    // document.getElementById('finish').innerHTML = "Finalizado!";
}

function alterou(id, objeto){
    document.getElementById(id).innerHTML = "Selecionado: " + objeto.files[0].name;
}