package Question;

import Metier.Metier;
import Metier.Notion;
import Metier.Ressource;
import Metier.Difficulte;

public abstract class Question
{
    private Metier     metier;
    private String     intitule;
    private Difficulte difficulte;
    private Notion     notion;
    private int        temps;
    private int        point;
    private String     explication;

    public Question(String intitule, Difficulte difficulte,Notion notion,int temps, int point, Metier metier)
    {
        this.intitule   = intitule;
        this.difficulte = difficulte;
        this.notion     = notion;
        this.temps      = temps;
        this.point      = point;
        this.metier     = metier;
    }

    /**
     * Constructeur de la classe Question.
     *
     * @param intitule      L'intitulé de la question.
     * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param ressource     La ressource concernée par la question.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
     * @param point         Le nombre de points que rapporte la question.
     * @param explication   Les explications fournies avec la réponse à la question.
     */

    public Question(String intitule, Difficulte difficulte,Ressource ressource,Notion notion,int temps, int point , Metier metier,String explication)
    {
        this.intitule    = intitule;
        this.difficulte  = difficulte;
        this.notion      = notion;
        this.temps       = temps;
        this.metier      = metier;
        this.explication = explication;
        this.point       = point;
    }

    // Get
    public String     getIntitule()    {return intitule;}
    public Difficulte getDifficulte()  {return this.difficulte;}
    public Notion     getNotion()      {return this.notion;}
    public int        getTemps()       {return this.temps;}
    public int        getPoint()       {return this.point;}
    public String     getExplication() {return this.explication;}

    public String     getAsData(){
        return (this.getClass().getName() + ";" + this.ressource.getId() + ";" + this.notion.getId() + ";" + this.intitule + ";" + this.difficulte.getIndice() + ";" + this.temps + ";" + this.point);
    }
    public static Question getAsInstance(String ligne, Metier metier){
        String[] data = ligne.substring(ligne.indexOf​(";")+1,ligne.length()+1).split(";");
        Notion n = metier.getNotionById(Integer.parseInt(data[1]));
    }

    // Set
    public void setIntitule(String intitule)          {this.intitule = intitule;}
    private void setDifficulte(Difficulte difficulte) {this.difficulte = difficulte;}
    private void setNotion(Notion notion)             {this.notion = notion;}
    private void setTemps(int temps)                  {this.temps = temps;}
    private void setPoint(int point)                  {this.point = point;}
    private void setExplication(String explication)   {this.explication = explication;}
}