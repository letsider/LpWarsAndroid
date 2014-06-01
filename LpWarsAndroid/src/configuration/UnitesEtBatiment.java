package configuration;

/**
 * Cette class permet de stocker les différentes initilialisations possible de GC
 * 
 @see Names.Unites
 *
 */
public class UnitesEtBatiment {
	
	public static class Batiment {
		
		public static class CASERNE {

			public static final Integer PV = 1000;
		}

		public static class USINE_DE_CHAR {

			public static final Integer PV = 5000;
		}
	}
	
	public static class Unites{
	
		public static class Infanterie {
			
			public static final Integer PV = Integer.valueOf(1);

			public static final Integer PA = Integer.valueOf(1);

			public static final Integer PM = Integer.valueOf(2);

		}
		
		public static class Vehicule {
			
			public static final Integer PV = Integer.valueOf(100);

			public static final Integer PA = Integer.valueOf(40);

			public static final Integer PM = Integer.valueOf(12);
			
		}
	}
}
