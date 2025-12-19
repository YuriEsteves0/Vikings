package Model.Estruturas

import Model.Personagem.*

class Mapa{
    val territorios = mutableListOf<Territorio>()

    fun criarTerritorios() {

        // PRIMEIRO MAPA

        val ruinasAntigas = Territorio(
            nome = "Ruínas Antigas",
            acaoDisponiveis = mutableListOf(
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
                Estruturas.Cabana,
                Estruturas.Mercado
            )
        )

        val estradaReal = Territorio(
            nome = "Estrada Real",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                LoboInimigo(),
                LoboInimigo()
            )
        )

        val portoVelho = Territorio(
            nome = "Porto Velho",
            acaoDisponiveis = mutableListOf(
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
            acaoDisponiveis = mutableListOf(
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
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                LoboInimigo(),
                GoblinArqueiro()
            )
        )

        val igrejaAntiga = Territorio(
            nome = "Igreja Antiga",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Entrar,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            estruturas = mutableListOf(
                Estruturas.Igreja
            ),
            inimigos = mutableListOf(
                GoblinMago()
            )
        )

        val vilaValkstad = Territorio(
            nome = "Vila de Valkstad",
            acaoDisponiveis = mutableListOf(
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
                Estruturas.Ferreiro,
                Estruturas.Mercado
            )
        )

        val planicie = Territorio(
            nome = "Planície",
            acaoDisponiveis = mutableListOf(
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
            acaoDisponiveis = mutableListOf(
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
            acaoDisponiveis = mutableListOf(
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
            acaoDisponiveis = mutableListOf(
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
                Estruturas.Ferreiro,
                Estruturas.Mercado
            ),
            disponivel = false
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

        // SEGUNDO MAPA

        val torreOraculo = Territorio(
            nome = "Torre do Oráculo",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Entrar,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            estruturas = mutableListOf(
                Estruturas.Torre_Oraculo
            ),
        )

        val vilarejoBrannor = Territorio(
            nome = "Vilarejo de Brannor",
            acaoDisponiveis = mutableListOf(
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
                Estruturas.Taverna,
                // Ponto social do vilarejo; permite descanso, rumores e pequenos contratos.
                Estruturas.Mercado
            )
        )

        val encruzilhadaNorte = Territorio(
            nome = "Encruzilhada do Norte",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                Bruxa(),
                GoblinMago()
            )
        )

        val camposDourados = Territorio(
            nome = "Campos Dourados",
            acaoDisponiveis = mutableListOf(
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
                Estruturas.Fazenda
            )
        )

        val fortalezaSkjold = Territorio(
            nome = "Fortaleza de Skjold",
            acaoDisponiveis = mutableListOf(
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
                Estruturas.Ferreiro,
                Estruturas.Taverna,
                Estruturas.Quartel_General
            )
        )

        val bosqueCinzas = Territorio(
            nome = "Bosque das Cinzas",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                Bruxa(),
                EspreitadorSombras()
            )
        )

        val valeSilencio = Territorio(
            nome = "Vale do Silêncio",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                ParasitaMental(),
                Esqueleto()
            )
        )

        val portoHaldrek = Territorio(
            nome = "Porto de Haldrek",
            acaoDisponiveis = mutableListOf(
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
                Estruturas.Porto
            ),
            inimigos = mutableListOf(
                Copy(),
                Copy()
            )
        )

        val abismoEsquecido = Territorio(
            nome = "Abismo Esquecido",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            estruturas = mutableListOf(
                Estruturas.Antro_Antigo
            ),
        )

        val cordilheiraFerro = Territorio(
            nome = "Cordilheira de Ferro",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            estruturas = mutableListOf(
                Estruturas.Mercado,
                Estruturas.Taverna
            )
        )

        val casteloViking = Territorio(
            nome= "Castelo Viking",
            acaoDisponiveis = mutableListOf(
                AcoesJogador.Andar,
                AcoesJogador.Atacar,
                AcoesJogador.Analisar_Territorio,
                AcoesJogador.Inventario,
                AcoesJogador.Entrar,
                AcoesJogador.Informações_Do_Reino,
                AcoesJogador.Ajuda,
                AcoesJogador.Sair
            ),
            inimigos = mutableListOf(
                Viking(),
                CarnicalFaminto()
            ),
            estruturas = mutableListOf(
                Estruturas.Castelo_Viking
            )
        )

        adicionarVizinhos(torreOraculo, vilarejoBrannor, Direcao.SUL)

        adicionarVizinhos(vilarejoBrannor, encruzilhadaNorte, Direcao.LESTE)
        adicionarVizinhos(vilarejoBrannor, camposDourados, Direcao.SUL)

        adicionarVizinhos(encruzilhadaNorte, fortalezaSkjold, Direcao.SUL)

        adicionarVizinhos(camposDourados, bosqueCinzas, Direcao.SUL)
        adicionarVizinhos(bosqueCinzas, valeSilencio, Direcao.LESTE)

        adicionarVizinhos(valeSilencio, portoHaldrek, Direcao.LESTE)
        adicionarVizinhos(valeSilencio, abismoEsquecido, Direcao.SUL)

        adicionarVizinhos(abismoEsquecido, cordilheiraFerro, Direcao.SUL)
        adicionarVizinhos(cordilheiraFerro, casteloViking, Direcao.SUL)

        territorios.addAll(
            listOf(

                // PRIMEIRO MAPA

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
                montanhas,

                // SEGUNDO MAPA

                torreOraculo, // 11
                vilarejoBrannor,
                encruzilhadaNorte,
                camposDourados,
                fortalezaSkjold,
                bosqueCinzas,
                valeSilencio,
                portoHaldrek,
                abismoEsquecido,
                cordilheiraFerro
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
