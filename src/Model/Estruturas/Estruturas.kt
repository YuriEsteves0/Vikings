package Model.Estruturas

import Helper.CMDHelper
import Model.Efeitos.Grimorio
import Model.Efeitos.Itens
import Model.Efeitos.itensInstanciados
import Model.Personagem.*
import kotlin.random.Random
import kotlin.random.nextInt

enum class Estruturas(var estado: EstadoEstrutura = EstadoEstrutura.DISPONIVEL) {
    // ELE JA LIMPA A TELA ANTERIORMENTE, NAO PRECISA FAZER ISSO
    Taverna {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            jogador.ultimoTerritorioComTaverna = jogador.territorioAtual.nome

            CMDHelper.limparTela()
            println()
            println("*--- TAVERNA ---*")
            println()
            println("Taverneiro: Olá viajante! O que o senhor veio fazer por aqui?")
            println()
            println("1. Descansar (5 ouro → +10 hp) ")
            println("2. Comer (3 ouro → +10 comida) ")
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
                    tropa.vida = (tropa.vida + 10).coerceAtMost(tropa.vidaTotal)
                }
                println("Seus soldados descansaram e se recuperaram.")
            } else {
                println("Ouro insuficiente.")
            }
        }

        private fun comer(jogador: Jogador) {
            if (jogador.ouro >= 3) {
                jogador.ouro -= 3
                jogador.comida += 10
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
            jogador.ultimoTerritorioComFerreiro = jogador.territorioAtual.nome

            println("*--- FERREIRO ---*")
            println()
            println("Ferreiro: Olá viajante! O que o senhor veio fazer por aqui?")
            println()

            println("| 0. Melhorar armas dos Guerreiros (5 ouro → +2 AT)")
            println("| 1. Melhorar cajados dos Magos (5 ouro → +2 AT)")
            println("| 2. Melhorar arcos dos Arqueiros (5 ouro → +2 AT)")
            println("| 3. Melhorar cajados dos Invocadores (5 ouro → +2 AT)")
            println("| 4. Sair")

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
                    if(jogador.ouro >= 5){
                        jogador.bonusInvocador += 2
                        jogador.ouro -= 5
                        println("Você melhorou o cajado dos seus invocadores!")
                    }
                }
                4 -> {
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
                                println("-1 HP")
                            }
                        }else{
                            for(tropa in jogador.tropas){
                                tropa.vida = tropa.vida + 1
                                println("Vocês foram abençoados pelos deuses")
                                println("+1 HP")
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

            var mercadoAberto = true

            if(jogador.procurado.contains(jogador.territorioAtual)){
                CMDHelper.limparTela()
                println("Você roubou essa loja, o lojista não irá te atender mais!")
                mercadoAberto = false
                CMDHelper.pressionarEnterContinuar()
            }

            while (mercadoAberto) {

                CMDHelper.limparTela()
                println("Você encontra uma estrutura em uma condição boa, há uma luz de dentro que te chama a atenção.")
                println("Ao entrar, você percebe que não há ninguém dentro exceto um homem atrás do balcão.")
                println()
                println("Dono do Mercado: Olá viajante! O que te traz aqui?")
                println("| 1. Comprar")
                println("| 2. Vender")
                println("| 3. Roubar")
                println("| 4. Sair")
                print("Digite sua ação: ")

                when (readLine()) {

                    "1" -> menuComprar(jogador)

                    "2" -> menuVender(jogador)

                    "3" -> {
                        CMDHelper.limparTela()
                        val chance = Random.nextInt(1, 101)

                        if (chance <= 30) {
                            println("Você conseguiu roubar um item sem ser percebido!")
                            jogador.inventario.add(Itens.pocaoCura)
                        } else {
                            println("Você foi pego tentando roubar!")
                            println("Dois guardas aparecem para defender o mercado!")
                            val local = mapa.encontrarTerritorio(jogador.territorioAtual.nome)
                            if(local != null){
                                local.inimigos.add(Guarda())
                                local.inimigos.add(Guarda())
                            }else{
                                println("Erro no sistema, por favor tente novamente!")
                            }
                            mercadoAberto = false
                            jogador.procurado.add(jogador.territorioAtual)
                        }

                        CMDHelper.pressionarEnterContinuar()
                    }

                    "4" -> {
                        CMDHelper.limparTela()
                        println("Dono do Mercado: tudo bem então! Volte sempre.")
                        CMDHelper.pressionarEnterContinuar()
                        mercadoAberto = false
                    }

                    else -> {
                        println("Opção inválida.")
                        CMDHelper.pressionarEnterContinuar()
                    }
                }
            }
        }

        private fun menuComprar(jogador: Jogador) {

            CMDHelper.limparTela()
            println("Dono do Mercado: Claro! O que você gostaria de comprar?")
            println()

            val itensVendiveis = itensInstanciados.filter { it.vendivel }

            if (itensVendiveis.isEmpty()) {
                println("No momento, não há itens à venda.")
                CMDHelper.pressionarEnterContinuar()
                return
            }

            itensVendiveis.forEachIndexed { index, item ->
                println("| ${index + 1}. ${item.nome} (${item.preco} ouros)")
            }

            println("| ${itensVendiveis.size + 1}. Voltar")
            print("Escolha uma opção: ")

            val escolha = readLine()?.toIntOrNull()

            if (escolha == null || escolha < 1 || escolha > itensVendiveis.size + 1) {
                println("Opção inválida.")
                CMDHelper.pressionarEnterContinuar()
                return
            }

            if (escolha == itensVendiveis.size + 1) {
                return
            }

            val itemEscolhido = itensVendiveis[escolha - 1]
            val chance = Random.nextInt(1, 101)

            CMDHelper.limparTela()

            if (chance <= 50) {
                println("Você conseguiu pechinchar!")
                println("Levou dois ${itemEscolhido.nome} pelo preço de um.")

                jogador.inventario.add(itemEscolhido)
                jogador.inventario.add(itemEscolhido)
                jogador.ouro -= itemEscolhido.preco
            } else {
                println("Você comprou ${itemEscolhido.nome} por ${itemEscolhido.preco} ouros.")

                jogador.inventario.add(itemEscolhido)
                jogador.ouro -= itemEscolhido.preco
            }

            CMDHelper.pressionarEnterContinuar()
        }

        private fun menuVender(jogador: Jogador) {

            CMDHelper.limparTela()
            println("Dono do Mercado: O que você deseja vender?")
            println()

            if (jogador.inventario.isEmpty()) {
                println("Você não possui itens para vender.")
                CMDHelper.pressionarEnterContinuar()
            }else{
                jogador.inventario.forEachIndexed { index, item ->
                    if(item.vendivel){
                        println("| ${index + 1}. ${item.nome} (${item.preco / 2} ouros)")
                    }
                }

                println("| ${jogador.inventario.size + 1}. Voltar")
                print("Escolha uma opção: ")

                val escolha = readLine()?.toIntOrNull()

                if (escolha == null || escolha < 1 || escolha > jogador.inventario.size + 1) {
                    println("Opção inválida.")
                    CMDHelper.pressionarEnterContinuar()
                    return
                }

                if (escolha == jogador.inventario.size + 1) {
                    return
                }

                val itemVendido = jogador.inventario[escolha - 1]

                jogador.inventario.removeAt(escolha - 1)
                jogador.ouro += itemVendido.preco / 2

                println("Você vendeu ${itemVendido.nome} por ${itemVendido.preco / 2} ouros.")
                CMDHelper.pressionarEnterContinuar()
            }
        }
    },

    Igreja {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            if(jogador.inventario.contains(Itens.chaveIgreja)){
                println("Você entra na igreja antiga, ela está completamente em ruinas")
                println("No altar você vê uma armadura de guerreiro curvada com a espada em sua frente")
                println("O peito dele brilha com uma luz que quase cega seus olhos, você deseja tocá-lo?")
                println("| 1. Sim")
                println("| 2. Não")

                print("Digite sua ação: ")

                when (readLine()){
                    "1" -> {
                        println()
                        CMDHelper.limparTela()
                        println("Você encosta no peito da armadura de guerreiro, ela começa a se tremer")
                        CMDHelper.pressionarEnterContinuar()
                        println("E subitamente ela dá um salto para suas costas e prepara um ataque")
                        CMDHelper.pressionarEnterContinuar()
                        val destino = mapa.encontrarTerritorio(jogador.territorioAtual.nome)

                        if(destino != null){
                            destino.inimigos.add(ArmaduraEnfeiticada())
                        }else{
                            println("Erro no sistema, por favor tente novamente")
                        }

                    }
                    "2" -> {
                        CMDHelper.limparTela()
                        println("Você não toca na armadura, quando você se vira, percebe que a porta da igreja")
                        portal(jogador, mapa)
                    }
                    else -> {
                        CMDHelper.limparTela()
                        println("Opção Inválida")
                        CMDHelper.pressionarEnterContinuar()
                    }
                }
            }else{
                CMDHelper.limparTela()
                println("A porta da igreja está trancada, há apenas um buraco para uma chave")
                CMDHelper.pressionarEnterContinuar()
            }
        }

        fun portal(jogador: Jogador, mapa: Mapa){
            println("É um portal. Ele brilha de um modo que te faz ficar nauseado, deseja passar pelo portal?")
            println("| 1. Sim")
            println("| 2. Não")
            print("Digite sua ação: ")

            when(readLine()) {
                "1" -> {
                    // Aqui vai pra outro mapa
                    CMDHelper.limparTela()
                    println("Você atravessa o portal")
                    CMDHelper.pressionarEnterContinuar()
                    jogador.territorioAtual = mapa.territorios[11] // Vai teleportar para a torre do oráculo
                }
                "2" -> {
                    CMDHelper.limparTela()
                    println("Você não atravessa o portal, mas surge a dúvida em sua mente, o que será que há pelo outro lado?")
                    CMDHelper.pressionarEnterContinuar()
                }
                else -> {
                    CMDHelper.limparTela()
                    println("Opção Invalida")
                    CMDHelper.pressionarEnterContinuar()
                }
            }
        }
    },

    Torre_Oraculo {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {
            // Se o jogador ja passou por aqui
            if(jogador.eventosChave.contains(EventosChave.INVOCADOR_PORTAL)){
                atravessarPortal(jogador, mapa)
            }else{
                // Jogador nunca passou por aqui

                println("Você vê um homem parado na direita do portal que você acabou de atravessar...")
                println("Invocador: Quem é você? O que há do outro lado do portal?")
                println("| 1. Explicar sua história")
                println("| 2. Atacar")
                println()
                print("Digite sua ação: ")

                when(readLine()){
                    "1" -> {
                        println()
                        println("Você consegue convencer o invocador a não te atacar, você consegue recrutá-lo")
                        jogador.tropas.add(InvocadorAliado())
                        println("Invocador: Eu também carrego comigo um pouco de comida... Caso isso te ajude em algo...")
                        jogador.comida += 10

                        atravessarPortal(jogador, mapa)
                    }
                    "2" -> {
                        val localAtual = mapa.encontrarTerritorio(jogador.territorioAtual.nome)
                        if(localAtual != null){
                            localAtual.inimigos.add(InvocadorInimigo())
                            localAtual.acaoDisponiveis.add(1, AcoesJogador.Atacar)
                        }else{
                            println("Erro no sistema, por favor tente novamente")
                        }
                    }
                    else-> {
                        CMDHelper.limparTela()
                        println("Opção Invalida")
                        CMDHelper.pressionarEnterContinuar()
                    }
                }
            }
        }

        fun atravessarPortal(jogador: Jogador, mapa: Mapa){
            println("Você vê o portal que acabou de atravessar, deseja atravessá-lo novamente?")
            println("| 1. Sim")
            println("| 2. Não")
            println()
            print("Digite sua ação: ")

            when(readLine()){
                "1" ->{
                    CMDHelper.limparTela()
                    println("Você atravessa o portal novamente...")
                    CMDHelper.pressionarEnterContinuar()
                    jogador.territorioAtual = mapa.territorios[5] // Vai teleportar para a igreja antiga
                }
                "2"->{
                    CMDHelper.limparTela()
                    println("Você desiste de atravessar o portal")
                    CMDHelper.pressionarEnterContinuar()
                }
                else->{
                    CMDHelper.limparTela()
                    println("Opção Invalida")
                    CMDHelper.pressionarEnterContinuar()
                }
            }
        }
    },

    Fazenda {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {

            val ANDADAS_PARA_DIVIDENDOS = 8

            CMDHelper.limparTela()
            println("Você chega em um grande campo amarelado.")
            println("Há uma construção agrícola quase pronta e três homens trabalhando.")
            println()
            println("| 1. Falar com os homens")
            println("| 2. Sair")
            println()
            print("Digite sua ação: ")

            while (true) {
                when (readLine()) {
                    "1" -> {
                        CMDHelper.limparTela()

                        // CASO: jogador já investiu e ainda não coletou
                        if (
                            jogador.eventosChave.contains(EventosChave.INVESTIMENTO_FAZENDA) &&
                            !jogador.eventosChave.contains(EventosChave.COLETA_DIVIDENDOS_FAZENDA)
                        ) {

                            if (jogador.andadasAposInvestimentoFazenda < ANDADAS_PARA_DIVIDENDOS) {
                                println("Homem 1: A construção vai bem, mas ainda leva tempo.")
                                println("Homem 2: Continue viajando, os lucros ainda não chegaram.")
                                println()
                                println("(${jogador.andadasAposInvestimentoFazenda}/$ANDADAS_PARA_DIVIDENDOS andadas)")
                                CMDHelper.pressionarEnterContinuar()
                                break
                            }

                            println("Homem 1: Ora, viajante! Que bom vê-lo novamente.")
                            println("Homem 2: A fazenda prosperou graças ao seu investimento.")
                            println("Homem 3: Aqui estão seus dividendos.")
                            println("Homem 1: E como agradecimento, receba um pouco de nossa colheita.")
                            println()
                            println("Você recebeu 150 de ouro e 10 de comida.")

                            jogador.ouro += 150
                            jogador.comida += 10
                            jogador.eventosChave.add(EventosChave.COLETA_DIVIDENDOS_FAZENDA)

                            CMDHelper.pressionarEnterContinuar()
                            break
                        }

                        // CASO: jogador já coletou tudo
                        if (jogador.eventosChave.contains(EventosChave.COLETA_DIVIDENDOS_FAZENDA)) {
                            println("Homem 1: A fazenda segue firme graças a você.")
                            println("Homem 2: Nós já te pagamos tudo o que te devíamos.")
                            CMDHelper.pressionarEnterContinuar()
                            break
                        }

                        // DIÁLOGO INICIAL
                        println("Homem 1: Olá viajante! O que veio fazer por estas bandas?")
                        println("| 1. Perguntar sobre a construção")
                        println("| 2. Perguntar onde você está")
                        println("| 3. Sair")
                        println()
                        print("Digite sua ação: ")

                        when (readLine()) {

                            "1" -> {
                                CMDHelper.limparTela()
                                println("Homem 2: Esta é a fazenda dos Campos Dourados.")
                                println("Produzimos mantimentos para o vilarejo de Brannor.")
                                println("Falta apenas investimento para concluir a construção.")
                                println()
                                println("| 1. Ajudar com 30 ouros")
                                println("| 2. Não ajudar")
                                println("| 3. O que eu ganho com isso?")
                                println("| 4. Sair")
                                println()
                                print("Digite sua ação: ")

                                when (readLine()) {

                                    "1" -> {
                                        CMDHelper.limparTela()
                                        if (jogador.ouro < 30) {
                                            println("Homem 3: Parece que você não tem ouro suficiente.")
                                        } else {
                                            jogador.ouro -= 30
                                            jogador.eventosChave.add(EventosChave.INVESTIMENTO_FAZENDA)
                                            println("Homem 1: Excelente decisão!")
                                            println("Homem 2: Volte no futuro para receber sua parte dos lucros.")
                                        }
                                        CMDHelper.pressionarEnterContinuar()
                                        break
                                    }

                                    "2" -> {
                                        CMDHelper.limparTela()
                                        println("Homem 1: Entendemos, viajante.")
                                        CMDHelper.pressionarEnterContinuar()
                                        break
                                    }

                                    "3" -> {
                                        CMDHelper.limparTela()
                                        println("Homem 2: Quando a fazenda prosperar,")
                                        println("você receberá dividendos da venda de mantimentos.")
                                        CMDHelper.pressionarEnterContinuar()
                                        continue
                                    }

                                    "4" -> {
                                        CMDHelper.limparTela()
                                        println("Você decide não se envolver por agora.")
                                        CMDHelper.pressionarEnterContinuar()
                                        break
                                    }

                                    else -> {
                                        CMDHelper.limparTela()
                                        println("Opção inválida.")
                                        CMDHelper.pressionarEnterContinuar()
                                        continue
                                    }
                                }
                            }

                            "2" -> {
                                CMDHelper.limparTela()
                                println("Homem 3: Você está na fazenda dos Campos Dourados.")
                                CMDHelper.pressionarEnterContinuar()
                                continue
                            }

                            "3" -> {
                                CMDHelper.limparTela()
                                println("Você dá meia volta.")
                                CMDHelper.pressionarEnterContinuar()
                                break
                            }

                            else -> {
                                CMDHelper.limparTela()
                                println("Opção inválida.")
                                CMDHelper.pressionarEnterContinuar()
                                continue
                            }
                        }
                    }

                    "2" -> {
                        CMDHelper.limparTela()
                        println("Você decide ir embora.")
                        CMDHelper.pressionarEnterContinuar()
                        break
                    }

                    else -> {
                        CMDHelper.limparTela()
                        println("Opção inválida.")
                        CMDHelper.pressionarEnterContinuar()
                    }
                }
            }
        }
    },

    Quartel_General {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {

            CMDHelper.limparTela()
            println("Você entra no Quartel-General.")
            println("Oficial: Olá guerreiro, vi que o senhor anda bem acompanhado com suas tropas...")
            CMDHelper.pressionarEnterContinuar()
            println("O Oficial analisa suas tropas")
            CMDHelper.pressionarEnterContinuar()
            println("Oficial: Eu vos ofereço um treinamento avançado, para melhorar suas habilidades de combate")
            println()
            println("| 1. Melhorar todas as tropas (30 ouros)")
            println("| 2. Sair")
            println()
            print("Digite sua ação: ")

            while (true) {
                when (readLine()) {

                    "1" -> {
                        CMDHelper.limparTela()

                        if (jogador.eventosChave.contains(EventosChave.UPGRADE_QUARTEL_GENERAL)) {
                            println("Oficial: Desculpe, eu ensinei tudo o que eu sei")
                            CMDHelper.pressionarEnterContinuar()
                            break
                        }

                        if (jogador.ouro < 30) {
                            println("Você não possui ouro suficiente.")
                            CMDHelper.pressionarEnterContinuar()
                            break
                        }

                        jogador.ouro -= 30

                        jogador.tropas.forEach { tropa ->
                            tropa.vidaTotal += 7
                            tropa.vida += 7
                            tropa.ataque += 3
                        }

                        jogador.eventosChave.add(EventosChave.UPGRADE_QUARTEL_GENERAL)

                        println("Suas tropas passaram por treinamento intensivo!")
                        println("+7 Vida Máxima")
                        println("+3 Ataque")
                        CMDHelper.pressionarEnterContinuar()
                        break
                    }

                    "2" -> {
                        CMDHelper.limparTela()
                        println("Você deixa o Quartel-General.")
                        CMDHelper.pressionarEnterContinuar()
                        break
                    }

                    else -> {
                        CMDHelper.limparTela()
                        println("Opção inválida.")
                        CMDHelper.pressionarEnterContinuar()
                    }
                }
            }
        }
    },

    Antro_Antigo {
        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {

            if (estado == EstadoEstrutura.DISPONIVEL) {
                estado = EstadoEstrutura.BLOQUEADO

                CMDHelper.limparTela()
                println("*--- ANTRO ANTIGO ---*")
                println("O local emana uma presença opressora.")
                println()
                println("1. Inscrições Antigas")
                println("2. Poço de Energia")
                println("3. Trono Quebrado")
                println("0. Sair")
                print("O que você deseja analisar? ")

                when (readLine()) {
                    "1" -> inscricoesAntigas(jogador)
                    "2" -> pocoEnergia(jogador)
                    "3" -> invocarCiclope(jogador, mapa)
                    "0" -> {
                        println("Você decide não se arriscar.")
                        CMDHelper.pressionarEnterContinuar()
                    }
                    else -> invocarCiclope(jogador, mapa)
                }

                println()
                println("O Antro começa a ruir.")
                println("Você escapa por pouco antes que tudo desmorone.")
                CMDHelper.pressionarEnterContinuar()

            } else {
                CMDHelper.limparTela()
                println("O Antro Antigo desabou. Nada resta aqui.")
                CMDHelper.pressionarEnterContinuar()
            }
        }

        fun inscricoesAntigas(jogador: Jogador) {
            CMDHelper.limparTela()
            println("Você estuda símbolos entalhados na pedra.")
            println("O conhecimento antigo flui para sua mente...")

            val magia = Grimorio.magiaAleatoria()
            jogador.tropas.first().magiasConhecidas.add(magia)

            println("Seus magos aprenderam a magia: ${magia.nome}")
            CMDHelper.pressionarEnterContinuar()
        }

        fun pocoEnergia(jogador: Jogador) {
            CMDHelper.limparTela()
            println("Uma energia instável emana do poço.")
            println("Você tenta canalizá-la...")

            val chance = Random.nextInt(1, 101)

            if (chance <= 60) {
                val item = itensInstanciados.random()
                jogador.inventario.add(item)
                println("A energia se condensa em um item: ${item.nome}")
            } else {
                println("A energia explode!")
                jogador.tropas.forEach {
                    it.vida -= 2
                }
                println("Todas as tropas sofreram -2 HP.")
            }

            CMDHelper.pressionarEnterContinuar()
        }

        fun invocarCiclope(jogador: Jogador, mapa: Mapa) {

            if (jogador.eventosChave.contains(EventosChave.CICLOPE_DERROTADO)) {
                println("O trono está vazio e quebrado.")
                println("O mal que habitava aqui já foi destruído.")
                CMDHelper.pressionarEnterContinuar()
                return
            }

            CMDHelper.limparTela()
            println("Você sente o chão tremer.")
            CMDHelper.pressionarEnterContinuar()
            println("Um rugido ecoa pelo Antro...")
            CMDHelper.pressionarEnterContinuar()
            println("CICLOPE!")
            CMDHelper.pressionarEnterContinuar()
            CMDHelper.limparTela()

            val territorio = mapa.encontrarTerritorio(jogador.territorioAtual.nome)
            territorio?.inimigos?.add(Ciclope())
        }
    },

    Castelo_Viking {

        private val bausRestantes = mutableSetOf(1, 2, 3)
        private val bauVerdadeiro = (1..3).random()

        override fun funcaoEstrutura(jogador: Jogador, mapa: Mapa) {

            CMDHelper.limparTela()
            println("*--- CASTELO VIKING ---*")
            println("O salão está silencioso.")
            println("Três baús antigos repousam à sua frente.")
            println()

            if (bausRestantes.isEmpty()) {
                println("Todos os baús já foram abertos.")
                println("Nada mais resta neste castelo.")
                CMDHelper.pressionarEnterContinuar()
                return
            }

            bausRestantes.forEach {
                println("| $it. Baú $it")
            }
            println("| 0. Sair")
            println()
            print("Qual baú você deseja abrir? ")

            when (val escolha = readLine()?.toIntOrNull()) {

                0 -> {
                    println("Você decide não mexer nos baús por enquanto.")
                    CMDHelper.pressionarEnterContinuar()
                }

                in bausRestantes -> {
                    bausRestantes.remove(escolha)
                    abrirBau(escolha, jogador, mapa)
                }

                else -> {
                    println("Escolha inválida.")
                    CMDHelper.pressionarEnterContinuar()
                }
            }
        }

        private fun abrirBau(numero: Int?, jogador: Jogador, mapa: Mapa) {

            CMDHelper.limparTela()
            println("Você se aproxima do baú $numero...")
            CMDHelper.pressionarEnterContinuar()

            if (numero == bauVerdadeiro) {
                bauVerdadeiro(jogador)
            } else {
                bauMimico(jogador, mapa)
            }
        }

        private fun bauVerdadeiro(jogador: Jogador) {

            println("O baú se abre lentamente...")
            println("Tesouros repousam em seu interior!")
            println()

            jogador.comida += 10
            jogador.ouro += 50

            val magia = Grimorio.magiaAleatoria()
            jogador.tropas.first().magiasConhecidas.add(magia)

            println("+10 Comida")
            println("+50 Ouro")
            println("Uma tropa aprendeu a magia: ${magia.nome}")

            CMDHelper.pressionarEnterContinuar()
        }

        private fun bauMimico(jogador: Jogador, mapa: Mapa) {

            println("O baú range...")
            CMDHelper.pressionarEnterContinuar()
            println("Dentes surgem da madeira!")
            CMDHelper.pressionarEnterContinuar()
            println("É UM MÍMICO!")
            CMDHelper.pressionarEnterContinuar()

            val territorio = mapa.encontrarTerritorio(jogador.territorioAtual.nome)
            territorio?.inimigos?.add(Mimico())
        }
    };


    abstract fun funcaoEstrutura(jogador: Jogador, mapa: Mapa)

    enum class EstadoEstrutura {
        DISPONIVEL, BLOQUEADO
    }
}