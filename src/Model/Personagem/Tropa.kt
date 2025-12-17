package Model.Personagem

import Model.Efeitos.Grimorio
import Model.Efeitos.Item
import Model.Efeitos.Magia

abstract class Tropa(
    val tipo: TiposTropa,
    override var vida: Int,
    override var vidaTotal: Int,
    override var ataque: Int,
    var magiasConhecidas: MutableList<Magia> = mutableListOf(
        Grimorio.bolaDeFogo,
        Grimorio.curaSimples,
        Grimorio.sono
    ),
    var statusPersonagem: StatusPersonagem = StatusPersonagem.NADA,
) : Personagem(vida, vidaTotal, ataque, statusPersonagem) {

    abstract fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo): Int
    abstract fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int

    fun decidirMovimento(jogador: Jogador, inimigo: Inimigo): Int {
        aplicarEfeitoStatus()

        return if ((1..10).random() <= 5) {
            ataqueNormal(jogador, inimigo)
        } else {
            habilidadeEspecial(jogador, inimigo)
        }
    }
}

class Guerreiro : Tropa(TiposTropa.GUERREIRO, 18, 18, 5) {
    override fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int {
        return ataque + jogador.bonusGuerreiroAT
    }

    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo): Int {
        println("Guerreiro usou ataque especial!")
        return ataque + 1
    }
}


class Arqueiro : Tropa(TiposTropa.ARQUEIRO, 10, 10, 4) {

    override fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int {
        return ataque + jogador.bonusArqueiroAT
    }

    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo): Int {
        println("Arqueiro dispara Flecha de Fogo!")

        val chanceDeAcerto = (1..100).random()
        if (chanceDeAcerto > 70) {
            println("A flecha errou!")
            return 0
        }

        val dano = ataque + jogador.bonusArqueiroAT

        val chanceQueimar = (1..100).random()
        if (chanceQueimar <= 40 && inimigo.status != StatusPersonagem.QUEIMANDO) {
            inimigo.status = StatusPersonagem.QUEIMANDO
            println("O inimigo foi incendiado!")
        }

        return dano
    }
}

class Mago : Tropa(TiposTropa.MAGO, 8, 8, 2) {
    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo): Int {
        var magia = decidirMagia()
        return magia.executar(this, inimigo)
    }

    override fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int {
        return ataque + jogador.bonusMagoAT
    }

    fun decidirMagia() : Magia{
        if (vida <= vidaTotal / 2){
            return Grimorio.curaSimples
        }

        val magiaAleatoria = magiasConhecidas.random()
        println("Mago lançou ${magiaAleatoria.nome}")
        return magiaAleatoria
    }
}

enum class TiposTropa{
    GUERREIRO, MAGO, ARQUEIRO
}

