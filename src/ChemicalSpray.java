public class ChemicalSpray {
	private double chemicalTank, chemicalLossRate, chemicalFillRate;
	private boolean fillingChemTank;
	
	public ChemicalSpray(WindowCleaner winCleaner) {
		chemicalTank = 1;
		chemicalLossRate = 0.001;
		chemicalFillRate = 0.002;
	}
	
	public void checkChemicals(WindowCleaner winCleaner) {
		if (winCleaner.getCurrentCleaning())
			chemicalTank -= chemicalLossRate;
		
		if (chemicalTank < 0.2) {
			fillingChemTank = true;
		}
		
		if (fillingChemTank) {
			chemicalTank +=chemicalFillRate;
			
			if (chemicalTank > 0.7) {
				fillingChemTank = false;
			}
		}
	}
	
	public double getChemicalTank() {
		return chemicalTank;
	}
	
	public boolean getFillingChemTank() {
		return fillingChemTank;
	}
	
	public double getChemicalLossRate() {
		return chemicalLossRate;
	}
	
	public double getChemicalFillRate() {
		return chemicalFillRate;
	}
}
