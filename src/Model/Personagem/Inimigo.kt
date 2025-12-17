package Model.Personagem

abstract class Inimigo(
    val nome: TiposInimigos,
    override var vida: Int,
    override var vidaTotal: Int,
    override var ataque: Int,
    var statusPersonagem: StatusPersonagem = StatusPersonagem.NADA
) : Personagem(vida, vidaTotal, ataque, statusPersonagem){
    abstract fun habilidadeEspecial(inimigo: Tropa): Int

    fun ataqueNormal() : Int{
        return ataque
    }

    fun decidirMovimento(inimigo: Tropa): Int {
        aplicarEfeitoStatus()

        if((1..10).random() <= 5){
            return ataqueNormal()
        }else{
            return habilidadeEspecial(inimigo)
        }
    }
}

class Lobo : Inimigo(TiposInimigos.LOBO, 6, 6, 3) {
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        return if ((1..100).random() <= 30) {
            println("O Lobo atacou duas vezes!")
            ataque + 1
        } else {
            ataque
        }
    }
}

class Orc : Inimigo(TiposInimigos.ORC, 14, 14, 5) {
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        println("O Orc usa ataque brutal!")
        vida -= 1
        return ataque + 2
    }
}

class GoblinGuerreiro : Inimigo(TiposInimigos.GOBLIN_GUERREIRO, 4, 4, 1){
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        println("O Goblin Guerreiro usa ataque brutal menor!")
        return ataque + 2
    }
}

class GoblinArqueiro : Inimigo(TiposInimigos.GOBLIN_ARQUEIRO, 4, 4, 1){
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        println("O Goblin Arqueiro dispara uma flecha de fogo!")

        val chanceDeAcerto = (1..100).random()
        if (chanceDeAcerto > 70) {
            println("A flecha errou!")
            return 0
        }

        val dano = ataque

        val chanceQueimar = (1..100).random()
        if (chanceQueimar <= 40 && inimigo.status != StatusPersonagem.QUEIMANDO) {
            inimigo.status = StatusPersonagem.QUEIMANDO
            println("${inimigo.tipo.toString().lowercase().capitalize()} foi incendiado!")
        }

        return ataque + 2
    }
}

class GoblinMago : Inimigo(TiposInimigos.GOBLIN_MAGO, 4, 4, 1){
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        return 0
    }
}

enum class TiposInimigos{
    LOBO, ORC, GOBLIN_GUERREIRO, GOBLIN_MAGO, GOBLIN_ARQUEIRO
}