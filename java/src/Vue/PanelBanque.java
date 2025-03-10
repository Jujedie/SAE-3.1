package src.Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import java.util.Iterator;
import java.util.ArrayList;

import src.Metier.Question;
import src.Metier.QCM;
import src.Metier.EliminationReponse;
import src.Metier.Notion;
import src.Controleur;
import src.Metier.Ressource;

public class PanelBanque extends JPanel implements  ActionListener, ItemListener 
{
	private static final int MAX_VISIBLE_ROWS = 5;

	private boolean ignorerEvents = false;

	private JButton              btnCreaQuest;
	private JButton              btnSupp;
	private JButton              btnModif;
	private JPanel               panelBanque;
	private JTable               tbQuestion;
	private JComboBox<Notion>    ddlstNotions;
	private JComboBox<Ressource> ddlstRessources;

    private Controleur           ctrl;
	private ArrayList<Question>  listQ;
	private Notion               notion;

	/**
	 * Constructeur de la class PanelBanque
	 * @param ctrl	Le contrôleur
	 */
    public PanelBanque(Controleur ctrl)
	{
        this.ctrl         = ctrl;
        this.panelBanque  = new JPanel();
		this.setLayout (new BorderLayout());
		this.setVisible(true);

        String[] tabEntetes = {"Enoncé", "Difficulté", "Ressource", "Notion", "Points", "Type de question"};

		this.listQ = new ArrayList<>(this.ctrl.getQuestionsParNotion(this.ctrl.getNotionsParRessource(this.ctrl.getRessources().get(0)).get(0)));
        String[][] data = new String[this.listQ.size()][6];

		for(int i = 0; i < this.listQ.size();i++)
		{
			String typeQuestion = "";

			if(this.listQ.get(i) instanceof QCM)
			{
				if(((QCM) this.listQ.get(i)).estVraiouFaux()) {typeQuestion = "Réponse unique";}
				else {typeQuestion = "QCM";}
			}
			else 
			{
				if(this.listQ.get(i) instanceof EliminationReponse){typeQuestion = "Elimination Réponse";} 
				else {typeQuestion = "Association d'éléments";}
			}
			
			data[i][0] = this.listQ.get(i).getEnonce();
			data[i][1] = this.listQ.get(i).getDifficulte().getNom();
			data[i][2] = this.listQ.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][3] = this.listQ.get(i).getNotion().getNom();
			data[i][4] = "" + this.listQ.get(i).getPoint();
			data[i][5] = typeQuestion;
		}
        DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
        this.tbQuestion = new JTable(model);

		this.ddlstRessources  = new JComboBox<>(ctrl.getRessources().toArray(new Ressource[0]));
		this.ddlstRessources.setSelectedItem(ctrl.getRessources().getFirst());

		this.ddlstNotions = new JComboBox<>(ctrl.getNotionsParRessource(ctrl.getRessources().getFirst()).toArray(new Notion[0]));
		this.ddlstNotions.setSelectedItem(ctrl.getNotionsParRessource(ctrl.getRessources().getFirst()).getFirst());

		JPanel panelParametre = new JPanel();

        // Nombre maximal de lignes sans scroll
        int maxVisibleRows = 5;

        // Calcul dynamique de la hauteur
        int rowHeight     = this.tbQuestion.getRowHeight();
        int headerHeight  = this.tbQuestion.getTableHeader().getHeight();
        int visibleHeight = rowHeight * Math.min(this.tbQuestion.getRowCount(),  PanelBanque.MAX_VISIBLE_ROWS) + headerHeight;

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, visibleHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btnSupp           = new JButton("Supprimer Question");
		this.btnCreaQuest      = new JButton("Nouvelle Question");
		this.btnModif          = new JButton("Modifier Question");

		this.btnSupp.setBackground		(new Color(163,206,250));
		this.btnSupp.setFont			(new Font("Arial", Font.PLAIN, 16));
		this.btnCreaQuest.setBackground (new Color(163,206,250));
		this.btnCreaQuest.setFont	    (new Font("Arial", Font.PLAIN, 16));
		this.btnModif.setBackground		(new Color(163,206,250));
		this.btnModif.setFont			(new Font("Arial", Font.PLAIN, 16));


		panelParametre.add(this.ddlstRessources);
		panelParametre.add(this.ddlstNotions);

		this.panelBanque.add(panelParametre   , BorderLayout.NORTH );
		this.panelBanque.add(scrollPane       , BorderLayout.CENTER);
		this.panelBanque.add(this.btnSupp     , BorderLayout.SOUTH );
		this.panelBanque.add(this.btnCreaQuest, BorderLayout.SOUTH );
		this.panelBanque.add(this.btnModif    , BorderLayout.SOUTH );

