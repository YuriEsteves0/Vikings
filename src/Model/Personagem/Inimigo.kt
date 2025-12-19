package Model.Personagem

import Model.Efeitos.Grimorio
import Model.Efeitos.Magia
import formatarNome

abstract class Inimigo(
    val nome: TiposInimigos,
    override var vida: Int,
    override var vidaTotal: Int,
    override var ataque: Int,
    var ouro: Int,
    var magiasConhecidas: MutableList<Magia> = mutableListOf(
        Grimorio.bolaDeFogo,
        Grimorio.curaSimples,
        Grimorio.congelar,
        Grimorio.sono
    ),
    var statusPersonagem: StatusPersonagem = StatusPersonagem.NADA,
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

class BonecoTeste : Inimigo(TiposInimigos.BONECO_TESTE, 10000, 10000, 0, 0){
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        println("Boneco de teste ficou la parado...")
        return 0
    }
}

class Lobo : Inimigo(TiposInimigos.LOBO, 6, 6, 3, 2) {
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        return if ((1..100).random() <= 30) {
            println("O Lobo atacou duas vezes!")
            ataque + 1
        } else {
            ataque
        }
    }
}

class Orc : Inimigo(TiposInimigos.ORC, 14, 14, 5, 4) {
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        println("O Orc usa ataque brutal!")
        vida -= 1
        return ataque + 2
    }
}

class GoblinGuerreiro : Inimigo(TiposInimigos.GOBLIN_GUERREIRO, 4, 4, 1, 1){
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        println("O Goblin Guerreiro usa ataque brutal menor!")
        return ataque + 2
    }
}

class GoblinArqueiro : Inimigo(TiposInimigos.GOBLIN_ARQUEIRO, 4, 4, 1, 1){
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


class GoblinMago : Inimigo(TiposInimigos.GOBLIN_MAGO, 4, 4, 1, 1){
    override fun habilidadeEspecial(inimigo: Tropa): Int {

        var magia = decidirMagia()
        return magia.executar(this, inimigo)

        return 0
    }

    fun decidirMagia() : Magia{
        val magiaAleatoria = magiasConhecidas.random()
        println("Goblin mago usou o feitiço ${magiaAleatoria.nome}")
        return magiaAleatoria
    }
}

//class Bruxa : Inimigo(TiposInimigos.BRUXA, 7, 7, 3, 5){
//    override fun habilidadeEspecial(inimigo: Tropa): Int {
//
//    }
//}

class Guarda : Inimigo(TiposInimigos.GUARDA, 22, 22, 3,0){
    override fun habilidadeEspecial(inimigo: Tropa): Int {
        inimigo.status = StatusPersonagem.ENFRAQUECIDO
        println("O guarda enfraqueceu o ${inimigo.tipo.toString().formatarNome()}")
        return 0
    }
}


enum class TiposInimigos{
    LOBO,
    ORC,
    GOBLIN_GUERREIRO,
    GOBLIN_MAGO,
    GOBLIN_ARQUEIRO,
    BONECO_TESTE,
    BRUXA,
    GIGANTE,
    DRAGAO,
    MIMICO,
    ESQUELETO,
    CICLOPE,
    GUARDA
}

// esqueleto depois de atacar ele morre '-'
// bosses: gigante, dragão, ciclope