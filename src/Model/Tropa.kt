package Model

abstract class Tropa(
    val tipo: TiposTropa,
    var vida: Int,
    var vidaTotal: Int,
    protected var ataqueBase: Int
){
    open fun ataqueTotal(jogador: Jogador): Int {
        return ataqueBase
    }

    abstract fun habilidadeEspecial(jogador: Jogador): Int
}

class Guerreiro : Tropa(TiposTropa.GUERREIRO, 18, 18, 4) {

    override fun ataqueTotal(jogador: Jogador): Int {
        return ataqueBase + jogador.bonusGuerreiroAT
    }

    override fun habilidadeEspecial(jogador: Jogador): Int {
        println("Guerreiro se defende!")
        return ataqueTotal(jogador)
    }
}


class Arqueiro : Tropa(TiposTropa.ARQUEIRO, 10, 10, 5) {

    override fun ataqueTotal(jogador: Jogador): Int {
        return ataqueBase + jogador.bonusArqueiroAT
    }

    override fun habilidadeEspecial(jogador: Jogador): Int {
        return if ((1..100).random() <= 25) {
            println("Tiro crítico do Arqueiro!")
            ataqueTotal(jogador) * 2
        } else ataqueTotal(jogador)
    }
}


class Mago : Tropa(TiposTropa.MAGO, 8, 8, 6) {

    override fun ataqueTotal(jogador: Jogador): Int {
        return ataqueBase + jogador.bonusMagoAT
    }

    override fun habilidadeEspecial(jogador: Jogador): Int {
        println("Mago lança magia poderosa!")
        return ataqueTotal(jogador) + 2
    }
}


enum class TiposTropa{
    GUERREIRO, MAGO, ARQUEIRO
}
