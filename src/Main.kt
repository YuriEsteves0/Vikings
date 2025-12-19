import Helper.CMDHelper
import Helper.DadoHelper
import Helper.Dados
import Helper.SorteStatus
import Model.Efeitos.Magia
import Model.Estruturas.*
import Model.Personagem.*
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

        if(jogador.comida <= 0){
            jogador.comida = 0
            for(tropa in jogador.tropas){
                tropa.vida = tropa.vida - 1
            }
            println("Suas tropas estão com fome, penalidade de -1 hp")
        }

        println("Comida: ${jogador.comida}      Ouro: ${jogador.ouro}      Tropas: ${jogador.tropas.size}")
        val territorioAtual = jogador.territorioAtual

        if(territorioAtual.inimigos.isNotEmpty()){
            println()
            for(inimigo in territorioAtual.inimigos){
                if(inimigo.status == StatusPersonagem.NADA){
                    println("• ${inimigo.nome.name.formatarNome()} " +
                            "${barraVida(inimigo.vida, inimigo.vidaTotal)} ${inimigo.vida}/${inimigo.vidaTotal}")
                }else{
                    println("• ${inimigo.nome.name.formatarNome()} " +
                            "${barraVida(inimigo.vida, inimigo.vidaTotal)} ${inimigo.vida}/${inimigo.vidaTotal}")
                    inimigo.aplicarEfeitoStatus()
                }
            }
        }

        val acaoUsu = acoesUsu(territorioAtual.acaoDisponiveis)

        when(acaoUsu){
            AcoesJogador.Andar -> {

                if (territorioAtual.inimigos.isNotEmpty()) {
                    CMDHelper.limparTela()
                    println("Ainda há inimigos neste território")
                    CMDHelper.pressionarEnterContinuar()
                    return
                }else{
                    println()
                    println("Para onde deseja ir?")
                    println("Ex: Montanhas | norte | cima")
                    println()


                    val entrada = readLine()?.trim() ?: ""

                    val destino = resolverDestino(territorioAtual, mapa, entrada)

                    if (destino == null) {
                        CMDHelper.limparTela()
                        println("Destino inválido ou inacessível!")
                        CMDHelper.pressionarEnterContinuar()
                    }else{

                        if (territoriosAnalisados.containsKey(destino.nome)) {

                            val sorte = territoriosAnalisados[destino.nome]!!
                            val chance = Random.nextInt(1, 101)

                            when (sorte) {
                                SorteStatus.FALHA_CRITICA -> if (chance <= 100) emboscada(jogador, destino)
                                SorteStatus.FALHA -> if (chance <= 70) emboscada(jogador, destino)
                                SorteStatus.OK -> if (chance <= 50) emboscada(jogador, destino)
                                SorteStatus.SUCESSO -> if (chance <= 30) emboscada(jogador, destino)
                                SorteStatus.SUCESSO_CRITICO -> {}
                            }

                        } else if (destino.inimigos.isNotEmpty()) {
                            val chance = Random.nextInt(1, 10)
                            if (chance <= 5) {
                                emboscada(jogador, destino)
                            } else {
                                println()
                                println("Você evitou uma emboscada")
                            }
                        }

                        jogador.territorioAtual = destino
                        consumirComida(jogador, AcoesJogador.Andar)
                        CMDHelper.limparTela()
                    }
                }
            }

            AcoesJogador.Analisar_Territorio -> {
                if(jogador.territorioAtual.inimigos.isNotEmpty()){
                    CMDHelper.limparTela()
                    println("Você não pode analisar territorios no momento, há inimigos por perto")
                    CMDHelper.pressionarEnterContinuar()
                }else{
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
                        fun estimativa(real: Int, percentualErro: Double, erroMin: Int = 1): Int {
                            if (real <= 0) return 0

                            val erroCalculado = maxOf((real * percentualErro).toInt(), erroMin)
                            val min = maxOf(real - erroCalculado, 0)
                            val max = real + erroCalculado

                            return (min..max).random()
                        }

                        when (sorte) {
                            SorteStatus.FALHA_CRITICA, SorteStatus.FALHA -> {
                                println("Falha ao analisar ${destino.nome}")
                                territoriosAnalisados[destino.nome] = SorteStatus.FALHA
                            }

                            SorteStatus.OK -> {
                                val soldados = estimativa(destino.inimigos.size, 0.75)
                                println("O território ${destino.nome} tem aproximadamente $soldados soldados")
                                territoriosAnalisados[destino.nome] = SorteStatus.OK
                            }

                            SorteStatus.SUCESSO -> {
                                val soldados = estimativa(destino.inimigos.size, 0.35)
                                println("O território ${destino.nome} tem entre ${soldados - 1}~${soldados + 1} soldados")
                                territoriosAnalisados[destino.nome] = SorteStatus.SUCESSO
                            }

                            SorteStatus.SUCESSO_CRITICO -> {
                                println(
                                    "O território ${destino.nome} tem ${destino.inimigos.size} soldados " +
                                            "e ${destino.estruturas.size} estruturas"
                                )
                                territoriosAnalisados[destino.nome] = SorteStatus.SUCESSO_CRITICO
                            }
                        }

                        CMDHelper.pressionarEnterContinuar()
                    }
                }
            }

            AcoesJogador.Atacar -> {

                if (territorioAtual.inimigos.isEmpty()) {
                    println("Não há inimigos neste território.")
                    CMDHelper.pressionarEnterContinuar()
                }else{
                    var combateAtivo = true

                    while (
                        combateAtivo &&
                        jogador.tropas.isNotEmpty() &&
                        territorioAtual.inimigos.isNotEmpty()
                    ) {

                        val ordemAtaque = mutableListOf<Any>()

                        var iAliado = 0
                        var iInimigo = 0
                        var vezAliado = Random.nextBoolean()

                        while (iAliado < jogador.tropas.size || iInimigo < territorioAtual.inimigos.size) {

                            // Se é a vez do aliado, adicione ele na ordem de ataque
                            if (vezAliado && iAliado < jogador.tropas.size) {
                                ordemAtaque.add(jogador.tropas[iAliado++])
                                // Se é a vez do inimigo, adicione ele na ordem de ataque
                            } else if (!vezAliado && iInimigo < territorioAtual.inimigos.size) {
                                ordemAtaque.add(territorioAtual.inimigos[iInimigo++])

                            }

                            vezAliado = !vezAliado
                            if (iAliado >= jogador.tropas.size) vezAliado = false
                            if (iInimigo >= territorioAtual.inimigos.size) vezAliado = true
                        }

                        CMDHelper.limparTela()
                        mostrarOrdemAtaque(ordemAtaque)
                        CMDHelper.pressionarEnterContinuar()

                        for (entidade in ordemAtaque) {

                            if (!combateAtivo) break
                            if (jogador.tropas.isEmpty()) break
                            if (territorioAtual.inimigos.isEmpty()) break

                            CMDHelper.limparTela()
                            mostrarPainelBatalha(jogador, territorioAtual.inimigos)

                            when (entidade) {

                                is Tropa -> {
                                    if (!jogador.tropas.contains(entidade)) continue

                                    if (!entidade.podeAgir()) {
                                        println("\n${entidade.tipo.name.formatarNome()} está incapacitado!")
                                        entidade.aplicarEfeitoStatus()
                                        CMDHelper.pressionarEnterContinuar()
                                        continue
                                    }

                                    val alvosPossiveis =
                                        territorioAtual.inimigos.filter { it.status != StatusPersonagem.INVISIVEL }

                                    if (alvosPossiveis.isEmpty()) continue

                                    val inimigoAlvo = alvosPossiveis.random()

                                    println("\nTURNO DO ALIADO")
                                    println("${entidade.tipo.name.formatarNome()} ➜ ${inimigoAlvo.nome.name.formatarNome()}")

                                    var dano = entidade.decidirMovimento(jogador, inimigoAlvo)

                                    if(entidade.status == StatusPersonagem.ENFRAQUECIDO){
                                        dano -= 2
                                        entidade.aplicarEfeitoStatus()
                                    }

                                    println("Dano causado: $dano")

                                    inimigoAlvo.vida -= dano

                                    if (inimigoAlvo.vida <= 0) {
                                        textoMorto(entidade, inimigoAlvo, false)
                                        jogador.ouro += inimigoAlvo.ouro
                                        territorioAtual.inimigos.remove(inimigoAlvo)

                                        if (territorioAtual.inimigos.isEmpty()) {
                                            println("Todos os inimigos foram derrotados!")
                                            combateAtivo = false
                                        }
                                    }
                                }

                                is Inimigo -> {
                                    if (!territorioAtual.inimigos.contains(entidade)) continue

                                    if (!entidade.podeAgir()) {
                                        println("\n${entidade.nome.name.formatarNome()} está incapacitado!")
                                        entidade.aplicarEfeitoStatus()
                                        CMDHelper.pressionarEnterContinuar()
                                        continue
                                    }

                                    val alvosPossiveis =
                                        jogador.tropas.filter { it.status != StatusPersonagem.INVISIVEL }

                                    if (alvosPossiveis.isEmpty()) continue

                                    val tropaAlvo = alvosPossiveis.random()

                                    println("\nTURNO DO INIMIGO")
                                    println("${entidade.nome.name.formatarNome()} ➜ ${tropaAlvo.tipo.name.formatarNome()}")

                                    var dano = entidade.decidirMovimento(tropaAlvo)

                                    if(entidade.status == StatusPersonagem.ENFRAQUECIDO){
                                        dano -= 2
                                        entidade.aplicarEfeitoStatus()
                                    }

                                    println("Dano causado: $dano")

                                    tropaAlvo.vida -= dano

                                    if (tropaAlvo.vida <= 0) {
                                        textoMorto(tropaAlvo, entidade, true)
                                        jogador.tropas.remove(tropaAlvo)

                                        if (jogador.tropas.isNotEmpty()) {
                                            println()
                                            println("Um aliado morreu. Deseja continuar o combate?")
                                            println("[1] Continuar")
                                            println("[2] Fugir")

                                            if (readLine()?.trim() == "2") {
                                                println("Você decidiu fugir do combate!")
                                                combateAtivo = false
                                            }
                                        } else {
                                            println("Todas as suas tropas foram derrotadas!")
                                            combateAtivo = false
                                        }
                                    }
                                }
                            }

                            CMDHelper.pressionarEnterContinuar()
                        }
                    }
                }

                if (territorioAtual.inimigos.isEmpty()) {
                    jogador.territoriosDominados.add(jogador.territorioAtual)
                }
            }

            AcoesJogador.Entrar -> {
                if(jogador.territorioAtual.inimigos.isNotEmpty()){
                    CMDHelper.limparTela()
                    println("Você não pode entrar em estruturas no momento, há inimigos por perto")
                    CMDHelper.pressionarEnterContinuar()
                }else{
                    CMDHelper.limparTela()
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
                        CMDHelper.limparTela()
                        estruturaEscolhida?.funcaoEstrutura(jogador, mapa)
                    } else {
                        println("Opção de estrutura inválida!")
                    }
                }
            }

            AcoesJogador.Informações_Do_Reino -> {
                CMDHelper.limparTela()
                println()
                mostrarPainelTropas(jogador.tropas, jogador.bonusGuerreiroAT, jogador.bonusMagoAT, jogador.bonusArqueiroAT)
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

            AcoesJogador.Ajuda -> {
                println()
                println("Para receber ajuda com os comandos visite:")
                println("https://github.com/YuriEsteves0/Vikings/blob/main/docs/Comandos.md")
                CMDHelper.pressionarEnterContinuar()
            }

            AcoesJogador.Inventario -> {
                CMDHelper.limparTela()
                println()
                println("*-----* INVENTÁRIO *-----*")

                if (jogador.inventario.isEmpty()) {
                    println("| Inventário vazio")
                    println("*-----*------------*-----*")
                    println()
                    println("*-----* Magias Conhecidas *-----*")
                    val magias = mutableSetOf<Magia>()

                    jogador.tropas.forEach { tropa ->
                        if (tropa.magiasConhecidas.isEmpty()) {
                            println("| Sem magias conhecidas")
                        } else {
                            tropa.magiasConhecidas.forEach { magia ->
                                if (magias.add(magia)) {
                                    println("| ${magia.nome}")
                                }
                            }
                        }
                    }
                    println("*-----*-------------------*-----*")
                    println()
                    CMDHelper.pressionarEnterContinuar()
                    CMDHelper.limparTela()
                    continue
                }

                jogador.inventario.forEachIndexed { i, item ->
                    println("| $i. ${item.nome}")
                }
                println("| ${jogador.inventario.size}. Sair")
                println("*-----*------------*-----*")
                println()

                println("*-----* Magias Conhecidas *-----*")
                val magias = mutableSetOf<Magia>()

                jogador.tropas.forEach { tropa ->
                    if (tropa.magiasConhecidas.isEmpty()) {
                        println("| Sem magias conhecidas")
                    } else {
                        tropa.magiasConhecidas.forEach { magia ->
                            if (magias.add(magia)) {
                                println("| ${magia.nome}")
                            }
                        }
                    }
                }
                println("*-----*-------------------*-----*")
                println()


                print("Escolha o item do inventário: ")
                val escolha = readLine()?.toIntOrNull()

                if (escolha == null || escolha !in jogador.inventario.indices) {
                    continue
                }

                val itemEscolhido = jogador.inventario[escolha]

                val alvo = if (itemEscolhido.precisaAlvo) {
                    escolherTropa(jogador)
                } else null

                itemEscolhido.usar(alvo)
                jogador.inventario.removeAt(escolha)

                CMDHelper.pressionarEnterContinuar()
            }

            else -> {
                println("Opção inválida! Tente novamente.")
                continue
            }
        }
    }
}

