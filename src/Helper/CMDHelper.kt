package Helper

class CMDHelper {
    companion object{
        fun limparTela() {
            print("\u001b[H\u001b[2J")
            System.out.flush()
        }

        fun pressionarEnterContinuar(){
            println()
            println("Pressione ENTER para continuar...")
            readLine()
        }

        fun fimDeJogo(){
            limparTela()
            println("************** OBRIGADO POR JOGAR **************")
            println("Designed by: Yuri Esteves")
        }

        fun Debug(mensagem: String){
            println("************** DEBUG **************")
            println(mensagem)
            println("************** DEBUG **************")
        }
    }
}