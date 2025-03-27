//cohesion
//Niet goed -> Deze functie regelt processing en save dat zijn 2 processen
function multiplyAndSave(number){
    // Processing data
    var newNumber = number * 2;
    // Saving data
    println(newNumber); //Dit zou dan een save functie moeten zijn
}

//Wel goed -> hierbij heeft elke functie 1 verantwoordelijkheid
function multiply(number){
    return number * 2;
}
function save(number){
    println(number) //Dit zou dan een save functie moeten zijn
}