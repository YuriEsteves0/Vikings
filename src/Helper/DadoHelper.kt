package Helper

import kotlin.random.Random

class DadoHelper {
    companion object{
        val random = Random

        fun girarD20() : Int{
            return random.nextInt(0, 20)
        }

        fun girarD3() : Int{
            return random.nextInt(0, 3)
        }

        fun verificarSorte(dado: Dados, numero: Int, vantagem: Boolean) : SorteStatus{
            var numeroReal = numero
            if(vantagem){
                numeroReal = numero + girarD3()
            }

            when(dado){
                Dados.D20 -> {
                    if(numeroReal == 1){
                        return SorteStatus.FALHA_CRITICA
                    }
                    if(numeroReal > 1 && numeroReal <= 9){
                        return SorteStatus.FALHA
                    }
                    if(numeroReal > 9 && numeroReal <= 14){
                        return SorteStatus.OK
                    }
                    if(numeroReal > 14 && numeroReal <= 19){
                        return SorteStatus.SUCESSO
                    }
                    if(numeroReal >= 20){
                        return SorteStatus.SUCESSO_CRITICO
                    }
                }
            }
            return SorteStatus.OK
        }
    }
}

enum class SorteStatus{
    FALHA_CRITICA, FALHA, OK, SUCESSO, SUCESSO_CRITICO
}

enum class Dados{
    D20
}