package Model.Personagem

import Model.Efeitos.Grimorio
import Model.Efeitos.Magia
import Model.Estruturas.Mapa

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

    abstract fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo, mapa: Mapa): Int
    abstract fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int

    abstract fun habilidadeEspecialDescricao(): String

    fun atualizarBuff() {
        if (turnosStatus > 0) {
            turnosStatus--
        }
    }

    fun decidirMovimento(jogador: Jogador, inimigo: Inimigo, mapa: Mapa): Int {
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
            habilidadeEspecial(jogador, inimigo, mapa)
        }

        atualizarBuff()
        return dano
    }

}

class Guerreiro : Tropa(TiposTropa.GUERREIRO, 15, 15, 5) {
    override fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int {
        return ataque + jogador.bonusGuerreiroAT
    }

    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo, mapa: Mapa): Int {
        println("Guerreiro usou ataque especial!")
        return ataque + jogador.bonusGuerreiroAT + 1
    }

    override fun habilidadeEspecialDescricao(): String {
        return "Aumento de ataque"
    }
}


class Arqueiro : Tropa(TiposTropa.ARQUEIRO, 10, 10, 3) {

    override fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int {
        return ataque + jogador.bonusArqueiroAT
    }

    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo, mapa: Mapa): Int {
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

    override fun habilidadeEspecialDescricao(): String {
        return "Flecha de Fogo"
    }
}

class Mago : Tropa(TiposTropa.MAGO, 8, 8, 2) {
    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo, mapa: Mapa): Int {
        var magia = decidirMagia()
        return magia.executar(this, inimigo, jogador, mapa)
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

    override fun habilidadeEspecialDescricao(): String {
        return "Feitiço Aleatório"
    }

}

class InvocadorAliado : Tropa(TiposTropa.INVOCADOR, 12, 12, 3){
    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo, mapa: Mapa): Int {
        for(tropa in jogador.tropas){
            if(tropa.tipo == TiposTropa.LOBO){
                return ataque + jogador.bonusInvocador
            }else{
                println("O invocador invocou um lobo")
                jogador.tropas.add(LoboAliado())
                return 0
            }
        }
        return ataque + jogador.bonusInvocador
    }

    override fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int {
        return ataque + jogador.bonusInvocador
    }

    override fun habilidadeEspecialDescricao(): String {
        return "Invoca um lobo para te proteger"
    }
}

class LoboAliado : Tropa(TiposTropa.LOBO, 6, 6, 3) {
    override fun habilidadeEspecial(jogador: Jogador, inimigo: Inimigo, mapa: Mapa): Int {
        return if ((1..100).random() <= 30) {
            println("O Lobo atacou duas vezes!")
            ataque + 1
        } else {
            ataque
        }
    }

    override fun ataqueNormal(jogador: Jogador, inimigo: Inimigo): Int {
        return ataque
    }

    override fun habilidadeEspecialDescricao(): String {
        return "Chance de atacar duas vezes"
    }
}

enum class TiposTropa{
    GUERREIRO,
    MAGO,
    ARQUEIRO,
    LOBO,
    INVOCADOR,
//    BANDIDO, // ASSASSINO
}

// ladino pode usar uma invisibilidade a cada 5 turnos e tem chance de entrar invisivel

