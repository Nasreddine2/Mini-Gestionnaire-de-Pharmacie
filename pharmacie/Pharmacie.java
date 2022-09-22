class Client {
 //ATTRIBUTS
 private String 	nom;
 private String 	prenom;
 private String 	dateN;
 private boolean 	assure;
 private String		NSS;
 //CONSTRUCTEUR
 Client (String name, String firstname, String date, boolean assurance, String NSS){
	nom=name;prenom=firstname;dateN=date;assure=assurance;
	if (assure==true)
	this.NSS=NSS;
	else System.out.println("client(e) non assure(e)");
 }
 //METHODES
 public boolean getassurance(){return assure;}
 public String  getname(){return nom;}
 public String  getfirstname(){return prenom;}
}
class Medicament {
 //ATTRIBUTS 
 private String 	nom;
 private int 		nb;
 private double 	pua;
 private double 	marge;
 private boolean 	remb;
 private Medicament suivant;
 //CONSTRUCTEURS
 public Medicament (String name, int nbr, double price, double marge, boolean remboursable){
	nom=new String(name);
	this.nb=nbr;
	pua=price;
	this.marge=marge;
	remb=remboursable;
	suivant=null;
 }
 public Medicament (Medicament Medoc){
	 this.nom=new String(Medoc.nom);
	 this.nb=Medoc.nb;
	 this.pua=Medoc.pua;
	 this.marge=Medoc.marge;
	 this.remb=Medoc.remb;
	 this.suivant=Medoc.suivant;
 }
 public Medicament (){}
 //METHODES
	//1//GETTERS
	public String 	  getname()   
	{return nom;}
	public int 		  get_nb()	 
	{return nb;}
	public double 	  get_pua()   
	{return pua;}
	public double 	  get_marge() 
	{return marge;}
	public boolean 	  get_remb()  
	{return remb;}
	public Medicament get_suivant()  
	{return suivant;}
	//2//SETTERS
	public void	set_nb(int n)
	{nb=n;}
	public void set_pua(double p)
	{pua=p;}
	public void set_suivant(Medicament s)
	{suivant=s;}
	
}
class ListeMedicament {
	//attributs
	private char caractere;
	private Medicament ListeMed;
	//constructeur
	public ListeMedicament (char car){
		caractere=car;
		ListeMed=null;
	}
	public ListeMedicament (char car,Medicament l){
		caractere=car;
		ListeMed=l;
	}
	//1//GETTERS
	public char getc(){return caractere;}
	public Medicament GetListeMedicament() {
		return ListeMed;
	}
	//2//SETTERS
	public void SetListeMed(Medicament Medoc){
		ListeMed=new Medicament(Medoc);
	}
	public void SetCar(char car){caractere=car;}
}
class Stock {
 //ATTRIBUTS
 private ListeMedicament[] Tableau= new ListeMedicament[26];
 //CONSTRUCTEUR
 public Stock(){
	for (int i = 0; i < 26; i++) {Tableau[i]= new ListeMedicament((char)(65 + i));}
	}

 /* Écrire la méthode rechecher(..) permettant de rechercher un médicament par son 
nom dans le stock (accès direct dans l’index, puis séquentiel dans la liste)
*/

 public boolean rechercher(String chaine){
	int index=(int)(chaine.charAt(0))-65;
	if (Tableau[index].GetListeMedicament()==null){
	 System.out.println("\nCe medicament n'est pas en stock");
	 return false;
	}
	else {
	 Medicament Medi=Tableau[index].GetListeMedicament();
	 while (Medi.get_suivant()!=null&&Medi.getname().compareToIgnoreCase(chaine)!=0) {
		 Medi=Medi.get_suivant();
	 }
	 if (Medi.getname().compareToIgnoreCase(chaine)==0){
		 System.out.println("\nCe medicament est disponible en stock");
		 return true;
	 }
	 else {
		 System.out.println("\nCe medicament n'est pas en stock");
		 return false;
	 }	
	}
}
	
/*Écrire la méthode achat(..) permettant au pharmacien d’alimenter son stock du 
médicament med (reconnu par son nom) ayant le prix d’achat pa par une certaine 
quantité qte. On considère qu’un médicament prend toujours le nouveau prix. Si le 
médicament n’existe pas, il faut l’insérer dans la bonne position.*/

