package Model.Personagem

import Helper.DadoHelper.Companion.random
import Model.Efeitos.Grimorio
import Model.Efeitos.Magia
import Model.Estruturas.Mapa
import formatarNome

abstract class Inimigo(
    val nome: TiposInimigos,
    override var vida: Int,
    override var vidaTotal: Int,
    override var ataque: Int,
    var ouro: Int,
    var magiasConhecidas: MutableList<Magia> = mutableListOf(
        Grimorio.bolaDeFogo,
        Grimorio.curaSimples,
        Grimorio.congelar,
        Grimorio.sono,
    ),
    var statusPersonagem: StatusPersonagem = StatusPersonagem.NADA,
) : Personagem(vida, vidaTotal, ataque, statusPersonagem){
    abstract fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int

    fun ataqueNormal() : Int{
        return ataque
    }

    fun decidirMovimento(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        aplicarEfeitoStatus()

        if((1..10).random() <= 5){
            return ataqueNormal()
        }else{
            return habilidadeEspecial(inimigo, mapa, jogador)
        }
    }
}

class BonecoTeste : Inimigo(TiposInimigos.BONECO_TESTE, 10000, 10000, 0, 0){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        println("Boneco de teste ficou la parado...")
        return 0
    }
}

class LoboInimigo : Inimigo(TiposInimigos.LOBO, 6, 6, 3, 2) {
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        return if ((1..100).random() <= 30) {
            println("O Lobo atacou duas vezes!")
            ataque + 1
        } else {
            ataque
        }
    }
}

class Orc : Inimigo(TiposInimigos.ORC, 14, 14, 5, 4) {
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        println("O Orc usa ataque brutal!")
        vida -= 1
        return ataque + 2
    }
}

class GoblinGuerreiro : Inimigo(TiposInimigos.GOBLIN_GUERREIRO, 4, 4, 1, 1){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        println("O Goblin Guerreiro usa ataque brutal menor!")
        return ataque + 2
    }
}

class GoblinArqueiro : Inimigo(TiposInimigos.GOBLIN_ARQUEIRO, 4, 4, 1, 1){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        println("O Goblin Arqueiro dispara uma flecha de fogo!")

        val chanceDeAcerto = (1..100).random()
        if (chanceDeAcerto > 70) {
            println("A flecha errou!")
            return 0
        }

        val dano = ataque

        val chanceQueimar = (1..100).random()
        if (chanceQueimar <= 40 && inimigo.status != StatusPersonagem.QUEIMANDO) {
            inimigo.status = StatusPersonagem.QUEIMANDO
            println("${inimigo.tipo.toString().lowercase().capitalize()} foi incendiado!")
        }

        return ataque + 2
    }
}

class GoblinMago : Inimigo(TiposInimigos.GOBLIN_MAGO, 4, 4, 1, 1){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {

        var magia = decidirMagia()
        return magia.executar(this, inimigo, jogador, mapa)

        return 0
    }

    fun decidirMagia() : Magia{
        val magiaAleatoria = magiasConhecidas.random()
        println("Goblin mago usou o feitiço ${magiaAleatoria.nome}")
        return magiaAleatoria
    }
}

class Bruxa : Inimigo(TiposInimigos.BRUXA, 7, 7, 3, 5){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        var magia = decidirMagia()
        return magia.executar(this, inimigo, jogador, mapa)

        return 0
    }

    fun decidirMagia() : Magia{
        val magiaAleatoria = magiasConhecidas.random()
        println("Goblin mago usou o feitiço ${magiaAleatoria.nome}")
        return magiaAleatoria
    }
}

class Guarda : Inimigo(TiposInimigos.GUARDA, 22, 22, 3,0){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        inimigo.status = StatusPersonagem.ENFRAQUECIDO
        println("O guarda enfraqueceu o ${inimigo.tipo.toString().formatarNome()}")
        return 0
    }
}

class Esqueleto: Inimigo(TiposInimigos.ESQUELETO, 1, 1, 5, 3){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        vida--
        return ataque * 2
    }
}

class ArmaduraEnfeiticada : Inimigo(TiposInimigos.ARMADURA_ENFEITICADA, 30, 30, 5, 30){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        val chance = random.nextInt(1, 101)
        if(chance <= 20){
            val magia = magiasConhecidas[1] // Cura Simples
            magia.executar(this, inimigo, jogador, mapa)
        }
        return ataque
    }
}

class InvocadorInimigo : Inimigo(TiposInimigos.INVOCADOR, 12, 12, 3, 7){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        val territorioAtual = jogador.territorioAtual.nome
        val territorioAtualObj = mapa.encontrarTerritorio(territorioAtual)
        for(inimigoMapa in territorioAtualObj!!.inimigos){
            if(inimigoMapa.nome == TiposInimigos.LOBO){
                return ataque
            }else{
                println("O invocador invocou um lobo")
                territorioAtualObj.inimigos.add(LoboInimigo())
                return 0
            }
        }
        return ataque
    }
}

class Mimico : Inimigo(TiposInimigos.MIMICO, 12, 12, 5, 15){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        println("O mimico abriu a boca para morder")
        return ataque + 2
    }
}

class EspreitadorSombras : Inimigo(TiposInimigos.ESPREITADOR_SOMBRAS,8, 8, 4, 6) {
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        return if (inimigo.status != StatusPersonagem.NADA) {
            println("O Espreitador ataca a fraqueza do alvo!")
            ataque + 4
        } else {
            println("O Espreitador observa, esperando uma abertura.")
            ataque
        }
    }
}

class CarnicalFaminto : Inimigo(TiposInimigos.CARNICAL_FAMINTO,9, 9, 3, 7) {
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        if (vida < vidaTotal / 2) {
            println("O Carniçal entra em frenesi e se cura com a adrenalina!")
            vida += 3
            ataque += 1
        }
        return ataque
    }
}

