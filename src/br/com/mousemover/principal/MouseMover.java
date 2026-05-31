package br.com.mousemover.principal;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class MouseMover {
    
    private static volatile boolean executando = true;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Digite o tempo de execução (em segundos): ");
        int tempoSegundos = scanner.nextInt();
        
        // Configurar captura da tecla ESC
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent e) {
                    if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        executando = false;
                        System.out.println("\n⚠️ Tecla ESC pressionada - Parando movimento...");
                        return true;
                    }
                    return false;
                }
            });
        
        // Obter o tamanho da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        
        // Calcular 5 centímetros em pixels (assumindo 96 DPI)
        int deslocamentoPixels = (int) (5.0 / 2.54 * 96);
        
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        int leftX = centerX - (deslocamentoPixels / 2);
        int rightX = centerX + (deslocamentoPixels / 2);
        
        System.out.println("=====================================");
        System.out.println("INICIANDO MOVIMENTO DO MOUSE");
        System.out.println("=====================================");
        System.out.println("Formas de parar:");
        System.out.println("  • Pressione ESC - para parar imediatamente");
        System.out.println("  • Pressione ENTER - para parar imediatamente");
        System.out.println("  • Ctrl+F2 no Eclipse - para parar");
        System.out.println("  • Aguardar " + tempoSegundos + " segundos");
        System.out.println("=====================================");
        System.out.println("Movimento: 5cm no centro da tela");
        System.out.println("Posições: X=[" + leftX + " até " + rightX + "], Y=" + centerY);
        System.out.println("=====================================\n");
        
        // Thread para parar com ENTER
        Thread enterThread = new Thread(() -> {
            try {
                scanner.nextLine(); // Limpa o buffer
                scanner.nextLine(); // Aguarda o ENTER
                executando = false;
                System.out.println("\n⚠️ Tecla ENTER pressionada - Parando movimento...");
            } catch (Exception e) {
                // Ignora erros
            }
        });
        enterThread.setDaemon(true);
        enterThread.start();
        
        try {
            Robot robot = new Robot();
            long tempoInicial = System.currentTimeMillis();
            long tempoFinal = tempoInicial + (tempoSegundos * 1000L);
            
            boolean indoParaDireita = true;
            int ciclo = 1;
            
            while (executando && System.currentTimeMillis() < tempoFinal) {
                if (indoParaDireita) {
                    // Move da esquerda para direita
                    for (int x = leftX; x <= rightX && executando; x++) {
                        if (System.currentTimeMillis() >= tempoFinal) break;
                        robot.mouseMove(x, centerY);
                        robot.delay(3);
                    }
                    indoParaDireita = false;
                } else {
                    // Move da direita para esquerda
                    for (int x = rightX; x >= leftX && executando; x--) {
                        if (System.currentTimeMillis() >= tempoFinal) break;
                        robot.mouseMove(x, centerY);
                        robot.delay(3);
                    }
                    indoParaDireita = true;
                    
                    // Mostrar progresso a cada ciclo completo
                    if (executando && System.currentTimeMillis() < tempoFinal) {
                        long segundosRestantes = (tempoFinal - System.currentTimeMillis()) / 1000;
                        System.out.print("\rCiclo " + ciclo++ + " completo | Tempo restante: " + segundosRestantes + "s");
                    }
                }
            }
            
            System.out.println("\n\n=====================================");
            if (!executando) {
                System.out.println("MOVIMENTO INTERROMPIDO PELO USUÁRIO!");
            } else {
                System.out.println("MOVIMENTO CONCLUÍDO COM SUCESSO!");
            }
            System.out.println("=====================================");
            
        } catch (AWTException e) {
            System.err.println("Erro ao criar Robot: " + e.getMessage());
            System.err.println("Verifique se o Java tem permissão para controlar o mouse.");
        }
        
        scanner.close();
    }
}