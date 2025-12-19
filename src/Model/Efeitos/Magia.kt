package Model.Efeitos

import Helper.CMDHelper
import Model.Estruturas.Mapa
import Model.Personagem.Jogador
import Model.Personagem.Personagem
import Model.Personagem.StatusPersonagem


class Magia(
    val nome: String,
    val descricao: String,
    val tipo: TiposMagia,
    val poder: Int,
    val chanceStatus: Int = 0,
    val statusAplicado: StatusPersonagem? = null
) {
    fun executar(caster: Personagem, alvo: Personagem, jogador: Jogador, mapa: Mapa): Int {
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
                // Se a magia aplicar um status em quem recebe E o numero da chance (entre 1 e 100) for menor do que
                // a chance escolhida na hora de criar a magia.
                if (statusAplicado != null && (1..100).random() <= chanceStatus) {

                    caster.status = statusAplicado
                    // Poder aqui, se encaixa como se fosse a quantidade de turnos que o alvo irá ficar com o efeito
                    caster.turnosStatus = poder

                    println("${caster.javaClass.simpleName} recebeu o buff $statusAplicado por $poder turno(s)")
                }

                0
            }

            TiposMagia.DEBUFF -> {
                // Se a magia aplicar um status em quem recebe E o numero da chance (entre 1 e 100) for menor do que
                // a chance escolhida na hora de criar a magia.
                if(statusAplicado != null && (1..100).random() <= chanceStatus){
                    alvo.status = statusAplicado
                    // Poder aqui, se encaixa como se fosse a quantidade de turnos que o alvo irá ficar com o efeito
                    alvo.turnosStatus = poder

                    println("${alvo.javaClass.simpleName} recebeu o debuff $statusAplicado por $poder turno(s)")
                }

                0
            }

//            TiposMagia.TELEPORTE -> {
//                if (jogador == null || mapa == null) {
//                    println("A magia falhou.")
//                    return 0
//                }
//
//                println("Escolha o destino do teleporte:")
//                println("| 1. Taverna")
//                println("| 2. Ferreiro")
//                print("Opção: ")
//
//                when (readLine()) {
//                    "1" -> {
//                        val destino = jogador.ultimoTerritorioComTaverna
//                        if (destino != null) {
//                            jogador.territorioAtual = mapa.encontrarTerritorio(destino)!!
//                            println("Você é envolto por runas e surge em uma taverna conhecida...")
//                        } else {
//                            println("Você nunca visitou uma taverna.")
//                        }
//                    }
//
//                    "2" -> {
//                        val destino = jogador.ultimoTerritorioComFerreiro
//                        if (destino != null) {
//                            jogador.territorioAtual = mapa.encontrarTerritorio(destino)!!
//                            println("O ar se distorce e você surge próximo a uma forja antiga...")
//                        } else {
//                            println("Você nunca visitou um ferreiro.")
//                        }
//                    }
//
//                    else -> println("A magia se dissipa sem efeito.")
//                }
//
//                CMDHelper.pressionarEnterContinuar()
//                0
//            }
        }
    }
}

enum class TiposMagia {
    DANO, CURA, BUFF, DEBUFF
}


object Grimorio {

    val congelar = Magia(
        nome = "Congelar",
        descricao = "Feitiço que congela seu inimigo",
        tipo = TiposMagia.DEBUFF,
        poder = 1,
        chanceStatus = 60,
        statusAplicado = StatusPersonagem.CONGELADO
    )

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

//    val teleporteRunico = Magia(
//        nome = "Passagem Rúnica",
//        descricao = "Permite ao conjurador se teleportar para a última Taverna ou Ferreiro visitado",
//        tipo = TiposMagia.TELEPORTE,
//        poder = 0
//    )


    private val magias = listOf(
        invisivel,
        sono,
        bolaDeFogo,
        curaSimples,
        congelar
    )

    fun magiaAleatoria(): Magia{
        return magias.random()
    }
}

