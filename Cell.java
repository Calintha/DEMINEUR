public class Cell   {
	

	private int x;
	private int y;
	private boolean mine=false, decouverte, drapeau;
	int valeur ;
	boolean bombouverte= false ;
	

	 public Cell(int x, int y,boolean mine, boolean decouverte,boolean drapeau) {
		 
		
		this.x = x ;
		this.y = y ;
		this.mine =mine; 
		this.decouverte=decouverte; 
		valeur = 0 ;
		this.drapeau=drapeau;
		
	}
	 
	
		
		
		// méthodes (mutateurs et assesseurs)
		public boolean isMine()		{  return mine; }
		public void bombOuverte(boolean etat)		{ bombouverte= etat ;  }
		public boolean isDecouverte() 	{ return decouverte; }
		public boolean isDrapeau()		{ return drapeau; }
		public int getValeur()			{ return valeur; }
		public void setMine(boolean b)		{ mine = b; valeur = -1; }
		public void setDecouverte(boolean d){ decouverte = d; }
		public void setDrapeau()
		{
			if (decouverte==true) return;
			drapeau = !drapeau;

		 }
		
		public void setValeur(int v)		{ valeur += v;}
		
		

	public static boolean isValid(int x, int y) {
			if (  (x >= 0 && y >=0) && ( x < Grille.getColonne() ) && ( y < Grille.getLigne() ) ) 
			return true;
			else return false;
			
		}
		



}
