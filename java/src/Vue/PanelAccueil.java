package src.Vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import src.Controleur;

public class PanelAccueil extends JPanel implements  ActionListener{
    private Controleur ctrl;
    private JPanel     panelAccueil;
    private JButton    btBanque;
    private JButton    btQuestionnaire;
    private JButton    btRessources;
    private JButton    btnCreationQuestion;

    // Constructeur

    /**
     * Constructeur de la class PanelAccueil
     * @param ctrl  Le contrôleur
     */
    public PanelAccueil( Controleur ctrl ){
        this.ctrl         = ctrl;
        this.panelAccueil = new JPanel(new GridLayout(4,1));
        this.setLayout ( new BorderLayout() );

        this.setVisible(true);

        this.btQuestionnaire = new JButton("Génération d'un Questionnaire");
        this.btRessources    = new JButton("Paramètre"              );
        this.btBanque        = new JButton("Banque de question"      );
        this.btnCreationQuestion = new JButton("Création de Question");


        this.panelAccueil.add(this.btQuestionnaire, BorderLayout.CENTER );
        this.panelAccueil.add(this.btRessources   , BorderLayout.SOUTH  );
        this.panelAccueil.add(this.btBanque       , BorderLayout.NORTH  );
        this.panelAccueil.add(this.btnCreationQuestion, BorderLayout.AFTER_LAST_LINE);
        this.add(panelAccueil);

        this.btBanque       .addActionListener(this) ;
        this.btQuestionnaire.addActionListener(this) ;
        this.btRessources   .addActionListener(this) ;
        this.btnCreationQuestion.addActionListener(this);
    }

    // Methode
    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    public void actionPerformed(ActionEvent e){
        if ( btBanque == e.getSource())
            FrameBanque.creerFrameBanque(this.ctrl);
        if( btQuestionnaire == e.getSource())
            FrameQuestionnaire.creerFrameQuestionnaire(this.ctrl);
        if(btRessources == e.getSource())
            FrameParametrage.creerFrameFrameParametrage(this.ctrl);
        if(btnCreationQuestion == e.getSource())
            new FrameCreationQuestion(this.ctrl);
    }
}