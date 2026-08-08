# Mouse Mover 🖱️

Um utilitário simples e eficiente desenvolvido em Java para manter o computador ativo. Ele simula o movimento físico do mouse, impedindo que o sistema operacional bloqueie a tela ou entre em modo de suspensão/hibernação devido à inatividade.

---

## 🚀 Como Funciona?

O programa utiliza a classe `java.awt.Robot` para interagir diretamente com a interface do sistema. 

1. Você define o **tempo de execução** (em segundos).
2. O cursor do mouse começa a se mover suavemente para a esquerda e para a direita (cerca de 5 cm de distância) no centro da tela.
3. O terminal exibe em tempo real o progresso (ciclos concluídos) e o tempo restante.
4. O programa encerra automaticamente ao fim do tempo ou caso você decida interrompê-lo.

---

## ⚙️ Funcionalidades

- **Movimento Suave:** Diferente de outros scripts que apenas "teletransportam" o ponteiro, este utilitário move o cursor pixel por pixel, simulando um movimento natural.
- **Interrupção Fácil e Segura:** Pare o movimento instantaneamente a qualquer momento pressionando:
  - `ESC` no seu teclado.
  - `ENTER` no terminal.
  - `Ctrl + F2` (se estiver executando no Eclipse).
- **Feedback em Tempo Real:** Mostra quantos ciclos foram completados e quanto tempo resta para terminar.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 8 ou superior
- **Bibliotecas Nativas:** `java.awt.Robot` e `java.awt.Toolkit` (para obter as dimensões da tela e controlar o mouse)

---

## 📋 Pré-requisitos

Para executar este projeto, você precisa ter instalado no seu computador:
- Java Development Kit (JDK) instalado e configurado nas variáveis de ambiente.

---

## 🏃 Como Executar

### Via IDE (Eclipse, VS Code, IntelliJ, etc.)
1. Importe o projeto para a sua IDE favorita.
2. Execute a classe `br.com.mousemover.principal.MouseMover`.
3. Insira o tempo de execução desejado no console e acompanhe.

### Via Terminal
1. Navegue até o diretório raiz do projeto.
2. Compile a classe:
   ```bash
   javac -d bin src/br/com/mousemover/principal/MouseMover.java
   ```
3. Execute o programa:
   ```bash
   java -cp bin br.com.mousemover.principal.MouseMover
   ```

---

## ⚠️ Observações de Permissão
Em alguns sistemas operacionais (como macOS ou Linux), pode ser necessário dar permissões de acessibilidade ao Java ou ao Terminal para que o controle do cursor funcione corretamente. Se encontrar problemas, verifique as configurações de segurança do seu sistema.
