import java.util.*;

public class MineSweeper
{

  /*
    // si les cases sont fermé
    -1 = bombs
    0 = pas de autour de la case.
    1 - 8 = entre 1 et 8 cases.
    9 = bombs ouvert
    10 - 18 = case de 0 à 8 ouvert
    18 = case de bombs avec un drapeau
    19 - 26 = case de 0 à 8 avec un drapeau.
  */

  private final int[][] cases;
  private final int x, y;

  /*
    0 = Plus de case et donc on a gagné
    -1 = On est tombé sur une bombe et donc on a perdu.
  */

  private int casesLibres;

  private MineSweeper(int x, int y, int bombs)
  {
    this.x = x;
    this.y = y;

    cases = new int[x][y];
    generate(bombs < 10 ? 10 : bombs > 90 ? 90 : bombs, new Random());
  }

  private void generate(int bombs, Random random)
  {
    bombs = x * y * bombs / 100;
    casesLibres = x * y - bombs;

    while(bombs != 0)
    {
      int randomx = random.nextInt(x);
      int randomy = random.nextInt(y);

      if(isBomb(randomx, randomy)) continue;
      cases[randomx][randomy] = -1;

      for(int x = -1; x < 2; x++)
      {
        for(int y = -1; y < 2; y++)
        {
          if(isValid(randomx+x, randomy+y) && !isBomb(randomx+x, randomy+y))
            cases[randomx+x][randomy+y]++;
        }
      }
      bombs--;
    }
  }

  private boolean isBomb(int x, int y)
  {
    return isValid(x, y) && (cases[x][y] == -1 || cases[x][y] == 9 || cases[x][y] == 19);
  }

  private boolean isValid(int x, int y)
  {
    return x >= 0 && y >=0 && x < this.x && y < this.y;
  }

  private boolean hasFlag(int x,int y)
  {
    return isValid(x, y) && cases[x][y] > 18;
  }

  public boolean isOpen(int x, int y)
  {
    return isValid(x, y) && cases[x][y] > 8 && cases[x][y] < 19;
  }

  private void print()
  {
    StringBuilder builder = new StringBuilder();

    for(int y = -1; y < this.y; y++)
    {
      builder.append("\t[").append(y == -1 ? " " : y+1).append("]");
      for(int x = 0; x < this.x ; x++)
      {
        builder.append("[").append(y==-1 ? x+1
          : hasFlag(x, y) ? "P"
          : isOpen(x, y) ? isBomb(x, y) ? "+"
            : cases[x][y] == 10 ? " "
            : cases[x][y] - 10
          : "-").append("]");
      }
      builder.append("\n");
    }
    System.out.println(builder.toString());
  }

  private void setFlag(int x, int y)
  {
    if(!isValid(x, y) || isOpen(x, y)) return;
    cases[x][y] += hasFlag(x, y) ? -20 : 20;
  }

  private void open(int x, int y)
  {
    if(!hasFlag(x, y) && isBomb(x, y))
    {
      casesLibres = -1;
      for(int xx = 0; xx < this.x; xx++)
      {
        for(int yy = 0; yy < this.y; yy++)
        {
          if(isBomb(xx, yy)) cases[xx][yy] = 9;
        }
      }
    }
    else clear(x, y);
  }

  private void clear(int x, int y)
  {
    if(!isValid(x, y) || isOpen(x, y) || hasFlag(x, y)) return;
    cases[x][y] += 10;
    casesLibres--;
    if(cases[x][y] > 10) return;
    clear(x-1, y);
    clear(x+1, y);
    clear(x, y-1);
    clear(x, y+1);
  }

  private void play() throws MissingFormatArgumentException
  {
    Scanner scanner = new Scanner(System.in);
    regle();

    while(casesLibres > 0)
    {
      // boucle de jeu
      print();
      System.out.print("Veuillez entrer vos coordonnees x (n°colonne) et y (n°ligne) en seprarant chaque argument par un espace)");
      String[] line = scanner.nextLine().toUpperCase().split(" ");
     // System.out.println(line[2]);
     
     

      try{
        int x = Integer.parseInt(line[0]) - 1;
        int y = Integer.parseInt(line[1]) - 1;
        if(line.length==1)
            throw new ArrayIndexOutOfBoundsException (null); 
        boolean flag = line.length > 2;
        if(flag) {
          if(!line[2].equals("P")) 
            throw new NullPointerException (null);
          else
        	setFlag(x, y);}
        else open(x, y);
      }catch(NumberFormatException e){
        System.out.println("Valeurs incorrectes : Vous devez rentrer des chiffres separés d'un espace");
      }
    catch(NullPointerException e){
        System.out.println("Pour inserer un drapeau il faut entrer les coordonnées puis un P");
      }
      catch(ArrayIndexOutOfBoundsException e){
    	 System.out.println(" Vous devez rentrer des coordonées x et y separé d'un espace") ;
        }
      
    }

    print();
    System.out.println(casesLibres == 0 ? "Felicitation vous avez gagne." : "Vous avez perdu.");
    scanner.close();

    //Fin du progamme.
  }

  public static void regle() {
	
	  System.out.println(" Bienvenue dans le demineur de Josias et Kalvin \n Pour jouer il vous faut rentrer des coordonnées x et y"
	  		+ "separés d'un espace \n Pour ajouter une case en tant que drapeau rajouter un espace puis P \n Le jeu ne s'arretera "
	  		+ "automatiquement que lorsque vous aurez perdu ou nous l'esperons gagné ... =) Goog Luck!! \n");
	
}

public static void main(String... args)
  {
    new MineSweeper(5, 5, 10).play();
  }
}