package Model.Personagem

import Model.Efeitos.Item

abstract class Personagem (
    open var vida: Int,
    open var vidaTotal: Int,
    open var ataque: Int,
    open var status: StatusPersonagem,
    var turnosStatus: Int = 0
){
    fun aplicarEfeitoStatus(){
        when (status){
            StatusPersonagem.QUEIMANDO -> {
                vida -= 1
                if(vida < 0) vida = 0
                println("O personagem está queimando...")
            }
            StatusPersonagem.DORMINDO -> {
                println("O personagem está dormindo...")
            }

            StatusPersonagem.NADA -> {

            }

            StatusPersonagem.INVISIVEL -> {
                println("O personagem está invisivel...")
            }

            StatusPersonagem.CONGELADO -> {
                turnosStatus--
                if(turnosStatus <= 0) status = StatusPersonagem.NADA
                println("O personagem está congelado...")
            }

            StatusPersonagem.ENFRAQUECIDO -> {
                println("O personagem está fraco...")
            }

            StatusPersonagem.VOANDO -> {
                println("O personagem está voando...")
            }
        }
    }

    fun podeAgir(tropa: TiposTropa? = null, inimigo: TiposInimigos? = null): Boolean {
        return when (status) {
            StatusPersonagem.CONGELADO,
            StatusPersonagem.DORMINDO -> false
            StatusPersonagem.VOANDO -> {
                if (tropa == TiposTropa.MAGO || tropa == TiposTropa.ARQUEIRO || tropa == TiposTropa.INVOCADOR || inimigo == TiposInimigos.BRUXA || inimigo == TiposInimigos.GOBLIN_MAGO || inimigo == TiposInimigos.ESQUELETO || inimigo == TiposInimigos.INVOCADOR || inimigo == TiposInimigos.DRAGAO || inimigo == TiposInimigos.GOBLIN_ARQUEIRO){
                    return true
                }
                return false
            }
            else -> true
        }
    }
}

enum class StatusPersonagem{
    NADA, QUEIMANDO, DORMINDO, INVISIVEL, CONGELADO, ENFRAQUECIDO, VOANDO
}