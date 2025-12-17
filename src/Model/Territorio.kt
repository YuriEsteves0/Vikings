package Model

class Territorio(
    val nome: String,
    val acaoDisponiveis: List<AcoesJogador> = emptyList(),
    val estruturas: List<Estruturas> = emptyList(),
    val inimigos: MutableList<Inimigo> = mutableListOf(),
    val vizinhos: MutableMap<Direcao, Territorio> = mutableMapOf()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Territorio) return false
        return nome == other.nome
    }

    override fun hashCode(): Int {
        return nome.hashCode()
    }
}
enum class Direcao {
    NORTE, SUL, LESTE, OESTE
}