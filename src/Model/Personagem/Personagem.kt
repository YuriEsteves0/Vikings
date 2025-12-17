package Model.Personagem

import Model.Efeitos.Item

abstract class Personagem (
    open var vida: Int,
    open var vidaTotal: Int,
    open var ataque: Int,
    open var status: StatusPersonagem,
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
                println("Fazendo ainda")
            }
        }
    }

    fun podeAgir(): Boolean {
        return when (status) {
            StatusPersonagem.DORMINDO -> {
                false
            }
            else -> true
        }
    }
}

enum class StatusPersonagem{
    NADA, QUEIMANDO, DORMINDO, INVISIVEL
}