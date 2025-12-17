package Model

import Helper.CMDHelper
import kotlin.random.Random

enum class Estruturas {
    Taverna {
        override fun funcaoEstrutura(jogador: Jogador) {
            CMDHelper.limparTela()
            println()
            println("*--- TAVERNA ---*")
            println()
            println("Taverneiro: Olá viajante! O que o senhor veio fazer por aqui?")
            println()
            println("1. Descansar (5 ouro → +2 hp)")
            println("2. Comer (3 ouro → +3 comida)")
            println("3. Contratar soldados (8 ouro → +2 soldados)")
            println("0. Sair")

            when (readLine()) {
                "1" -> descansar(jogador)
                "2" -> comer(jogador)
                "3" -> contratar(jogador)
                "0" ->  println("Você saiu da taverna.")
                else -> println("Opção inválida")
            }
        }

        private fun descansar(jogador: Jogador) {
            if (jogador.ouro >= 5) {
                jogador.ouro -= 5
                for(tropa in jogador.tropas){
                    tropa.vida = (tropa.vida + 2).coerceAtMost(tropa.vidaTotal)
                }
                println("Seus soldados descansaram e se recuperaram.")
            } else {
                println("Ouro insuficiente.")
            }
        }

        private fun comer(jogador: Jogador) {
            if (jogador.ouro >= 3) {
                jogador.ouro -= 3
                jogador.comida += 3
                println("Seu exército está bem alimentado.")
            } else {
                println("Ouro insuficiente.")
            }
        }

        private fun contratar(jogador: Jogador) {
            if (jogador.ouro >= 8) {
                jogador.ouro -= 8
                val tipoTropa = (1..3).random()

                val quantidadeTropa = (1..3).random()

                when (tipoTropa) {
                    1 -> {
                        val soldados = (1..quantidadeTropa).map { Guerreiro() }
                        jogador.tropas.addAll(soldados)
                        println("Você contratou $quantidadeTropa Guerreiro(s).")
                    }
                    2 -> {
                        val soldados = (1..quantidadeTropa).map { Arqueiro() }
                        jogador.tropas.addAll(soldados)
                        println("Você contratou $quantidadeTropa Arqueiro(s).")
                    }
                    3 -> {
                        val soldados = (1..quantidadeTropa).map { Mago() }
                        jogador.tropas.addAll(soldados)
                        println("Você contratou $quantidadeTropa Mago(s).")
                    }
                }
            } else {
                println("Ouro insuficiente.")
            }
        }

    },

    Ferreiro{
        override fun funcaoEstrutura(jogador: Jogador) {
            CMDHelper.limparTela()
            var ultimoNumero: Int = 0
            var numeroGuerreiro: Int = 0
            var numeroMago: Int = 0
            var numeroArqueiro: Int = 0

            println("*--- FERREIRO ---*")
            println()
            println("Ferreiro: Olá viajante! O que o senhor veio fazer por aqui?")
            println()

            jogador.tropas.forEachIndexed{ i, tropa ->
                println("| $i. Melhorar armas dos ${tropa.tipo.name} (5 ouro → +2 AT")

                if(tropa.tipo.name == TiposTropa.GUERREIRO.name){
                    numeroGuerreiro = i
                }
                if(tropa.tipo.name == TiposTropa.MAGO.name){
                    numeroMago = i
                }
                if(tropa.tipo.name == TiposTropa.ARQUEIRO.name){
                    numeroArqueiro = i
                }

                ultimoNumero = i + 1
            }
            println("| $ultimoNumero. Sair")

            val escolha = readLine()?.toIntOrNull()
            if(jogador.ouro >= 5){
                when (escolha) {
                    numeroGuerreiro -> {
                        CMDHelper.limparTela()
                        for (tropa in jogador.tropas){
                            if(tropa.tipo == TiposTropa.GUERREIRO){
                                jogador.bonusGuerreiroAT += 2
                            }
                        }
                        println("Você melhorou a espada dos seus guerreiros!")
                    }
                    numeroMago -> {
                        CMDHelper.limparTela()
                        for (tropa in jogador.tropas){
                            if(tropa.tipo == TiposTropa.MAGO){
                                jogador.bonusMagoAT += 2
                            }
                        }
                        println("Você melhorou o cajado dos seus magos!")
                    }
                    numeroArqueiro -> {
                        CMDHelper.limparTela()
                        for (tropa in jogador.tropas){
                            if(tropa.tipo == TiposTropa.ARQUEIRO){
                                jogador.bonusArqueiroAT += 2
                            }
                        }
                        println("Você melhorou o arco dos seus arqueiros!")
                    }
                    ultimoNumero -> {
                        CMDHelper.limparTela()
                        println("Saindo do Ferreiro.")
                    }
                    else -> println("Opção inválida!")
                }
                jogador.ouro -= 5
            }else{
                println("Ouro Insuficiente")
            }

        }
    };

    abstract fun funcaoEstrutura(jogador: Jogador)
}