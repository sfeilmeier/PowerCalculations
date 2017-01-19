package test;

public class App {
	
	private static int MAX_ITERATIONS = 100;
	private static ApparentPower MAX_APPARENT = new ApparentPower(100000);
	
	public static void main(String[] args) {
		ActivePower meterP = new ActivePower(45000);
		ReactivePower meterQ = new ReactivePower(-20000);
		
		PowerFactor phi = new PowerFactor(0.95);
		
		ApparentPower meterS = PowerUtils.getApparent(meterP, meterQ);
		System.out.println(meterS);
		
		Power pwr = PowerUtils.getPower(MAX_APPARENT, phi);
		System.out.println(pwr.active);
		System.out.println(pwr.reactive);
		
		System.out.println(pwr.active.value() - meterP.value());
		System.out.println(pwr.reactive.value() - meterQ.value());
		
		
/*		ActivePower meterP = new ActivePower(-110000);
		
		PowerFactor phi = new PowerFactor(0.9);
		
		getOptimum(meterP, phi, MAX_APPARENT);*/		

	}
	
	private static Power getOptimum(ActivePower meterP, PowerFactor phi, ApparentPower sMax) {
		ActivePower p = new ActivePower(meterP.value() * -1);
		
		/*
		 * Find...
		 */
		for(int i=0; i<MAX_ITERATIONS; i++) {
			System.out.println(">> iter " + i);
			System.out.println("P: " + p);

			// erste Randbedingung: sqrt(p²+q²)
			ApparentPower s = PowerUtils.getApparent(p, phi);
			double s_error = s.value() - sMax.value();
			if(s_error <= 0) {
				// Bedingung erfüllt
				System.out.println("S=" + s + "; Erfüllt! Fehler: " + s_error);
				if (s_error > -1) {
					// ist konvergiert -> fertig
					System.out.println("fertig");
					break;
				} else {
					p.set(p.value() - s_error);
				}
				
			} else { 
				// nicht erfüllt
				System.out.println("S=" + s + "; Nicht erfüllt! Fehler: " + s_error);
				p.set(p.value() - s_error);
			}
		}
		return new Power(p, new ReactivePower(0));
	}
}
