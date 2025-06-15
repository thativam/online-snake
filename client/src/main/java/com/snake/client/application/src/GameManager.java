// Em GameManager.java
package com.snake.client.application.src;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

// Importe as classes necessárias
import com.snake.client.domain.aplication.Score;

public class GameManager {

    private JFrame mainFrame;
    private JPanel mainPanelContainer;
    private CardLayout cardLayout;

    // Adicione uma referência para o painel de gameplay
    private Gameplay gameplayPanel;

    public GameManager() {
        mainFrame = new JFrame("Snake Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(910, 750);

        cardLayout = new CardLayout();
        mainPanelContainer = new JPanel(cardLayout);

        // --- Início da Lógica do Jogo ---
        // Crie os componentes do jogo aqui para ter acesso a eles
        Score score = new Score();
        this.gameplayPanel = new Gameplay(score); // Armazene a referência
        InfoPanel infoPanel = new InfoPanel(score);
        gameplayPanel.subscribe(infoPanel);
        // --- Fim da Lógica do Jogo ---

        // Criar o painel do menu
        JPanel menuScreen = MainMenu.createMainMenuPanel(() -> showGameScreen());

        // Criar o painel do jogo usando os componentes já criados
        JPanel gameScreen = SnakeGame.createGamePanel(gameplayPanel, infoPanel);

        // Adicionar as "telas" ao container
        mainPanelContainer.add(menuScreen, "MENU");
        mainPanelContainer.add(gameScreen, "GAME");

        mainFrame.add(mainPanelContainer);

        showMenuScreen();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void showMenuScreen() {
        cardLayout.show(mainPanelContainer, "MENU");
    }

    public void showGameScreen() {
        cardLayout.show(mainPanelContainer, "GAME");

        // ****** A CORREÇÃO PRINCIPAL ESTÁ AQUI ******
        // Solicita que o painel do jogo ganhe foco para receber eventos de teclado.
        gameplayPanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        new GameManager();
    }
}