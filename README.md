
# Vikings

**Vikings** é um jogo de estratégia em turnos executado via terminal, desenvolvido em Kotlin, no qual o jogador assume o controle de um reino em expansão. A progressão ocorre por meio da exploração de territórios interconectados, gerenciamento de tropas e tomada de decisões táticas baseadas em risco, sorte e informação parcial.
## Funcionalidades

- **Exploração de Territórios**
  - Mapa composto por territórios interligados por direções (Norte, Sul, Leste e Oeste)
  - Movimentação condicionada à eliminação de inimigos no território atual
  - Exibição visual do mapa diretamente no terminal em formato ASCII

- **Sistema de Análise de Território**
  - Análise baseada em rolagem de dado D20
  - Resultados variam entre falha crítica e sucesso crítico
  - Informações fornecidas ao jogador possuem margem de erro conforme o nível de sucesso
  - Registro de territórios já analisados, influenciando eventos futuros

- **Emboscadas Dinâmicas**
  - Possibilidade de emboscada ao entrar em territórios hostis
  - Probabilidade de emboscada afetada pelo resultado da análise prévia
  - Dano aleatório aplicado às tropas do jogador antes do combate direto

- **Sistema de Combate por Turnos**
  - Ordem de ataque alternada entre tropas aliadas e inimigos
  - Seleção aleatória de alvos durante o combate
  - Cálculo de dano considerando atributos da tropa e bônus do jogador
  - Remoção automática de unidades derrotadas

- **Gerenciamento de Tropas**
  - Diferentes tipos de tropas com vida e ataque distintos
  - Exibição detalhada do estado das tropas (vida atual, vida máxima e ataque)
  - Perda definitiva de tropas ao atingir vida zero

- **Estruturas de Território**
  - Acesso a estruturas específicas dentro de cada território
  - Execução de ações estratégicas ao interagir com estruturas
  - Impacto direto nos recursos e no desempenho do reino

- **Recursos do Reino**
  - Sistema de recursos incluindo comida e ouro
  - Recursos exibidos em tempo real durante a jogabilidade
  - Influência dos recursos no desempenho das tropas

- **Progressão e Conquista**
  - Controle de territórios dominados pelo jogador
  - Objetivo principal de conquistar todos os territórios do mapa
  - Exibição do progresso geral do reino durante o jogo

- **Interface em Terminal**
  - Totalmente jogável via linha de comando
  - Menus interativos para ações, estruturas e combate
  - Feedback textual claro para todas as ações do jogador

- **Condições de Fim de Jogo**
  - Encerramento automático ao perder todas as tropas
  - Opção de saída manual a qualquer momento
  - Mensagem de encerramento exibida ao final da partida
## Ajuda nos comandos

Para buscar ajuda sobre como funciona algum comando do nosso jogo, visite a seção [Ajuda ](https://github.com/YuriEsteves0/Vikings/blob/main/docs/Comandos.md)sempre que tiver dúvidas durante a jogabilidade.  

## Autores

- [@yuriesteves0](https://github.com/YuriEsteves0)


## Feedback

Se você tiver algum feedback, por favor nos deixe saber por meio de yuriestevesempresarial@gmail.com


## Licença

[MIT](https://choosealicense.com/licenses/mit/)

O **Vikings** está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT), garantindo liberdade para modificar e reutilizar o código, desde que os créditos sejam preservados.  

Para mais detalhes, confira o arquivo [LICENSE](https://github.com/YuriEsteves0/Poker/blob/main/LICENSE).  


