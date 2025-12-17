import Helper.CMDHelper
import Helper.DadoHelper
import Helper.Dados
import Helper.SorteStatus
import Model.*
import java.util.*
import kotlin.random.Random

fun main() {
    CMDHelper.limparTela()
    val mapa = Mapa()
    mapa.criarTerritorios()
    var jogador = criarJogador(mapa)
    var territoriosAnalisados: MutableMap<String, SorteStatus> = mutableMapOf()

    while(true){
        if(jogador.tropas.isEmpty()){
            CMDHelper.fimDeJogo()
            break
        }

        criarMapa(jogador, mapa)

        println("Comida: ${jogador.comida}      Ouro: ${jogador.ouro}      Tropas: ${jogador.tropas.size}")
        val territorioAtual = jogador.territorioAtual

        if(territorioAtual.inimigos.isNotEmpty()){
            println()
            for(inimigo in territorioAtual.inimigos){
                println("${inimigo.nome.toString().lowercase().capitalize()} (${inimigo.vida} ❤ ) (${inimigo.ataque} ⚔\uFE0E )")
            }
        }

        val acaoUsu = acoesUsu(territorioAtual.acaoDisponiveis)

        when(acaoUsu){
            AcoesJogador.Andar -> {
                if(territorioAtual.inimigos.isEmpty()){
                    println("Digite o nome do lugar que você quer ir:")
                    println("(EX: montanha / RIO")
                    val acaoUsu = readLine() ?: ""

                    var acaoAndarUsu = acaoUsu.lowercase(Locale.getDefault())

                    val destino = mapa.encontrarTerritorio(acaoAndarUsu)

                    if (destino != null) {
                        if (territoriosAnalisados.containsKey(destino.nome)) {
                            val sorte = territoriosAnalisados[destino.nome]!!

                            val random = Random
                            val chanceEmboscada = random.nextInt(1, 10)

                            when (sorte) {
                                SorteStatus.FALHA_CRITICA -> if (chanceEmboscada <= 10) emboscada(jogador, destino)
                                SorteStatus.FALHA -> if (chanceEmboscada <= 7) emboscada(jogador, destino)
                                SorteStatus.OK -> if (chanceEmboscada <= 5) emboscada(jogador, destino)
                                SorteStatus.SUCESSO -> if (chanceEmboscada <= 3) emboscada(jogador, destino)
                                SorteStatus.SUCESSO_CRITICO -> {  }
                            }

                            jogador.territorioAtual = destino
                        } else{
                            if(destino.inimigos.size > 0){
                                val random = Random
                                val chanceEmboscada = random.nextInt(1,10)
                                if(chanceEmboscada <= 5){
                                    emboscada(jogador, destino)
                                    CMDHelper.pressionarEnterContinuar()
                                }else{
                                    println()
                                    println("Você evitou uma emboscada")
                                    CMDHelper.pressionarEnterContinuar()
                                }

                                jogador.territorioAtual = destino
                            }else{
                                jogador.territorioAtual = destino
                                CMDHelper.limparTela()
                            }
                        }
                    }else {
                        CMDHelper.limparTela()
                        println("Território não encontrado!")
                    }
                }else{
                    CMDHelper.limparTela()
                    println("Ainda há inimigos neste território")
                    CMDHelper.pressionarEnterContinuar()
                }
            }

            AcoesJogador.Analisar_Territorio -> {
                println("Digite o nome do lugar que você quer analisar:")
                println("(EX: montanha / rio)")
                val entrada = readLine() ?: ""

                val nomeTerritorio = entrada.lowercase(Locale.getDefault())
                val destino = mapa.encontrarTerritorio(nomeTerritorio)

                if (destino == null) {
                    CMDHelper.limparTela()
                    println("Território não encontrado!")
                    CMDHelper.pressionarEnterContinuar()
                } else if (territoriosAnalisados.contains(destino.nome)) {
                    CMDHelper.limparTela()
                    println("Território já analisado")
                    CMDHelper.pressionarEnterContinuar()
                } else {
                    CMDHelper.limparTela()
                    val numeroDado = DadoHelper.girarD20()
                    val sorte = DadoHelper.verificarSorte(Dados.D20, numeroDado, false)

                    println()
                    println("Número do dado: $numeroDado/20")
                    println()

                    fun estimativa(real: Int, erro: Int): Int {
                        val min = (real - erro).coerceAtLeast(0)
                        val max = real + erro
                        return (min..max).random()
                    }

                    when (sorte) {
                        SorteStatus.FALHA_CRITICA, SorteStatus.FALHA -> {
                            println("Falha ao analisar ${destino.nome}")
                            territoriosAnalisados.put(destino.nome, SorteStatus.FALHA)
                        }

                        SorteStatus.OK -> {
                            val soldados = estimativa(destino.inimigos.size, 4)
                            println("O território ${destino.nome} tem aproximadamente $soldados soldados")
                            territoriosAnalisados.put(destino.nome, SorteStatus.OK)
                        }

                        SorteStatus.SUCESSO -> {
                            val soldados = estimativa(destino.inimigos.size, 2)
                            println("O território ${destino.nome} tem entre ${soldados - 1}~${soldados + 1} soldados")
                            territoriosAnalisados.put(destino.nome, SorteStatus.SUCESSO)
                        }

                        SorteStatus.SUCESSO_CRITICO -> {
                            println(
                                "O território ${destino.nome} tem ${destino.inimigos.size} soldados " +
                                        "e ${destino.estruturas.size} estruturas"
                            )
                            territoriosAnalisados.put(destino.nome, SorteStatus.SUCESSO_CRITICO)
                        }
                    }
                    CMDHelper.pressionarEnterContinuar()
                }
            }

            AcoesJogador.Atacar -> {

                if (territorioAtual.inimigos.isEmpty()) {
                    println("Não há inimigos neste território.")
                    CMDHelper.pressionarEnterContinuar()
                } else {

                    val ordemAtaque = mutableListOf<Any>()

                    var iAliado = 0
                    var iInimigo = 0
                    var vezAliado = kotlin.random.Random.nextBoolean()

                    while (iAliado < jogador.tropas.size || iInimigo < territorioAtual.inimigos.size) {

                        if (vezAliado && iAliado < jogador.tropas.size) {
                            ordemAtaque.add(jogador.tropas[iAliado])
                            iAliado++
                        } else if (!vezAliado && iInimigo < territorioAtual.inimigos.size) {
                            ordemAtaque.add(territorioAtual.inimigos[iInimigo])
                            iInimigo++
                        }

                        vezAliado = !vezAliado

                        if (iAliado >= jogador.tropas.size) vezAliado = false
                        if (iInimigo >= territorioAtual.inimigos.size) vezAliado = true
                    }

                    CMDHelper.limparTela()
                    println("\n⚔  ORDEM DE ATAQUE ⚔")
                    ordemAtaque.forEachIndexed { i, entidade ->
                        when (entidade) {
                            is Tropa   -> println("${i + 1}. Aliado: ${entidade.tipo.toString().lowercase().capitalize()}")
                            is Inimigo -> println("${i + 1}. Inimigo: ${entidade.nome.toString().lowercase().capitalize()}")
                        }
                    }

                    CMDHelper.pressionarEnterContinuar()

                    for (entidade in ordemAtaque) {

                        if (jogador.tropas.isEmpty()) {
                            println("☠ Todas as suas tropas foram derrotadas!")
                            break
                        }

                        if (territorioAtual.inimigos.isEmpty()) {
                            println("Todos os inimigos foram derrotados!")
                            break
                        }

                        when (entidade) {

                            is Tropa -> {

                                if (!jogador.tropas.contains(entidade)) continue

                                val inimigoAlvo = territorioAtual.inimigos.random()
                                val nomeTropa = entidade.tipo.toString().lowercase().capitalize()

                                CMDHelper.limparTela()
                                println("$nomeTropa ataca ${inimigoAlvo.nome.toString().lowercase().capitalize()}!")
                                println("Dano causado: ${entidade.ataqueTotal(jogador)}")

                                inimigoAlvo.vida -= entidade.ataqueTotal(jogador)

                                if (inimigoAlvo.vida <= 0) {
                                    println("${inimigoAlvo.nome} foi derrotado!")
                                    territorioAtual.inimigos.remove(inimigoAlvo)
                                } else {
                                    println("${inimigoAlvo.nome} agora tem ${inimigoAlvo.vida} ❤")
                                }
                            }

                            is Inimigo -> {

                                if (!territorioAtual.inimigos.contains(entidade)) continue

                                val tropaAlvo = jogador.tropas.random()
                                val nomeTropa = tropaAlvo.tipo.toString().lowercase().capitalize()

                                CMDHelper.limparTela()
                                println("${entidade.nome} ataca $nomeTropa!")
                                println("Dano causado: ${entidade.ataque}")

                                tropaAlvo.vida -= entidade.ataque

                                if (tropaAlvo.vida <= 0) {
                                    println("$nomeTropa foi derrotado!")
                                    jogador.tropas.remove(tropaAlvo)
                                } else {
                                    println("$nomeTropa agora tem ${tropaAlvo.vida} ❤")
                                }
                            }
                        }

                        jogador.territoriosDominados.add(jogador.territorioAtual)
                        CMDHelper.pressionarEnterContinuar()
                    }
                }
            }

            AcoesJogador.Entrar -> {
                println()
                println("*-----* ESTRUTURAS *-----*")

                val territorioAtual = mapa.encontrarTerritorio(jogador.territorioAtual.nome)

                if (territorioAtual == null) {
                    println("Território não encontrado!")
                    break
                }

                val estruturasDoTerritorio = territorioAtual.estruturas
                val ordemEstruturas = mutableMapOf<Int, Estruturas>()

                estruturasDoTerritorio.forEachIndexed { i, estrutura ->
                    println("| $i. $estrutura")
                    ordemEstruturas[i] = estrutura
                }

                println("*-----*------------*-----*")
                println()

                print("Digite sua ação: ")
                val entrada = readLine() ?: ""
                val indice = entrada.toIntOrNull() ?: -1

                if (indice in ordemEstruturas) {
                    val estruturaEscolhida = ordemEstruturas[indice]
                    estruturaEscolhida?.funcaoEstrutura(jogador)
                } else {
                    println("Opção de estrutura inválida!")
                }
            }

            AcoesJogador.Informações_Do_Reino -> {
                CMDHelper.limparTela()
                println()
                println("*-----* INFORMAÇÕES *-----*")
                for (tropa in jogador.tropas){
                    println("${tropa.tipo.toString().lowercase().capitalize()} (${tropa.vida}/${tropa.vidaTotal} ❤ ) (${tropa.ataqueTotal(jogador)} ⚔\uFE0E )")
                }
                println()
                println("${jogador.nome} você conquistou ${jogador.territoriosDominados.size}/${mapa.territorios.size} territórios")
                println("*-----*-------------*-----*")
                CMDHelper.pressionarEnterContinuar()
                CMDHelper.limparTela()
            }

            AcoesJogador.Sair -> {
                CMDHelper.limparTela()
                CMDHelper.fimDeJogo()
                break
            }

            else -> {
                println("Opção inválida! Tente novamente.")
                continue
            }
        }
    }
}

