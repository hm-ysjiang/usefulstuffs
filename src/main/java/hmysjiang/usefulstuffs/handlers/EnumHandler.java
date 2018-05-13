<<<<<<< HEAD
package hmysjiang.usefulstuffs.handlers;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {
	
	public static enum ExcaliburType implements IStringSerializable{
		UNCHARGED("uncharged", 0),
		CHARGED("charged", 1);
		
		private int ID;
		private String name;
		
		private ExcaliburType(String name, int ID){
			this.name = name;
			this.ID = ID;
		}

		@Override
		public String getName() {
			return this.name;
		}
		
		public int getID() {
			return this.ID;
		}
		
		@Override
		public String toString() {
			return this.getName();
		}
		
	}
	
}
=======
package hmysjiang.usefulstuffs.handlers;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {
	
	public static enum ExcaliburType implements IStringSerializable{
		UNCHARGED("uncharged", 0),
		CHARGED("charged", 1);
		
		private int ID;
		private String name;
		
		private ExcaliburType(String name, int ID){
			this.name = name;
			this.ID = ID;
		}

		@Override
		public String getName() {
			return this.name;
		}
		
		public int getID() {
			return this.ID;
		}
		
		@Override
		public String toString() {
			return this.getName();
		}
		
	}
	
}
>>>>>>> Git init
