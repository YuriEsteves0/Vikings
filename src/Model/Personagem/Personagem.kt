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
        }
    }

    fun podeAgir(): Boolean {
        return when (status) {
            StatusPersonagem.CONGELADO,
            StatusPersonagem.DORMINDO -> false
            else -> true
        }
    }
}

enum class StatusPersonagem{
    NADA, QUEIMANDO, DORMINDO, INVISIVEL, CONGELADO, ENFRAQUECIDO
}