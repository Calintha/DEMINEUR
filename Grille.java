
import java.util.Random;
import java.util.Scanner;

public class Grille 
{

	
	
	  private  Cell[][] cases;
	  private  final static   int colonne = 5;
	  private final static int ligne = 5;
	  private   int casesLibres;
	
	  public static int getLigne() {
		return ligne;
	}
	  
	  public static int getColonne() {
		return colonne;
	}
	

	public int getCasesLibres() {
		return casesLibres;
	}

	// j'ai mis les lignes et colonnes en static pour tester notamment le pourcentage
	  public Grille( int pourcent)
	  {
	    cases = new Cell[colonne][ligne];
		
		for(int x = 0; x<colonne; x++)
			for(int y=0; y<ligne; y++)
				cases[x][y] = new Cell(x,y,false,false,false);
	    generate(pourcent < 12 ? 12 : pourcent > 90 ? 90 : pourcent, new Random());
	  
	  }
	
	  private void generate(int bombs, Random random)
	  {
		
	    bombs = (int) colonne * ligne * bombs / 100;
	    casesLibres = colonne* ligne - bombs;
	    
	
	    while(bombs != 0)
	    {
	   
	      int randomx = random.nextInt(colonne);
	      int randomy = random.nextInt(ligne);
	      
	     
	      if(cases[randomx][randomy].isMine()) continue;
	   //  System.out.println("Nb de bombes restantes"+bombs);
	      cases[randomx][randomy].setMine(true);
	     // System.out.println(" cette case :"+ randomx +" "+ randomy + cases[randomx][randomy].isMine());
		  for(int x1 = -1; x1 < 2; x1++)
	      {
			
	        for(int y1 = -1; y1 < 2; y1++)
	        {
	        	
	        	//System.out.println("valide ? :"+ Cell.isValid(randomx+x1,randomy+y1));
	          if(Cell.isValid(randomx+x1,randomy+y1) && (!cases[randomx+x1][randomy+y1].isMine())) 
	          {
	              cases[randomx+x1][randomy+y1].setValeur(1);
	            //  System.out.println("valeur case "+ (randomx+x1) + " "+ (randomy+y1) + " = "+ cases[randomx+x1][randomy+y1].getValeur()) ;
	          }
	        }
	      } 
		  bombs--; 
	   }
	}
	
	  public   void Affiche()
	  {		  
	    StringBuilder chaineformate = new StringBuilder();

	    for(int y = -1; y < ligne; y++)
	    {
	      chaineformate.append("[").append(y == -1 ? " " : y).append("]");
	      for(int x = 0; x < colonne ; x++)
	      {
	        chaineformate.append("[\t").append(y==-1 ? x
	          : cases[x][y].isDrapeau() ? "P"
	          : cases[x][y].isDecouverte() ? cases[x][y].isMine() ? "+"
	          : cases[x][y].getValeur()==0  ? " "
	          : cases[x][y].getValeur()
	          : "?" ).append("\t]");
	      }
	      chaineformate.append("\n");
	    }
	    System.out.println(chaineformate.toString());
	  }
	  
	  public void open(int x, int y)
	  {

	    if(!cases[x][y].isDrapeau() && cases[x][y].isMine())
	    {
	      cases[x][y].setDecouverte(true);
	      casesLibres = -1;
	      for(int xx = 0; xx < colonne; xx++)
	      {
	        for(int yy = 0; yy < ligne; yy++)
	        {
	          if(cases[xx][yy].isMine()) cases[xx][yy].setDecouverte(true);
	        }
	      }
	    }
	    else deblaye(x, y);
	  }
	  
	  private void deblaye(int x, int y)
	  {
		  
	    if(!Cell.isValid(x, y) || cases[x][y].isDecouverte() || cases[x][y].isDrapeau()) return;
	    else {  cases[x][y].setDecouverte(true);
	    casesLibres--;}
	    
	    if(cases[x][y].getValeur() == 0) {
	    	 deblaye(x-1, y);
	    	 deblaye(x-1, y+1);
	    	 deblaye(x+1,y-1);
	    	 deblaye(x+1, y);
	    	 deblaye(x+1, y+1);
	    	 deblaye(x, y-1);
	    	 deblaye(x, y+1);
	         deblaye(x-1, y-1);}
	    else return;
	  }
	  
	  public void play() 
	  {
		
		  Scanner scanner = new Scanner(System.in);
		  	
		    while(casesLibres > 0)
		    {
		    System.out.println("nb cases libres "+ casesLibres);
		   
		    // boucle de jeu
		      Affiche();
		      System.out.print("Veuillez entrer vos coordonnees x et y");
		      String[] line = scanner.nextLine().split(" ");
		      
		      try{
		        int x = Integer.parseInt(line[0]) -1 ;
		        int y = Integer.parseInt(line[1]) -1 ;
		        
		     //   if(cases[x][y].getValeur()==-1) { System.out.println("AIIEE ....  c'est une bombe !"); casesLibres=-1 ;}
		        if( line.length > 2 )
		         cases[x][y].setDrapeau() ;
		        else  open(x, y);
		      }catch(Exception e){
		        System.out.println("Valeur non comprise.");
		      }
		    }

		    Affiche();
		    System.out.println(casesLibres == 0 ? "Felicitation vous avez gagne." : "Vous avez perdu.");
		   
		    scanner.close();
	     
	    }

	public void initDrapeau(int x, int y) {
		
		 cases[x][y].setDrapeau() ;
		
	} 
}