fun escolherTropa(jogador: Jogador): Tropa? {
    if (jogador.tropas.isEmpty()) return null

    println()
    println("Escolha a tropa:")
    jogador.tropas.forEachIndexed { i, tropa ->
        println("| $i. ${tropa.tipo.name.formatarNome()} (${tropa.vida}/${tropa.vidaTotal} ❤ )")
    }
    println()

    val entrada = readLine()?.toIntOrNull()
    if (entrada == null || entrada !in jogador.tropas.indices) {
        println("Escolha inválida.")
        return null
    }

    return jogador.tropas[entrada]
}


fun String.formatarNome(): String {
    return this
        .lowercase()
        .replace("_", " ")
        .replaceFirstChar { it.uppercase() }
}


fun consumirComida(jogador: Jogador, acaoUsu: AcoesJogador){
    val consumoBase: Int = jogador.tropas.size
    val consumoExtra = when (acaoUsu){
        AcoesJogador.Andar -> 0
        AcoesJogador.Atacar -> 0
        AcoesJogador.Analisar_Territorio -> 0
        AcoesJogador.Entrar -> 0
        AcoesJogador.Informações_Do_Reino -> 0
        AcoesJogador.Sair -> 0
        AcoesJogador.Ajuda -> 0
        else -> 0
    }

    val consumoTotal = consumoBase + consumoExtra
    jogador.comida -= consumoTotal

    println("Suas tropas consumiram $consumoTotal de comida")
}

