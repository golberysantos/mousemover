# Mouse Mover 🖱️

Um utilitário simples, leve e eficiente desenvolvido em Java para manter o computador sempre ativo. Ele simula o movimento físico do cursor na tela, impedindo que o sistema operacional bloqueie o usuário ou entre em modo de suspensão/hibernação por inatividade.

---

## 🚀 Como Funciona?

O programa utiliza recursos nativos do Java (`java.awt.Robot` e `javax.swing`) sem a necessidade de nenhuma biblioteca externa:

1. **Alerta de Inicialização:** Ao executar, uma mensagem amigável é exibida explicando o funcionamento e solicitando a confirmação do usuário.
2. **Janela de Controle Flutuante:** Uma pequena janela fica visível na tela mostrando o status e o tempo total de atividade.
3. **Movimento com Intervalo Inteligente:** O cursor faz um deslocamento suave de 5 cm no centro da tela e depois **aguarda 5 segundos de pausa** antes do próximo movimento. Isso evita que você precise "lutar" contra o mouse para usá-lo.
4. **Encerramento Fácil e Imediato:** Para parar o movimento a qualquer momento, você pode:
   - Pressionar a tecla **ESC** no teclado;
   - Clicar no botão vermelho **"⏹️ Parar Movimento (ESC)"**;
   - Ou simplesmente fechar a janela no botão **X**.

---

## ⚙️ Funcionalidades

- **Interface Gráfica Nativa:** Não requer console nem IDE para ser utilizado.
- **Alerta Prévio:** Pergunta e orienta o usuário antes de começar qualquer movimentação.
- **Janela Sempre Visível (*Always-on-Top*):** A janela de controle não se perde atrás de outras aplicações, facilitando a parada.
- **Movimento Suave:** Move o cursor pixel a pixel, simulando uma interação natural.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 8 ou superior
- **Bibliotecas Nativas:** `java.awt.Robot`, `java.awt.Toolkit` e `javax.swing.*`

---

## 📋 Pré-requisitos

- Java Runtime Environment (JRE) ou Java Development Kit (JDK) 8+ instalado.

---

## 🏃 Como Executar

### Via Arquivo Executável / IDE
Basta executar a classe `br.com.mousemover.principal.MouseMover` ou dar um duplo clique caso tenha gerado o executável `.jar`.

### Via Terminal
1. Compile a aplicação:
   ```bash
   javac -d bin src/br/com/mousemover/principal/MouseMover.java
   ```
2. Execute o programa:
   ```bash
   java -cp bin br.com.mousemover.principal.MouseMover
   ```

---

## ⚠️ Observações de Permissão
Em sistemas como macOS ou Linux, pode ser necessário conceder permissão de **Acessibilidade** ao Java para permitir o controle do ponteiro do mouse.