class ParasitaMental : Inimigo(TiposInimigos.PARASITA_MENTAL,6, 6, 2, 0) {
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        val roubo = minOf(3, jogador.ouro)
        jogador.ouro -= roubo
        ouro += roubo
        println("O Parasita drena $roubo de ouro do jogador!")
        return ataque
    }
}

class EcoDeGuerra : Inimigo(TiposInimigos.ECO_DE_GUERRA,14, 14, 3, 10) {
    private var ultimoDanoRecebido = 0

    fun registrarDano(dano: Int) {
        ultimoDanoRecebido = dano
    }

    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        val retorno = ultimoDanoRecebido / 2
        println("O Eco devolve o impacto recebido!")
        return ataque + retorno
    }
}

// BOSS
class Gigante : Inimigo(TiposInimigos.GIGANTE,50, 50, 5, 50) {
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {

        if (vida < vidaTotal / 2) {
            println("O Gigante entra em fúria!")
            ataque += 2
        }

        if (inimigo.status != StatusPersonagem.ENFRAQUECIDO) {
            inimigo.status = StatusPersonagem.ENFRAQUECIDO
            println("${inimigo.tipo.name.formatarNome()} foi enfraquecido pelo impacto!")
        }

        return ataque
    }
}

// BOSS
class Ciclope : Inimigo(TiposInimigos.CICLOPE,45, 45, 7, 45) {
    private var alvoMarcado: Tropa? = null

    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        // O ciclope tem dois momentos na habilidade especial, no primeiro momento ele só fixa o olhar, no segundo momento ele
        // aplica um golpe que da bastante dano e congela a tropa
        return if (alvoMarcado == inimigo) {
            println("O Ciclope reconhece o alvo e desfere um golpe devastador!")
            inimigo.status = StatusPersonagem.CONGELADO
            ataque + 3
        } else {
            println("O Ciclope fixa seu olhar em ${inimigo.tipo.name.formatarNome()}!")
            alvoMarcado = inimigo
            0
        }
    }
}

// BOSS
class Dragao : Inimigo(TiposInimigos.DRAGAO,80, 80, 6, 100) {
    private var emVoo = true
    private var turnos = 0

    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        turnos++

        if(emVoo){
            println("O dragão sobrevoa o campo e cospe fogo")
            this.status = StatusPersonagem.VOANDO
            val chance = random.nextInt(1, 101)
            if(chance <= 50){
                //pegou fogo
                inimigo.status = StatusPersonagem.QUEIMANDO
            }

            if(turnos >= 2){
                emVoo = false
                println("O dragão pousa violentamente no campo de batalha")
            }

            return ataque - 3
        }

        println("O dragão ruge")

        if(turnos >= 4){
            emVoo = true
            turnos = 0
            println("O dragão alça voo novamente!")
        }

        return ataque + 2
//        turnos++
//
//        if (emVoo) {
//            println("O Dragão sobrevoa o campo e cospe fogo!")
//            inimigo.status = StatusPersonagem.QUEIMANDO
//
//            if (turnos >= 2) {
//                emVoo = false
//                println("O Dragão pousa violentamente no campo de batalha!")
//            }
//
//            return ataque - 3
//        }
//
//        println("O Dragão ruge e ataca com força total!")
//        inimigo.status = StatusPersonagem.DORMINDO
//
//        if (turnos >= 4) {
//            emVoo = true
//            turnos = 0
//            println("O Dragão alça voo novamente!")
//        }
//
//        return ataque + 5
    }
}

class Copy : Inimigo(TiposInimigos.COPY, 8, 8, 1, 5){
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        if(vida <= vidaTotal / 2){
            val territorio = mapa.encontrarTerritorio(jogador.territorioAtual.nome)
            if(territorio != null){
                val quantidadeCopy = territorio.inimigos.filter { it.nome == TiposInimigos.COPY }.size
                if(quantidadeCopy <= 5){
                    println("Copy se multiplica e usa a habilidade de se curar")
                    territorio.inimigos.add(Copy())
                    this.vida += 2
                    return 0
                }
                return ataque
            }
            return ataque
        }
        return ataque
    }
}

class Viking : Inimigo(TiposInimigos.VIKING,vida = 18,vidaTotal = 18,ataque = 4,ouro = 6) {
    override fun habilidadeEspecial(inimigo: Tropa, mapa: Mapa, jogador: Jogador): Int {
        val vidaPercentual = vida.toDouble() / vidaTotal.toDouble()

        return when {
            // Berserker: quanto menos vida, mais dano
            vidaPercentual <= 0.4 -> { // Começa quando ele tiver com 7 de vida...
                println("O Viking entra em fúria berserker!")
                ataque + 4
            }

            // Golpe exaustivo
            (1..100).random() <= 30 -> {
                println("O Viking acerta um golpe exaustivo!")
                inimigo.status = StatusPersonagem.ENFRAQUECIDO
                ataque + 1
            }

            else -> ataque
        }
    }
}

enum class TiposInimigos{
    LOBO,
    ORC,
    GOBLIN_GUERREIRO,
    GOBLIN_MAGO,
    GOBLIN_ARQUEIRO,
    BONECO_TESTE,
    BRUXA,
    ESQUELETO,
    ARMADURA_ENFEITICADA,
    GUARDA,
    INVOCADOR,
    MIMICO,
    ESPREITADOR_SOMBRAS,
    CARNICAL_FAMINTO,
    PARASITA_MENTAL,
    ECO_DE_GUERRA,
    GIGANTE,
    DRAGAO,
    CICLOPE,
    COPY,
    VIKING
}

// bosses: gigante, dragão, ciclope, armadura enfeitiçada