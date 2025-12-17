package Model

abstract class Inimigo(
    val nome: TiposInimigos,
    var vida: Int,
    var ataque: Int
){
    abstract fun habilidadeEspecial(): Int
}

class Lobo : Inimigo(TiposInimigos.LOBO, 6, 3) {
    override fun habilidadeEspecial(): Int {
        return if ((1..100).random() <= 30) {
            println("O Lobo atacou duas vezes!")
            ataque + 1
        } else {
            ataque
        }
    }
}

class Orc : Inimigo(TiposInimigos.ORC, 14, 5) {
    override fun habilidadeEspecial(): Int {
        println("O Orc usa ataque brutal!")
        vida -= 1
        return ataque + 2
    }
}

enum class TiposInimigos{
    LOBO, ORC
}