 public void achat (String MedName, double prix, int quantite, boolean remboursement, double marge){
	Medicament Medoc=new Medicament(MedName, 0,(prix/quantite), marge, remboursement);
	char chaine=Medoc.getname().charAt(0);
	int index=((int)chaine)-65;
	if (Tableau[index].GetListeMedicament()==null){
		Medoc.set_pua(prix/quantite);
		Medoc.set_nb(quantite);
		Tableau[index].SetListeMed(Medoc);}
	else {
		Medicament Medi=Tableau[index].GetListeMedicament();
		Medicament PrecedantMed=Medi;
		while (Medi.get_suivant()!=null&&Medi.getname().compareToIgnoreCase(Medoc.getname())<0){
		    PrecedantMed=Medi;
			Medi=Medi.get_suivant();
		}
		
		 if (Medi.getname().compareToIgnoreCase(Medoc.getname())==0){
				Medoc.set_nb(Medi.get_nb
		()+quantite);
				Medoc.set_suivant(Medi.get_suivant());
				PrecedantMed.set_suivant(Medoc);
		 }
		 else if (Medi.get_suivant()==null){
				Medoc.set_nb(quantite);
				Medi.set_suivant(Medoc);
		 }
		 else if (Medi.getname().compareToIgnoreCase(Medoc.getname())<0){;
				Medoc.set_nb(quantite);
				Medoc.set_suivant(Medi);
				PrecedantMed.set_suivant(Medoc);
		 }
	}	
	
 }


/*Écrire la méthode vente(..) permettant au pharmacien de vendre un certain nombre 
nb de boite d’un médicament med à un client cl et de retourner le montant à payer 
par le client. La vente n’est possible que si le nombre de boite est disponible dans le 
stock. La vente impacte le stock du médicament en question. Si le nombre de boites 
du médicament vendu devient 0 alors le médicament doit être supprimé.
*/

 public double vente(String Medi, int nb, Client cl){
	if (rechercher(Medi)){
	 int index=(int)(Medi.charAt(0))-65;
	 Medicament temporaire=Tableau[index].GetListeMedicament();
	 Medicament pretmp=temporaire;
	 while (Medi.compareToIgnoreCase(temporaire.getname())!=0) {
	 	 pretmp=temporaire;
		 temporaire=temporaire.get_suivant();}
	 //Maintenant que nous avons trouver le medicament on le vend et apporte les modif necessaires 
	 if (nb<=temporaire.get_nb()){
		 temporaire.set_nb(temporaire.get_nb()-nb); 
		 if (temporaire.get_nb()==0){
			 System.out.println("C etait la derniere boite");
			 pretmp.set_suivant(temporaire.get_suivant());}
		 else {pretmp=temporaire;}
		 if (!cl.getassurance()){
			 return (nb*((pretmp.get_pua())+(pretmp.get_pua()*pretmp.get_marge())/100));}
		 else if (pretmp.get_remb()!=true){
			 
			 return (nb*((pretmp.get_pua())+(pretmp.get_pua()*pretmp.get_marge())/100));}
		 else {
			 return ((nb*((pretmp.get_pua())+(pretmp.get_pua()*pretmp.get_marge())/100))*0.2);}
	 }
	 //bien sur si tte fois il y a la quantite suffisante
	 else {System.out.println("achat impossible quantite insuffisante en stock"); return -1;}
	}
	//apres le if on a le else (le cas ou il n'y pas le medoc en stock
	 else  return -1;
 }

/*Ecrire une méthode valStock(..) qui calcule et renvoie le montant total valorisant (au 
prix d’achat) les stock de médicament existant. */

