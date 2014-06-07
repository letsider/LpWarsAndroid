package configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette class permet de stocker les différentes initilialisations possible de GC
 * 
 @see Names.Unites
 *
 */
public class UnitesEtBatiment {
	
	public static class Batiment {
		
		public static final int ID = 1;
		
		public static class CASERNE {

			public static final int ID = 0;
			
			public static final List<Integer> unitesDisponible(){

				List<Integer> res = new ArrayList<Integer>();
				
				res.add(Integer.valueOf(Unites.Infanterie.ID));
				
				return res;
				
			};
		}

		public static class USINE_DE_CHAR {

			public static final int ID = 1;
			
			public static final List<Integer> unitesDisponible(){

				List<Integer> res = new ArrayList<Integer>();
				
				res.add(Integer.valueOf(Unites.Vehicule.ID));

				return res;
				
			};
		}
	}
	
	public static class Unites{
		
		public static final int ID = 0;
	
		public static class Infanterie {
			
			public static final int ID = 1;
			
			public static final Integer PV = Integer.valueOf(1);

			public static final Integer PA = Integer.valueOf(1);

			public static final Integer PM = Integer.valueOf(2);

		}
		
		public static class Vehicule {
			
			public static final int ID = 2;
			
			public static final Integer PV = Integer.valueOf(100);

			public static final Integer PA = Integer.valueOf(40);

			public static final Integer PM = Integer.valueOf(12);
			
		}
	}
}
