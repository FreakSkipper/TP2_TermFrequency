function alterou(id, objeto){
    if(objeto.files[0]){
        document.getElementById(id).innerHTML = "Selecionado: " + objeto.files[0].name;
    }
    else{
        document.getElementById(id).innerHTML = "Selecionado: ";
    }
}

function okayError(){
    document.getElementById("alerta").style.display = "none";
}