 public double ValStock(){
	 double Somme=0;
	 Medicament temporaire;
	 for (int i=0; i<26; i++){
		 if (Tableau[i].GetListeMedicament()!=null){
			 temporaire=Tableau[i].GetListeMedicament();
			 Somme+=(temporaire.get_pua()*temporaire.get_nb());
			 temporaire=temporaire.get_suivant();
			 while (temporaire!=null){
				 Somme+=(temporaire.get_pua()*temporaire.get_nb());
				 temporaire=temporaire.get_suivant();
			 }
		 }
	 }
	 return Somme;
 }

}

/* Créer une classe Pharmacie avec une méthode main, dans laquelle, on créer un 
nouveau stock, puis on l’alimente avec quinze (15) médicaments (des achats) et trois 
(03) clients.*/

public class Pharmacie {
 public static void main (String[] arges) {
	 
	System.out.println("\n\nHAMMOUDI NASREDDINE GROUPE 1\n\n");
	
	Stock StockMedoc=new Stock();
	 
	 StockMedoc.achat("TIORFAN",5783.45, 112, true, 10.3);
	 StockMedoc.achat("PYOSTACINE",1662.05, 2, true, 12.5);//Rupture Prochaine
	 StockMedoc.achat("OGAST",5320.45, 142, true, 15.3);
	 StockMedoc.achat("ZALDIAR",14640.45, 423, true, 10.0);
	 StockMedoc.achat("BETADINE",620.69, 177, true, 11.02);
	 StockMedoc.achat("LEXOMIL",3450.78, 113, false, 9.3);
	 StockMedoc.achat("XYZALL",7642.45, 3, false, 20.0);//Non remboursable
	 StockMedoc.achat("ATARAX",7432.48, 612, true, 11.7);
	 StockMedoc.achat("TANAKAN",5225.45, 222, false, 10.5);
	 StockMedoc.achat("CELESTENE",6522.45, 332, true, 13.6);
	 StockMedoc.achat("PNEUMOREL",6345.45, 172, false, 9.9);
	 StockMedoc.achat("GINKOR",6543.74, 2, true, 12.1);
	 StockMedoc.achat("EFFEXOR",5321.89, 312, true, 12.0);
	 StockMedoc.achat("TEMESTA",4662.47, 14, true, 13.7);
	 StockMedoc.achat("INIPOMP",4326.45, 112, true, 10.3);
	 
	 Client client1=new Client("HAMMOUDI","NASREDDINE", "27.12.2002", true, "2020310248124");
	 Client client2=new Client("MESSI","LEONEL", "24.06.1987", true, "1234567890567");
	 Client client3=new Client("CRISTIANO","RONALDO", "05.02.1985", false, "6300035710047");
	 
    //Achat d’un médicament existant dans le stock.
	 StockMedoc.achat("TIORFAN",3640.74, 114, true, 11.2);

	 System.out.println("\nVente dun medicament remboursable a un client assure:");

	 double v1=StockMedoc.vente("EFFEXOR",4,client1);
	 System.out.println("\n"+client1.getname()+" "+client1.getfirstname()+" doit payer :"+v1+" DA");
	 
	 System.out.println("\nVente dun medicament remboursable à un client assuré:");

	 double v2=StockMedoc.vente("ATARAX",1,client3);
	 System.out.println("\n"+client3.getname()+" "+client3.getfirstname()+" doit payer :"+v2+" DA");
	 
	 System.out.println("\nVente dun medicament non remboursable à un client assure:");

	 double v3=StockMedoc.vente("XYZALL",2,client2);
	 System.out.println("\n"+client2.getname()+" "+client2.getfirstname()+" doit payer :"+v3+" DA");
	 
	 System.out.println("\nVente dun medicament avec un stock limite (0 boites après la vente)");

	 double v4=StockMedoc.vente("PYOSTACINE",2,client2);
	 System.out.println("\n"+client2.getname()+" "+client2.getfirstname()+" doit payer :"+v4+" DA");
	


   }
   
   }