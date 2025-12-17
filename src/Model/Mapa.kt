package Model

class Mapa{
    val territorios = mutableListOf<Territorio>()

    fun criarTerritorios(){
        val baldurheim = Territorio(
            "Baldurheim",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Entrar,
                AcoesJogador.Informações_Do_Reino,
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
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            listOf(
                //Estruturas
            ),
            mutableListOf(
                Lobo(),
                Lobo()
            )
        )

        val floresta = Territorio(
            "Floresta",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            listOf(
                // Estruturas
            ),
            mutableListOf(
                Orc()
            )
        )

        val valkstad = Territorio(
            "Valkstad",
            listOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            listOf(
                Estruturas.Taverna
            )
        )

        adicionarVizinhos(baldurheim, floresta, Direcao.LESTE)
        adicionarVizinhos(floresta, rio, Direcao.LESTE)
        adicionarVizinhos(rio, valkstad, Direcao.SUL)

        territorios.add(baldurheim)
        territorios.add(floresta)
        territorios.add(rio)
        territorios.add(valkstad)
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
