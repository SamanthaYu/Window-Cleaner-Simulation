public class WaterTank {
	private double cleanWaterTank, waterLossRate, waterFillRate, waterPumpRate;
	private boolean fillingWaterTank;
	
	public WaterTank(WindowCleaner winCleaner) {
		cleanWaterTank = 30;
		waterLossRate = 0.02;
		waterFillRate = 0.04;
		waterPumpRate = 0.006;
	}
	
	public void checkWater(WindowCleaner winCleaner) {
		if (winCleaner.getCurrentCleaning())
			cleanWaterTank -= waterLossRate;
		
		if (cleanWaterTank < 10) {
			fillingWaterTank = true;
		}
		
		if (fillingWaterTank) {
			cleanWaterTank += waterFillRate;
			
			if (cleanWaterTank > 20) {
				fillingWaterTank = false;
			}
		}
	}
	
	public double getCleanWaterTank() {
		return cleanWaterTank;
	}
	
	public boolean getFillingWaterTank() {
		return fillingWaterTank;
	}
	
	public double getWaterLossRate() {
		return waterLossRate;
	}
	
	public double getWaterFillRate() {
		return waterFillRate;
	}
	
	public double getWaterPumpRate() {
		return waterPumpRate;
	}
}
