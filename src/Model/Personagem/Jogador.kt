package Model.Personagem

import Model.Efeitos.Item
import Model.Efeitos.Itens
import Model.Estruturas.Territorio

class Jogador(
    var nome: String,
    var territorioAtual: Territorio,
    val tropas: MutableList<Tropa> = mutableListOf(
        Guerreiro(),
        Mago(),
        Arqueiro()
    ),
    var ouro: Int = 20,
    var comida: Int = 24,
    var territoriosDominados: MutableList<Territorio> = mutableListOf(territorioAtual),
    var inventario: MutableList<Item> = mutableListOf(
        Itens.pocaoCura,
    )
){
    var bonusGuerreiroAT = 0
    var bonusMagoAT = 0
    var bonusArqueiroAT = 0
}