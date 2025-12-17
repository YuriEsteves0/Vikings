package Model.Estruturas

import Model.Personagem.*

class Mapa{
    val territorios = mutableListOf<Territorio>()

    fun criarTerritorios(){
        val baldurheim = Territorio(
            "Baldurheim",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            listOf(
                Estruturas.Ferreiro
            ),
        )

        val rio = Territorio(
            "Rio",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            listOf(
                //Estruturas
            ),
            mutableListOf(
                Lobo(),
                Lobo(),
            )
        )

        val floresta = Territorio(
            "Floresta",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            listOf(
                // Estruturas
            ),
            mutableListOf(
                GoblinArqueiro(),
                GoblinMago(),
                GoblinGuerreiro()
            )
        )

        val valkstad = Territorio(
            "Valkstad",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            listOf(
                Estruturas.Taverna
            )
        )

        val caverna = Territorio(
            "Caverna",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair,
            ),
            listOf(
                Estruturas.Caverna
            ),
            mutableListOf(
                GoblinMago(),
                GoblinGuerreiro(),
                GoblinGuerreiro(),
                GoblinGuerreiro(),
                GoblinArqueiro(),
            ),
        )

        adicionarVizinhos(baldurheim, floresta, Direcao.LESTE)
        adicionarVizinhos(floresta, rio, Direcao.LESTE)
        adicionarVizinhos(rio, valkstad, Direcao.SUL)
        adicionarVizinhos(valkstad, caverna, Direcao.SUL)

        territorios.add(baldurheim)
        territorios.add(floresta)
        territorios.add(rio)
        territorios.add(valkstad)
        territorios.add(caverna)
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
