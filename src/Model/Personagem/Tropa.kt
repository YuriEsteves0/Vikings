package Model.Personagem

import Model.Efeitos.Grimorio
import Model.Efeitos.Item
import Model.Efeitos.Magia
import formatarNome

abstract class Tropa(
    val tipo: TiposTropa,
    override var vida: Int,
    override var vidaTotal: Int,
    override var ataque: Int,
    var magiasConhecidas: MutableList<Magia> = mutableListOf(
        Grimorio.bolaDeFogo,
        Grimorio.curaSimples,
    ),
    statusInicial: StatusPersonagem = StatusPersonagem.NADA
) : Personagem(vida, vidaTotal, ataque, statusInicial) {

    abstract fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo): Int
    abstract fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int

    fun atualizarBuff() {
        if (turnosStatus > 0) {
            turnosStatus--
        }
    }


    fun decidirMovimento(jogador: Jogador, inimigo: Inimigo): Int {
        if (status == StatusPersonagem.INVISIVEL) {
            println("🗡️ ATAQUE FURTIVO!")
            status = StatusPersonagem.NADA
            turnosStatus = 0
            return ataque * 2
        }

        aplicarEfeitoStatus()

        val dano = if ((1..10).random() <= 5) {
            ataqueNormal(jogador, inimigo)
        } else {
            habilidadeEspecial(jogador, inimigo)
        }

        atualizarBuff()
        return dano
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

    fun decidirMagia(): Magia {
        if (status != StatusPersonagem.INVISIVEL) {
            magiasConhecidas.find { it == Grimorio.invisivel }?.let {
                println("Mago usou o feitiço Invisibilidade")
                return it
            }
        }

        val magiaAleatoria = magiasConhecidas.random()
        println("Mago usou o feitiço ${magiaAleatoria.nome}")
        return magiaAleatoria
    }

}

enum class TiposTropa{
    GUERREIRO, MAGO, ARQUEIRO
}