        this.add(panelBanque);

		this.btnSupp     .addActionListener(this);
		this.btnCreaQuest.addActionListener(this);
		this.btnModif    .addActionListener(this);

		this.ddlstRessources.addItemListener(this);
		this.ddlstNotions   .addItemListener(this);
    }


	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
	public void actionPerformed(ActionEvent e)
	{
        if ( this.btnCreaQuest == e.getSource()) 
		{
			if (((Ressource) (this.ddlstRessources.getSelectedItem())) != null )
			{
				FrameCreationQuestion.creerFrameCreationQuestion(
						this.ctrl,
						this,
						(Ressource) (this.ddlstRessources.getSelectedItem()),
						(Notion) (this.ddlstNotions.getSelectedItem())
				);
			} 
			else {FrameCreationQuestion.creerFrameCreationQuestion(this.ctrl, this, null, null);}
		}
		if (this.btnSupp == e.getSource()) 
		{
			int row = this.tbQuestion.getSelectedRow();
			Iterator<Question> iterator = this.ctrl.getQuestions().iterator();
			while (iterator.hasNext()) 
			{
				Question q = iterator.next();
				if (this.listQ.get(row) == q) 
				{
					iterator.remove();
					this.ctrl.supprimerQuestion(q);
					this.maj();
					break;
				}
			}
		}

		if(this.btnModif == e.getSource())
		{
			int row = this.tbQuestion.getSelectedRow();
			if(row == -1) {return;}
			for(int i = 0; i < this.ctrl.getQuestions().size();i++)
			{
				if( this.listQ.get(row) == this.listQ.get(i)) {
					FrameModifQuestion.creerFrameModifQuestion(this.ctrl, this, this.listQ.get(i));
					return;
				}
			}
		}
	}

	public void maj()
	{
		String[] tabEntetes = {"Enoncé", "Difficulté", "Ressource", "Notion", "Points", "Type de question"};

		if (this.notion != null) {this.listQ = this.ctrl.getQuestionsParNotion(notion);}
		else {this.listQ = new ArrayList<>();}

		String[][] data = new String[this.listQ.size()][6];

		for(int i = 0; i < this.listQ.size();i++)
		{
			String typeQuestion = "";

			if(this.listQ.get(i) instanceof QCM)
			{
				if(((QCM) this.listQ.get(i)).estVraiouFaux())
				{
					typeQuestion = "Réponse unique";
				}
				else
				{
					typeQuestion = "QCM";
				}
			} 
			else {
				if(this.listQ.get(i) instanceof EliminationReponse){typeQuestion = "Elimination Réponse";} 
				else {typeQuestion = "Association d'éléments";}
			}

			data[i][0] =      this.listQ.get(i).getEnonce();
			data[i][1] = 	  this.listQ.get(i).getDifficulte().getNom();
			data[i][2] =      this.listQ.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][3] =      this.listQ.get(i).getNotion().getNom();
			data[i][4] = "" + this.listQ.get(i).getPoint();
			data[i][5] = typeQuestion;
		}


		DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
		this.tbQuestion.setModel(model);

		// Calcul dynamique de la hauteur
		int rowHeight     = this.tbQuestion.getRowHeight();
		int headerHeight  = this.tbQuestion.getTableHeader().getHeight();
		int visibleHeight = rowHeight * PanelBanque.MAX_VISIBLE_ROWS + headerHeight;

		// Ajuster la taille visible de la table
		this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, visibleHeight));
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (this.ignorerEvents) {return;}

		this.ignorerEvents = true;

		if (e.getSource() == this.ddlstRessources)
		{
			Ressource ressource = (Ressource) this.ddlstRessources.getSelectedItem();
			this.notion = (Notion) this.ddlstNotions.getSelectedItem();

			this.ddlstRessources.setModel(
					new DefaultComboBoxModel<>(
							ctrl.getRessources().toArray(new Ressource[0])
					)
			);
			this.ddlstNotions   .setModel(
					new DefaultComboBoxModel<>(
							ctrl.getNotionsParRessource(ressource).toArray(new Notion[0])
					)
			);

			this.ddlstRessources.setSelectedItem(ressource);
			if(this.notion != null && ressource != null &&this.notion.getRessourceAssociee().equals(ressource)){
				this.ddlstNotions.setSelectedItem(this.notion);
			}
			else {this.notion	= null;}
		} else if (e.getSource() == this.ddlstNotions){
				this.notion = (Notion) this.ddlstNotions.getSelectedItem();
		}

		this.ignorerEvents = false;
		this.maj();
	}
}
