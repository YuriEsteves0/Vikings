package Model.Efeitos

import Model.Personagem.Personagem
import Model.Personagem.StatusPersonagem
import Model.Personagem.Tropa
import formatarNome


class Magia(
    val nome: String,
    val descricao: String,
    val tipo: TiposMagia,
    val poder: Int,
    val chanceStatus: Int = 0,
    val statusAplicado: StatusPersonagem? = null
) {
    fun executar(caster: Tropa, alvo: Personagem): Int {
        return when (tipo) {
            TiposMagia.DANO -> {
                val dano = poder + caster.ataque

                if (statusAplicado != null && (1..100).random() <= chanceStatus) {
                    alvo.status = statusAplicado
                    println("${alvo.javaClass.simpleName} recebeu o status $statusAplicado")
                }

                dano
            }

            TiposMagia.CURA -> {
                val cura = poder
                caster.vida = minOf(caster.vida + cura, caster.vidaTotal)
                println("${caster.javaClass.simpleName} foi curado em $cura")
                0
            }

            TiposMagia.BUFF -> {
                if (statusAplicado != null && (1..100).random() <= chanceStatus) {

                    caster.status = statusAplicado
                    caster.turnosStatus = poder

                    println("✨ ${caster.tipo.name.formatarNome()} recebeu o buff $statusAplicado por $poder turno(s)")
                }

                0
            }
        }
    }
}


enum class TiposMagia{
    DANO, CURA, BUFF
}

object Grimorio {

    val invisivel = Magia(
        nome = "Invisibilidade",
        descricao = "Deixa o conjurador invisível por uma rodada",
        tipo = TiposMagia.BUFF,
        poder = 1,
        chanceStatus = 100,
        statusAplicado = StatusPersonagem.INVISIVEL
    )

    val sono = Magia(
        nome = "Sono",
        descricao =  "Causa sono no seu oponente",
        tipo = TiposMagia.DANO,
        poder = 0,
        chanceStatus = 90,
        statusAplicado = StatusPersonagem.DORMINDO
    )

    val bolaDeFogo = Magia(
        nome = "Bola de Fogo",
        descricao = "Causa dano e pode queimar o inimigo",
        tipo = TiposMagia.DANO,
        poder = 4,
        chanceStatus = 40,
        statusAplicado = StatusPersonagem.QUEIMANDO
    )

    val curaSimples = Magia(
        nome = "Cura Simples",
        descricao = "Restaura vida",
        tipo = TiposMagia.CURA,
        poder = 5
    )
}