fun emboscada(jogador: Jogador, destino: Territorio) {
    if(destino.inimigos.isNotEmpty()){


        CMDHelper.limparTela()

        println("════════════════════════════════════")
        println("   EMBOSCADA!")
        println("════════════════════════════════════")
        println("Você foi atacado de surpresa por ${destino.inimigos.size} inimigos!\n")

        val random = Random
        val tropasMortas = mutableListOf<Tropa>()

        for (inimigo in destino.inimigos) {

            if (jogador.tropas.isEmpty()) break

            val indice = random.nextInt(jogador.tropas.size)
            val tropaAlvo = jogador.tropas[indice]

            println(" ${inimigo.nome.name.formatarNome()} ataca ${tropaAlvo.tipo.name.formatarNome()}!")

            val dano = inimigo.decidirMovimento(tropaAlvo)
            tropaAlvo.vida -= dano

            println(
                "Dano: $dano   " +
                        "HP ${barraVida(tropaAlvo.vida, tropaAlvo.vidaTotal)} " +
                        "${tropaAlvo.vida}/${tropaAlvo.vidaTotal}"
            )

            if (tropaAlvo.vida <= 0) {
                println(" ${tropaAlvo.tipo.name.formatarNome()} caiu em combate!")
                tropasMortas.add(tropaAlvo)
            }

            println()
        }

        jogador.tropas.removeAll(tropasMortas)

        if (tropasMortas.isNotEmpty()) {
            println(" Perdas na emboscada:")
            tropasMortas.forEach {
                println("• ${it.tipo.name.formatarNome()}")
            }
        }

        if (jogador.tropas.isEmpty()) {
            println("\n Todas as suas tropas foram exterminadas na emboscada...")
        }

        println("════════════════════════════════════")
        CMDHelper.pressionarEnterContinuar()
    }
}

