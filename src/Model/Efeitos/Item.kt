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
        }
    }
}

enum class TiposItens{
    CURA, BUFF, EVASAO
}

object Itens {
    val pocaoCura = Item(
        nome = "Poção de Cura",
        descricao = "Cura uma tropa em 3 de vida",
        tipo = TiposItens.CURA,
        poder = 3,
        precisaAlvo = true
    )
}
