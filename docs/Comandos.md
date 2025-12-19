## Central de Ajuda

Esta seção tem como objetivo explicar, de forma detalhada, as principais mecânicas do jogo **Vikings**, servindo como referência durante a jogabilidade. Aqui são descritos os sistemas centrais, cálculos aproximados de dano, efeitos de status, funcionamento das tropas e exemplos de magias disponíveis no grimório.

---

## Instalação e Execução (Windows)

Para garantir a exibição correta de acentuação e caracteres especiais no terminal, siga os passos abaixo:

1. Baixe o arquivo `.zip` do jogo.
2. Extraia o conteúdo para uma pasta de sua preferência.
3. Abra o **Prompt de Comando (CMD)**.
4. No CMD, execute o comando abaixo para habilitar UTF-8:

   
    ```
    chcp 65001
    ```
    
5. Navegue até a pasta onde o jogo foi extraído utilizando o comando cd:
     ```
     cd C:\caminho\para\a\pasta\do\jogo
     ```
     `ATENÇÃO: VOCÊ DEVERÁ TROCAR PELO CAMINHO ONDE ESTÁ SALVO O SEU JOGO`

6. Execute o jogo digitando:
     ```
      Vikings.exe
     ```
---

### Como se movimentar pelo mapa
O jogador pode se deslocar entre territórios utilizando a ação **Andar**. A movimentação só é permitida quando **todos os inimigos do território atual foram eliminados**.

- Os territórios disponíveis para deslocamento são definidos pelas direções cardeais (Norte, Sul, Leste e Oeste).
- Apenas direções válidas são exibidas no mapa em ASCII.
- Ao entrar em um novo território, eventos como **emboscadas**, **combates** ou **acesso a estruturas** podem ocorrer.

---

### O que é a Análise de Território
A ação **Analisar Território** permite obter informações antecipadas sobre um território antes de se mover para ele.

- A análise utiliza uma rolagem de **D20**, podendo sofrer modificadores de vantagem.
- O resultado da rolagem determina o **nível de precisão** das informações obtidas.

#### Resultados possíveis da análise
- **Falha Crítica (1)**  
  Informações totalmente incorretas ou enganosas.
- **Falha (2–9)**  
  Informações vagas, com alta margem de erro.
- **OK (10–14)**  
  Informações parcialmente confiáveis.
- **Sucesso (15–19)**  
  Informações majoritariamente corretas.
- **Sucesso Crítico (20 ou mais)**  
  Informações totalmente precisas.

O resultado da análise influencia diretamente:
- A chance de sofrer emboscadas
- A previsibilidade do número e tipo de inimigos

---

### Como funcionam as emboscadas
Emboscadas podem ocorrer ao entrar em territórios hostis.

- A chance de emboscada é maior quando o território **não foi analisado** ou quando a análise resultou em falha.
- Caso ocorra, os inimigos causam **dano direto e aleatório** às tropas do jogador antes do combate começar.
- O dano pode aplicar **efeitos de status**, dependendo do tipo de inimigo.

---

### Sistema de combate
O combate ocorre em **turnos alternados** entre tropas aliadas e inimigos.

- A ordem de ataque é definida dinamicamente.
- Cada unidade ataca um alvo aleatório do lado oposto.
- O dano base é calculado a partir do atributo **Ataque**, podendo receber bônus ou modificadores.

#### Cálculo de dano (simplificado)
- **Ataque Normal**:  
  `Ataque da unidade + bônus do jogador`
- **Habilidades Especiais**:  
  Podem causar dano adicional, múltiplos ataques ou aplicar efeitos de status.
- Unidades com vida igual ou inferior a zero são removidas imediatamente do combate.

---

### Tropas e seus papéis
O jogador inicia com três tipos de tropas, cada uma com funções distintas:

#### Guerreiro
- Alta vida e bom ataque
- Focado em dano consistente
- Habilidade especial aumenta o dano do ataque

#### Arqueiro
- Ataque médio e vida moderada
- Pode errar ataques
- Possui chance de aplicar **QUEIMANDO** nos inimigos

#### Mago
- Baixa vida e ataque base reduzido
- Utiliza magias do grimório
- Capaz de causar dano mágico, curar tropas ou aplicar efeitos especiais

---

### Inimigos e comportamento
Cada inimigo possui padrões próprios de ataque.

- A cada turno, o inimigo decide entre ataque normal ou habilidade especial.
- Alguns inimigos sacrificam vida para causar mais dano.
- Outros podem aplicar efeitos de status ou atacar mais de uma vez.

---

### Efeitos de Status
Efeitos de status alteram temporariamente o comportamento das unidades.

- **QUEIMANDO**  
  Causa 1 ponto de dano por turno.
- **DORMINDO**  
  A unidade não pode agir enquanto durar o efeito.
- **INVISÍVEL**  
  O próximo ataque causa dano dobrado e remove o efeito.
- **NADA**  
  Estado normal, sem efeitos ativos.

---

### Magias do Grimório (exemplos)
Algumas magias disponíveis para o Mago incluem:

- **Bola de Fogo**  
  Causa dano mágico ao inimigo, com chance de aplicar **QUEIMANDO**.
- **Cura Simples**  
  Restaura vida de uma tropa aliada.
- **Invisibilidade**  
  Concede o status **INVISÍVEL**, permitindo um ataque furtivo com dano dobrado.

As magias disponíveis podem variar conforme progressão e decisões do jogador.

---

### Estruturas do território
Alguns territórios possuem estruturas acessíveis pela ação **Entrar**.

- Estruturas podem:
  - Conceder bônus permanentes ou temporários
  - Aumentar recursos
  - Melhorar atributos das tropas
- As opções variam de acordo com o território dominado.

---

### Recursos do reino
O reino é gerenciado por dois recursos principais:

- **Ouro**  
  Utilizado para melhorias e interações estratégicas.
- **Comida**  
  Necessária para manter o exército operacional.

A má gestão de recursos pode comprometer a progressão do jogo.

---

### Progresso e objetivo do jogo
O objetivo principal é **dominar todos os territórios do mapa**.

- O progresso é exibido na seção de informações do reino.
- Cada território conquistado reduz riscos futuros e amplia as opções estratégicas.

---

### Dicas para novos jogadores
- Sempre analise territórios antes de se mover.
- Evite entrar em territórios hostis com tropas enfraquecidas.
- Utilize magias e efeitos de status de forma estratégica.
- Planeje rotas para minimizar emboscadas consecutivas.

---

### Encerramento do jogo
O jogo termina automaticamente quando o jogador perde todas as suas tropas.  
Também é possível encerrar a partida manualmente utilizando a opção **Sair** no menu de ações.
