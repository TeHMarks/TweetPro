package it.uniroma1.lcl.tweetpro;

import java.util.Arrays;
import java.util.List;

import it.uniroma1.lcl.tweetpro.interfaces.UniqueUserStrategyInterface;

public class AdvancedLogLog implements UniqueUserStrategyInterface
{
	public static final Double[] ARRAY = { 0.0, 0.44567926005415, 1.2480639342271, 2.8391255240079, 6.0165231584809, 12.369319965552, 25.073991603111, 50.482891762408, 101.30047482584, 202.93553338100, 406.20559696699, 812.74569744189, 1625.8258850594, 3251.9862536323, 6504.3069874480, 13008.948453415, 26018.231384516, 52036.797246302, 104073.92896967, 208148.19241629, 416296.71930949, 832593.77309585, 1665187.8806686, 3330376.0958140, 6660752.5261049, 13321505.386687, 26643011.107850, 53286022.550177, 106572045.43483, 213144091.20414, 426288182.74275, 852576365.81999 };
	
	@Override
	public int execute(List<Long> idUserList)
	{
		//NUMERO MASSIMO DI STIMATORI k = 8, m = 256
		final int k = 8;
		int m = (int) Math.pow(2, k);

		// M GRANDE, ARRAY DI GRANDEZZA m.
		int[] estimators = new int[m];

//		int total = 0;
		for(long userId : idUserList)
		{
			// NUMERO GENERATO RANDOM TRA 0 E m (escluso)
			int i = (int) (Math.random() * m);
			// POSIZIONE DEL PRIMO 1 MENO SIGNIFICATIVO A PARTIRE DA DESTRA
			int b = Long.numberOfTrailingZeros(userId) + 1;
			// ALLA POSIZIONE i-esima VADO A METTERE IL MAX TRA IL VECCHIO VALORE E b
			estimators[i] = Math.max(estimators[i], b);
		}
		// SOMMA DI TUTTI I VALORI ALL'INTERNO DELL'ARRAY
//		for(int value : estimators) { total += value; }
		// ESPONENTE DELLA FORMULA (MEDIA)
//		double average = (1.0/(double) m) * total;
		double media = Arrays.stream(estimators).summaryStatistics().getAverage();
		return (int) (ARRAY[k] * Math.pow(2, media));
	}
}
