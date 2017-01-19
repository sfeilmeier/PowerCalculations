package test;

class Power {
	public ActivePower active;
	public ReactivePower reactive;

	public Power(ActivePower active, ReactivePower reactive) {
		this.active = active;
		this.reactive = reactive;
	}
}

class PowerUtils {
	/*
	 * Power
	 */
	public static Power getPower(ApparentPower s, PowerFactor phi) {
		double p = phi.value() * s.value();
		double q = Math.sin(Math.acos(phi.value())) * s.value();
		return new Power(new ActivePower(p), new ReactivePower(q));
	}
	
	/*
	 * Active
	 */

	/*
	 * Reactive
	 */

	public static ReactivePower getReactive(ActivePower p, ApparentPower s) {
		double q = Math.sqrt(Math.pow(s.value(), 2) - Math.pow(p.value(), 2));
		return new ReactivePower(q);
	}

	/*
	 * Apparent
	 */
	public static ApparentPower getApparent(ActivePower p, PowerFactor phi) {
		double s = p.value() / phi.value();
		return new ApparentPower(Math.abs(s));
	}

	public static ApparentPower getApparent(ActivePower p, ReactivePower q) {
		double s = Math.sqrt(Math.pow(p.value(), 2) + Math.pow(q.value(), 2));
		return new ApparentPower(s);
	}

	/*
	 * Factor
	 */

	public static PowerFactor getFactor(ActivePower p, ApparentPower s) {
		double phi = p.value() / s.value();
		return new PowerFactor(phi);
	}

}

class AbstractPower {
	private double value;

	public AbstractPower(double value) {
		this.value = value;
	}

	public double value() {
		return value;
	}
	
	public AbstractPower add(double value) {
		this.value += value;
		return this;
	}
	
	public void set(double value) {
		this.value = value;
	}
}

class ActivePower extends AbstractPower {
	public ActivePower(double value) {
		super(value);
	}

	@Override
	public String toString() {
		return value() + " W";
	}
}

class ReactivePower extends AbstractPower {
	public ReactivePower(double value) {
		super(value);
	}

	@Override
	public String toString() {
		return value() + " var";
	}
}

class ApparentPower extends AbstractPower {
	public ApparentPower(double value) {
		super(value);
	}

	@Override
	public String toString() {
		return value() + " VA";
	}
}

class PowerFactor extends AbstractPower {
	public PowerFactor(double value) {
		super(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value());
	}
}
