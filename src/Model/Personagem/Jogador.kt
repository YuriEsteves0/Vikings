package Model.Personagem

import Model.Efeitos.Item
import Model.Efeitos.Itens
import Model.Estruturas.Territorio
import formatarNome

class Jogador(
    var nome: String,
    var territorioAtual: Territorio,
    val tropas: MutableList<Tropa> = mutableListOf(
        Guerreiro(),
        Mago(),
        Arqueiro(),
    ),
    var ouro: Int = 20,
    var comida: Int = 42,
    var territoriosDominados: MutableList<Territorio> = mutableListOf(territorioAtual),
    var inventario: MutableList<Item> = mutableListOf(
    )
){
    var bonusGuerreiroAT = 0
    var bonusMagoAT = 0
    var bonusArqueiroAT = 0
}

fun textoMorto(tropaAlvo: Tropa, alvoInimigo: Inimigo? = null, textoTropa: Boolean){
    if(textoTropa){
        println("☠ ${tropaAlvo.tipo.name.formatarNome()} foi derrotado!")
    }else{
        println("☠ ${alvoInimigo?.nome?.name?.formatarNome()} foi derrotado!")
    }
}