package hmysjiang.usefulstuffs.items.variants;

import net.minecraft.util.IStringSerializable;

public enum EnumBulletVariants implements IStringSerializable {
	SHELL(0, "shell"),
	NORMAL(1, "normal");
	
	private int ID;
	private String name;
	
	private EnumBulletVariants(int id, String name) {
		this.ID = id;
		this.name = name;
	}
	
	public int getID() {
		return this.ID;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
