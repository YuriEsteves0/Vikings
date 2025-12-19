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
                AcoesJogador.Entrar,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            estruturas = mutableListOf(
                Estruturas.Cabana
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
                Lobo(),
                Lobo()
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
                Orc()
            ),
            estruturas = mutableListOf(
                Estruturas.Porto
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
            ),
            inimigos = mutableListOf(
                GoblinGuerreiro(),
                GoblinMago()
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
            ),
            inimigos = mutableListOf(
                Lobo(),
                GoblinArqueiro()
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
            ),
            inimigos = mutableListOf(
                GoblinMago()
            )
        )

        val vilaValkstad = Territorio(
            nome = "Vila de Valkstad",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            estruturas = mutableListOf(
                Estruturas.Taverna,
                Estruturas.Ferreiro
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
            ),
            inimigos = mutableListOf(
                Orc(),
                GoblinGuerreiro()
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
            ),
            inimigos = mutableListOf(
                Orc(),
                GoblinGuerreiro(),
                GoblinMago()
            ),
            estruturas = mutableListOf(
                Estruturas.Caverna
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
            ),
            inimigos = mutableListOf(
                Orc(),
                Orc()
            )
        )

        val capitalAuren = Territorio(
            nome = "Capital de Auren",
            acaoDisponiveis = listOf(
                AcoesJogador.Andar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            estruturas = mutableListOf(
                Estruturas.Taverna,
                Estruturas.Ferreiro
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
        adicionarVizinhos(igrejaAntiga, planicie, Direcao.SUL)

        adicionarVizinhos(cavernaProfunda, vilaValkstad, Direcao.NORTE)
        adicionarVizinhos(vilaValkstad, capitalAuren, Direcao.NORTE)

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
