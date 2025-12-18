package Model.Estruturas

import Model.Personagem.*

class Mapa{
    val territorios = mutableListOf<Territorio>()

    fun criarTerritorios() {


        val ruinasAntigas = Territorio(
            nome = "Ruínas Antigas",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val portoVelho = Territorio(
            nome = "Porto Velho",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                Orc(),
                Orc()
            )
        )

        val estradaReal = Territorio(
            nome = "Estrada Real",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                BonecoTeste()
            )
        )

        val capitalAuren = Territorio(
            nome = "Capital de Auren",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val pantano = Territorio(
            nome = "Pântano",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val igrejaAntiga = Territorio(
            nome = "Igreja Antiga",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val vilaValkstad = Territorio(
            nome = "Vila de Valkstad",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val florestaSombria = Territorio(
            nome = "Floresta Sombria",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val planicie = Territorio(
            nome = "Planície",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val cavernaProfunda = Territorio(
            nome = "Caverna Profunda",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        val montanhas = Territorio(
            nome = "Montanhas",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            )
        )

        adicionarVizinhos(ruinasAntigas, estradaReal, Direcao.SUL)

        adicionarVizinhos(portoVelho, estradaReal, Direcao.LESTE)
        adicionarVizinhos(estradaReal, capitalAuren, Direcao.LESTE)

        adicionarVizinhos(portoVelho, pantano, Direcao.SUL)
        adicionarVizinhos(pantano, florestaSombria, Direcao.SUL)

        adicionarVizinhos(pantano, igrejaAntiga, Direcao.LESTE)
        adicionarVizinhos(igrejaAntiga, vilaValkstad, Direcao.LESTE)

        adicionarVizinhos(florestaSombria, planicie, Direcao.LESTE)
        adicionarVizinhos(planicie, cavernaProfunda, Direcao.LESTE)

        adicionarVizinhos(planicie, montanhas, Direcao.SUL)
        adicionarVizinhos(igrejaAntiga, estradaReal, Direcao.NORTE)


        territorios.addAll(
            listOf(
                ruinasAntigas,
                portoVelho,
                estradaReal,
                capitalAuren,
                pantano,
                igrejaAntiga,
                vilaValkstad,
                florestaSombria,
                planicie,
                cavernaProfunda,
                montanhas
            )
        )
    }

    fun encontrarTerritorio(territorio: String): Territorio? {
        return territorios.find { it.nome.equals(territorio, ignoreCase = true) }
    }

    // HELPERS

    fun adicionarVizinhos(territorio1: Territorio, territorio2: Territorio, direcao: Direcao){
        var direcaoOposta: Direcao = direcao

        when(direcaoOposta){
            Direcao.LESTE -> {
                direcaoOposta = Direcao.OESTE
            }
            Direcao.SUL -> {
                direcaoOposta = Direcao.NORTE
            }
            Direcao.NORTE -> {
                direcaoOposta = Direcao.SUL
            }
            Direcao.OESTE -> {
                direcaoOposta = Direcao.LESTE
            }
        }

        territorio1.vizinhos[direcao] = territorio2
        territorio2.vizinhos[direcaoOposta] = territorio1

    }
}
