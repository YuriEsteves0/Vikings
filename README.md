
# Vikings

**Vikings** é um jogo de estratégia em turnos executado via terminal, desenvolvido em Kotlin, no qual o jogador assume o controle de um reino em expansão. A progressão ocorre por meio da exploração de territórios interconectados, gerenciamento de tropas e tomada de decisões táticas baseadas em risco, sorte e informação parcial.
## Funcionalidades

### Exploração de Territórios
- Mapa composto por territórios interligados por direções cardeais (Norte, Sul, Leste e Oeste)
- Movimentação condicionada ao estado do território e à presença de inimigos
- Controle progressivo de territórios dominados pelo jogador
- Interface em terminal reorganizada para melhor legibilidade e fluxo de navegação

### Sistema de Análise de Território
- Análise baseada em rolagem de dado **D20**, com suporte a modificadores de vantagem
- Resultados classificados em falha crítica, falha, sucesso parcial, sucesso e sucesso crítico
- Informações apresentadas ao jogador variam conforme o nível de sucesso, incluindo incerteza intencional
- Registro de territórios analisados, afetando eventos futuros como emboscadas e encontros

### Emboscadas Dinâmicas
- Possibilidade de emboscadas ao entrar em territórios hostis
- Probabilidade influenciada diretamente pela análise prévia do território
- Aplicação de dano e efeitos de status nas tropas antes do início do combate
- Melhor balanceamento e correção de inconsistências no cálculo de dano inicial

### Sistema de Combate por Turnos
- Combate por turnos entre tropas aliadas e inimigos
- Ordem de ataque dinâmica e seleção aleatória de alvos
- Sistema de habilidades especiais para tropas e inimigos
- Aplicação de efeitos de status (como queimadura, invisibilidade e sono)
- Correções de bugs relacionados a turnos, estados inválidos e remoção de unidades derrotadas

### Gerenciamento de Tropas
- Tropas com papéis distintos (Guerreiro, Mago e Arqueiro)
- Atributos individuais de vida, ataque e habilidades especiais
- Sistema de bônus aplicados conforme progressão do jogador
- Perda definitiva de tropas ao atingir vida zero
- Exibição clara e padronizada do estado atual das tropas

### Inimigos e IA
- Diversos tipos de inimigos, cada um com comportamento e habilidades próprias
- Decisão de ações baseada em probabilidade entre ataque normal e habilidade especial
- Integração consistente entre IA inimiga e sistema de efeitos de status

### Estruturas de Território
- Territórios podem conter estruturas interativas
- Execução de ações estratégicas ao interagir com estruturas específicas
- Impacto direto nos recursos e no desempenho geral do reino

### Recursos do Reino
- Sistema de recursos incluindo ouro e comida
- Consumo e ganho de recursos vinculados a ações e eventos
- Exibição contínua e organizada dos recursos no terminal

### UI/UX no Terminal
- Reestruturação da interface de texto para melhorar clareza e imersão
- Separação visual de ações, mensagens de combate e informações do jogador
- Fluxo de interação mais intuitivo, reduzindo ambiguidade nas escolhas
- Padronização de mensagens e feedback ao jogador

### Estabilidade e Manutenção
- Correção de bugs relacionados a:
  - Cálculo de dano e efeitos de status
  - Estados inconsistentes de personagens
  - Fluxo de turnos em combate
- Melhor organização do código em camadas (Helper, Model, Estruturas)
- Base preparada para futuras expansões de conteúdo e sistemas


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


