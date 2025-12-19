package Model.Personagem

import Model.Efeitos.Item
import Model.Efeitos.Itens
import Model.Estruturas.Estruturas
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
    var eventosChave: MutableList<EventosChave> = mutableListOf(),
    var procurado: MutableList<Territorio> = mutableListOf(),
    var territoriosDominados: MutableList<Territorio> = mutableListOf(territorioAtual),
    var inventario: MutableList<Item> = mutableListOf(
        Itens.chaveIgreja
    )
){
    var bonusGuerreiroAT = 0
    var bonusMagoAT = 0
    var bonusArqueiroAT = 0
    var bonusInvocador = 0

    var andadasAposInvestimentoFazenda: Int = 0
}

fun textoMorto(tropaAlvo: Tropa, alvoInimigo: Inimigo? = null, textoTropa: Boolean){
    if(textoTropa){
        println("${tropaAlvo.tipo.name.formatarNome()} foi derrotado!")
    }else{
        println("${alvoInimigo?.nome?.name?.formatarNome()} foi derrotado!")
    }
}

enum class EventosChave {
    INVOCADOR_PORTAL, INVESTIMENTO_FAZENDA, COLETA_DIVIDENDOS_FAZENDA, UPGRADE_QUARTEL_GENERAL, CICLOPE_DERROTADO, DRAGAO_DERROTADO, GIGANTE_DERROTADO
}