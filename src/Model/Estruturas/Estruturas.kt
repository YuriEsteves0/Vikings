package Model.Estruturas

import Helper.CMDHelper
import Helper.DadoHelper.Companion.random
import Model.Efeitos.Grimorio
import Model.Efeitos.Itens
import Model.Efeitos.Magia
import Model.Personagem.*
import kotlin.math.log
import kotlin.random.Random
import kotlin.random.nextInt

enum class Estruturas(var estado: EstadoEstrutura = EstadoEstrutura.DISPONIVEL) {
    // ELE JA LIMPA A TELA ANTERIORMENTE, NAO PRECISA FAZER ISSO
    Taverna {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            CMDHelper.limparTela()
            println()
            println("*--- TAVERNA ---*")
            println()
            println("Taverneiro: Olá viajante! O que o senhor veio fazer por aqui?")
            println()
            println("1. Descansar (5 ouro → +2 hp) ")
            println("2. Comer (3 ouro → +3 comida) ")
            println("3. Contratar soldados (8 ouro → +1 soldados) ")
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

                val quantidadeTropa = 1

                when (tipoTropa) {
                    1 -> {
                        val soldados = (1..quantidadeTropa).map { Guerreiro() }
                        jogador.tropas.addAll(soldados)
                        println("Você contratou $quantidadeTropa Guerreiro.")
                    }
                    2 -> {
                        val soldados = (1..quantidadeTropa).map { Arqueiro() }
                        jogador.tropas.addAll(soldados)
                        println("Você contratou $quantidadeTropa Arqueiro.")
                    }
                    3 -> {
                        val soldados = (1..quantidadeTropa).map { Mago() }
                        jogador.tropas.addAll(soldados)
                        println("Você contratou $quantidadeTropa Mago.")
                    }
                }
            } else {
                println("Ouro insuficiente.")
            }
        }

    },

    Ferreiro{
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            CMDHelper.limparTela()

            println("*--- FERREIRO ---*")
            println()
            println("Ferreiro: Olá viajante! O que o senhor veio fazer por aqui?")
            println()

            println("| 0. Melhorar armas dos Guerreiros (5 ouro → +2 AT)")
            println("| 1. Melhorar cajados dos Magos (5 ouro → +2 AT)")
            println("| 2. Melhorar arcos dos Arqueiros (5 ouro → +2 AT)")
            println("| 3. Sair")

            val escolha = readLine()?.toIntOrNull() ?: return

            when (escolha) {
                0 -> {
                    if (jogador.ouro >= 5) {
                        jogador.bonusGuerreiroAT += 2
                        jogador.ouro -= 5
                        println("Você melhorou a espada dos seus guerreiros!")
                    }
                }
                1 -> {
                    if (jogador.ouro >= 5) {
                        jogador.bonusMagoAT += 2
                        jogador.ouro -= 5
                        println("Você melhorou o cajado dos seus magos!")
                    }
                }
                2 -> {
                    if (jogador.ouro >= 5) {
                        jogador.bonusArqueiroAT += 2
                        jogador.ouro -= 5
                        println("Você melhorou o arco dos seus arqueiros!")
                    }
                }
                3 -> {
                    println("Saindo do Ferreiro.")
                    return
                }
                else -> println("Opção inválida!")
            }

        }
    },

    Caverna{
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
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
                    }
                    "3" -> {
                        cristais(jogador)
                    }
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

            when(chanceAleatoria){
                in 1..20 -> {
                    // ouro
                    println("Você encontra um pote de ouro 🪙")
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
                    println("Você encontra uma carne assada, aparentemente fresca 🍖")
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
            // Ao oferecer 2 ouros, o jogador tem chance de ganhar um guerreiro extra ou perder to_do o dinheiro
            println("| 1. Oferecer ouro")
            // ao oferecer comida, o jogador tem chance de ganhar 1hp ou perder 1hp para todas as tropas
            println("| 2. Oferecer comida")
            // ao rezar no altar, o jogador tem chance de receber +1 de ataque para todas as tropas ou receber -1 de ataque para todas as tropas
            println("| 3. Rezar")

            print("Digite a sua ação: ")

            val random = Random
            val chance = random.nextInt(1,101)

            while(true){
                when(readLine()){
                    "1" -> {
                        println("Você coloca 2 ouros no altar... Ele subitamente desaparece e...")
                        CMDHelper.pressionarEnterContinuar()
                        if(chance <= 50){
                            jogador.ouro = jogador.ouro - 2
                            jogador.tropas.add(Guerreiro())
                            println("A sua oferenda foi convertida em um Guerreiro para suas tropas")
                        }else{
                            jogador.ouro = jogador.ouro - 2
                            println("Você perde 2 de ouro")
                        }
                        CMDHelper.pressionarEnterContinuar()
                        break
                    }
                    "2" -> {
                        jogador.comida = jogador.comida - 1
                        println("Você coloca comida no altar... Ela subitamente desaparece e...")
                        CMDHelper.pressionarEnterContinuar()

                        if(chance <= 50){
                            for(tropa in jogador.tropas){
                                tropa.vida = tropa.vida - 1
                                println("Vocês foram punidos pela sua indecencia contra os deuses")
                                println("-1 ❤ ")
                            }
                        }else{
                            for(tropa in jogador.tropas){
                                tropa.vida = tropa.vida + 1
                                println("Vocês foram abençoados pelos deuses")
                                println("+1 ❤ ")
                            }
                        }
                        CMDHelper.pressionarEnterContinuar()
                        break
                    }
                    "3" -> {
                        println("Você ora ajoelhado no altar e ele te retribui com um cristal...")
                        CMDHelper.pressionarEnterContinuar()

                        if(chance <= 50){
                            println("Você recebe uma benção de dano de ataque para todas as suas tropas")
                            jogador.bonusMagoAT++
                            jogador.bonusArqueiroAT++
                            jogador.bonusGuerreiroAT++
                        }else{
                            println("Você recebe uma maldição de dano de ataque para todas as suas tropas")
                            jogador.bonusMagoAT--
                            jogador.bonusArqueiroAT--
                            jogador.bonusGuerreiroAT--
                        }
                        CMDHelper.pressionarEnterContinuar()
                        break
                    }
                    else -> {
                        println("Opção inválida")
                        continue
                    }
                }
            }
        }
        fun cristais(jogador: Jogador){
            val random = Random
            val chance = random.nextInt(1,101)

            // pos: +1 ataque nos magos / chance magia (congelar/dormir/levitar/clone)

            if(chance <= 50){
                val chance2 = random.nextInt(1, 101)
                if(chance2 <= 50){
                    // magia nova
                    println()
                    println("Você consegue converter o cristal em uma magia nova para suas tropas")
                    var conseguiu = false
                    jogador.tropas.forEachIndexed{i, tropa ->
                        if(!conseguiu){
                            var magiaAleatoria = Grimorio.magiaAleatoria()
                            if(!tropa.magiasConhecidas.contains(magiaAleatoria)){
                                tropa.magiasConhecidas.add(magiaAleatoria)
                                conseguiu = true
                            }
                        }else{
                        }
                    }
                }else{
                    println("Você consegue converter o cristal em um subito poder de ataque para seus magos")
                    jogador.bonusMagoAT = jogador.bonusMagoAT + 2
                }
            }else{
                //Uma explosão acontece")

                println("Uma explosão acontece bem na sua frente")

                jogador.tropas.forEachIndexed{i, tropa ->
                    tropa.vida = tropa.vida - 3

                    if(tropa.vida <= 0){
                        textoMorto(tropaAlvo = tropa, textoTropa = true)
                    }
                }

            }
        }
    },

    Porto {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            println("Você entra em um porto, há somente um homem em um barco...")
            CMDHelper.pressionarEnterContinuar()
            CMDHelper.limparTela()
            println("Homem: Olá viajante, gostaria de uma carona?")
            println("| 1. Sim")
            println("| 2. Não")
            println()
            print ("Digite sua ação: ")
            when(readLine()){
                "1" -> {
                    CMDHelper.limparTela()
                    println("O homem do barco te leva para a capital do mundo, onde tudo é mágico, ele te levou para o reino de Auren")
                    val destino = mapa.encontrarTerritorio("Capital de Auren")
                    CMDHelper.pressionarEnterContinuar()

                    if(destino != null){
                        jogador.territorioAtual = destino
                    }else{
                        CMDHelper.Debug("ERRO: DESTINO NAO ENCONTRADO")
                    }
                }
                "2" -> {
                    CMDHelper.limparTela()
                    println("Homem: Okay então! Boa sorte na sua jornada!")
                    CMDHelper.pressionarEnterContinuar()
                }
                else -> {
                    println("Opção inválida")
                }
            }
        }
    },

    Cabana {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            println("Você entrou em sua antiga cabana, um lugar que você fazia de casa para se abrigar de monstros do lado de fora")
            println()
            println("Você encontra seu antigo baú, deseja abri-lo?")
            println("| 1. Sim")
            println("| 2. Não")
            println()
            print("Digite sua ação: ")

            when(readLine()){
                "1" -> {
                    println("Você encontra seus pertences, lá se encontrava duas poções de cura")
                    jogador.inventario.add(Itens.pocaoCura)
                    jogador.inventario.add(Itens.pocaoCura)
                    CMDHelper.pressionarEnterContinuar()
                }
                "2" -> {
                    println("Você deixa o baú para lá...")
                    CMDHelper.pressionarEnterContinuar()
                }
                else -> {
                    println("Opção Inválida")
                    CMDHelper.pressionarEnterContinuar()
                }
            }
        }
    },

    Mercado {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            println("")
        }
    };

    abstract fun funcaoEstrutura(jogador: Jogador, mapa: Mapa)

    enum class EstadoEstrutura {
        DISPONIVEL, BLOQUEADO
    }
}