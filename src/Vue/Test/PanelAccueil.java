

//import src.Controleur;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelAccueil extends JPanel implements  ActionListener
{
    //private Controleur ctrl;
    private JPanel panelAccueil;
    private JButton btBanque;
    private JButton btQuestionnaire;
    private JButton btRessources;

    public PanelAccueil( ){
        //this.ctrl         = ctrl;
        this.panelAccueil = new JPanel();
        this.setLayout ( new BorderLayout() );

        this.setVisible(true);

        this.btBanque        = new JButton("Banque de question"    );
        this.btQuestionnaire = new JButton("Création Questionnaire");
        this.btRessources    = new JButton("Ressources"            );

        this.panelAccueil.add(this.btBanque, BorderLayout.NORTH          );
        this.panelAccueil.add(this.btQuestionnaire, BorderLayout.CENTER );
        this.panelAccueil.add(this.btRessources, BorderLayout.SOUTH     );

        this.add(panelAccueil);

        this.btBanque.addActionListener(this)        ;
        this.btQuestionnaire.addActionListener(this) ;
        this.btRessources.addActionListener(this)    ;
    }

    public void actionPerformed(ActionEvent e){
        if ( btBanque == e.getSource()){
            System.out.println("Hey la frame Banque s'ouvre");
            new FrameBanque();
        }
        if( btQuestionnaire == e.getSource()){
            System.out.println("Hey la frame Questionaire s'ouvre");
            //new FrameQuestionnaire(/*ctrl*/);
        }
        if(btRessources == e.getSource()){
            System.out.println("Hey la frame Param s'ouvre");
            new FrameRessource();
        }
    }
}