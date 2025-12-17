package Model

class Jogador(
    var nome: String,
    var territorioAtual: Territorio,
    val tropas: MutableList<Tropa> = mutableListOf(
        Guerreiro(),
        Mago(),
        Arqueiro()
    ),
    var ouro: Int = 2000,
    var comida: Int = 10,
    var territoriosDominados: MutableList<Territorio> = mutableListOf(territorioAtual)
){
    var bonusGuerreiroAT = 0
    var bonusMagoAT = 0
    var bonusArqueiroAT = 0
}