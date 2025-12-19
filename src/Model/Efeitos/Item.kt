package Model.Efeitos

import Model.Personagem.StatusPersonagem
import Model.Personagem.Tropa
import formatarNome

class Item(
    val nome: String,
    val descricao: String,
    val tipo: TiposItens,
    val poder: Int,
    val precisaAlvo: Boolean = false
) {
    fun usar(alvo: Tropa?) {
        when (tipo) {
            TiposItens.CURA -> {
                if (alvo == null) return
                val vidaAntes = alvo.vida
                alvo.vida = minOf(alvo.vida + poder, alvo.vidaTotal)
                val curado = alvo.vida - vidaAntes
                println("${alvo.tipo.name.formatarNome()} foi curado em $curado ❤")
            }

            TiposItens.BUFF -> {
                if (alvo == null) return
                alvo.ataque += poder
                println("${alvo.tipo.name.formatarNome()} recebeu +$poder de ataque ⚔")
            }

            TiposItens.EVASAO -> {
                if (alvo == null) return
                alvo.status = StatusPersonagem.INVISIVEL
                println("${alvo.tipo.name.formatarNome()} ficou invisível 👁")
            }

            TiposItens.MISSAO -> {
                println()
                println("O item serve apenas para a missão principal")
            }
        }
    }
}

enum class TiposItens{
    CURA, BUFF, EVASAO, MISSAO
}

object Itens {
    val pocaoCura = Item(
        nome = "Poção de Cura",
        descricao = "Cura uma tropa em 3 de vida",
        tipo = TiposItens.CURA,
        poder = 3,
        precisaAlvo = true
    )

    val chaveIgreja = Item(
        nome = "Chave da Igreja Antiga",
        descricao = "Uma chave antiga que serve para destrancar as portas da Igreja Antiga",
        tipo = TiposItens.MISSAO,
        poder = 0
    )
}
