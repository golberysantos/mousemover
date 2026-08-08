package br.com.mousemover.principal;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

public class MouseMover {
    
    private static volatile boolean executando = true;
    private static long tempoInicial;
    
    public static void main(String[] args) {
        // Usar o visual nativo do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> {
            // 1. Alerta explicativo inicial antes de começar
            int opcao = JOptionPane.showConfirmDialog(
                null,
                "O Mouse Mover manterá seu computador ativo movendo o cursor suavemente.\n\n"
                + "• Uma pequena janela de controle permanecerá aberta na sua tela.\n"
                + "• Para encerrar a qualquer momento, pressione a tecla ESC ou clique em 'Parar'.\n\n"
                + "Deseja iniciar o movimento agora?",
                "Mouse Mover - Iniciar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE
            );
            
            if (opcao != JOptionPane.OK_OPTION) {
                System.exit(0);
                return;
            }
            
            // 2. Criar e exibir a janela de controle
            criarJanelaControle();
            
            // 3. Iniciar o movimento do mouse em uma Thread separada
            tempoInicial = System.currentTimeMillis();
            iniciarThreadMovimento();
        });
    }
    
    private static void criarJanelaControle() {
        JFrame frame = new JFrame("Mouse Mover");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(340, 180);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true); // Fica visível para o usuário achar facilmente
        frame.setLocationRelativeTo(null); // Centraliza na tela
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 247, 250));
        
        // Status e Tempo
        JLabel lblStatus = new JLabel("🟢 Mouse Mover Ativo", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblStatus.setForeground(new Color(34, 139, 34));
        
        JLabel lblTempo = new JLabel("Tempo ativo: 0s", SwingConstants.CENTER);
        lblTempo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTempo.setForeground(new Color(70, 80, 95));
        
        JPanel panelInfo = new JPanel(new BorderLayout(5, 5));
        panelInfo.setOpaque(false);
        panelInfo.add(lblStatus, BorderLayout.NORTH);
        panelInfo.add(lblTempo, BorderLayout.SOUTH);
        
        // Botão Parar
        JButton btnParar = new JButton("⏹️ Parar Movimento (ESC)");
        btnParar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnParar.setBackground(new Color(220, 53, 69));
        btnParar.setForeground(Color.WHITE);
        btnParar.setFocusPainted(false);
        btnParar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnParar.setPreferredSize(new Dimension(0, 42));
        
        Runnable pararAcao = () -> {
            executando = false;
            frame.dispose();
            JOptionPane.showMessageDialog(
                null,
                "Movimento do mouse finalizado com sucesso!",
                "Mouse Mover",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0);
        };
        
        btnParar.addActionListener(e -> pararAcao.run());
        
        // Configurar tecla ESC para fechar na hora
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "fecharComEsc");
        frame.getRootPane().getActionMap().put("fecharComEsc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pararAcao.run();
            }
        });
        
        // Evento de fechar no 'X' da janela
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                pararAcao.run();
            }
        });
        
        panel.add(panelInfo, BorderLayout.CENTER);
        panel.add(btnParar, BorderLayout.SOUTH);
        
        frame.setContentPane(panel);
        frame.setVisible(true);
        
        // Timer de interface para atualizar os segundos na janela a cada segundo
        Timer timerUI = new Timer(1000, e -> {
            if (executando) {
                long segundos = (System.currentTimeMillis() - tempoInicial) / 1000;
                lblTempo.setText("Tempo ativo: " + segundos + "s");
            }
        });
        timerUI.start();
    }
    
    private static void iniciarThreadMovimento() {
        Thread moverThread = new Thread(() -> {
            try {
                Robot robot = new Robot();
                
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
                
                while (executando) {
                    // 1. Movimento suave para a direita
                    for (int x = leftX; x <= rightX && executando; x++) {
                        robot.mouseMove(x, centerY);
                        robot.delay(3);
                    }
                    
                    // 2. Movimento suave para a esquerda
                    for (int x = rightX; x >= leftX && executando; x--) {
                        robot.mouseMove(x, centerY);
                        robot.delay(3);
                    }
                    
                    // 3. Pausa de 5 segundos entre os ciclos (em fatias de 100ms para responder rápido ao ESC)
                    for (int i = 0; i < 50 && executando; i++) {
                        Thread.sleep(100);
                    }
                }
            } catch (AWTException | InterruptedException e) {
                if (e instanceof AWTException) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(
                            null,
                            "Erro ao controlar o mouse: " + e.getMessage() + "\nVerifique as permissões de acessibilidade do Java.",
                            "Erro - Mouse Mover",
                            JOptionPane.ERROR_MESSAGE
                        );
                        System.exit(1);
                    });
                }
            }
        });
        
        moverThread.setDaemon(true);
        moverThread.start();
    }
}