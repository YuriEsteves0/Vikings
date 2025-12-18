package Model.Estruturas

import Helper.CMDHelper
import Model.Efeitos.Grimorio
import Model.Efeitos.Itens
import Model.Personagem.*
import kotlin.random.Random
import kotlin.random.nextInt

enum class Estruturas(var estado: EstadoEstrutura = EstadoEstrutura.DISPONIVEL) {
    Taverna {
        override fun funcaoEstrutura(jogador: Jogador) {
            CMDHelper.limparTela()
            println()
            println("*--- TAVERNA ---*")
            println()
            println("Taverneiro: Olá viajante! O que o senhor veio fazer por aqui?")
            println()
            println("1. Descansar (5 ouro → +2 hp) ")
            println("2. Comer (3 ouro → +3 comida) ")
            println("3. Contratar soldados (8 ouro → +2 soldados) ")
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
    },

    Caverna{
        override fun funcaoEstrutura(jogador: Jogador) {
            if(estado == EstadoEstrutura.DISPONIVEL){
                estado = EstadoEstrutura.BLOQUEADO
                CMDHelper.limparTela()
                println("*--- CAVERNA ---*")
                println("1. Mina Abandonada")
                println("2. Altar Antigo")
                println("3. Cristais Luminosos")
                println("0. Sair")
                print("O que você deseja analisar? ")

                when(readLine()){
                    "1" -> {
                        minaAbandonada(jogador)
                    }
                    "2" -> {
                        altarAntigo(jogador)
                    } // oferecer ouro (-2 ouro + 1 tropa aleatoria ou perde so o ouro) / comida (-1 comida por tropa + 1 hp por tropa ou -1 hp por tropa) / rezar (50/50 +1AT / -1AT)
                    "3" -> {
                        cristais(jogador)
                    } // pos: +1 ataque nos magos / chance magia (congelar/dormir/levitar/clone)
                }
                println("Uma pedra gigante é solta e acaba rolando contra você, você corre e consegue sair da caverna antes de ser esmagado...")
                CMDHelper.pressionarEnterContinuar()
            }else if(estado == EstadoEstrutura.BLOQUEADO){
                CMDHelper.limparTela()
                println("Você não consegue entrar mais na caverna, há uma pedra na entrada...")
                CMDHelper.pressionarEnterContinuar()
            }
        }

        fun minaAbandonada(jogador: Jogador){
            val random = Random
            val chanceAleatoria = random.nextInt(1..101)
            println("Você adentra a mina abandonada e...")
            CMDHelper.Debug(chanceAleatoria.toString())

            when(chanceAleatoria){
                in 1..20 -> {
                    // ouro
                    println("Você encontra um pote de ouro")
                    jogador.ouro += 15
                }

                in 21..40 -> {
                    // -2hp
                    println("Cai uma chuva de pedras do topo da caverna")
                    for(tropa in jogador.tropas){
                        tropa.vida -= 2
                    }
                }
                in 41..60 -> {
                    // +2 comida
                    println("Você encontra uma carne assada, aparentemente fresca")
                    jogador.comida = jogador.comida + 2
                }
                in 61..80 -> {
                    // +1 item aleatoria
                    println("Você encontra um item")
                    jogador.inventario.add(Itens.pocaoCura)
                }
                in 81 .. 100 -> {
                    // nada
                    println("Não encontra nada")
                }
            }
            CMDHelper.pressionarEnterContinuar()
        }

        fun altarAntigo(jogador: Jogador){
            println("Você encontra um altar que aparenta ser de alguma divindade antiga, o que você deseja fazer?")
            println("| 1. Oferecer ouro")
            println("| 2. Oferecer comida")
            println("| 3. Rezar")
            print("Digite a sua ação: ")

            val random = Random
            val chance = random.nextInt(1,101)
            when(readLine()){
                "1" -> {
                    println("Você coloca 2 ouros no altar... Ele subitamente desaparece e...")
                    if(chance <= 50){
                        jogador.ouro = jogador.ouro - 2
                        jogador.tropas.add(Guerreiro())
                        println("A sua oferenda foi convertida em um Guerreiro para suas tropas")
                    }else{
                        jogador.ouro = jogador.ouro - 2
                        println("Você perde 2 de ouro")
                    }
                }
                "2" -> {
                    jogador.comida = jogador.comida - 1
                    println("Você coloca comida no altar... Ela subitamente desaparece e...")
                    CMDHelper.pressionarEnterContinuar()

                    if(chance <= 50){
                        for(tropa in jogador.tropas){
                            tropa.vida = tropa.vida - 1
                            println("-1 ❤ ")
                        }
                    }else{
                        for(tropa in jogador.tropas){
                            tropa.vida = tropa.vida + 1
                            println("+1 ❤ ")
                        }
                    }
                }
                "3" -> {
                    println("Você ora ajoelhado no altar e ele te retribui com um cristal")

                    if(chance <= 50){
                        val chance2 = random.nextInt(1, 101)

                        if(chance2 <= 50){
                            // magia nova
                            jogador.tropas.forEachIndexed{i, tropa ->
                                tropa.magiasConhecidas.add(Grimorio.sono)
                            }
                        }else{
                            jogador.bonusMagoAT = jogador.bonusMagoAT + 2
                        }
                    }else{
                        //Uma explosão acontece")

                    }
                }
                else -> {

                }
            }
        }
        fun cristais(jogador: Jogador){

        }
    };

    abstract fun funcaoEstrutura(jogador: Jogador)

    enum class EstadoEstrutura {
        DISPONIVEL, BLOQUEADO
    }
}