fun acoesUsu(acoesDisponiveis: List<AcoesJogador>) : AcoesJogador? {
    val ordemAcoes = mutableMapOf<Int, AcoesJogador>()

    println()
    println("*-----* AÇÕES *-----*")
    acoesDisponiveis.forEachIndexed { i, acao ->
        val texto = acao.name.formatarNome()
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
        "!  ${territorio.nome} ! "
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


fun criarJogador(mapa: Mapa) : Jogador {
    return Jogador("Yuri", mapa.territorios[0])
}

fun barraVida(atual: Int, max: Int, tamanho: Int = 10): String {
    if (max <= 0) return "░".repeat(tamanho)
    val preenchido = (atual * tamanho) / max
    val vazio = tamanho - preenchido
    return "█".repeat(preenchido.coerceAtLeast(0)) + "░".repeat(vazio.coerceAtLeast(0))
}

fun mostrarPainelBatalha(jogador: Jogador, inimigos: List<Inimigo>) {
    println("════════════════════════════════════")
    println("   BATALHA EM ANDAMENTO")
    println("════════════════════════════════════")

    println("\n ALIADOS")
    jogador.tropas.forEach {
        if(it.status == StatusPersonagem.NADA){
            println(
                "• ${it.tipo.name.formatarNome()} " +
                        "HP  ${barraVida(it.vida, it.vidaTotal)} ${it.vida}/${it.vidaTotal}"
            )
        }else{
            println(
                "• ${it.tipo.name.formatarNome()} " +
                        "HP  ${barraVida(it.vida, it.vidaTotal)} ${it.vida}/${it.vidaTotal}   ${it.status.name.formatarNome()}")
        }
    }

    println("\n INIMIGOS")
    inimigos.forEach {
        if(it.status == StatusPersonagem.NADA){
            println("• ${it.nome.name.formatarNome()} " +
                    "HP  ${barraVida(it.vida, it.vidaTotal)} ${it.vida}/${it.vidaTotal}")
        }else{

            println("• ${it.nome.name.formatarNome()} " +
                    "HP  ${barraVida(it.vida, it.vidaTotal)} ${it.vida}/${it.vidaTotal}   ${it.status.name.formatarNome()}")
        }
    }

    println("════════════════════════════════════")
}

fun mostrarOrdemAtaque(ordemAtaque: List<Any>) {
    println("-- ORDEM DE ATAQUE --")
    ordemAtaque.forEach {
        when (it) {
            is Tropa -> println("[ALIADO -> ${it.tipo.name.formatarNome()}] ")
            is Inimigo -> println("[INIMIGO -> ${it.nome.name.formatarNome()}] ")
        }
    }
}


fun resolverDestino(
    territorioAtual: Territorio,
    mapa: Mapa,
    input: String
): Territorio? {

    val comando = input.lowercase().trim()

    val direcao = aliasesDirecao[comando]
    if (direcao != null) {
        return territorioAtual.vizinhos[direcao]
    }

    return mapa.encontrarTerritorio(input)
}

fun mostrarPainelTropas(
    tropas: List<Tropa>,
    bonusGuerreiroAT: Int,
    bonusMagoAT: Int,
    bonusArqueiroAT: Int
) {
    println("*-----* TROPAS *-----*")

    println("|")
    tropas.forEachIndexed { index, tropa ->
        val bonusAtaque = when (tropa.tipo) {
            TiposTropa.GUERREIRO -> bonusGuerreiroAT
            TiposTropa.MAGO -> bonusMagoAT
            TiposTropa.ARQUEIRO -> bonusArqueiroAT
        }

        val vidaAtual = tropa.vida.coerceAtLeast(0)

        println(
            "| $index. ${tropa.tipo.name.formatarNome()} " +
                    "${barraVida(vidaAtual, tropa.vidaTotal, tropa.vidaTotal)} (${vidaAtual}/${tropa.vidaTotal} HP  ) " +
                    "(${tropa.ataque + bonusAtaque} AT  )"
        )
        println("|      Habilidade Especial: ${tropa.habilidadeEspecialDescricao()}")
        println("|")

    }

    println("*-----*-------------*-----*")
}
