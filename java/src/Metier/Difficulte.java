package src.Metier;

public enum Difficulte
{
    TRES_FACILE("Tres facile", 1),
    FACILE     ("Facile", 2),
    MOYEN      ("Moyen", 3),
    DIFFICILE  ("Difficile", 4);

    private final String nom;
    private final int    indice;

    // Constructeur
    /**
     * Contructeur de Difficulte
     * @param nom     Le nom de la difficulté
     * @param indice  L'indice de la difficulté
     */
    Difficulte(String nom, int indice)
    {
        this.nom    = nom;
        this.indice = indice;
    }

    //Getter
    public String getNom   () {return nom; }
    public int    getIndice() {return indice; }

    public static Difficulte getDifficulteByIndice(int indice)
    {
        for (Difficulte difficulte : Difficulte.values())
        {
            if (difficulte.getIndice() == indice)
            {
                return difficulte;
            }
        }
        return null;
    }
    public String toString(){return this.nom + " (" + this.indice + ")"; }
}