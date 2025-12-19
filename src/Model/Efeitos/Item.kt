package Model.Efeitos

import Helper.CMDHelper
import Model.Estruturas.Mapa
import Model.Personagem.Jogador
import Model.Personagem.StatusPersonagem
import Model.Personagem.Tropa
import formatarNome

class Item(
    val nome: String,
    val descricao: String,
    val tipo: TiposItens,
    val poder: Int,
    val preco: Int,
    val vendivel: Boolean,
    val precisaAlvo: Boolean = false
) {
    fun usar(alvo: Tropa?, jogador: Jogador, mapa: Mapa) {
        when (tipo) {
            TiposItens.CURA -> {
                if (alvo == null) return
                val vidaAntes = alvo.vida
                alvo.vida = minOf(alvo.vida + poder, alvo.vidaTotal)
                val curado = alvo.vida - vidaAntes
                println("${alvo.tipo.name.formatarNome()} foi curado em $curado ❤")
            }

            TiposItens.BUFF -> {
                if (alvo == null) return
                alvo.ataque += poder
                println("${alvo.tipo.name.formatarNome()} recebeu +$poder de ataque ⚔")
            }

            TiposItens.EVASAO -> {
                if (alvo == null) return
                alvo.status = StatusPersonagem.INVISIVEL
                println("${alvo.tipo.name.formatarNome()} ficou invisível 👁")
            }

            TiposItens.MISSAO -> {
                println()
                println("O item serve apenas para a missão principal")
            }

            TiposItens.TELEPORTE -> {
                usarTeleporte(jogador, mapa)
            }

            else -> {
                println("Este item não pode ser usado dessa forma.")
            }
        }
    }

    private fun usarTeleporte(jogador: Jogador, mapa: Mapa) {
        println("A runa começa a pulsar com energia mágica...")
        println("| 1. Taverna")
        println("| 2. Ferreiro")
        print("Escolha o destino: ")

        when (readLine()) {
            "1" -> {
                val destino = jogador.ultimoTerritorioComTaverna
                if (destino == null) {
                    println("Você nunca visitou uma taverna.")
                    return
                }
                jogador.territorioAtual = mapa.encontrarTerritorio(destino)!!
                println("Você foi teleportado para a última taverna visitada.")
            }

            "2" -> {
                val destino = jogador.ultimoTerritorioComFerreiro
                if (destino == null) {
                    println("Você nunca visitou um ferreiro.")
                    return
                }
                jogador.territorioAtual = mapa.encontrarTerritorio(destino)!!
                println("Você foi teleportado para o último ferreiro visitado.")
            }

            else -> {
                println("A runa perde o brilho.")
                return
            }
        }
    }

    fun deveConsumir(): Boolean {
        return tipo != TiposItens.MISSAO
    }
}

    enum class TiposItens {
        CURA, BUFF, EVASAO, MISSAO, TELEPORTE
    }

    val itensInstanciados: List<Item> = listOf(
        Itens.pocaoCura,
        Itens.chaveIgreja,
        Itens.runaDeRetorno
    )

    object Itens {

        val runaDeRetorno = Item(
            nome = "Runa de Retorno",
            descricao = "Teleporta o usuário para a última Taverna ou Ferreiro visitado",
            tipo = TiposItens.TELEPORTE,
            poder = 0,
            preco = 15,
            vendivel = true,
            precisaAlvo = false
        )


        val pocaoCura = Item(
            nome = "Poção de Cura",
            descricao = "Cura uma tropa em 3 de vida",
            tipo = TiposItens.CURA,
            poder = 3,
            preco = 2,
            vendivel = true,
            precisaAlvo = true
        )

        val chaveIgreja = Item(
            nome = "Chave da Igreja Antiga",
            descricao = "Uma chave antiga que serve para destrancar as portas da Igreja Antiga",
            tipo = TiposItens.MISSAO,
            preco = 0,
            vendivel = false,
            poder = 0
        )
    }