fun emboscada(jogador: Jogador, destino: Territorio){
    CMDHelper.limparTela()
    println()
    println("Você foi emboscado por ${destino.inimigos.size} inimigos!")
    println()

    val random: Random = Random
    val tropasMortas = mutableListOf<Tropa>()

    for(inimigo in destino.inimigos){
        if (jogador.tropas.isEmpty()) break
        if (jogador.tropas.isNotEmpty()) {
            val tropaAleatoria = random.nextInt(0, jogador.tropas.size)
            val dano = inimigo.habilidadeEspecial()
            jogador.tropas[tropaAleatoria].vida -= dano
            println()
            println("${jogador.tropas[tropaAleatoria].tipo.toString().lowercase().capitalize()} (${jogador.tropas[tropaAleatoria].vida}/${jogador.tropas[tropaAleatoria].vidaTotal} ❤ )")
        } else {
            println("Você não tem tropas disponíveis para combater!")
            break
        }

    }

    jogador.tropas.forEach { tropa ->
        if (tropa.vida <= 0) {
            tropasMortas.add(tropa)
        }
    }

    jogador.tropas.removeAll(tropasMortas)

    if (jogador.tropas.isEmpty()) {
        println()
        println("Suas tropas foram exterminadas na emboscada...")
    }
}

