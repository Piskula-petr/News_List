package cz.news_list;

public enum Topic {

	
	/*
	 * Domácí
	 */
	HOME {
		
		@Override
		public String toString() {
			return "Domácí";
		}
	},
	
	
	/**
	 * Zahraniční
	 */
	FOREIGN {
		
		@Override
		public String toString() {
			return "Zahraniční";
		}
	},
	
	
	/**
	 * Ekonomika
	 */
	ECONOMY {
		
		@Override
		public String toString() {
			return "Ekonomika";
		}
	},
	
	
	/**
	 * Krimi
	 */
	KRIMI {
		
		@Override
		public String toString() {
			return "Krimi";
		}
	},
	
	
	/**
	 * Počasí
	 */
	WEATHER {
		
		@Override
		public String toString() {
			return "Počasí";
		}
	}
	
}