fun acoesUsu(acoesDisponiveis: List<AcoesJogador>) : AcoesJogador? {
    val ordemAcoes = mutableMapOf<Int, AcoesJogador>()

    println()
    println("*-----* AÇÕES *-----*")
    acoesDisponiveis.forEachIndexed { i, acao ->
        val texto = acao.name.replace("_", " ")
        println("| $i. $texto")
        ordemAcoes[i] = acao
    }
    println("*-----*-------*-----*")
    println()

    print("Digite sua ação: ")
    val entrada = readLine() ?: return null

    val indice = entrada.toIntOrNull() ?: return null

    return ordemAcoes[indice]
}


fun criarMapa(jogador: Jogador, mapa: Mapa) {
    val nomeCentro = nomeComInimigos(jogador.territorioAtual)
    var norte = ""
    var sul = ""
    var leste = ""
    var oeste = ""

    for (vizinho in jogador.territorioAtual.vizinhos) {
        when (vizinho.key) {
            Direcao.NORTE -> norte = vizinho.value.nome
            Direcao.SUL -> sul = vizinho.value.nome
            Direcao.LESTE -> leste = vizinho.value.nome
            Direcao.OESTE -> oeste = vizinho.value.nome
        }
    }

    val textos = listOf(nomeComInimigos(jogador.territorioAtual), norte, sul, leste, oeste)
    val maiorTexto = textos.maxBy { it.length }
    val larguraCelula = maiorTexto.length + 2

    fun calcEspacos(texto: String): Pair<Int, Int> {
        val livre = larguraCelula - texto.length
        val e = livre / 2
        val d = livre - e
        return Pair(e, d)
    }

    val (eN, dN) = calcEspacos(norte)
    val (eS, dS) = calcEspacos(sul)
    val (eL, dL) = calcEspacos(leste)
    val (eO, dO) = calcEspacos(oeste)
    val (eC, dC) = calcEspacos(nomeCentro)
    val (eV, dV) = calcEspacos("")

    println()
    println(
        linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula)
    )

    println(
        renderizarCelula(eV, dV, "", true, larguraCelula) +
                renderizarCelula(eN, dN, norte, true, larguraCelula) +
                renderizarCelula(eV, dV, "", true, larguraCelula)
    )

    println(
        linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula)
    )

    println(
        renderizarCelula(eO, dO, oeste, true, larguraCelula) +
                renderizarCelula(eC, dC, nomeCentro, true, larguraCelula) +
                renderizarCelula(eL, dL, leste, true, larguraCelula)
    )

    println(
        linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula)
    )

    println(
        renderizarCelula(eV, dV, "", true, larguraCelula) +
                renderizarCelula(eS, dS, sul, true, larguraCelula) +
                renderizarCelula(eV, dV, "", true, larguraCelula)
    )

    println(
        linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula) +
                linhaHorizontal(larguraCelula)
    )

    println()
}

fun nomeComInimigos(territorio: Territorio) : String{
    return if(territorio.inimigos.isNotEmpty() || territorio.inimigos.size > 0){
        "⚔  ${territorio.nome} ⚔ "
    }else{
        territorio.nome
    }
}

fun renderizarCelula(espacoE: Int,espacoD: Int,texto: String,comBorda: Boolean,larguraCelula: Int): String {
    val conteudo = " ".repeat(espacoE) + texto + " ".repeat(espacoD)

    return if (comBorda) {
        "|$conteudo|"
    } else {
        conteudo
    }
}

fun linhaHorizontal(larguraCelula: Int): String {
    return "+" + "-".repeat(larguraCelula) + "+"
}


fun criarJogador(mapa: Mapa) : Jogador{
    return Jogador("Yuri", mapa.territorios[0])
}
